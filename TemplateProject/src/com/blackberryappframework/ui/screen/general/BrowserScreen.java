package com.blackberryappframework.ui.screen.general;

import org.w3c.dom.Document;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

public class BrowserScreen extends MainScreen {
	private CMAnimationDialog _dialog;
	private boolean firstTimeRun = true;
	private String errorMsg ;
	
	public BrowserScreen(String url) {
		this(url, null, null, null);
	}
	
	public BrowserScreen(String url, String loadingInfo, String spinnerFileName, String errorMsg) {
		if (loadingInfo != null) {
			_dialog = new CMAnimationDialog(loadingInfo, spinnerFileName, 10000);
		}
		
		this.errorMsg = errorMsg;
		
		BrowserField browser = new BrowserField();
		browser.addListener(new CMBrowserFieldListener());
		this.add(browser);

		browser.requestContent(url);
	}
	
	/**
	 * When this screen is pushed to display, pop a loading dialog if the page is not loaded
	 */
	protected void onFocusNotify(boolean focus) {
		if (focus) {
			if( firstTimeRun ) {
				firstTimeRun  = false;
				pushLoadingIndicator();
			}
		}
	}
	
	private void pushLoadingIndicator() {
		synchronized( UiApplication.getEventLock() ) {
			UiApplication.getUiApplication().pushScreen(_dialog);
		}
	}
	
	private void popLoadingIndicator() {
		synchronized( UiApplication.getEventLock() ) {
			UiApplication app = UiApplication.getUiApplication();
			if(app.getActiveScreen() == _dialog) {
				app.popScreen(_dialog);
			}
		}
	}
	
	private class CMBrowserFieldListener extends BrowserFieldListener {
		// If the loading is finished, pop the dialog
		public void documentLoaded(BrowserField browserField, Document document) throws Exception {
			super.documentLoaded(browserField, document);
			popLoadingIndicator();
		}

		// If error happens, pop the dialog and alert the error
		public void documentError(BrowserField browserField, Document document) throws Exception {
			super.documentError(browserField, document);
			popLoadingIndicator();
			
			browserField.getErrorHandler().displayContentError(document.getBaseURI(), 
					errorMsg + browserField.getDocumentTitle());
		}
	};
}
