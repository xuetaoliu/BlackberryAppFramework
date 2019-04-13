package com.blackberryappframework.ui.progressbar;


public interface ObserverInterface {

	public void processMessageUpdate(String message, boolean resetGauge);
    public void processStatusUpdate(int status, String statusString);
    // Observer can be notified by Observed as it is going along, with regular status updates
    // Could be used to pass messages back to be displayed, for example a sequence like:
    // finding Server, logging in, logged in, requested update, update received, logging out....
    // Could also be used as in this sample, to pass back a % complete

    public void processResponse(String responseMsg);
    // If the processing is successful, response is passed to called using this.

    public void processError(int errorCode, String errorMessage);
    
    // If there is an error, an error indication is passed back using this
    public static int CANCELLED = -1;
    public static int ERROR = -2;
    public static int OK = 0;

}