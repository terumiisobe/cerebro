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

		FloatMatrix alfa;
		FloatMatrix beta;
		FloatMatrix nextR;
		
		// f0, r0 and p0
		f = FloatMatrix.zeros(3600, 1);
		FloatMatrix aux = H.mmul(f);
		r = g.sub(aux);
		p = H.transpose().mmul(r);
		
		int iteractions = 0;
		while(!stopCondition(r)) {
			
			alfa = (r.transpose().mmul(r)).div(p.transpose().mmul(p));
			f = f.add(alfa.mmul(p));
			nextR = r.sub(alfa.mmul(H.mmul(p)));
			beta = (nextR.transpose().mmul(nextR)).div(r.transpose().mmul(r));
			p = H.transpose().mmul(nextR).add(beta.mmul(p));
			r = nextR;

			iteractions += 1;
		}
		
		return f;
	}
	
	public Boolean stopCondition(FloatMatrix residue) {
		Float residueNorm = residue.norm2();
		Float limit = (float) 0.000271828; 
		
		if(residueNorm.compareTo(limit) <= 0) {
			return true;
		}
		
		System.out.println("**Executed algorithms with residue = " + residueNorm.toString());

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
}
