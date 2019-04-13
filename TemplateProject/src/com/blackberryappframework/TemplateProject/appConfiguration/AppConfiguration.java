package com.blackberryappframework.TemplateProject.appConfiguration;

import net.rim.device.api.util.Persistable;

public class AppConfiguration implements Persistable{
	public AppConfiguration() {
	}
	
	private boolean acceptTerms;
	public  void acceptTerms(boolean accept) { this.acceptTerms = accept; }
	public boolean acceptedTerms() { return this.acceptTerms; } 
	
	private boolean firstTimeRun;
	public void firstTimeRun(boolean on) { this.firstTimeRun = on; }
	public boolean firstTimeRun() { return this.firstTimeRun; }
	
	private String terms_conditions_version;
	public String getTermsConditionsVersion() { return this.terms_conditions_version == null? "" : this.terms_conditions_version; }
	public void setTermsConditionsVersion(String version) { this.terms_conditions_version = version; }
	
	//app settings
}
