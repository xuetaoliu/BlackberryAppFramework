/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0914
 * @comments    CMImageButton is a field which can display a bitmap and/or text at the same time
 *              The layout of the bitmap and text can be left-right (default) or top-down                                 
 *
 *              To implement feature:
 *                 1) to support multi-line label --- MUST_HAVE
 *
 * @history
 *              Date        Author  Comments
 *              2011/09/14  X.L.    created based on previous version
 **/

package com.blackberryappframework.ui.field;

import java.util.Vector;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;
import com.blackberryappframework.utility.GeneralUtils;
import com.blackberryappframework.utility.StringUtils;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYRect;

public class CMImageButton extends Field implements CMFieldInterface {

	//property
	protected Bitmap active_image;
	protected Bitmap image;
	protected Bitmap image_focus;
	protected String label;
	
	protected int iconLayout;

	//field display feature
	private int fieldLayout ;

	protected int fieldWidth;
	protected int fieldHeight;
	
	protected int horizontalPadding;
	protected int verticalPadding;
	
	protected int textColor;
	protected int textColor_focus;
	protected int backgroundColor;
	protected int backgroundColor_focus;
	protected int borderColor;
	protected int borderColor_focus;
	
	protected int maxLabelLines;
	
	//field control feature
	protected boolean enabled;
	
	public CMImageButton(String label, Bitmap icon, Bitmap icon_f, int iconLayout) {
		this(label, icon, icon_f, iconLayout, Field.FIELD_HCENTER);
	}
	
	public CMImageButton(String label, Bitmap icon, Bitmap icon_f, int iconLayout, long style) {
		super(style | Field.FOCUSABLE);
		
		this.active_image = icon;
		this.image = icon;
		this.image_focus = icon_f;
		this.label = label;
		this.iconLayout = iconLayout;
		
		this.enabled = true;
		
		this.maxLabelLines = 1;
		
		this.setTextColor(CMFieldCommon.DEFAULT_COLOR_TEXT, CMFieldCommon.DEFAULT_COLOR_TEXT_FOCUS);
		this.setBackgroundColor(CMFieldCommon.DEFAULT_COLOR_BACKGROUND, CMFieldCommon.DEFAULT_COLOR_BACKGROUND_FOCUS);
		this.setBorderColor(CMFieldCommon.DEFAULT_COLOR_BORDER, CMFieldCommon.DEFAULT_COLOR_BORDER_FOCUS);
		
		setFieldPadding(CMFieldCommon.DEFAULT_HORIZONTAL_PADDING, CMFieldCommon.DEFAULT_VERTICAL_PADDING);
		this.adjustButtonSize();
	}
	
	public String getLabel() {return this.label;}
	public void enable(boolean on) { this.enabled = on; }
	
	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
		
		//this.setPadding(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding);
	}
	
	public void setFieldWidth(int width) { this.fieldWidth = width; }
	public int getFieldWidth() { return this.fieldWidth; }
	public void setFieldHeight(int height) { this.fieldHeight = height; }
	public int getFieldHeight() { return this.fieldHeight; }

	public void setLayoutStyle(int style) { this.fieldLayout = style; }
	public int getLayoutStyle() { return this.fieldLayout; }

	public void setBackgroundColor(int color, int focusColor) {
		this.backgroundColor = color;
		this.backgroundColor_focus = focusColor;
	}
	public void setTextColor(int color, int focusColor) {
		this.textColor = color;
		this.textColor_focus = focusColor;
	}
	
	public void setBorderColor(int color, int focusColor) {
		this.borderColor = color;
		this.borderColor_focus = focusColor;
	}
	
	protected boolean focusable = true;
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}
	public boolean isFocusable() { return this.focusable; }

	public void setPreferredWidth(int width) {  setFieldWidth( width - horizontalPadding * 2 ); }
	public void setPreferredHeight(int height) { setFieldHeight ( height - verticalPadding * 2 ); }
	public int getPreferredWidth() {  return this.fieldWidth + horizontalPadding * 2; }
	public int getPreferredHeight() { return this.fieldHeight + verticalPadding * 2; }
	
	public void setDisplayWidth(int width) {
		if (width > this.fieldWidth) {
			horizontalPadding = (width - fieldWidth) / 2; 
		}
	}
	public void setDisplayHeight(int height) {
		if (height > this.fieldHeight) {
			verticalPadding = (height - fieldHeight) / 2; 
		}
	}
	
	public void wrapLabel(int maxLines, int textWidth) { 
		if ( (image != null) && (textWidth < image.getWidth())) {
			this.fieldWidth = image.getWidth();
		} else {
			this.fieldWidth = textWidth;
		}

		this.maxLabelLines = maxLines;
		this.adjustButtonSize();
	}

	public void setFont(Font font) {
		super.setFont(font);
		this.adjustButtonSize();
	}

	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(), getPreferredHeight());
	}

	protected void paint(Graphics graphics) {
		if(this.backgroundColor != -1) {
			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
													   getPreferredWidth(), getPreferredHeight(), 
													   backgroundColor, backgroundColor, true, false);
		} 
//		else {
//			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
//					   getPreferredWidth(), getPreferredHeight(), 
//					   backgroundColor, backgroundColor, true, false);
//		}
		
		if (this.borderColor != -1) {
			graphics.setColor(borderColor);
			graphics.drawRoundRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight(), CMFieldCommon.DEFAULT_ROUND_RECT,CMFieldCommon.DEFAULT_ROUND_RECT );
		}
		
		paintImageText(graphics, this.active_image);
	}

	protected void drawFocus(Graphics graphics, boolean on) {
		if (! this.enabled) return;
		
		if(this.backgroundColor_focus != -1) {
			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
													   getPreferredWidth(), getPreferredHeight(), 
													   backgroundColor_focus, backgroundColor_focus, true, true);
		} 
//		else {
//			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
//					   getPreferredWidth(), getPreferredHeight(), 
//					   backgroundColor_focus, backgroundColor_focus, true, true);
//		}
		
		if (this.borderColor_focus != -1) {
			graphics.setColor(borderColor_focus);
			graphics.drawRoundRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight(), CMFieldCommon.DEFAULT_ROUND_RECT,CMFieldCommon.DEFAULT_ROUND_RECT );
		}
		
		Bitmap icon = image_focus == null ? image : image_focus;
		paintImageText(graphics, icon);
	}

	protected void paintImageText(Graphics graphics, Bitmap icon) {
		int posX_image = horizontalPadding;
		int posY_image = verticalPadding;
		int posX_label = horizontalPadding;
		int posY_label = verticalPadding;
		
		int iconHeight = icon.getHeight();
		int iconWidth  = icon.getWidth();

		switch (iconLayout) {
		case CMFieldCommon.LEFT_RIGHT:
			posX_image = horizontalPadding;
			posY_image = ( getPreferredHeight() - iconHeight ) / 2;
			
			posX_label = posX_image + iconWidth + CMFieldCommon.DEFAULT_COLUMN_SPACE;
			posY_label = verticalPadding;
			break;

		case CMFieldCommon.TOP_DOWN:
		default:
			posX_image = ( this.getPreferredWidth() - iconWidth ) / 2;
			posY_image = verticalPadding;
			
			posX_label = 0;
			posY_label = posY_image + iconHeight + CMFieldCommon.DEFAULT_ROW_SPACE;
			break;			
		}

		int maxTextWidth = this.fieldWidth;
		if (iconLayout == CMFieldCommon.LEFT_RIGHT)
			maxTextWidth = fieldWidth - iconWidth - CMFieldCommon.DEFAULT_COLUMN_SPACE;

		int maxTextHeight = this.fieldHeight;
		if (iconLayout == CMFieldCommon.TOP_DOWN) {
			maxTextHeight = fieldHeight - iconHeight - CMFieldCommon.DEFAULT_ROW_SPACE;
		}
		
		paintImage(graphics, icon, posX_image, posY_image);
		paintText(graphics, posX_label, posY_label, maxTextWidth, maxTextHeight);
	}
	
	protected void paintImage(Graphics graphics, Bitmap icon, int posX, int posY) {
		if (icon == null) return;
		
		int iconHeight = icon.getHeight();
		int iconWidth  = icon.getWidth();

		//draw image
		XYRect iconRect=new XYRect(posX, posY, iconWidth, iconHeight);
		graphics.drawBitmap(iconRect, icon, 0, 0);
	}
	
	protected void paintText(Graphics graphics, int posX, int posY, int maxTextWidth, int maxTextHeight) {
		if (label == null) return;
		
		Font font = getFont();
		Vector labelLines;
		if (this.maxLabelLines == 1) {
			labelLines = new Vector();
			labelLines.addElement(label);
		} else {
			labelLines = StringUtils.wrapText(label, maxTextWidth, false, font);
		}
		
		int size = labelLines.size() > maxLabelLines ? maxLabelLines : labelLines.size();
		int textHeight = font.getHeight() * size;
		textHeight += CMFieldCommon.DEFAULT_ROW_SPACE * (size - 1);

		int linePosX = posX;
		int linePosY = posY;
		if (textHeight < maxTextHeight) {
			linePosY += (maxTextHeight - textHeight) / 2;
		} 
		
		if (this.isFocus()) 
			graphics.setColor(textColor_focus);
		else
			graphics.setColor(textColor);
		graphics.setFont(font);
		for (int i = 0; i < labelLines.size(); i++ ){
			String line = (String) labelLines.elementAt(i);
			
			if (this.iconLayout == CMFieldCommon.TOP_DOWN) {
				linePosX = (this.getPreferredWidth() - font.getAdvance(line)) / 2;
			}

			if (i < this.maxLabelLines - 1) {
				graphics.drawText(line, linePosX, linePosY);
			} else {
				if (labelLines.size() <= this.maxLabelLines) {
					graphics.drawText(line, linePosX, linePosY, DrawStyle.ELLIPSIS, maxTextWidth);
				}else{
					if (this.iconLayout == CMFieldCommon.TOP_DOWN) 
						linePosX = this.horizontalPadding;
				
					graphics.drawText(line + (String) labelLines.elementAt(i+1), linePosX, linePosY, DrawStyle.ELLIPSIS, maxTextWidth);
					break;
				} 
			}
				
			linePosY += font.getHeight() ;
		}
	}
	
	protected void adjustButtonSize() {
		int width;
		int height;
		
		Font font = getFont();
		int textWidth = 0;
		int textHeight = 0;
		if (label != null) {
			textWidth = font.getAdvance(label);
			textHeight = font.getHeight() * maxLabelLines;
		}
		
		int iconWidth = 0;
		int iconHeight = 0;
		if (image != null) {
			iconWidth = image.getWidth();
			iconHeight = image.getHeight();
		}

		switch (iconLayout) {
		case CMFieldCommon.LEFT_RIGHT: 
			height = Math.max(iconHeight, textHeight);

			if (this.maxLabelLines != 1){
				width = this.fieldWidth;
			}else {
				width = iconWidth + textWidth;
				if ( (textWidth != 0) && (iconWidth != 0) ) 
					width += CMFieldCommon.DEFAULT_COLUMN_SPACE;
			} 
			break;
			
		case CMFieldCommon.TOP_DOWN:
		default:
			if (this.maxLabelLines != 1) {
				width = this.fieldWidth;
			} else {
				width = iconWidth > textWidth ? iconWidth : textWidth;
			}
			
			height = iconHeight + textHeight;
			if ( (textHeight != 0) && (iconHeight != 0) ) 
				height += CMFieldCommon.DEFAULT_ROW_SPACE;
			
			break;
		}
		
		
		setFieldWidth (width);
		setFieldHeight(height);
	}

    protected boolean navigationClick(int status, int time) {
    	if (this.enabled) 
    		fieldChangeNotify(1);
    	return true;
    } 
    
    protected boolean keyDown(int keycode, int time) {
		if (keycode == Keypad.KEY_ENTER) {
			if (this.enabled) 
				fieldChangeNotify(1);
			return true;
		}
		
		return false;
	}
    
    protected boolean touchEvent(TouchEvent message) {
        
        // Get the screen coordinates of the touch event           
        int eventCode = message.getEvent();
        
        if (eventCode == TouchEvent.CLICK) {
	    	if (!this.enabled) {
	    		return true;
	    	}
	    }
        
        return super.touchEvent(message);
    }

}
