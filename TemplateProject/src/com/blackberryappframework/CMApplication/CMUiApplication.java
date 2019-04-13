package com.blackberryappframework.CMApplication;

import com.blackberryappframework.TemplateProject.appConfiguration.AppDefinitions;
import com.blackberryappframework.TemplateProject.appConfiguration.AppSetting;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

public class CMUiApplication extends UiApplication{
    
    protected MainScreen welcomeScrn ;
    protected Screen mainScrn ;
    public void setMainScreen(Screen scrn) { this.mainScrn = scrn; }
    
    protected AppSetting appSetting;
    public  AppSetting getAppSetting() { return appSetting;}
    
    //fadeout
	protected void startFadeout(Screen from, Screen to) {
		if (from != null) {
			TransitionContext brandOut = new TransitionContext(
					TransitionContext.TRANSITION_FADE);
			brandOut.setIntAttribute(TransitionContext.ATTR_KIND,
					TransitionContext.KIND_OUT);
			brandOut.setIntAttribute(TransitionContext.ATTR_DURATION, AppDefinitions.FLASH_DELAY);
	
			UiEngineInstance engine = Ui.getUiEngineInstance();
			engine.setTransition(from, to, UiEngineInstance.TRIGGER_PUSH, brandOut);
			
			invokeLater(new FadeoutTask(from, to), AppDefinitions.FLASH_DELAY, false);
		} else {
			pushScreen(to);
		}
	}
	
	public void displayError(final String errorMsg, final boolean exitSystem) {
		invokeLater(new Runnable() {
			public void run() {
 				Dialog.alert(errorMsg);
 				
 				if (exitSystem)
 					System.exit(0);
 			}
		});
	}
	
	private class FadeoutTask implements Runnable {
		
		private Screen fromScrn;
		private Screen toScrn;
		public FadeoutTask(Screen from, Screen to) {
			fromScrn = from;
			toScrn = to;
		}

		public void run() {
			if (toScrn != null) {
				pushScreen(toScrn);
			}
			invokeLater(new CloseScreenTask(fromScrn));
		}
	}
	
	private class CloseScreenTask implements Runnable {
		private Screen closeScrn;
		public CloseScreenTask(Screen scrn) {
			closeScrn = scrn;
		}
		public void run() {
			if (closeScrn != null)
				closeScrn.close();
		}
	}
	
	public boolean requestClose() {
		appSetting.commitPersistentObject();
		
		return super.requestClose();
	}
}
