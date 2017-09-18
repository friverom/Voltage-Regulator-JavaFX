/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.views;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.Vreg;
import vreg.common.Signal;
import vreg.common.DataBufferArray;

/**
 * FXML Controller class
 *
 * @author Federico
 */
public class FXMLLoggerController implements Initializable {
    
    private Signal flag = Vreg.readFlag;
    private Signal loggerFlag = Vreg.readFlag;
  /*  private float [] Vout = new float[1440];
    private float [] Vin = new float[1140];
    private float [] Setpoint = new float[1140];
    private float [] PIDout = new float[1440];*/
    final private int size=30;
    private DataBufferArray Vin_Buffer = new DataBufferArray(size);
    private DataBufferArray Vout_Buffer = new DataBufferArray(size);
    private DataBufferArray Control_Buffer = new DataBufferArray(size);
    private BlockingQueue<Float> queue = new ArrayBlockingQueue(12);
        
    XYChart.Series serie_Vin = new XYChart.Series<>();
    XYChart.Series serie_Vout = new XYChart.Series<>();
    XYChart.Series serie_PIDout = new XYChart.Series<>();
    
    @FXML
    private LineChart<String, Number> voltageChart;
    @FXML
    private LineChart<String, Number> controlChart;
    @FXML
    private CategoryAxis voltage_xAxis;
    @FXML
    private NumberAxis voltChartNumberAxis;
    @FXML
    private TextField upperRange;
    @FXML
    private TextField lowerRange;
    @FXML
    private Label pidOutlbl;
    @FXML
    private Label voutLbl;
    @FXML
    private Label vinLbl;
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loggerFlag.rstRunFlag();
        flag.rstRunFlag();
              
        serie_Vout.setName("Salida");
        serie_Vin.setName("Entrada");
       // serie_Setpoint.setName("Setpoint");
        serie_PIDout.setName("Salida PID");
        voltageChart.setCreateSymbols(false);
        controlChart.setCreateSymbols(false);
        voltageChart.getData().addAll(serie_Vin,serie_Vout);
       // voltageChart.getData().addAll(serie_Vin,serie_Vout,serie_Setpoint);
        controlChart.getData().add(serie_PIDout);
        
        upperRange.setText(String.valueOf(voltChartNumberAxis.getUpperBound()));
        lowerRange.setText(String.valueOf(voltChartNumberAxis.getLowerBound()));
        
        loggerFlag.setRunFlag();
        Thread displayData = new Thread(new logger(queue),"logger");   
        displayData.start();
        Thread updateDisplay = new Thread(new display());
        updateDisplay.start();
        
      
    }    

    @FXML
    private void setUpperBound(ActionEvent event) {
        
        voltChartNumberAxis.setUpperBound(Double.parseDouble(upperRange.getText()));
    }
    
    @FXML
    private void setLowerBound(ActionEvent event){
    
        voltChartNumberAxis.setLowerBound(Double.parseDouble(lowerRange.getText()));
    }
    
        public class display implements Runnable {

        @Override
        public void run() {
            
        while(true){
            try {
                Vin_Buffer.add(queue.take());
                Vout_Buffer.add(queue.take());
                Control_Buffer.add(queue.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLLoggerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(()-> {
            pidOutlbl.setText(String.valueOf(Control_Buffer.getData(0)));
            vinLbl.setText(String.valueOf(Vin_Buffer.getData(0)));
            voutLbl.setText(String.valueOf(Vout_Buffer.getData(0)));
            serie_Vin.getData().clear();
            serie_Vout.getData().clear();
            serie_PIDout.getData().clear();
            
            serie_Vin.getData().add(new XYChart.Data(String.valueOf(0), Vin_Buffer.getData(0)));
            serie_Vout.getData().add(new XYChart.Data(String.valueOf(0), Vout_Buffer.getData(0)));
            serie_PIDout.getData().add(new XYChart.Data(String.valueOf(0), Control_Buffer.getData(0)));
            for (int i = 1; i < size; i++) {
                serie_Vin.getData().add(new XYChart.Data(String.valueOf(i), Vin_Buffer.getNext()));
                serie_Vout.getData().add(new XYChart.Data(String.valueOf(i), Vout_Buffer.getNext()));
                serie_PIDout.getData().add(new XYChart.Data(String.valueOf(i), Control_Buffer.getNext()));
            }
            });
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLLoggerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    }
    public class logger implements Runnable {
        
        BlockingQueue queue;
        int i = 0;
        
        public logger(BlockingQueue queue){
            this.queue = queue;
        }
        
        @Override
        public void run() {
            
            while (loggerFlag.flagStat()==true) {

                String data = Vreg.Vreg_Connect.get_data_vreg();
                String[] values = data.split(" ");

                try {
                    if (values.length == 5) {
                        queue.put(Float.parseFloat(values[0]));
                        queue.put(Float.parseFloat(values[1]));
                        queue.put(Float.parseFloat(values[3]));
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLLoggerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } 
    }
}
    

