package com.terumi.isobe.Cerebro.rn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ejml.simple.SimpleMatrix;
import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;

import com.terumi.isobe.Cerebro.api.SignalApi;
import com.terumi.isobe.Cerebro.dao.ReconstructionDao;
import com.terumi.isobe.Cerebro.model.UltrasoundImage;

import io.swagger.models.Response;

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
		//test();
		FloatMatrix model = loadModelFile();
		FloatMatrix signal = convertSignalToMatrix(signalApi);
		reconstructSignal(model, signal);
		
	}
	
	/**
	 * Method called by the client to reconstruct a signal to an ultrasound image.
	 */
	public void reconstructSignal(FloatMatrix model, FloatMatrix signal) {
		UltrasoundImage imageInfo = new UltrasoundImage();
		FloatMatrix image = CGNEAlgorithm(model, signal);
	}
	
	/**
	 * Executes CGNE algorithm.
	 */
	// H[50816x3600] g[50816x1] f[3600x1] r[50816x1] p[3600x1] alfa beta
	public FloatMatrix CGNEAlgorithm(FloatMatrix model, FloatMatrix signal) {
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
		r = g.sub(H.mmul(f));
		p = H.transpose().mmul(r);
		
		int iteractions = 0;
		boolean condition = true;
		while(condition) {
			
			alfa = ((r.transpose().mmul(r)).div(p.transpose().mmul(p))).get(0, 0);
			f.addi(p.mul(alfa));
			nextR = r.sub((H.mmul(p)).mul(alfa));
			beta = ((nextR.transpose().mmul(nextR)).div(r.transpose().mmul(r))).get(0,0);
			p = H.transpose().mmul(nextR).add(p.mul(beta));
			condition = !stopCondition(r, nextR);
			r = nextR;
			
			iteractions += 1;
		}
		
		return f;
	}
	
	
	public Boolean stopCondition(FloatMatrix residueBefore, FloatMatrix residueAfter) {
		Float residueBeforeNorm = Math.abs(residueBefore.norm2());
		Float residueAfterNorm = Math.abs(residueAfter.norm2());
		Float limit = (float) 0.000271828; 
		
		if(Math.abs(residueBeforeNorm - residueAfterNorm) <= limit) {
			System.out.println("**STOP = " + (residueBeforeNorm - residueAfterNorm));
			return true;
		}
		
		System.out.println("**Executed algorithms with residue = " + (residueBeforeNorm - residueAfterNorm));
		return false;
	}

	/**
	 * Loads model file H, which contains the matrix model, and converts it to a matrix.
	 */
	// implements possibility to select another model file
	public FloatMatrix loadModelFile() {
		try {
			BufferedReader abc = new BufferedReader(new FileReader("/home/terumi/development/workspace/cerebro/server/src/main/resources/H-1.txt"));
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
	
	public FloatMatrix convertFileToMatrix(List<String> rawArray) {
		try {
			FloatMatrix model = new FloatMatrix(50816, 3600);

			for(int i = 0; i < rawArray.size(); i++) {
				String[] elementsSingleLine = rawArray.get(i).split(",");
				//DoubleMatrix rowOfModel = new DoubleMatrix(3600, 1);
				
				String[] element;
				Double number;
				Integer power;
				for(int j = 0; j < elementsSingleLine.length; j++) {
					if(elementsSingleLine[j].contains("e")) {
						element = elementsSingleLine[j].split("e");
						number = Double.parseDouble(element[0]);
						power = new Integer(element[1]);
	 					//rowOfModel.put(j, number*(Math.pow(Math.E, (double) power)));
						model.put(i, j, (float) (number*(Math.pow(Math.E, (double) power))));
					}
					else {
						//rowOfModel.put(j, Double.parseDouble(elementsSingleLine[j]));
						model.put(i, j, (float) Double.parseDouble(elementsSingleLine[j]));
					}
				}
				//model.putRow(i, rowOfModel);
			}
						
			return model;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public FloatMatrix convertSignalToMatrix(SignalApi signalApi) {
		FloatMatrix g = new FloatMatrix(50816, 1);
		List<BigDecimal> signal = signalApi.getSignal();
		
		for(int i = 0; i < signal.size(); i++) {
			g.put(i, (float) signal.get(i).doubleValue());
		}
		
		return g; 
	}
	
	public void test() {
		FloatMatrix f = new FloatMatrix(2, 3);
		f.put(0, 0, 1);
		f.put(0, 1, 2);
		f.put(0, 2, 3);
		f.put(1, 0, 4);
		f.put(1, 1, 5);
		f.put(1, 2, 6);
		FloatMatrix g = new FloatMatrix(3, 2);
		g.put(0, 0, 5);
		g.put(0, 1, 10);
		g.put(1, 0, 15);
		g.put(1, 1, 20);
		g.put(2, 0, 25);
		g.put(2, 1, 30);
		FloatMatrix h = new FloatMatrix(2, 3);
		h.put(0, 0, 1);
		h.put(0, 1, 2);
		h.put(0, 2, 3);
		h.put(1, 0, 4);
		h.put(1, 1, 5);
		h.put(1, 2, 6);
		Float s = (float) 1;
		
		FloatMatrix x = new FloatMatrix(2, 2);
		//x = g.mmul(f);
		f.add(h);
		g.add(s);
		g.transpose();
		
		System.out.println("nice");
		System.out.println("f: " + f);
		System.out.println("g: " + g);

	}
}
