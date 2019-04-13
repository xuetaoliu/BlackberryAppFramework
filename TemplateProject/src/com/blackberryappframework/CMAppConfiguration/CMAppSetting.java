package com.blackberryappframework.CMAppConfiguration;

import com.blackberryappframework.CMAppConfiguration.Logging.Logger;
import com.blackberryappframework.TemplateProject.appConfiguration.AppDefinitions;
import com.blackberryappframework.TemplateProject.appConfiguration.ResourceLoader;
import com.blackberryappframework.utility.BlackberryFeature;
import com.blackberryappframework.utility.GeneralUtils;
import com.blackberryappframework.utility.PersistentHashtable;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.UiApplication;

public abstract class CMAppSetting {

	public static String DEFAULT_IMAGE_FOLDER = "/img/";	
	public static String DEFAULT_VIDEO_FOLDER = "/video/";

	public static String DEFAULT_SCREEN_FOLDER = "default/";
	public static String DEFAULT_320x240_SCREEN_FOLDER = "320x240/";
	public static String DEFAULT_360x480_SCREEN_FOLDER = "360x480/";
	public static String DEFAULT_480x320_SCREEN_FOLDER = "480x320/";
	public static String DEFAULT_480x360_SCREEN_FOLDER = "480x360/";
	public static String DEFAULT_480x640_SCREEN_FOLDER = "480x640/";
	public static String DEFAULT_480x800_SCREEN_FOLDER = "480x800/";
	public static String DEFAULT_640x480_SCREEN_FOLDER = "640x480/";
	
	public static final int GPS_DELAY = 60 * 1 ;

	protected final int INCORRECT_APPLICATION_DEFINITION = 999;

	public UiApplication app;
	protected BlackberryFeature blackberryFeature;
	protected CMAppSetting() {
		_errorId = -1;
		
		app = UiApplication.getUiApplication();
		blackberryFeature = new BlackberryFeature();
		
		initializePersistentData(false);
	}

	/**
	 * Put the none-heavy initialization code here, 
	 * which will be called automatically
	 * */
	public abstract void initializeAppEnv();

	/**
	 * Pub heavy initialization code here, which will be launched with a progress indicator if launching on startup
	 * Or the proper time the application needs it...
	 * */
	public abstract int loadAppData();
	
	/**
	 * This function will be called by the constructor automatically
	 * */
	public void initializePersistentData(boolean clearData) {
		synchronized(this) {
			if (AppDefinitions.PERSIST_LOG) {
				AppDefinitions.persistentLog = PersistentStore.getPersistentObject(AppDefinitions.KEY_LOG_OBJECT);
				
				PersistentObject persistentLogObject = AppDefinitions.persistentLog;
				Object poLogContent = persistentLogObject.getContents();
				if ( poLogContent == null || clearData) {
					persistentLogObject.setContents(Logger.getLogger());
					commitPersistentObject();
				} else {
					Logger.setLogger((Logger)poLogContent);
				}
			}
			
			/* initialize PO object, which is stored in the hashtable*/
			AppDefinitions.persistentObject = PersistentStore.getPersistentObject(AppDefinitions.KEY_APP_OBJECT);
			PersistentObject persistentObject = AppDefinitions.persistentObject;
			Object poContent = persistentObject.getContents();
			if ( poContent == null || clearData) {
				AppDefinitions.persistentHashtable = new PersistentHashtable();
				
				persistentObject.setContents(AppDefinitions.persistentHashtable);
				commitPersistentObject();
			} else {
				AppDefinitions.persistentHashtable = (PersistentHashtable) poContent;
			}
		}
	}
	
	public void commitPersistentObject() {
		if (AppDefinitions.persistentLog != null)
			AppDefinitions.persistentLog.commit();

		if (AppDefinitions.persistentObject != null)
			AppDefinitions.persistentObject.commit();
	}

	public void clearPersistentObject() {
		/* KEEP this piece of code in case we need to persist logs in the future
		if (CMAppDefinitions.persistentLog != null) {
			Logger.getLogger().clearLog();
		}
		*/
		if (AppDefinitions.persistentObject != null) {
			AppDefinitions.persistentHashtable.clear();
			AppDefinitions.persistentObject.setContents(null);			
		}
		
		commitPersistentObject();
	}

	private int _errorId;
	public void setErrorId(int id) { _errorId = id; }
	public int getErrorId() { return _errorId; }

	public Bitmap loadBitmapFromFile(int imageResourceID) {
		return loadBitmapFromFile(ResourceLoader.getString(imageResourceID));
	}
	
	public Bitmap loadBitmapFromFile(String bitmapFileName) {
		return GeneralUtils.loadBitmapFromFile(bitmapFileName, blackberryFeature.screenType());
	}

	public int getProperDimension()
	{
		int dimension;
		switch (blackberryFeature.screenType()) {
		case BlackberryFeature.BLACKBERRY_320x240: 
		case BlackberryFeature.BLACKBERRY_360x480:
		case BlackberryFeature.BLACKBERRY_480x320:
			dimension = 75;
			break;
		case BlackberryFeature.BLACKBERRY_480x360: 	
			dimension = 113;
			break;
		case BlackberryFeature.BLACKBERRY_480x640: 
			dimension = 100;
			break;	
		case BlackberryFeature.BLACKBERRY_480x800:
			dimension = 120;
			break;
		case BlackberryFeature.BLACKBERRY_640x480: 
			dimension = 150;
			break;
		default:
			dimension = 80;
			break;
		}
		
		return dimension;
	}
	
	public int getProperFontSize()
	{
		int font_size;
		switch (blackberryFeature.screenType()) {
		case BlackberryFeature.BLACKBERRY_320x240: 
			font_size = 12;
			break;
		case BlackberryFeature.BLACKBERRY_480x320:
			font_size = 12;
			break;
		case BlackberryFeature.BLACKBERRY_480x360: 		
			font_size = 14;
			break;	
		case BlackberryFeature.BLACKBERRY_360x480:
			font_size = 14;
			break;
		case BlackberryFeature.BLACKBERRY_480x640: 
			font_size = 18;
			break;	
		case BlackberryFeature.BLACKBERRY_480x800:
		case BlackberryFeature.BLACKBERRY_640x480: 
			font_size = 20;
			break;
		default:
			font_size = 7;
			break;
		}
		
		return font_size;
	}
	
	public String getVideoSourceName(String videoFileName) {
		return GeneralUtils.getVideoFileName(videoFileName, blackberryFeature.screenType());
	}
	
}
