/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0512
 * @comments    Specified UI Screen to dispaly the flash app logo *                                  
 *                  
 * @history
 *              Date        Author  Comments
 *              2011/--/--  X.L.    created
 **/

package com.blackberryappframework.ui.screen.general;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class WelcomeScreen extends MainScreen{
	
	public static WelcomeScreen startWelcomeScreen(Bitmap scrnBG) {
		WelcomeScreen welcomeScreen = null;
		
		if (scrnBG != null) {
			welcomeScreen = new WelcomeScreen (scrnBG);
		}
		
		return welcomeScreen;
	}
	
	private WelcomeScreen(Bitmap scrnBG) {
		super();
		
		Manager manager = this.getMainManager();
		manager.setBackground(BackgroundFactory.createBitmapBackground(scrnBG, Background.POSITION_X_CENTER, Background.POSITION_Y_CENTER, Background.REPEAT_NONE));
	}
	
	protected boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ESCAPE) {
			return true;
		}
		
		return super.keyChar(character, status, time);
	}
	
	/**
	 * This will remove the save prompt message when closing the window
	 * */
	protected boolean onSavePrompt() {
		return true;		
	}
}
