package com.blackberryappframework.ui.screen.general;

import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;

public abstract class CMPopupScreen extends Screen {

	public void close() {
		app.popScreen(this);
	}
	
	private UiApplication app;
	
	protected CMPopupScreen(Manager manager) {
		super(manager, Screen.DEFAULT_CLOSE);
		
		app = UiApplication.getUiApplication();
		
		this.initializeContent();
	}
	
	protected abstract void initializeContent();
	public abstract int getPreferredWidth();
	
	protected void sublayout(int width, int height) {
		
		int scrnMaxWidth = width * 3 / 4;
		int scrnMaxHeight = height * 3 /4;
		int scrnPadding = 10;
		
		int preferredWidth = this.getPreferredWidth();
		if (preferredWidth > scrnMaxWidth)
			preferredWidth = scrnMaxWidth;
		
		//layout fields
		layoutDelegate(preferredWidth, scrnMaxHeight - scrnPadding * 2);
		setPositionDelegate(scrnPadding, scrnPadding);
		
		//set scrn layout
		scrnPadding += scrnPadding*2;
		setExtent(preferredWidth, Math.min(scrnMaxHeight, getDelegate().getHeight() + scrnPadding));
		setPosition((width-preferredWidth) / 2, (height - getHeight()) / 2);
		 
	}
	
}
