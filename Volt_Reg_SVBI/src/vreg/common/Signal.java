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
public class Signal {

    private volatile boolean flag;

    public Signal() {
        flag = false;
    }

    public synchronized void setRunFlag() {
        flag = true;
    }

    public synchronized void rstRunFlag() {
        flag = false;
    }

    public synchronized boolean flagStat() {
        return flag;
    }

}
