package com.blackberryappframework.CMAppConfiguration.Logging;

public class Log {
	public int level;
	public long logTime;
	public String module;
	public String logs;
	
	public Log(int level, long time, String module, String logs) {
		this.level = level;
		this.logTime = time;
		this.module = module;
		this.logs = logs;
	}
	
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(level + ",");
		strBuffer.append(logTime + ",");
		strBuffer.append(module + ",");
		strBuffer.append('"' + logs + '"' + ",");
		
		return strBuffer.toString();
	}
}
