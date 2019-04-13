package com.blackberryappframework.TemplateProject.appConfiguration;


import com.blackberryappframework.CMAppConfiguration.CMAppSetting;

public class AppSetting extends CMAppSetting{
	
	private static String APP_CONFIG = "configuration";
	
	public static AppSetting getAppSetting() {
		if (appSetting == null)
			appSetting = new AppSetting();
		
		return appSetting;
	}
	private static AppSetting appSetting;
	
	
	/**
	 * This function will be called by the constructor automatically
	 * */
	private AppConfiguration appConfig;
	public AppConfiguration getAppConfiguration() { return this.appConfig; }
	
	public void initializePersistentData(boolean clearData) {
		super.initializePersistentData(clearData);
		
		synchronized(this) {
			appConfig = (AppConfiguration) AppDefinitions.persistentHashtable.get(APP_CONFIG);
			if (appConfig == null) {
				appConfig = new AppConfiguration();
				AppDefinitions.persistentHashtable.put(APP_CONFIG, appConfig);
				commitPersistentObject();
			}
		}
	}
	
	public void initializeAppEnv() {
		// TODO Auto-generated method stub
		
	}

	public int loadAppData() {
		return 0;
	}

}
