/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.loader;

import java.util.Timer;

/**
 *
 * @author Влад
 */
public class LoaderDataTimer {
    
    private Timer timer = new Timer();
    private LoaderTimerTask loaderTimerTask = new LoaderTimerTask();
    
    public LoaderDataTimer() {
        timer.schedule(loaderTimerTask, 0, 1000 * 25 * 60);
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public LoaderTimerTask getLoaderTimerTask() {
        return loaderTimerTask;
    }

    public void setLoaderTimerTask(LoaderTimerTask loaderTimerTask) {
        this.loaderTimerTask = loaderTimerTask;
    }
    
}
