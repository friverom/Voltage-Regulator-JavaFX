/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import src.Vreg;
import vreg.common.Signal;

/**
 * FXML Controller class
 *
 * @author Federico
 */
public class FXMLPidController implements Initializable {

    private Signal flag = Vreg.readFlag;
    private Signal cmdFlag = new Signal();
    
    @FXML
    private TextField pid_output;
    @FXML
    private TextField voltage_in;
    @FXML
    private TextField pid_kp;
    @FXML
    private TextField pid_ti;
    @FXML
    private TextField pid_td;
    @FXML
    private TextField sample_time;
    @FXML
    private TextField RC_constant;
    @FXML
    private TextField pid_setpoint;
    @FXML
    private TextField control_voltage;
    @FXML
    private TextField control_mode;
    @FXML
    private TextField pid_deadband;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Vreg.loggerFlag.rstRunFlag();
        pid_kp.setText(Vreg.Vreg_Connect.get_kp());
        pid_ti.setText(Vreg.Vreg_Connect.get_ti());
        pid_td.setText(Vreg.Vreg_Connect.get_td());
        sample_time.setText(Vreg.Vreg_Connect.get_sample_time());
        RC_constant.setText(Vreg.Vreg_Connect.get_filter_constant());
        control_mode.setText(Vreg.Vreg_Connect.get_control_mode());
        pid_setpoint.setText(Vreg.Vreg_Connect.get_setpoint());
        control_voltage.setText(Vreg.Vreg_Connect.get_control_voltage());
        pid_deadband.setText(Vreg.Vreg_Connect.get_deadband());
        pid_output.setText(Vreg.Vreg_Connect.get_PID_output());
        voltage_in.setText(Vreg.Vreg_Connect.get_Vin());
        flag.setRunFlag();
        Thread getdata = new Thread(new updateData(),"UpdateData");
        getdata.start();
                
    }    

    @FXML
    private void pidActionEvent(ActionEvent event) {
        
        if(event.getSource()==pid_kp){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_kp(pid_kp.getText());
        }
        else if(event.getSource()==pid_ti){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_ti(pid_ti.getText());
        }
        else if(event.getSource()==pid_td){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_td(pid_td.getText());
        }
        else if(event.getSource()==sample_time){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_sample_time(sample_time.getText());
        }
        else if(event.getSource()==RC_constant){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_filter_constant(RC_constant.getText());
        }
        else if(event.getSource()==pid_setpoint){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_setpoint(pid_setpoint.getText());
        }
        else if(event.getSource()==pid_deadband){
            while(cmdFlag.flagStat()==true);
            Vreg.Vreg_Connect.set_deadband(pid_deadband.getText());
        }
    }
    
    public class updateData implements Runnable {
        
        String rx_data;
        
        @Override
        public void run() {

            while (flag.flagStat() == true) {
                cmdFlag.setRunFlag();
                rx_data = Vreg.Vreg_Connect.get_data_vreg();
                String[] values = rx_data.split(" ");
                if (values.length == 5) {
                    control_voltage.setText(values[0]);
                    voltage_in.setText(values[1]);
                    pid_output.setText(values[3]);
                    control_mode.setText(values[4]);
                }
                
                /*control_mode.setText(Vreg.Vreg_Connect.get_control_mode());
                control_voltage.setText(Vreg.Vreg_Connect.get_control_voltage());
                pid_output.setText(Vreg.Vreg_Connect.get_PID_output());
                voltage_in.setText(Vreg.Vreg_Connect.get_Vin());*/
                cmdFlag.rstRunFlag();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLPidController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Exception en Thread update data");
                }
            }

        }
        
        
    
    }
    
}
