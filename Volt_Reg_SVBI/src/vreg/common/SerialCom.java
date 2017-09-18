package vreg.common;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Federico
 */
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialCom {

    private String RXdata; //RX String received
    private Semaphore RXFlag; //Synchro flag to RX

    private String TXdata; //TX STring to TRansmit
    byte[] TXBuff = new byte[64]; //TX Buffer
    private int TXlen;  //Lenght of Buffer to transmit
    private Semaphore TXFlag;   //Synchro Flag to Transmit

    // ArrayBlockingQueue queue;  
    //Class Constructor
    public SerialCom() {
        super();
        RXFlag = new Semaphore();
        TXFlag = new Semaphore();

        RXFlag.clearFlag(); //When set, RX data ready
        RXFlag.clearRespFlag(); // not use
        TXFlag.clearFlag(); //When set, TX in action
        TXFlag.clearRespFlag(); //Set to wait for response
        
      //  queue = new ArrayBlockingQueue(1024);
    }
    public void OpenSerialPort(String ComPort){
        
        try {
            connect(ComPort);
        } catch (Exception ex) {
            Logger.getLogger(SerialCom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void connect(String portName) throws Exception {
        
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                TXlen = 0;
                

                (new Thread(new SerialWriter(out))).start();

                serialPort.addEventListener((SerialPortEventListener) new SerialReader(in));
                serialPort.notifyOnDataAvailable(true);

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
//This routine waits for RXData to be ready and return received data
    public String getData() {

        int timer = 0; //Timeout timer
//This loop should be inside a synchronized block
        while ((RXFlag.getFlag() == false) && (timer < 400)) {
            try {
                timer++;
                Thread.sleep(10); //Loop every 10 mSec
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (timer < 400) {
            RXFlag.clearFlag();
            TXFlag.clearRespFlag();
            return RXdata;
        } else {
            RXFlag.clearFlag();
            TXFlag.clearRespFlag();
            RXdata = "x"; //Returns -1 if timeout RX
            return RXdata;
        }

    }
//This routine wait for TX ready and loads Buffer to TX
    public void sendData(String s) {

        //this loop should be inside a synchronized block
        while ((TXFlag.getFlag() == true) || (TXFlag.getRespFlag() == true)) {
            try {
                Thread.sleep(10); //Loop every 10 mSec
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
        System.out.println("Cmd: " + s);
        synchronized (this) {
            TXlen = s.length();
            TXdata = s;
            TXBuff = TXdata.getBytes(Charset.forName("UTF-8"));
            TXFlag.setFlag();
            
            
            //	System.out.println("Cmd: " + TXdata);
        }
    }

    public String[] listPorts() {
        ArrayList portList = new ArrayList();
        String portArray[] = null;

        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portList.add(portIdentifier.getName());
            }
            //   System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }
        portArray = (String[]) portList.toArray(new String[0]);
        return portArray;
    }

    /**
     * Handles the input coming from the serial port. A new line character is
     * treated as the end of a block in this example.
     */
    public class SerialReader implements SerialPortEventListener {

        private InputStream in;
        private byte[] RXbuffer = new byte[1024];

        public SerialReader(InputStream in) {
            this.in = in;

        }

        public void serialEvent(SerialPortEvent arg0) {
            int data;

            try {
                int len = 0;
                while ((data = in.read()) > -1) {
                    if (data == '\n') {
                        break;
                    }
                    RXbuffer[len++] = (byte) data;
                }
                RXdata = new String(RXbuffer, 0, len);
                RXFlag.setFlag();
                //   System.out.println("1: " + RXdata);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

    }

    /**
     *      */
    public class SerialWriter implements Runnable {

        OutputStream out;

        int len;
        String data;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            while (true) {

                if (TXFlag.getFlag() == true) {
                    // 	System.out.println("Send Buffer...");	        	
                    try {
                        for (int i = 0; i < TXlen; i++) {
                            this.out.write(TXBuff[i]);
                            //	System.out.println("i " + i + " " + TXBuff[i]);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.exit(-1);
                    }
                  /*  try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }*/
                    synchronized (this) {
                        TXlen = 0;
                    }
                    TXFlag.clearFlag();

                    
                    //   	System.out.println("End TX..");
                }
            }
        }

    }

    public class Semaphore {

        private volatile boolean flag = false;
        private volatile boolean waitResp = false;

        public synchronized void setRespFlag() {
            waitResp = true;
        }

        public synchronized boolean getRespFlag() {
            return waitResp;
        }

        public synchronized void clearRespFlag() {
            waitResp = false;
        }

        public synchronized void setFlag() {

            flag = true;
        }

        public synchronized void clearFlag() {

            flag = false;
        }

        public synchronized boolean getFlag() {

            return flag;
        }
    }
}
