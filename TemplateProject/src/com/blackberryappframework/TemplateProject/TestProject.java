package com.blackberryappframework.TemplateProject;

import com.blackberryappframework.CMApplication.CMUiApplication;
import com.blackberryappframework.TemplateProject.appConfiguration.AppConfiguration;
import com.blackberryappframework.TemplateProject.appConfiguration.AppDefinitions;
import com.blackberryappframework.TemplateProject.appConfiguration.AppSetting;
import com.blackberryappframework.TemplateProject.appConfiguration.ResourceLoader;
import com.blackberryappframework.ui.screen.HomeScreen;
import com.blackberryappframework.ui.screen.TermsNCondsScreen;
import com.blackberryappframework.ui.screen.general.WelcomeScreen;
import com.blackberryappframework.utility.CMBBApplication;

import net.rim.device.api.ui.Screen;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class TestProject extends CMUiApplication
{
	
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        TestProject dingFree = new TestProject();
        dingFree.enterEventDispatcher();
    }

    public TestProject()
    {        
    	super();
    	
        //fetch the device information
    	CMBBApplication.lockOrientation(AppDefinitions.lockOrientationChange, AppDefinitions.lockOrientationNorth);
    	appSetting = AppSetting.getAppSetting();
    	
    	//push splash screen
    	String backgroundImage = ResourceLoader.getString(ResourceLoader.ID_IMG_SCREEN_SPLASH);
    	welcomeScrn = WelcomeScreen.startWelcomeScreen(appSetting.loadBitmapFromFile(backgroundImage));
    	pushScreen(welcomeScrn);

    	//load data
    	//appSetting.loadData();
    	
    	//transit to the main screen
    	mainScrn = getMainScreen();
    	if (mainScrn != null) {
    		startFadeout(welcomeScrn, mainScrn);
    	}
    }    
    
    
    protected Screen getMainScreen() {
    	if (mainScrn == null) { 
        	Screen scrn = null;

        	AppConfiguration appConfig = appSetting.getAppConfiguration();
	    	if (appConfig.acceptedTerms())
	    		scrn = new HomeScreen(); 
	    	else
	    		scrn = new TermsNCondsScreen(ResourceLoader.getString(ResourceLoader.ID_URL_TERM_N_CONDS));
	    	
	    	this.setMainScreen(scrn);
    	}
    	
    	return mainScrn;
    }
    

}
