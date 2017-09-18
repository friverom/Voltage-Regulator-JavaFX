/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author frive
 */
public class FXMLSelectionsController implements Initializable {

    @FXML
    private Button comBtn;
    @FXML
    private Button calBtn;
    @FXML
    private Button alrmBtn;
    @FXML
    private Button pidBtn;
    @FXML
    private Button logBtn;
    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonsActionEvent(ActionEvent event) {
        if(event.getSource()==comBtn){
            System.out.println("Com BUtton");
        }
    }
    
}
