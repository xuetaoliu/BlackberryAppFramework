package com.blackberryappframework.ui.screen;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.blackberryappframework.CMApplication.CMUiApplication;
import com.blackberryappframework.TemplateProject.appConfiguration.AppCommands;
import com.blackberryappframework.TemplateProject.appConfiguration.AppConfiguration;
import com.blackberryappframework.TemplateProject.appConfiguration.ResourceLoader;
import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.field.CMActiveRichTextField;
import com.blackberryappframework.ui.field.CustomerButton;
import com.blackberryappframework.ui.field.StandardLabelField;
import com.blackberryappframework.ui.screen.general.StandardScreen;
import com.blackberryappframework.utility.XmlUtils;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.GridFieldManager;

public class TermsNCondsScreen extends StandardScreen implements FieldChangeListener
{
	private static final int TITLE = 1;
	private static final int HEADING = 2;
	private static final int BODY = 3;
	private static String TITLE_S = "title";
	private static String HEADING_S = "heading";
	private static String BODY_S = "body";
	
	private boolean acceptedTerms;
	private CustomerButton agreeField;
	private CustomerButton disagreeField;
	public TermsNCondsScreen(String urlStr) {
		super();
		
		this.acceptedTerms = appSetting.getAppConfiguration().acceptedTerms();
		if (acceptedTerms) {
			this.footerManager.deleteAll();
		}
		
//		browserField.requestContent(urlStr);
		displayTermsNConds(urlStr);
	}
	
	protected void initializeScreenHeader() {
		super.initializeScreenHeader();
		
		StandardLabelField lblField = new StandardLabelField("Terms and Conditions", StandardLabelField.FIELD_HCENTER | StandardLabelField.FIELD_VCENTER, false);
		lblField.setPadding(CMFieldCommon.DEFAULT_VERTICAL_PADDING, CMFieldCommon.DEFAULT_HORIZONTAL_PADDING, CMFieldCommon.DEFAULT_VERTICAL_PADDING, CMFieldCommon.DEFAULT_HORIZONTAL_PADDING);
		lblField.setTextColor(Color.WHITE, -1);
		lblField.setFont(getFont().derive(Font.BOLD));
		this.headerManager.add(lblField);
	}
	
//	private BrowserField browserField;
	protected void initializeScreenBody() {
		super.initializeScreenBody();
		
//		browserField = new BrowserField();
//		browserField.addListener(new BrowserListener());
//		this.contentManager.add(browserField);
		
		
	}
	
	protected void initializeScreenFooter() {
		super.initializeScreenFooter();
		
		Font font = super.getFont();
		
		GridFieldManager gfm = new GridFieldManager(1, 2, GridFieldManager.USE_ALL_WIDTH | GridFieldManager.NO_VERTICAL_SCROLL);
		gfm.setColumnProperty(0, GridFieldManager.AUTO_SIZE, 100);
		gfm.setColumnProperty(1, GridFieldManager.AUTO_SIZE, 100);
		gfm.setPadding(0,0,0,0);
		this.footerManager.add(gfm);
		
		agreeField = new CustomerButton(ResourceLoader.getString(ResourceLoader.ID_TXT_BTN_ACCEPT), CustomerButton.FIELD_HCENTER);
		agreeField.setPadding(CustomerButton.DEFAULT_VERTICAL_PADDING/2, CustomerButton.DEFAULT_HORIZONTAL_PADDING/2, CustomerButton.DEFAULT_VERTICAL_PADDING/2, CustomerButton.DEFAULT_HORIZONTAL_PADDING/2);
		agreeField.setFont(font.derive(Font.PLAIN, font.getHeight(Ui.UNITS_pt)-2, Ui.UNITS_pt));
		agreeField.setFocusable(true);
		agreeField.setCookie(AppCommands.SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT[0]);
		agreeField.setChangeListener(this);
		gfm.set(agreeField, 0, 0);

		disagreeField = new CustomerButton(ResourceLoader.getString(ResourceLoader.ID_TXT_BTN_DECLINE), CustomerButton.FIELD_HCENTER);
		disagreeField.setPadding(CustomerButton.DEFAULT_VERTICAL_PADDING/2, CustomerButton.DEFAULT_HORIZONTAL_PADDING/2, CustomerButton.DEFAULT_VERTICAL_PADDING/2, CustomerButton.DEFAULT_HORIZONTAL_PADDING/2);
		disagreeField.setFont(font.derive(Font.PLAIN, font.getHeight(Ui.UNITS_pt)-2, Ui.UNITS_pt));
		disagreeField.setFocusable(true);
		disagreeField.setCookie(AppCommands.SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE[0]);
		disagreeField.setChangeListener(this);
		gfm.set(disagreeField, 0, 1);
		
		agreeField.setFieldWidth(disagreeField.getFieldWidth());
	}
	
	private void displayTermsNConds(String urlStr) {
		Document termsCondsXml = XmlUtils.getXMLDocumentFromFile(urlStr);
		if (termsCondsXml != null) {
			Node xmlNode = termsCondsXml.getFirstChild();
			if (xmlNode != null)  {
				NodeList contents = xmlNode.getChildNodes();
				for (int index = 0; index < contents.getLength(); index++) {
					Node contentNode = contents.item(index);
					
					int type = 0;//body
					String nodeTag = contentNode.getNodeName();
					if (nodeTag.equalsIgnoreCase(TITLE_S)) {
						type = TITLE;
					} else if (nodeTag.equalsIgnoreCase(HEADING_S)) {
						type = HEADING;
					} else if (nodeTag.equalsIgnoreCase(BODY_S)) {
						type = BODY;
					}
					
					Font textFont = this.getFont();
					switch (type) {
					case TITLE:
						textFont = textFont.derive(Font.BOLD, textFont.getHeight(Ui.UNITS_pt) + 1, Ui.UNITS_pt);
						break;
					case HEADING:
						textFont = textFont.derive(Font.BOLD);
						break;
					case BODY:
						textFont = textFont.derive(Font.PLAIN);
						break;
					}
					
					String text = XmlUtils.getNodeTextValue(contentNode);
					CMActiveRichTextField uiField = new CMActiveRichTextField(text);
					uiField.setFont(textFont);
					this.contentManager.add(uiField);
				}
			}
		}
	}
	// Disable the Escape key.
	protected boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ESCAPE && !this.acceptedTerms) {
			return true;
		}
		
		return super.keyChar(character, status, time);
	}

	protected void paintBackground(Graphics graphics) {
		graphics.setColor(Color.LIGHTGRAY);
		graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		
		graphics.setColor(Color.GRAY);
		graphics.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
	}
	
	public void fieldChanged(Field field, int context) {
		String cookie = (String) field.getCookie();
		int commandId = AppCommands.getApplicationCmdID(cookie);
		switch (commandId) {
		case AppCommands.KEY_SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT:
			acceptTerms();
			break;
			
		case AppCommands.KEY_SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE:
			declineTerms();
			break;
		}
		
	}
	
	private void acceptTerms() {
		AppConfiguration appConfig = appSetting.getAppConfiguration();
		appConfig.acceptTerms(true);
		
		StandardScreen scrn ;
		scrn = HomeScreen.createHomeScreen();
		
		((CMUiApplication)(appSetting.app)).setMainScreen(scrn);
		appSetting.app.pushScreen(scrn);
		
		this.onClose();
	}
	
	private void declineTerms() {
		appSetting.getAppConfiguration().acceptTerms(false);
		Dialog.alert(ResourceLoader.getString(ResourceLoader.ID_TXT_ACCEPTTERMS));
		
		this.onClose();
	}
}
