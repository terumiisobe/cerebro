package com.terumi.isobe.cerebro.client.controller;

import java.util.List;

import com.terumi.isobe.cerebro.client.model.ImageNamesApi;
import com.terumi.isobe.cerebro.client.rn.ClientRn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientController {
	
	public ClientRn clientRn = new ClientRn();

	@FXML
    private TextField nameField;

    @FXML
    private TextField signalField;
    
    @FXML
    private TextField imageField;

    @FXML
    private Button reconstuctButton;
    
    @FXML
    private Button imagesButton;
    
    @FXML
    private Button saveButton;

    @FXML
    protected void handleReconstructButtonAction(ActionEvent event) {
    	
        if(nameField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Name field is required!");
            a.show();
            
            return;
        }
        
        if(signalField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Signal field is required!");
            a.show();

            return;
        }
       
    	try {
            clientRn.reconstructSignal(nameField.getText(), signalField.getText());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Confirmation!");
            a.setHeaderText("Your signal was sent to Cerebro!");
            a.show();

    	}catch (Exception e) {
    		System.out.println("Exception in handleReconstructButtonAction");
    	}
    }
    
    @FXML
    protected void handleImagesButtonAction(ActionEvent event) {
    	
        if(nameField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Name field is required!");
            a.show();
            
            return;
        }
        
    	try {
    		List<String> images = clientRn.getAllImages(nameField.getText());
    		Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Images received!");
            if(images == null || images.isEmpty()) {
                a.setHeaderText("There are no images in Cerebro.");
            }
            else {
            	String reference = "";
                for (int i = 0; i < images.size(); i++) {
                	reference = reference + "Image " + images.get(i) + "\n";
                }
                a.setHeaderText("This are your ultrasound images:\n" + reference);
            }
            a.show();

    	}catch (Exception e) {
    		System.out.println("Exception in handleReconstructButtonAction");
    	}
    }
    
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
    	
    	if(nameField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Name field is required!");
            a.show();
            
            return;
        }
    	
    	if(imageField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Image field is required!");
            a.show();
            
            return;
        }
    	
    	try {
    		
    		String reference = clientRn.saveImageLocally(imageField.getText());
    		if(reference == null) {
        		System.out.println("Could not save image.");
        		return;
    		}
    		
    		Alert a = new Alert(Alert.AlertType.CONFIRMATION);
    		a.setTitle("Image saved!");
    		a.setHeaderText("The image with id=" + imageField.getText() + " was saved locally in resources/images as " + reference);
    		a.show();
    		
    	}catch(Exception e) {
    		System.out.println("Exception in handleSaveButtonAction");
    	}
    }
}
