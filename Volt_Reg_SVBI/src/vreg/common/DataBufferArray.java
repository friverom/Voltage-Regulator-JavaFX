/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vreg.common;

/**
 *  Shift Buffer to prepare streaming data to display
 * @author Federico
 */
public class DataBufferArray {
    
    private float[] ShiftBuffer = new float[1440];
    final private int size;
    private int index;
      
    /**
     * Initialize ShiftBuffer to 0 value
     * @param size Int variable to size the shift buffer 
     */
    public DataBufferArray(int size){
        this.size=size;
        index=0;
        for(int i=0;i<1440;i++){
            ShiftBuffer[i]=0;
        }
    }
    /**
     * Add method Shift the Buffer one place and adds val to first position
     * @param val float value to add to buffer 
     */
    public void add(float val){
        synchronized(this){
        for(int i=size-1;i>0;i--){
            ShiftBuffer[i]=ShiftBuffer[i-1]; //Shift Buffer
        }
        ShiftBuffer[0]=val;
        }
    }
    /**
     * Gets data from Buffer at position "index"
     * @param index position of value to be return
     * @return float value
     */
    public float getData(int index){
        synchronized(this){
        if(index>size-1){
            this.index=size-1;
        }
        else {
            this.index=index;
        }
        return ShiftBuffer[this.index++];
        }
    }
    /**
     * Gets data from buffer at next position
     * @return float value
     */
    public float getNext(){
        synchronized(this){
        if(this.index>size-1){
            this.index=size-1;
        }
        return ShiftBuffer[this.index++];
        }
    }
    
}
