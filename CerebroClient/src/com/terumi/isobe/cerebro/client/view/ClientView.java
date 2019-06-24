package com.terumi.isobe.cerebro.client.view;

import com.terumi.isobe.cerebro.client.rn.ClientRn;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Cerebro");
        clientRn = new ClientRn();
        Button btn = new Button();
        btn.setText("Send to Cerebro");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event){
                System.out.println("Button click");
                try {
                	clientRn.reconstructSignal(1L, "g-1");
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
