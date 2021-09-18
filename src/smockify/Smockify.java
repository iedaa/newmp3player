/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smockify;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Santana
 */

public class Smockify extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void startPlayerScene(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.centerOnScreen();
        
        stage.setResizable(false);
        
        stage.setTitle("Smockify");
        
        //startScene2(stage);
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentSplash.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.centerOnScreen();
        
        stage.setResizable(false);
        
        stage.setTitle("Smockify");
        
        PauseTransition wait = new PauseTransition(Duration.seconds(3.3));
        
        wait.setOnFinished(e -> {
            try {
                startPlayerScene(stage);
            } catch (Exception ex) {
                Logger.getLogger(Smockify.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            wait.stop();
        });
        
        wait.play();
        
    }

}
