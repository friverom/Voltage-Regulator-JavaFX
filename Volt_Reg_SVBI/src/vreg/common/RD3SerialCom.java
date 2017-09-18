/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Federico
 */
public class RD3SerialCom extends SerialCom implements SerialComCommands{

    ArrayBlockingQueue buffer = new ArrayBlockingQueue(10);
    Signal wait = new Signal();
    String data;
    
    public RD3SerialCom() {

        Runnable Q1 = new Queue(buffer);
        new Thread(Q1, "TXQueue").start();

    }
        
    public RD3SerialCom(String ComPort) {

        try {
            this.connect(ComPort);
        } catch (Exception ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        Runnable Q1 = new Queue(buffer);
        new Thread(Q1, "TXQueue").start();

    }

    @Override
    public String get_time() {

        try {
            buffer.put("get time\r,t");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void reset_min() {
        
        try{
            buffer.put("reset min\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    
    }

    @Override
    public void reset_max() {
        
        try{
            buffer.put("reset max\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_date() {
        try{
            buffer.put("get date\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    
    }

    @Override
    public void lcd_contrast(String value) {
        try {
            buffer.put("lcd contrast " + value +"\r,f");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void lcd_backlight(String value) {
        try {
            buffer.put("lcd backlight " + value +"\r,f");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String get_Vin() {
        try{
            buffer.put("get vin\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public String get_max_Vl_alarm() {
        try{
            buffer.put("get max\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public String get_min_Vl_alarm() {
        try{
            buffer.put("get min\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_max_Vl_alarm(String value) {
        try {
            buffer.put("set max " + value +"\r,f");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void set_min_Vl_alarm(String value) {
        try {
            buffer.put("set min " + value +"\r,f");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String get_kp() {
        try{
            buffer.put("get kp\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_kp(String value) {
        try{
            buffer.put("set kp "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_ti() {
        try{
            buffer.put("get ti\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_ti(String value) {
        try{
            buffer.put("set ti "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_td() {
        try{
            buffer.put("get td\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_td(String value) {
        try{
            buffer.put("set td "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_sample_time() {
        try{
            buffer.put("get sample_t\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_sample_time(String value) {
        try{
            buffer.put("set sample_t "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_deadband() {
    try{
            buffer.put("get deadband\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_deadband(String value) {
        try{
            buffer.put("set deadband "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_setpoint() {
        try{
            buffer.put("get setpoint\r,t");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_setpoint(String value) {
        try{
            buffer.put("set setpoint "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_control_voltage() {
        try {
            buffer.put("get vc\r,t");

        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public String get_filter_constant() {
        try {
            buffer.put("get rc\r,t");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_filter_constant(String value) {
        try{
            buffer.put("set rc "+ value+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_control_mode() {
        try {
            buffer.put("get modo\r,t");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public void set_time(String time) {
        try{
            buffer.put("set time "+ time + "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_date(String date) {
        try{
            buffer.put("set date "+ date + "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V1_zero(String data) {
        try{
            buffer.put("cal v1_z "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V2_zero(String data) {
        try{
            buffer.put("cal v2_z "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V3_zero(String data) {
        try{
            buffer.put("cal v3_z "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_Vin_zero(String data) {
        try{
            buffer.put("cal vin_z "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V1_span(String data) {
        try{
            buffer.put("cal v1_s "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V2_span(String data) {
        try{
            buffer.put("cal v2_s "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_V3_span(String data) {
        try{
            buffer.put("cal v3_s "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public void set_Vin_span(String data) {
        try{
            buffer.put("cal vin_s "+ data+ "\r,f");
        } catch(InterruptedException ex){
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    @Override
    public String get_PID_output() {
        try {
            buffer.put("get pidout\r,t");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=getData();
        wait.rstWait();
        return data;
    }

    @Override
    public String get_data_vreg() {
        try {
            buffer.put("get data\r,t");
        } catch (InterruptedException ex) {
            Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = getData();
        wait.rstWait();
        return data;
    }
    
    public class Signal{
        
        private boolean flag;
        
        public Signal(){
            flag=false;
        }
    
        public synchronized void setWait(){
            flag=true;
        }
        
        public synchronized void rstWait(){
            flag=false;
        }
        
        public synchronized boolean getWaitStat(){
            return flag;
        }
}
    
    public class Queue implements Runnable {

        ArrayBlockingQueue queue = null;
                
        public Queue(ArrayBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            String cmd = "";

            while (true) {
                try {
                    cmd = queue.take().toString();
                    String parts[] = cmd.split(",");
                    if (parts[1] == "t") {
                        wait.setWait();
                        sendData(parts[0]);
                        while(wait.getWaitStat()==true){
                            Thread.sleep(100);
                        }
                    }
                    else{
                        sendData(parts[0]);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(RD3SerialCom.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

}
