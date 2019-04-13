package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.component.BitmapField;

public class CMImageField extends BitmapField implements CMFieldInterface{

	public CMImageField(Bitmap image) {
		this(image, BitmapField.FIELD_VCENTER | BitmapField.FIELD_VCENTER);
	}
	
	public CMImageField(Bitmap image, long style) {
		super(image, BitmapField.FIELD_VCENTER | BitmapField.FIELD_HCENTER | style);
		
		this.setFieldPadding(CMFieldCommon.DEFAULT_HORIZONTAL_PADDING, CMFieldCommon.DEFAULT_VERTICAL_PADDING);
	}

	public void setFieldWidth(int width) { ; }
	public void setFieldHeight(int height) {; }
	public int getFieldWidth() { return this.getBitmapWidth(); }
	public int getFieldHeight() { return this.getBitmapHeight(); }
	public void setPreferredWidth(int width) { }
	public void setPreferredHeight(int height) { }

	public void setLayoutStyle(int style) { ; }
	public int getLayoutStyle() { return DrawStyle.LEFT; }

	private int horizontalPadding;
	private int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) { 
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
		
//		this.setPadding(this.verticalPadding, this.horizontalPadding, this.verticalPadding, this.horizontalPadding);
	}

	public void setBackgroundColor(int color, int focusColor) { }
	public void setTextColor(int color, int focusColor) { }
	public void setBorderColor(int color, int focusColor) { }

	private boolean focusable = false;
	public void setFocusable(boolean focusable) { this.focusable = focusable; }
	
	public boolean isFocusable() { return this.focusable; }
}
