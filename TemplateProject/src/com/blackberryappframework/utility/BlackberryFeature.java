package com.blackberryappframework.utility;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Touchscreen;

public class BlackberryFeature {
	
	public static final int BLACKBERRY_320x240 = 0x01;  //curve 5.0/6.0 
	public static final int BLACKBERRY_360x480 = 0x02;  //torch /storm 5.0 /6.0
	public static final int BLACKBERRY_480x320 = 0x03;  //bold 5.0 / 6.0
	public static final int BLACKBERRY_480x360 = 0x04;  //bold 5.0 / 6.0
	public static final int BLACKBERRY_480x640 = 0x05;  //torch 7.0
	public static final int BLACKBERRY_480x800 = 0x06;  //torch 7.0 -- no keyboard
	public static final int BLACKBERRY_640x480 = 0x07;  //bold 7.0

	public BlackberryFeature() {
//		int height = Display.getHeight();
//		int width = Display.getWidth();
		
		orientationLocked = false;
		lowResolution = false;
		touchSupported = Touchscreen.isSupported();
		deviceName = CMBBApplication.getDeviceName();
		
		if (deviceName.startsWith("8520"))
			gpsSupported = false;
		else
			gpsSupported = true;
		
		if (   deviceName.startsWith("8520")
			|| deviceName.startsWith("8530")
			|| deviceName.startsWith("9300")
			|| deviceName.startsWith("9330")) {
			
			screenType = BLACKBERRY_320x240;
			lowResolution = true;
			
		} else if (   deviceName.startsWith("9000") ) {
			
			screenType = BLACKBERRY_480x320;
			
		} else if (   deviceName.startsWith("8900")
				   || deviceName.startsWith("8910")
				   || deviceName.startsWith("9650")
				   || deviceName.startsWith("9350")
				   || deviceName.startsWith("9360")
				   || deviceName.startsWith("9700")
				   || deviceName.startsWith("9780")
				   || deviceName.startsWith("9630")
				   || deviceName.startsWith("9350")
				   || deviceName.startsWith("9360")
				   || deviceName.startsWith("9370")) {
			
			screenType = BLACKBERRY_480x360;
			
		} else if (   deviceName.startsWith("9520")
				   || deviceName.startsWith("9550")
				   || deviceName.startsWith("9800")
				   || deviceName.startsWith("9370")
				   || deviceName.startsWith("9380")) {
			
			screenType = BLACKBERRY_360x480;
			
		} else if (   deviceName.startsWith("9810") ) {
			
			screenType = BLACKBERRY_480x640;
			
		} else  if (   deviceName.startsWith("9850")
				    || deviceName.startsWith("9860")) {
			
			screenType = BLACKBERRY_480x800;
			
		} else  if (  deviceName.startsWith("9900")
				   || deviceName.startsWith("9930")) {
			
			screenType = BLACKBERRY_640x480;
			
		} else{
			
			screenType = BLACKBERRY_360x480;
		}
		
		orientationChangeSupported = false;
		if (   deviceName.startsWith("9520")
		    || deviceName.startsWith("9550")
		    || deviceName.startsWith("9800")
		    || deviceName.startsWith("9810")
		    || deviceName.startsWith("9850")
		    || deviceName.startsWith("9860")
		    ) {
			orientationChangeSupported = true;
		}
	}
	
	private String  deviceName;
	private boolean lowResolution;
	private boolean touchSupported;
	private boolean orientationChangeSupported;
	public boolean orientationChangeSupported() { return orientationChangeSupported; }
	
	private boolean gpsSupported;
	private int screenType;
	public  int screenType() { return screenType; }
	
	private boolean orientationLocked;
	public void setOrientationLocked(boolean lock) { orientationLocked = lock;}

	public int getScreenWidth() {
		int width = 480;
		
		switch (screenType) {
		case BLACKBERRY_320x240:
			width = 320;
			break;
		case BLACKBERRY_360x480:
			width = 360;
			break;
		case BLACKBERRY_480x320:
		case BLACKBERRY_480x360:
		case BLACKBERRY_480x640:
		case BLACKBERRY_480x800:
			width = 480;
			break;
		case BLACKBERRY_640x480:
			width = 640;
			break;
		}
		
		return width;
	}
	
	public int getScreenHeight() {
		int height = 480;
		
		switch (screenType) {
		case BLACKBERRY_320x240:
			height = 240;
			break;
		case BLACKBERRY_360x480:
			height = 480;
			break;
		case BLACKBERRY_480x320:
			height = 320;
			break;
		case BLACKBERRY_480x360:
			height = 360;
			break;
		case BLACKBERRY_480x640:
			height = 640;
			break;
		case BLACKBERRY_480x800:
			height = 800;
			break;
		case BLACKBERRY_640x480:
			height = 480;
			break;
		}
		
		return height;
	}
	
	public int getDisplayWidth() {
		if (orientationLocked)
			return getScreenWidth();
		else
			return Display.getWidth();
	}
	
	public int getDisplayHeight() {
		if (orientationLocked)
			return getScreenHeight();
		else
			return Display.getHeight();
	}
}
