/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.common;

/**
 *
 * @author Federico
 */
public interface SerialComCommands {
    
    //LCD display control
    public abstract void lcd_contrast(String value);
    public abstract void lcd_backlight(String value);
    
    //Voltage in commands
    public abstract void reset_min();
    public abstract void reset_max();
    public abstract String get_Vin();
    public abstract String get_max_Vl_alarm();
    public abstract String get_min_Vl_alarm();
    public abstract void set_max_Vl_alarm(String value);
    public abstract void set_min_Vl_alarm(String value);
    
    
    //Get PID data
    public abstract String get_kp();
    public abstract void set_kp(String value);
    public abstract String get_ti();
    public abstract void set_ti(String value);
    public abstract String get_td();
    public abstract void set_td(String value);
    public abstract String get_sample_time();
    public abstract void set_sample_time(String value);
    public abstract String get_deadband();
    public abstract void set_deadband(String value);
    public abstract String get_setpoint();
    public abstract void set_setpoint(String value);
    public abstract String get_control_voltage();
    public abstract String get_filter_constant();
    public abstract void set_filter_constant(String value);
    public abstract String get_PID_output();
    
    public abstract String get_control_mode();
    public abstract String get_data_vreg();
    
   //Date and time
    public abstract String get_time();
    public abstract void set_time(String time);
    public abstract String get_date();
    public abstract void set_date(String date);
    
    //Calibration values
    public abstract void set_V1_zero(String data);
    public abstract void set_V2_zero(String data);
    public abstract void set_V3_zero(String data);
    public abstract void set_Vin_zero(String data);
    public abstract void set_V1_span(String data);
    public abstract void set_V2_span(String data);
    public abstract void set_V3_span(String data);
    public abstract void set_Vin_span(String data);
}
