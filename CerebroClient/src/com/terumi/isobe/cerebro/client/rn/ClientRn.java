package com.terumi.isobe.cerebro.client.rn;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.terumi.isobe.cerebro.client.model.ImageNamesApi;
import com.terumi.isobe.cerebro.client.model.SignalApi;
import com.terumi.isobe.cerebro.client.model.UltrasoundImageApi;

public class ClientRn {
	
	
	/**
	 * Saves chosen image locally
	 */
	public String saveImageLocally(String imageId) {
		try {
			
			Client client = Client.create();
			WebResource wt = client.resource("http://localhost:8080/cerebro/reconstruction/image/" + imageId);
			UltrasoundImageApi api= (UltrasoundImageApi) wt.accept("application/json").type("application/json").get(UltrasoundImageApi.class);
			
			System.out.println("**Signal received from Cerebro server.");

			if (api == null || api.getImage() == null || api.getImage().isEmpty()) {
				System.out.println("No images reconstructed!");
				return null;
			}
			
			saveInFile(api.getReference(), api.getImage());
			return api.getReference();
			
		}catch (Exception e) {
			System.out.println("Exception receiving signal to Cerebro: " + e);
		}
		return null;
	}
	
	/**
	 * Get all images reconstructed from server
	 */
	public List<String> getAllImages(String username) {
		try {
			
			Client client = Client.create();
			WebResource wt = client.resource("http://localhost:8080/cerebro/reconstruction/images/" + username);
			ImageNamesApi api= (ImageNamesApi) wt.accept("application/json").type("application/json").get(ImageNamesApi.class);
			
			System.out.println("**Signal received from Cerebro server.");

			if (api == null) {
				System.out.println("No images reconstructed!");
				return null;
			}
			
			return api.getImageNames();
			
		}catch (Exception e) {
			System.out.println("Exception receiving signal to Cerebro: " + e);
		}
		
		return null;	
	}
	
	/**
	 * Send an ultrasound signal to reconstruction in server.
	 * 
	 */
	public boolean reconstructSignal(String username, String filename) throws Exception {
		List<Float> signal = new ArrayList<Float>();
		List<Float> treatedSignal = new ArrayList<Float>();
		try {
			
			signal = loadLocalFile(filename);
			treatedSignal = applyGain(signal);
			SignalApi signalApi = convertToApi(username, signal);
			sendToCerebro(signalApi);
			
			return true;
			
		} catch(Exception e) {
			throw e;
		}

	}
	
	/**
	 * Loads data from file to array in number format.
	 * 
	 */
	private List<Float> loadLocalFile(String filename) throws Exception {
		try {
			BufferedReader abc = new BufferedReader(new FileReader("/home/terumi/development/workspace/cerebro/CerebroClient/resources/" + filename + ".txt"));
			List<String> signalString = new ArrayList<String>();
			String s;
			
			while((s = abc.readLine()) != null) 
				signalString.add(s);
			
			abc.close();
			System.out.println("**Signal loaded to array.");
			
			List<Float> signalDecimal = new ArrayList<Float>();			
			for(int i = 0; i < signalString.size(); i++) {
				signalDecimal.add(i, Float.parseFloat(signalString.get(i)));
				//System.out.println("signalString: " + signalString.get(i).toString() + ", signalDecimal: " + signalDecimal.get(i).toString());
			}
			
			System.out.println("**Signal converted to number format.");
			return signalDecimal;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	private List<Float> applyGain(List<Float> signal){
		List<Float> treatedSignal = new ArrayList<Float>();
		
		int gainUp = 0;
		int gain = 1;
		for(int i = 0; i < signal.size(); i++) {
			if(gainUp == 64) {
				gainUp = 0;
				gain = gain + 1;
			}
			
			treatedSignal.add(signal.get(i) + gain);
			//System.out.println("gain: " + gain + ", gainUp: " + gainUp + ", signal: " + signal.get(i).toString() + ", treated: " + treatedSignal.get(i).toString());
			gainUp = gainUp + 1;
		}
		
		return treatedSignal;
	}
	
	private SignalApi convertToApi(String username, List<Float> signal) {
		SignalApi signalApi = new SignalApi();
		signalApi.setUsername(username);
		signalApi.setSignal(signal);
		
		return signalApi;
	}
	
	private void sendToCerebro(SignalApi signalApi) {
		try {
			
			Client client = Client.create();
			WebResource wr = client.resource("http://localhost:8080/cerebro/reconstruction/reconstruct");
			ClientResponse response = wr.accept("application/json").type("application/json").post(ClientResponse.class, signalApi);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
			
			System.out.println("**Signal sent o Cerebro server.");
						
		}catch (Exception e) {
			System.out.println("Exception sending signal to Cerebro: " + e);
		}		
	}
	
	private void saveInFile(String filename, List<Float> data) {
		BufferedImage bi = new BufferedImage(60, 60, BufferedImage.TYPE_BYTE_GRAY);
		
		for (int i = 0; i < 60; i++) {
			for (int j = 0;j < 60; j++) {
				bi.setRGB(i, j, toRGB(data.get(i*60 + j)));
			}
		}
		try {
			ImageIO.write(bi, "bmp", new File("/home/terumi/development/workspace/cerebro/CerebroClient/resources/images/" + filename + ".bmp"));
			System.out.println("**Image saved to file.");
			
		}catch(Exception e) {
			System.out.println("Exception saving to file: " + e);
		}
	}
	
	private int toRGB(Float value) {
		int part = Math.round(value*255);
		return part * 0x10101;
	}
}
