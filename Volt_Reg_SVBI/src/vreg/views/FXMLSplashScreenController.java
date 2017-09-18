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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import src.Vreg;

/**
 * FXML Controller class
 *
 * @author Federico
 */
public class FXMLSplashScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private AnchorPane root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void loadSplashScreen() {

        Vreg.splashScreen = true;
        
        try {
            StackPane pane = FXMLLoader.load(getClass().getResource("/vreg/views/FXMLSplashScreen.fxml"));
            root.getChildren().setAll(pane);

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
                    root.getChildren().setAll(parent);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
