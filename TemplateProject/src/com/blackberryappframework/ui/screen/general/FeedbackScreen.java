package com.blackberryappframework.ui.screen.general;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.global.Formatter;
import javax.microedition.io.HttpConnection;

import com.blackberryappframework.CMAppConfiguration.Logging.Logger;
import com.blackberryappframework.TemplateProject.appConfiguration.AppDefinitions;
import com.blackberryappframework.TemplateProject.appConfiguration.AppSetting;
import com.blackberryappframework.TemplateProject.appConfiguration.ResourceLoader;
import com.blackberryappframework.ui.field.ExtendedButtonField;
import com.blackberryappframework.ui.field.ExtendedEditField;
import com.blackberryappframework.ui.field.StandardLabelField;
import com.blackberryappframework.utility.CMBBApplication;
import com.blackberryappframework.utility.ConnectionUtils;

import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class FeedbackScreen extends MainScreen implements FieldChangeListener{
	public static void addFeedbackFunc(MainScreen scrn) {
		if (!AppDefinitions.debug)
			return;
		if (scrn == null)
			return;
		
		scrn.addMenuItem(MenuItem.separator(0));
		MenuItem item = new MenuItem(ResourceLoader.getFamily(), ResourceLoader.ID_TXT_CLEAN_STORAGE, 0, 0) {
			public void run() {
				AppSetting.getAppSetting().initializePersistentData(true);
				System.exit(0);
			}
		};
		scrn.addMenuItem(item);
		
		item = new MenuItem(ResourceLoader.getFamily(), ResourceLoader.ID_TXT_FEEDBACK, 0, 0) {
			public void run() {
				FeedbackScreen.launchFeedbackScreen();
			}
		};
		
		scrn.addMenuItem(item);
		scrn.addMenuItem(MenuItem.separator(0));
	}
	
	private static void launchFeedbackScreen() {
		FeedbackScreen scrn = new FeedbackScreen();
		UiApplication.getUiApplication().pushScreen(scrn);
	}
	
	private ExtendedEditField subjectEditFld;
	private ExtendedEditField messageEditFld;
	private FeedbackScreen() {
		super(MainScreen.NO_VERTICAL_SCROLL);
		
		Manager mainManager = this.getMainManager();

		Font font;
		LabelField lblFld;
		
		VerticalFieldManager vfm ;
		//header
		vfm = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.NO_VERTICAL_SCROLL);
		vfm.setBackground(BackgroundFactory.createLinearGradientBackground(0x527BA3, 0x527BA3, 0x003165,0x003165));
		mainManager.add(vfm);
		
		lblFld = new LabelField("", LabelField.USE_ALL_WIDTH | LabelField.HCENTER); 
		font = lblFld.getFont();
		lblFld.setFont(font.derive(Font.BOLD, 5, Ui.UNITS_pt));
		vfm.add(lblFld);

		lblFld = new StandardLabelField("Feedback", LabelField.USE_ALL_WIDTH | LabelField.HCENTER, false); 
		font = lblFld.getFont();
		lblFld.setFont(font.derive(Font.BOLD, font.getHeight(Ui.UNITS_pt) + 1, Ui.UNITS_pt));
		((StandardLabelField)lblFld).setTextColor(Color.WHITE, -1);
		vfm.add(lblFld);
		
		lblFld = new LabelField("", LabelField.USE_ALL_WIDTH | LabelField.HCENTER); 
		font = lblFld.getFont();
		lblFld.setFont(font.derive(Font.BOLD, 5, Ui.UNITS_pt));
		vfm.add(lblFld);

		
		//content
		vfm = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH);
		mainManager.add(vfm);
		
		lblFld = new LabelField("Summary", LabelField.USE_ALL_WIDTH); 
		lblFld.setFont(font.derive(Font.BOLD));
		subjectEditFld = new ExtendedEditField("Please input a subject", ExtendedEditField.EDITABLE);
		vfm.add(lblFld);
		vfm.add(subjectEditFld);
		
		lblFld = new LabelField("Message", LabelField.USE_ALL_WIDTH); 
		lblFld.setFont(font.derive(Font.BOLD));
		messageEditFld = new ExtendedEditField("Please input your message here.", ExtendedEditField.EDITABLE);
		vfm.add(lblFld);
		vfm.add(messageEditFld);
		
		//footer
		ExtendedButtonField btnField = new ExtendedButtonField("Submit", ExtendedButtonField.HCENTER | ExtendedButtonField.FIELD_HCENTER);
		btnField.setChangeListener(this);
		this.setStatus(btnField);
		
	}
	
	/**
	 * This will remove the save prompt message when closing the window
	 * */
	protected boolean onSavePrompt() {
		return true;		
	}

	public void fieldChanged(Field field, int context) {
		if (field instanceof ExtendedButtonField) {
			String subject = subjectEditFld.getText();
			String message = messageEditFld.getText();
			if ( message == null || message.trim().length() == 0) {
				Dialog.alert("Please input the message!");
				return;
			}
			
			String moduleName = ApplicationDescriptor.currentApplicationDescriptor().getModuleName();
			String msgSubject = Formatter.formatMessage( "[{0}] {1}", 
					                                  new String[] { 
					                                        moduleName, 
					                                        subject 
					                                  });
			String feedbackMsg = composeMessage(msgSubject, message);
			Feedback submitFeedback = new Feedback(feedbackMsg);
			submitFeedback.run();
			while (submitFeedback.isAlive()) ;

			if (submitFeedback.responseCode == 202 || submitFeedback.responseCode == 304 ) {
				Dialog.inform("Thanks for your feedback. We will try to check the problem you reported.");
				
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication.getUiApplication().popScreen(FeedbackScreen.this);
		 			}
				});

			} else {
				Dialog.alert("Failed to submit the feedback. Please resubmit.");
			}
			
		}
	}
	
	private String composeMessage(String subject, String content) {
		/*
		 		   {
			     "summary" : "the summary of this feedback",
			     "description" : "the detailed description",
			     "report_date_time" : "",
			     "project_name" : "name of the project",
			     "project_version" : "1.0.0",
			     "platform" : "blackberry/android/iPhone/iPad",
			     "platform_version" : "5.0.0.193/2.0.1/",
			     "device_type" : "8520/Sumsung galaxy / iPhone4S",
			     "logs" : [
			                 "timestamp, class, function, logid, param1, param2, �",
			                 "timestamp, class, function, logid, param1, param2, �"
			              ],
			   }

		 * */
		String moduleName = ApplicationDescriptor.currentApplicationDescriptor().getModuleName();
		String versions   = ApplicationDescriptor.currentApplicationDescriptor().getVersion();
		String logs = Logger.getLogger().fetchLogs().toString();

		String message = Formatter.formatMessage("\"{00}\" : \"{01}\", " +
												  "\"{02}\" : \"{03}\", " +
												  "\"{04}\" : \"{05}\", " +
												  "\"{06}\" : \"{07}\", " +
												  "\"{08}\" : \"{09}\", " +
												  "\"{10}\" : \"{11}\", " +
												  "\"{12}\" : \"{13}\", " +
												  "\"{14}\" : \"{15}\", " +
												  "\"{16}\" : [ {17} ], ", 
										new String[] {
											"summary"         , subject,
											"description"     , content,
											"report_date_time", "",
											"project_name"    , moduleName,
											"project_version" , versions,
											"platform"        , "Blackberry",
											"platform_version", CMBBApplication.getPlatformVersion() + "." + CMBBApplication.getOSVersion(),
											"device_type"     , CMBBApplication.getDeviceName(),
											"logs"            , logs
										});
		return "{" + message + "}";
	}
	private class Feedback extends Thread {
		public Feedback(String message) {
			this.message = message;
		}
		
		private String message;
		public int responseCode = -1;
		public void run() {
			HttpConnection connection = null;
			OutputStream os = null;
			try {
				String urlStr = Formatter.formatMessage("{0}?project={1}", 
														new String[] {
						                                    AppDefinitions.APP_BETA_FEEDBACK_URL,
															"11"});
				connection = ConnectionUtils.getHttpConnection(urlStr);
				if (connection != null) {
					connection.setRequestMethod(HttpConnection.POST);
					connection.setRequestProperty("Content-Type", "application/jason; charset=utf-8");
					connection.setRequestProperty("User-Agent", "BlackBerry/" + CMBBApplication.getDeviceName());

					os = connection.openOutputStream();
					os.write(message.getBytes("UTF-8"));
					os.flush();
					os.close();
					
					responseCode = connection.getResponseCode();
				} 
			} catch (IOException e) {
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (IOException e) { }
			}
		}
	}
}
