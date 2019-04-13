package com.blackberryappframework.utility;

public abstract class CallBack {
	public boolean receivedCallBack = false;
	protected int statusCode = -1;
	
	public void setStatusCode(int code) { statusCode = code; }
	public int  getStatusCode() { return statusCode; }
	
	//todo: add post thread code here...
	public abstract void finished(Object obj) ;
}
