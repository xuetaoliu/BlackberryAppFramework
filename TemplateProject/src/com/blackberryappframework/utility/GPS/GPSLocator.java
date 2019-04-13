package com.blackberryappframework.utility.GPS;

import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.location.Coordinates;

public class GPSLocator extends TimerTask {
	private static int MAX_RUN_TIME = 5;
	private static int TIMER_INTERVAL = 60*1000;
	
	private static GPSLocator t_GPSLocator = null;
	public static GPSLocator getGPSLocator(boolean accurateFirst) {
		if (t_GPSLocator == null) {
			t_GPSLocator = new GPSLocator();
			
			t_GPSLocator.startGPSTimer(t_GPSLocator);
	   		
	   		while (!t_GPSLocator.finished()) ;
		} 
		
		t_GPSLocator.accurateFirst = accurateFirst;
		return t_GPSLocator;
	}
	
	private GPSLocator() {
		super();
		
		this.finishedInitialization = false;
		this.counter = 0;
		this.listeners = new Vector();
		this.accurateFirst = false;
		this.curCoordinates = null;
		this.errMsg = null;
	}
	
	private Vector listeners;
	public void addChangeListener(GPSChangeListener listener) {
		if (!this.listeners.contains(listener))
			this.listeners.addElement( listener );
	}
	public void removeChangeListener(GPSChangeListener listener) {
		this.listeners.removeElement( listener );
	}
	
	private boolean accurateFirst;
	private Coordinates curCoordinates;
	private String errMsg;
	
	public synchronized Coordinates getCurCoordinates() {
		//timer is cancelled, we need to restart it
		if (counter == -1) {
			GPSLocator new_GPSLocator = t_GPSLocator.clone();
			
			t_GPSLocator = new_GPSLocator;
			startGPSTimer(t_GPSLocator);
		}
		
		counter = 0;
		return curCoordinates;
	}
	
	private void setCurCoordinates(Coordinates coordinates) {
		synchronized(this) {
			this.curCoordinates = coordinates;
		}
		
		for (int i = 0; i < this.listeners.size(); i++) {
			GPSChangeListener listener = (GPSChangeListener)listeners.elementAt(i);
			if (listener != null) {
				
				listener.updateCurrentLocation(coordinates);
			}
		}
	}
	
	public synchronized String getErrors() { return this.errMsg; }
	private synchronized void setErrors(String errorMessage) { this.errMsg = errorMessage; }

	private boolean finishedInitialization;
	public synchronized boolean finished() { return finishedInitialization; }
	private synchronized void setFinishedFlag(boolean finished) { this.finishedInitialization = finished; }
	
	private void cancelGPSTimer() {
		if (t_GPSLocator != null) {
			t_GPSLocator.counter = -1;
			t_GPSLocator.cancel();
		}
	}
	
	private void startGPSTimer(GPSLocator gpsLocator) {
		Timer gpsTimer = new Timer();
   		gpsTimer.scheduleAtFixedRate(gpsLocator, 0, TIMER_INTERVAL);
	}
	
	private int counter;
	public void run() {

		if (counter == MAX_RUN_TIME) {
			counter = -1;
			cancelGPSTimer();
		} else {
			System.out.println("GPS is alive to run now... {" +  counter + "}");
	        GPSLauncher gpsLocactor = new GPSLauncher();
	        try {
	        	int GPSMode = GPSLauncher.GPS_MODE_ASSISTED;
	        	//we only use Tower Mode for the first time usage;
	        	//once we launched GPSLocator, we will NOT user tower mode anymore
	        	if (!this.accurateFirst && counter == 0 && !this.finished()) 
	        		GPSMode = GPSLauncher.GPS_MODE_TOWER;
	        	
		        if ( gpsLocactor.startGPS(GPSMode, GPSLauncher.GPS_ACCURACY_LEVEL_100M, true) ) {
		        	Coordinates coordinates = gpsLocactor.getCurCoordiates();
		        	this.setCurCoordinates(coordinates);
		        }
		        	
		        //if we failed to find location via tower mode, 
		        //we will try to use the assisted mode 
		        if (GPSMode == GPSLauncher.GPS_MODE_TOWER && this.curCoordinates == null) {
		        	GPSMode = GPSLauncher.GPS_MODE_ASSISTED;
			        if ( gpsLocactor.startGPS(GPSMode, GPSLauncher.GPS_ACCURACY_LEVEL_100M, true) ) {
			        	Coordinates coordinates = gpsLocactor.getCurCoordiates();
			        	this.setCurCoordinates(coordinates);
			        }
		        }
 
		        setFinishedFlag( true );
	        	
		        //after using tower mode to get a location, we will use Assist mode to get location at once
		        //so that if the user access gps quickly, he might be able to have an accurate result
		        if (GPSMode == GPSLauncher.GPS_MODE_TOWER) {
		        	LaunchGPS launchGPS = new LaunchGPS();
		        	launchGPS.start();
		        }
	        } catch (Exception e) {
	        	this.setErrors( e.getMessage() );    
	        }

	        counter++;
		}
	}
	
	public GPSLocator clone() {
		GPSLocator newLocator = new GPSLocator();
		
		newLocator.accurateFirst = this.accurateFirst;
		newLocator.setCurCoordinates(this.curCoordinates);
		Enumeration e = this.listeners.elements();
		while (e.hasMoreElements()) {
			GPSChangeListener listener = (GPSChangeListener)e.nextElement();
			newLocator.addChangeListener(listener);
		}
		
		return newLocator;
	}
	
	private class LaunchGPS extends Thread {
		public void run() {
			GPSLauncher gpsLocactor = new GPSLauncher();
			
        	try {
				if ( gpsLocactor.startGPS(GPSLauncher.GPS_MODE_ASSISTED, GPSLauncher.GPS_ACCURACY_LEVEL_100M, true) ) {
					Coordinates coordinates = gpsLocactor.getCurCoordiates();
					setCurCoordinates(coordinates);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}


