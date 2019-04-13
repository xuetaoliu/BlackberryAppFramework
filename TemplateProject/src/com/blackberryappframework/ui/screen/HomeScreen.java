package com.blackberryappframework.ui.screen;

import com.blackberryappframework.ui.screen.general.StandardScreen;

import net.rim.device.api.ui.component.LabelField;


public class HomeScreen extends StandardScreen {

	public static HomeScreen createHomeScreen() {
		HomeScreen scrn = new HomeScreen();
		
		return scrn;
	}
	
	public HomeScreen() {
		super();
		
	}
	
	protected void initializeScreenHeader() {
		super.initializeScreenHeader();

		this.headerManager.add(new LabelField("Add your HEADER components here"));
	}

	protected void initializeScreenBody() {
		super.initializeScreenBody();
		
		this.contentManager.add(new LabelField("Add your CONTENT components here"));
	}
	
	protected void initializeScreenFooter() {
		super.initializeScreenFooter();
		
		this.footerManager.add(new LabelField("Add your FOOTER components here"));
	}

}
