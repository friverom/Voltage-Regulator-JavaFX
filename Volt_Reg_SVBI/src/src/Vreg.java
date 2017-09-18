package src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vreg.common.RD3SerialCom;
import vreg.common.Signal;

/**
 *
 * @author Federico
 */
public class Vreg extends Application {
    
    public static Stage stage;
    public static boolean splashScreen = false;
    public static RD3SerialCom Vreg_Connect = new RD3SerialCom();
    public static Signal readFlag = new Signal();
    public static Signal loggerFlag = new Signal();
      
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        stage=primaryStage;
        //Load first scene into main window
        Parent root = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Regulador Voltaje");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
