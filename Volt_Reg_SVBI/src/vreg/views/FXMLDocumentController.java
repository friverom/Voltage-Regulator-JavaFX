/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.Vreg;
import vreg.common.Signal;

/**
 *
 * @author Federico
 */
public class FXMLDocumentController implements Initializable {
    
    private Signal flag = Vreg.readFlag;
    
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private SplitPane splitpane;
    @FXML
    private StackPane buttonsPane;
    @FXML
    private StackPane appPane;
    @FXML
    private Button calButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button comButton;

    @FXML
    private Button alarmButton;

    @FXML
    private Button loggerButton;

    @FXML
    private Button pidButton;

    @FXML
    void selectAction(ActionEvent event) throws IOException {
        Parent pane;
        if(event.getSource()==comButton){
            pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLConnect.fxml"));
            setAppPane(pane);
            appPane.setOpacity(1.0);
        }
        else if(event.getSource()==calButton){
            pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLCalibrate.fxml"));
            setAppPane(pane);
            appPane.setOpacity(1.0);
        }
        else if(event.getSource()==alarmButton){
            pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLAlarms.fxml"));
            setAppPane(pane);
            appPane.setOpacity(1.0);
        }
        else if(event.getSource()==pidButton){
            pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLPid.fxml"));
            setAppPane(pane);
            appPane.setOpacity(1.0);
        }
        else if(event.getSource()==loggerButton){
            pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLLogger.fxml"));
            setAppPane(pane);
            appPane.setOpacity(1.0);
        }
        else
            System.exit(0);
        

    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stage stage;
        
        if(Vreg.splashScreen==false){
          //  loadSplashScreen();
        }
        Image logo = new Image("vreg/images/logoADR3.png");
        ImageView image = new ImageView(logo);
        appPane.getChildren().add(image);
        appPane.scaleXProperty().setValue(0.8);
        appPane.scaleYProperty().setValue(0.8);
        appPane.setOpacity(0.5);
        flag.rstRunFlag();
      
    } 
    
    private void setAppPane(Parent pane) {
        appPane.getChildren().clear();
        appPane.scaleXProperty().setValue(1.0);
        appPane.scaleYProperty().setValue(1.0);
        appPane.getChildren().add(pane);
    }
    
    private void loadSplashScreen() {

        Vreg.splashScreen = true;
                
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLSplashScreen.fxml"));
            appPane.getChildren().add(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished(e -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished(e -> {
                try {
                    AnchorPane parent = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLDocument.fxml"));
                    mainAnchorPane.getChildren().setAll(parent);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
