package com.terumi.isobe.cerebro.client.rn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.terumi.isobe.cerebro.client.model.SignalApi;

public class ClientRn {
	
	/**
	 * Send an ultrasound signal to reconstruction in server.
	 * 
	 * @param filename
	 * @return
	 */
	public boolean reconstructSignal(Long userId, String filename) throws Exception {
		List<BigDecimal> signal = new ArrayList<BigDecimal>();
		List<BigDecimal> treatedSignal = new ArrayList<BigDecimal>();
		try {
			
			signal = loadLocalFile(filename);
			//treatedSignal = applyGain(signal);
			SignalApi signalApi = convertToApi(userId, signal);
			sendToCerebro(signalApi);
			
			return true;
			
		} catch(Exception e) {
			throw e;
		}

	}
	
	/**
	 * Loads data from file to array in number format.
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public List<BigDecimal> loadLocalFile(String filename) throws Exception {
		try {
			BufferedReader abc = new BufferedReader(new FileReader("/home/terumi/development/workspace/CerebroClient/resources/" + filename + ".txt"));
			List<String> signalString = new ArrayList<String>();
			String s;
			
			while((s = abc.readLine()) != null) 
				signalString.add(s);
			
			abc.close();
			System.out.println("**Signal loaded to array.");
			
			List<BigDecimal> signalDecimal = new ArrayList<BigDecimal>();
			BigDecimal number;
			Integer power;
			
			for(int i = 0; i < signalString.size(); i++) {
				if(signalString.get(i).contains("e")) {
					String[] elements = signalString.get(i).split("e");
					number = new BigDecimal(elements[0]); 
					power = new Integer(elements[1]);
					signalDecimal.add(i, number.multiply(new BigDecimal(Math.exp(power))));
				}
				else {
					signalDecimal.add(i, new BigDecimal(signalString.get(i)));
				}
				//System.out.println("signalString: " + signalString.get(i).toString() + ", signalDecimal: " + signalDecimal.get(i).toString());

			}
			
			System.out.println("**Signal converted to number format.");
			return signalDecimal;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<BigDecimal> applyGain(List<BigDecimal> signal){
		List<BigDecimal> treatedSignal = new ArrayList<BigDecimal>();
		
		int gainUp = 0;
		int gain = 1;
		for(int i = 0; i < signal.size(); i++) {
			if(gainUp == 64) {
				gainUp = 0;
				gain = gain + 1;
			}
			
			treatedSignal.add(signal.get(i).add(new BigDecimal(gain)));
			//System.out.println("gain: " + gain + ", gainUp: " + gainUp + ", signal: " + signal.get(i).toString() + ", treated: " + treatedSignal.get(i).toString());
			gainUp = gainUp + 1;
		}
		
		return treatedSignal;
	}
	
	public SignalApi convertToApi(Long userId, List<BigDecimal> signal) {
		SignalApi signalApi = new SignalApi();
		signalApi.setUserId(userId);
		signalApi.setSignal(signal);
		
		return signalApi;
	}
	
	public void sendToCerebro(SignalApi signalApi) {
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
}
