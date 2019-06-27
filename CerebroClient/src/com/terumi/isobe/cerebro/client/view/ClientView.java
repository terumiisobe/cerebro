package com.terumi.isobe.cerebro.client.view;

import com.terumi.isobe.cerebro.client.rn.ClientRn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientView extends Application {
	
	public ClientRn clientRn;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
//    	try {
//        	Parent root = FXMLLoader.load(getClass().getResource("form.xml"));
//        	primaryStage.setTitle("Welcome to Cerebro");
//        	primaryStage.setScene(new Scene(root, 800, 500));
//        	primaryStage.show();
//    	}catch (Exception e) {
//    		throw e;
//    	}
        primaryStage.setTitle("Welcome to Cerebro");
        clientRn = new ClientRn();
        Button btn = new Button();
        
        btn.setText("Send to Cerebro");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event){
                try {
                	clientRn.reconstructSignal("fenix", "g-1");
                }catch(Exception e) {
                	
                }
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
}
