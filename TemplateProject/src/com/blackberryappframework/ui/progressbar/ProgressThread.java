package com.blackberryappframework.ui.progressbar;


public abstract class ProgressThread extends Thread {
	
	protected ObserverInterface observer;
	protected boolean stopRequest = false;
	
	public ProgressThread(ObserverInterface observer) {
		super();
		
		this.observer = observer;
	}
	
	/**
	 * Process the long running or blocking operation in this Thread
	 * Update the Observer as required using
	 *    - processStatus, whenever desired
	 * and then one of:
	 *    - processError, if there was a problem
	 *    - processResponse, if the data was obtained OK
	 *    
	 * ATTENTION: MUST call finish() before when finish
	 * */
	    public abstract void run ();
	    
	/**
	 * stop is called if the processing should not continue.
	 */
	public void stop() {
		
		observerError(ObserverInterface.CANCELLED, "Cancelled by User");
		stopRequest = true; // Will no longer tell Observer anything
		this.interrupt(); // Give our Thread a kick
	}
	
	public void observerMessageUpdate(String message, boolean resetGauge) {
		if ( !stopRequest ) {
			observer.processMessageUpdate(message, resetGauge);
		}
	}
	
	public void observerStatusUpdate(final int status, final String statusString) {
		if ( !stopRequest ) {
			observer.processStatusUpdate(status, statusString);
		}
	}
	
	public void observerError(int errorCode, String errorMessage) {
		if ( !stopRequest ) {
			observer.processError(errorCode, errorMessage);
		}
	}
	
	public void observerResponse(String responseMsg) {
		if ( !stopRequest ) {
			observer.processResponse(responseMsg);
		}
	}
	
	// Make sure we do nothing else
	public void finish() {
		stopRequest = true;
        observer = null;
	}
}
