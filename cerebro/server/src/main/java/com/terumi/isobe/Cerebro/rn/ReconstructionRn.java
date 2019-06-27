package com.terumi.isobe.Cerebro.rn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jblas.FloatMatrix;

import com.terumi.isobe.Cerebro.api.SignalApi;
import com.terumi.isobe.Cerebro.dao.ReconstructionDao;
import com.terumi.isobe.Cerebro.model.UltrasoundImage;

public class ReconstructionRn {
	
	@Inject
	ReconstructionDao reconstructionDao;
	
	/**
	 * Lists all images reconstructs of a given user.
	 */
	public List<UltrasoundImage> listUtrasoundImages(Long userId){
		return reconstructionDao.listUltrasoundImages(userId);
	}
	
	/**
	 * Loads model and signal to server
	 */
	public void loadsServerFiles(SignalApi signalApi) {
		
		FloatMatrix model = loadModelFile();
		FloatMatrix signal = convertSignalToMatrix(signalApi);
		reconstructSignal(model, signal);
		
	}
	
	/**
	 * Method called by the client to reconstruct a signal into an ultrasound image.
	 */
	private void reconstructSignal(FloatMatrix model, FloatMatrix signal) {
		UltrasoundImage imageInfo = new UltrasoundImage();
		
		imageInfo.setReconstructionStartTime(new Date());
		FloatMatrix image = CGNEAlgorithm(model, signal, imageInfo);
		imageInfo.setReconstructionEndTime(new Date());
		imageInfo.setSize(image.length);
		
//		reconstructionDao.saveReconstructedImage(imageInfo, convertMatrixToFloatList(image));
	}
	
	/**
	 * Executes CGNE algorithm.
	 */
	// H[50816x3600] g[50816x1] f[3600x1] r[50816x1] p[3600x1] alfa beta
	private FloatMatrix CGNEAlgorithm(FloatMatrix model, FloatMatrix signal, UltrasoundImage imageInfo) {
		FloatMatrix H = model;
		FloatMatrix g = signal;
		
		FloatMatrix f = new FloatMatrix(3600, 1);
		FloatMatrix r = new FloatMatrix(50816, 1);
		FloatMatrix p = new FloatMatrix(3600, 1);

		Float alfa;
		Float beta;
		FloatMatrix nextR;
		
		// f0, r0 and p0
		f = FloatMatrix.zeros(3600, 1);
		r = g;
		p = H.transpose().mmul(r);

		int iteractions = 0;
		boolean condition = true;
		while(condition) {
			
			alfa = (r.dot(r))/(p.dot(p));
			f.addi(p.mul(alfa));
			
			FloatMatrix aux1 = H.mul(alfa);
			FloatMatrix aux2 = aux1.mmul(p);
			nextR = r.sub(aux2);
			
			beta = (nextR.dot(nextR))/(p.dot(p));
			
			FloatMatrix aux3 = H.transpose().mmul(nextR);
			FloatMatrix aux4 = p.mul(beta);
			p = aux3.add(aux4);
			
			condition = !stopCondition(r, nextR);
			
			r = nextR;
			
			iteractions += 1;
		}
		
		imageInfo.setIterationsPerformed(iteractions);
		return f;
	}
	
	/**
	 * Checks if the difference of the residue is lower than the limit.
	 * 
	 */
	private Boolean stopCondition(FloatMatrix residueBefore, FloatMatrix residueAfter) {
		Float residueBeforeNorm = residueBefore.norm2();
		Float residueAfterNorm = residueAfter.norm2();
		Float limit = (float) 0.0001; 
		
		if(Math.abs(residueAfterNorm - residueBeforeNorm) <= limit) {
			System.out.println("**Algorithm stopped at residue = " + (residueAfterNorm - residueBeforeNorm));
			return true;
		}		
		
		return false;
	}

	/**
	 * Loads model file H, which contains the matrix model, and converts it to a matrix.
	 */
	// implements possibility to select another model file
	private FloatMatrix loadModelFile() {
		try {
			BufferedReader abc = new BufferedReader(new FileReader("/home/terumi/development/workspace/cerebro/cerebro/server/src/main/resources/H-1.txt"));
			List<String> rawArray = new ArrayList<String>();
			String line;
			
			while((line = abc.readLine()) != null) 
				rawArray.add(line);
			
			abc.close();
			System.out.println("**Model loaded to array.");
			
			FloatMatrix model = convertFileToMatrix(rawArray);
			
			System.out.println("**Model converted to number format.");
			
			return model;
			
		}catch(Exception e) {
			System.out.println("Failed to load model file.");
		}
		return null;
	}
	
	private FloatMatrix convertFileToMatrix(List<String> rawArray) {
		try {
			FloatMatrix model = new FloatMatrix(50816, 3600);
			for(int i = 0; i < rawArray.size(); i++) {
				String[] elementsSingleLine = rawArray.get(i).split(",");
				for(int j = 0; j < elementsSingleLine.length; j++) {
					model.put(i, j, Float.parseFloat(elementsSingleLine[j]));
				}
			}
						
			return model;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	private FloatMatrix convertSignalToMatrix(SignalApi signalApi) {
		FloatMatrix g = new FloatMatrix(50816, 1);
		List<Float> signal = signalApi.getSignal();
		
		for(int i = 0; i < signal.size(); i++) {
			g.put(i, signal.get(i));
		}
		
		return g; 
	}
	
	private List<Float> convertMatrixToFloatList(FloatMatrix matrix) {
		List<Float> image = new ArrayList<Float>();
		
		for(int i = 0; i < matrix.length; i++) {
			image.add(i, matrix.get(i));
		}
		
		return image; 
	}

}
