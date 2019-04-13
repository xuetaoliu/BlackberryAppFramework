package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldInterface;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class StandardLabelField extends LabelField implements CMFieldInterface {

	public StandardLabelField(String string) {
		super(string);
	}

	public StandardLabelField(String label, long style, boolean focusable) {
		super(label, style);
		this.focusable = focusable;
	}

	private int horizontalPadding;
	private int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
		
		if (this.fieldHeight == 0)
			this.setPadding(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding);
	}

	public void setBackgroundColor(int color, int focusColor) {
	}

	private int textColor = Color.BLACK;
	private int textColor_f = this.DEFAULT_COLOR_BACKGROUND_FOCUS;
	public void setTextColor(int color, int focusColor) {
		this.textColor = color;
		this.textColor_f = focusColor;
	}

	public void setBorderColor(int color, int foucsColor) { }
	
	private int fieldWidth = 0;
	public void setFieldWidth(int width) { fieldWidth = width;}
	public int getFieldWidth() { return fieldWidth; }

	private int fieldHeight = 0;
	public void setFieldHeight(int height) { }
	public int getFieldHeight() { return 0; }

	private int layoutStyle = DrawStyle.LEFT;
	public void setLayoutStyle(int style) { layoutStyle = style; }
	public int getLayoutStyle() { return layoutStyle; }
	 
	private boolean focusable = false;
	public void setFocusable(boolean focusable) { this.focusable = focusable; }
	public boolean isFocusable() { return this.focusable; }
	
	protected void drawFocus(Graphics graphics, boolean on) { ; }
	protected void paint(Graphics graphics) {
		int color = textColor;
		if (this.isFocus()) {
			if (textColor_f != -1) 
				color = textColor_f;
		}
		graphics.setColor(color);
		
		super.paint(graphics);
	}
	
	protected boolean navigationClick(int status,int time) {
		 if(focusable) {
			 fieldChangeNotify(0);
	         return true;
		 } else {
			 return super.navigationClick(status, time);
		 }
	}

	public int getPreferredWidth() {
		if (fieldWidth == 0)
			return super.getPreferredWidth();
		else
			return fieldWidth + this.horizontalPadding * 2;
	}
	public int getPreferredHeight() {
		if (fieldHeight == 0)
			return super.getPreferredHeight();
		else
			return fieldHeight + this.verticalPadding * 2;
	}
}
