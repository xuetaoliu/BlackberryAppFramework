/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2012.0130
 * @comments    ExtendedButtonField is an extension of Blackberry standard EditField with the following features:
 *                1) support set text Color
 *                2) extended the enable/disable function  --> Blackberry 5.0 doesn't support this 
 *
 * @history
 *              Date        Author  Comments
 *              2012/01/30  X.L.    change text color to Gray when button is disabled
 *              2012/01/24  X.L.    change the feature that only when sth is inputed in the editField, we will hide the hintMsg
 **/

package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldInterface;

import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ButtonField;

public class ExtendedButtonField extends ButtonField implements CMFieldInterface{

	public ExtendedButtonField(String label, long style) {
		super(label, style | ButtonField.CONSUME_CLICK | ButtonField.VCENTER);
		if ((style & ButtonField.USE_ALL_WIDTH) != 0)
			setFieldWidth(1000);
		
		this.setTextColor(DEFAULT_COLOR_TEXT, DEFAULT_COLOR_TEXT_FOCUS);
		
		setLayoutStyle(DrawStyle.LEFT);
		this.setFieldPadding(0, 0);
	}

	
	/**
	 * Functions for CMFieldInterface
	 * */
	private int textColor;
	private int textColor_f;
	public void setTextColor(int color, int focusColor) {
		this.textColor = color;
		this.textColor_f = focusColor;
	}
	
	public void setBackgroundColor(int color, int focusColor) { }
	public void setBorderColor(int color, int foucsColor) { }
	
	private int fieldWidth = 0;
	public void setFieldWidth(int width) { fieldWidth = width; }
	public int getFieldWidth() {
		if (fieldWidth == 0)
			fieldWidth = getPreferredWidth();
		
		return fieldWidth;
	}

	private int fieldHeight = 0;
	public void setFieldHeight(int height) { fieldHeight = height; }
	public int getFieldHeight() {
		if (fieldHeight == 0)
			fieldHeight = getPreferredHeight();
		return fieldHeight;
	}
	
//	private int horizontalPadding;
//	private int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
//		this.horizontalPadding = horizontalPadding;
//		this.verticalPadding = verticalPadding;
	}

	private int layoutStyle;
	public void setLayoutStyle(int style) { layoutStyle = style; }
	public int getLayoutStyle() { return layoutStyle; }

	private boolean focusable = true;
	public void setFocusable(boolean focusable) { 
		this.focusable = focusable; 
		this.invalidate();
	}
	public boolean isFocusable() { return focusable; }

	public void drawFocus(Graphics graphics, boolean on) {
		if (this.textColor_f != -1) {
			graphics.setColor(this.textColor_f);
		}
		
		super.drawFocus(graphics, on);
	}
	
	public void paint(Graphics graphics) {
		if (this.isFocusable())
			graphics.setColor(textColor);
		else 
			graphics.setColor(DEFAULT_COLOR_TEXT_UNFOCUSABLE);
		
		super.paint(graphics);
	}
	
	public int getPreferredWidth() {
		int width = fieldWidth;
		if (width == 0)
			width = super.getPreferredWidth();

		return width;
	}
	
	public int getPreferredHeight() {
		int height = fieldHeight;
		if (height == 0)
			height = super.getPreferredHeight();

		return height;
	}
}
