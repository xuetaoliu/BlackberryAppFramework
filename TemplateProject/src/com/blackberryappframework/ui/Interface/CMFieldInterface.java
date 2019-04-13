package com.blackberryappframework.ui.Interface;

public interface CMFieldInterface extends CMFieldCommon{

	public void setFieldPadding(int horizontalPadding, int verticalPadding);
	
	public void setFieldWidth(int width);
	public int getFieldWidth();

	public void setFieldHeight(int height);
	public int getFieldHeight();
	
	public void setBackgroundColor(int color, int focusColor);
	public void setTextColor(int color, int focusColor);
	public void setBorderColor(int color, int focusColor);

	
	//the layout style in parent manager
	public void setLayoutStyle(int style);
	public int getLayoutStyle();
	
	public void setFocusable(boolean focusable);
	
}
