package com.blackberryappframework.ui.Interface;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;

public interface CMFieldCommon {
	
	public static final int DEFAULT_LEFT_MARGIN = 10;
	public static final int DEFAULT_RIGHT_MARGIN = 10;
	public static final int DEFAULT_TOP_MARGIN = 5;
	public static final int DEFAULT_BOTTOM_MARGIN = 5;
    
	public static final int DEFAULT_HORIZONTAL_PADDING = 12;
    public static final int DEFAULT_VERTICAL_PADDING = 10;

	public static final int DEFAULT_PADDING = 5;
	
	public static final int DEFAULT_ROUND_RECT = 15;
	
	public static final int DEFAULT_ROW_SPACE = 5;
	public static final int DEFAULT_COLUMN_SPACE = 10;
	public static final int DEFAULT_COMPONENT_SPACE = 5;

	public static final int DEFAULT_COLOR_TEXT             = Color.BLACK;
	public static final int DEFAULT_COLOR_TEXT_FOCUS       = Color.WHITE;
	public static final int DEFAULT_COLOR_TEXT_UNFOCUSABLE = Color.GRAY;
	public static final int DEFAULT_COLOR_BACKGROUND       = 0x00CCCDCF;//Color.GRAY;
	public static final int DEFAULT_COLOR_BACKGROUND_FOCUS = 0x002463C4;//0x00207CFE;
	public static final int DEFAULT_COLOR_BORDER           = 0x00BCBEC2;//0x00ECEEF0;//Color.CRIMSON;
	public static final int DEFAULT_COLOR_BORDER_FOCUS     = 0x000060E6;//Color.WHITE;//0x000060E6;

	public static final int DEFAULT_COLOR_BG_GRADIENT_START = 0x00E6E6E6;
	public static final int DEFAULT_COLOR_BG_GRADIENT_END   = 0x00BBBDBF;
	public static final int DEFAULT_COLOR_BG_FOCUS_GRADIENT_START = 0x00098CEE;
	public static final int DEFAULT_COLOR_BG_FOCUS_GRADIENT_END   = 0x001766DB;
	
	public static final Font DEFAULT_FONT = Font.getDefault();
	
	//layout
	public static final int TOP_DOWN   = 0; //default layout
	public static final int LEFT_RIGHT = 1;
	

}
