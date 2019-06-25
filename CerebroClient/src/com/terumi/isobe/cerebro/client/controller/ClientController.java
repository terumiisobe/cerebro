package com.terumi.isobe.cerebro.client.controller;

import com.terumi.isobe.cerebro.client.rn.ClientRn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class ClientController {
	
	public ClientRn clientRn;

	@FXML
    private TextField nameField;

    @FXML
    private TextField signalField;

    @FXML
    private Button reconstuctButton;
    
    @FXML
    private Button imagesButton;

    @FXML
    protected void handleReconstructButtonAction(ActionEvent event) {
    	try {
            clientRn.reconstructSignal(nameField.toString(), signalField.toString());

    	}catch (Exception e) {
    		System.out.println("Exception in handleReconstructButtonAction");
    	}
    	
        if(nameField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Name field is required!");
            
            return;
        }
        if(signalField.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Warning!");
            a.setHeaderText("Signal field is required!");
            
            return;
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation!");
        a.setHeaderText("You're signal is being sent to Cerebro.");
    }
    
    @FXML
    protected void handleImagesButtonAction(ActionEvent event) {
    	try {
            

    	}catch (Exception e) {
    		System.out.println("Exception in handleReconstructButtonAction");
    	}
    }
}
