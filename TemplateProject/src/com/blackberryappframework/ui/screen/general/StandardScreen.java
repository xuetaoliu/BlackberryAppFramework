package com.blackberryappframework.ui.screen.general;

import com.blackberryappframework.TemplateProject.appConfiguration.AppSetting;
import com.blackberryappframework.TemplateProject.appConfiguration.ResourceLoader;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;


public class StandardScreen extends MainScreen {

	public StandardScreen() {
		super(MainScreen.NO_VERTICAL_SCROLL);
		
		FeedbackScreen.addFeedbackFunc(this);

		appSetting = AppSetting.getAppSetting();
		
		headerManager = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.NO_VERTICAL_SCROLL);
		this.add(headerManager);

		contentManager = new VerticalFieldManager(VerticalFieldManager.VERTICAL_SCROLL | VerticalFieldManager.VERTICAL_SCROLLBAR);
		this.add(contentManager);

		footerManager = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.NO_VERTICAL_SCROLL);

		initializeScreenHeader();
		initializeScreenBody();
		initializeScreenFooter();
	}
	
	protected AppSetting appSetting;
	
	protected VerticalFieldManager headerManager;
	protected VerticalFieldManager contentManager;
	protected VerticalFieldManager footerManager;
	
	protected void initializeScreenHeader() {
//		Bitmap background = appSetting.loadBitmapFromFile(ResourceLoader.ID_IMG_SCREEN_GENERAL_HEADER);
//		headerManager.setBackground(BackgroundFactory.createBitmapBackground(background));
	}
	
	protected void initializeScreenBody() {
		
	}

	protected void initializeScreenFooter() {
		Bitmap background = appSetting.loadBitmapFromFile(ResourceLoader.ID_IMG_SCREEN_FOOTER_BG);
		footerManager.setBackground(BackgroundFactory.createBitmapBackground(background));
		
		this.setStatus(footerManager);
	}
	
	public boolean onSavePrompt() {
		return true;
	}
}
