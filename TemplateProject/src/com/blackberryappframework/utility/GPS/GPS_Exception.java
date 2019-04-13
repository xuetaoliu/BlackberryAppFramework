package com.blackberryappframework.utility.GPS;

public class GPS_Exception extends RuntimeException {
	public static final int ERROR_GPS_INITIALIZATION_FAILURE = 0x1;
	public static final int ERROR_GPS_GET_LOCATION_FAILURE = 0x02;
	
	public GPS_Exception(int code) {
		setExceptionCode(code);
	}
	
	private int exceptionCode;
	public void setExceptionCode(int exceptionCode) { this.exceptionCode = exceptionCode; }
	public int getExceptionCode() { return exceptionCode; }

}
