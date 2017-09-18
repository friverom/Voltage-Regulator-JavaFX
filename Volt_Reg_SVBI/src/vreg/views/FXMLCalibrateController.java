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
import javafx.scene.control.TextField;
import src.Vreg;
import vreg.common.Signal;

/**
 * FXML Controller class
 *
 * @author Federico
 */
public class FXMLCalibrateController implements Initializable {
    
    private Signal flag = Vreg.readFlag;
    
    @FXML
    private TextField V1_zero;
    @FXML
    private TextField V1_factor;
    @FXML
    private TextField V2_factor;
    @FXML
    private TextField V2_zero;
    @FXML
    private TextField Vin_zero;
    @FXML
    private TextField Vin_factor;
    @FXML
    private TextField V3_factor;
    @FXML
    private TextField V3_zero;
    @FXML
    private Button resetMinButton;
    @FXML
    private Button resetMaxButton;
    @FXML
    private TextField lcd_contrast;
    @FXML
    private TextField lcd_backlight;
    @FXML
    private TextField alarm_max;
    @FXML
    private TextField alarm_min;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alarm_max.setText(Vreg.Vreg_Connect.get_max_Vl_alarm());
        alarm_min.setText(Vreg.Vreg_Connect.get_min_Vl_alarm());
        flag.rstRunFlag();
        Vreg.loggerFlag.rstRunFlag();
    }    

    @FXML
    private void actionController(ActionEvent event) {
        
        if(event.getSource()==resetMinButton){
            Vreg.Vreg_Connect.reset_min();
        }
        else if(event.getSource()==resetMaxButton){
            Vreg.Vreg_Connect.reset_max();
        }
        else if(event.getSource()==V1_zero){
            Vreg.Vreg_Connect.set_V1_zero(V1_zero.getText());
        }
        else if(event.getSource()==V1_factor){
            Vreg.Vreg_Connect.set_V1_span(V1_factor.getText());
        }
        else if(event.getSource()==V2_zero){
            Vreg.Vreg_Connect.set_V2_zero(V2_zero.getText());
        }
        else if(event.getSource()==V2_factor){
            Vreg.Vreg_Connect.set_V2_span(V2_factor.getText());
        }
        else if(event.getSource()==V3_zero){
            Vreg.Vreg_Connect.set_V3_zero(V3_zero.getText());
        }
        else if(event.getSource()==V3_factor){
            Vreg.Vreg_Connect.set_V3_span(V3_factor.getText());
        }
        else if(event.getSource()==Vin_zero){
            Vreg.Vreg_Connect.set_Vin_zero(Vin_zero.getText());
        }
        else if(event.getSource()==Vin_factor){
            Vreg.Vreg_Connect.set_Vin_span(Vin_factor.getText());
        }
        else if(event.getSource()==lcd_contrast){
            Vreg.Vreg_Connect.lcd_contrast(lcd_contrast.getText());
        }
        else if(event.getSource()==lcd_backlight){
            Vreg.Vreg_Connect.lcd_backlight(lcd_backlight.getText());
        }
        else if(event.getSource()==alarm_min){
            Vreg.Vreg_Connect.set_min_Vl_alarm(alarm_min.getText());
        }
        else if(event.getSource()==alarm_max){
            Vreg.Vreg_Connect.set_max_Vl_alarm(alarm_max.getText());
        }
        
    }
    
}
