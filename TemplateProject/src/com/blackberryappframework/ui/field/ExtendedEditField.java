/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2012.0124
 * @comments    ExtendedEditField is an extension of Blackberry standard EditField with the following features:
 *                1) support Hint message
 *
 * @history
 *              Date        Author  Comments
 *              2012/02/13  X.L.    fixed a bug when hintmsg is null, the filter doesn't work as desired
 *              2012/01/24  X.L.    change the feature that only when sth is inputed in the editField, we will hide the hintMsg
 **/


package com.blackberryappframework.ui.field;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.text.TextFilter;

public class ExtendedEditField extends EditField {
	
	public ExtendedEditField() {
		this("Input data", EditField.EDITABLE);
	}
	public ExtendedEditField(String hintMsg, long style) {
		this("", hintMsg, EditField.DEFAULT_MAXCHARS, style);
	}
	public ExtendedEditField(String text, String hintMsg, int maxChars, long style) {
		super("", text, maxChars, style);
		
		this.setHintMsg(hintMsg);
		txtFilter = this.getFilter();
		
		drawHintMsg();
		
		this.setPadding(6, 6, 6, 6);
	}
	
	private String hintMsg;
	public void setHintMsg(String hintMsg) { this.hintMsg = hintMsg; }
	public String getHintMsg() { return hintMsg; }
	
	private int hintColor = Color.GRAY;
	private boolean drawHint = false;

	private TextFilter txtFilter;

	private boolean allowNewLine = false;
	public void newLine(boolean newLine) { this.allowNewLine = newLine; }
	
	public String getText() {
		String text = super.getText();
		if (text.equalsIgnoreCase(hintMsg)) {
			text = "";
		}
		return text;
	}
	
	public void setFilter(TextFilter filter) {
		super.setFilter(filter);
		
		if (filter != TextFilter.get(TextFilter.DEFAULT))
			txtFilter = filter;
	}
	
	protected void onFocus(int direction) {
		String text = super.getText();
		if (hintMsg != null && hintMsg.length() > 0) {
			if (text.equalsIgnoreCase(hintMsg)) {
				setFilter(txtFilter);
				setText("");
			}
		}
		
		super.onFocus(direction);
	}
	
	protected void onUnfocus() {
		drawHintMsg();
		
		super.onUnfocus();
	}
	
	protected void paint(Graphics graphics) {
		if (drawHint) {
			graphics.setColor(hintColor);
		} 
		
		super.paint(graphics);
	}
	
	protected boolean keyChar(char key, int status, int time) {
		if (key == Keypad.KEY_ENTER && !this.allowNewLine)
			return false;

		drawHint = false;

		boolean result = super.keyChar(key, status, time);
		return result;
	}

	public int getPreferredWidth() {
		int width = fieldWidth;
		if (width == 0)
			width = super.getPreferredWidth();

		Border border = this.getBorder();
		if (border != null)
			width += border.getLeft() + border.getRight();
		
		return width;
	}
	public int getPreferredHeight() {
		int height = fieldHeight;
		if (height == 0)
			height = super.getPreferredHeight();

		Border border = this.getBorder();
		if (border != null)
			height += border.getTop() + border.getBottom();
		return height;
	}
	
	private void drawHintMsg() {
		String text = super.getText();
		
		if ((hintMsg != null && hintMsg.trim().length() > 0)) {
			if ((text == null || text.trim().length() == 0 || text.trim().equalsIgnoreCase(hintMsg.trim()))) {
				this.drawHint = true;
				this.setFilter(TextFilter.get(TextFilter.DEFAULT));
				this.setText(hintMsg);
			}
		}
	}
	/**
	 * Functions for CMFieldInterface
	 * */

	private int fieldWidth = 0;
	public void setFieldWidth(int width) {
		fieldWidth = width;
	}
	public int getFieldWidth() {
		if (fieldWidth == 0)
			return this.getPreferredWidth();
		else
			return fieldWidth;
	}
	
	private int fieldHeight = 0;
	public void setFieldHeight(int height) {
		fieldHeight = height;
	}
	public int getFieldHeight() {
		if (fieldHeight == 0)
			return this.getPreferredHeight();
		else
			return fieldHeight;
	}
	
	public void setBackgroundColor(int color) { }
	public void setTextColor(int color) { }
	public void setFocusColor(int color) { }
	public void setBorderColor(int color, int foucsColor) { }
	
	private int fieldLayout = DrawStyle.LEFT;
	public void setLayoutStyle(int style) { 
		this.fieldLayout = style;
	}
	public int getLayoutStyle() {
		return this.fieldLayout;
	}
	
}
