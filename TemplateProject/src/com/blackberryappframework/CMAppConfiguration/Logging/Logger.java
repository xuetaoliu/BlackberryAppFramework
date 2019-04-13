package com.blackberryappframework.CMAppConfiguration.Logging;

import java.util.Vector;

import javax.microedition.global.Formatter;

import com.blackberryappframework.TemplateProject.appConfiguration.AppDefinitions;



import net.rim.device.api.ui.UiApplication;

public class Logger {
    public static int SEVERE = 0x01;
    public static int WARNING = 0x02;
    public static int INFO = 0x04;
    //the following logging levels are to monitor the usage of framework. They are for developer usage only
    public static int CONFIG = 0x08;
    public static int DEVELOPMENT = 0x10;
    public static int DEBBUGGING  = 0x20;

 
    public static void log(int level, String module, String msgTemplate, String[] params) {

    	String logStr ;
    	if (params == null)
    		logStr = msgTemplate;
    	else 
    		logStr = Formatter.formatMessage(msgTemplate, params);
    	Log logObj = new Log(level, System.currentTimeMillis(), module, logStr);
    	
    	if (AppDefinitions.debug || level < CONFIG) {
    		System.out.println("** " + UiApplication.getUiApplication().getClass().getName() + " **: " + logObj.toString());
    	} else {
    		if (level != SEVERE)
    			return;
    	}
    	getLogger().addLog(logObj);
    }
    
    private static Logger logger = null;
    public static Logger getLogger() {
    	if (logger == null)
    		logger = new Logger();
    	
    	return logger;
    }
    
    public static void setLogger(Logger logger) {
    	Logger.logger = logger;
    }


    private static int MAX_LOG_SIZE = 500;
    
    private int logCursor;
    private Vector logs;
    private Logger() {
    	logs = new Vector();
    	logCursor = 0;
    }
    
    public void addLog(Log log) {
    	if (logs.size() < MAX_LOG_SIZE)
    		logs.addElement(log);
    	else {
    		logs.removeElementAt(logCursor);
    		logs.insertElementAt(log, logCursor);
    	}
    	
    	logCursor = (logCursor + 1) % MAX_LOG_SIZE;
    }
    
    public void clearLog() {
    	logCursor = 0;
    	logs.removeAllElements();
    }
    
    public StringBuffer fetchLogs() {
    	StringBuffer strBuffer = new StringBuffer();
    	
    	boolean addSeperator = false;
    	int index = logCursor - 1;
    	boolean finished = false;
    	while ( !finished ) {
    		
    		if (index < 0) {
    			if (logs.size() == MAX_LOG_SIZE)
    				index = logs.size() - 1;
    			else
    				break;
    		}

    		Log log = (Log)logs.elementAt(index);
    		String logStr = '"'+log.toString() + '"';
    		if (addSeperator) {
    			logStr = ", " + logStr;
    		} else {
    			addSeperator = true;
    		}
			strBuffer.append(logStr);

			index -- ;
    		if (index == logCursor) {
    			break;
    		}
    	}
    	
    	return strBuffer;
    }

}
