package com.blackberryappframework.TemplateProject.appConfiguration;

import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;

public class AppDefinitions {
	public static boolean debug = true;
	public static final String APP_BETA_FEEDBACK_URL = "https://college-mobile-dev.com/service/feedback/submit.php";

	public static int FLASH_DELAY = 1000;
	
	//application related definitions
	public static boolean lockOrientationChange = true;
	public static boolean lockOrientationNorth  = true;

	// We might NOT need to persist logs
	public static boolean PERSIST_LOG = false;
	//com.CollegeMobile.Log.dingfree = 0x4bace4f306d7c424L
	public static final long KEY_LOG_OBJECT = 0x4bace4f306d7c424L;
	public static PersistentObject persistentLog;

	//com.CollegeMobile.dingfree = 0x4eb40c30898b8d0dL
	public static final long KEY_APP_OBJECT = 0x4eb40c30898b8d0dL;
	public static PersistentObject persistentObject;
	public static Hashtable persistentHashtable ;

	public static String version_number = "1.2";
	public static String terms_conditions_number = "1.0";
}
