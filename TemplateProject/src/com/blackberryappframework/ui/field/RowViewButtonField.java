package com.blackberryappframework.UI.Field;

import java.util.Vector;

import com.blackberryappframework.UI.Definition.MixedContent;
import com.blackberryappframework.UI.Definition.RowViewComponent;
import com.blackberryappframework.UI.Definition.RowViewElement;
import com.blackberryappframework.UI.Interface.ActionListener;
import com.blackberryappframework.UI.Interface.CMFieldCommon;
import com.blackberryappframework.UI.Interface.CMFieldInterface;


import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.ContextMenu;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;

public class RowViewButtonField extends Field implements CMFieldInterface {
	private final int ROUND_RECT = 15;
	
	private Vector components;

	private boolean wrapText_flag;
	private boolean showContextMenu;

	public RowViewButtonField(Vector components) {
		this(components, false, Field.FOCUSABLE);
	}

	public RowViewButtonField(Vector components, boolean showContextMenu, long style) {
		super(Field.USE_ALL_WIDTH | style);
		this.components = components;

		this.setBackgroundColor(-1, CMFieldCommon.DEFAULT_COLOR_BACKGROUND_FOCUS);
		
		this.setFieldPadding(CMFieldCommon.DEFAULT_HORIZONTAL_PADDING/2, CMFieldCommon.DEFAULT_VERTICAL_PADDING/2);
		this.assignWidth(getPreferredWidth());
		this.fieldHeight = this.calculateHeight();

		this.wrapText_flag = false;
		
		this.showContextMenu = showContextMenu;
		contentMenuItems = new Vector();
	}

	public void recalculateSize() { 
		this.fieldHeight = this.calculateHeight();
	}
	
	private int horizontalPadding;
	private int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) { 
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
		
		//shoud NOT call this function, otherwise we can't control the paint
//    	this.setPadding(verticalPadding, horizontalPadding, 
//    			verticalPadding, horizontalPadding);

//    	this.setFieldWidth( this.getPreferredWidth() - this.horizontalPadding * 2);
		fieldHeight= this.calculateHeight() + verticalPadding*2 ;
	}

    private boolean bottomLine = true;
    public void drawBottonLine(boolean on) { this.bottomLine = on; }
    
    private boolean border = false;
    public void drawBorder(boolean on) {this.border = on; }

	private int fieldWidth;
	public void setFieldWidth(int width) { fieldWidth = width; }
	public int getFieldWidth() { return fieldWidth; }

	private int fieldHeight;
	public void setFieldHeight(int height) {
		if (height > fieldHeight)
			fieldHeight = height;
	}
	public int getFieldHeight() { return fieldHeight; }

	public void setPreferredWidth(int width) { }
	public void setPreferredHeight(int height) { }
	
	public void setLayoutStyle(int style) { }
	public int getLayoutStyle() { return 0; }

	private int focusColor;
	public void setBackgroundColor(int color, int focusColor) {
		this.focusColor = focusColor;
	}
	public void setBorderColor(int color, int foucsColor) { }
	public void setTextColor(int color, int focusColor) { }
	
	protected boolean focusable = true;
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}
	
	protected Object data;
	public void setData(Object data) { this.data = data; }
	public Object getData() { return this.data; }
	
	public boolean isFocusable() { return this.focusable; }

	public int getPreferredWidth() { return Display.getWidth() - this.horizontalPadding * 2; }
	public int getPreferredHeight() { return fieldHeight + this.verticalPadding * 2; }

	private Vector contentMenuItems;
	public void addContentMenu(String menuText, int actionId, ActionListener listener) {
		CustomerMenuItem menuItem = new CustomerMenuItem(menuText, contentMenuItems.size(), 0, 0);
		menuItem.setActionID(actionId);
		menuItem.setListener(listener);
		
		contentMenuItems.addElement(menuItem);
	}
	
	protected void makeContextMenu(ContextMenu contextMenu)
	{
//		if (showContextMenu) {
		if (contentMenuItems != null && contentMenuItems.size() > 0) {
			for (int i = 0; i < contentMenuItems.size(); i++) {
				CustomerMenuItem menuItem = (CustomerMenuItem) contentMenuItems.elementAt(i);
				menuItem.data(this.getData());
				contextMenu.addItem( menuItem );
			}
		}
//		}
	};
	
	protected void layout(int width, int height) {
		assignWidth(width);
		fieldHeight = calculateHeight();
		
		setExtent(width, getPreferredHeight());
	}

	protected void paint(Graphics graphics) {
		if(backgroundColor != -1)
		{
			graphics.setColor(this.backgroundColor);
			if (this.border)
				graphics.fillRoundRect(horizontalPadding, 0, this.getPreferredWidth(), this.getPreferredHeight(), ROUND_RECT, ROUND_RECT);
			else
				graphics.fillRect(horizontalPadding, 0, this.getPreferredWidth(), this.getPreferredHeight());
		}
		
		this.drawContent(graphics);

		graphics.setColor(Color.GRAY);
		if (this.bottomLine) {
			graphics.drawLine(horizontalPadding, this.getPreferredHeight()-1, this.getPreferredWidth()*2, this.getPreferredHeight()-1);
		}
		if (this.border) {
			graphics.drawRoundRect(horizontalPadding, 0, this.getPreferredWidth(), this.getPreferredHeight(), ROUND_RECT, ROUND_RECT);
		}
	}

	protected void drawFocus(Graphics graphics, boolean on) {
		graphics.setColor(focusColor);
		if (this.border)
			graphics.fillRoundRect(horizontalPadding, 0, this.getPreferredWidth(), this.getPreferredHeight(), ROUND_RECT, ROUND_RECT);
		else
			graphics.fillRect(horizontalPadding, 0, this.getPreferredWidth(), this.getPreferredHeight());

		this.drawContent(graphics);
	}

	protected boolean navigationClick(int status, int time) {
		fieldChangeNotify(0);
		return true;
	}

	public boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ENTER) {
			fieldChangeNotify(0);
			return true;
		}
		return super.keyChar(character, status, time);
	}

	private void drawContent(Graphics graphics) {
		int posX = this.horizontalPadding + CMFieldCommon.DEFAULT_COLUMN_SPACE;
		int posY = 0;
		for (int i = 0; i < components.size(); i++) {
			RowViewComponent component = (RowViewComponent) components.elementAt(i);
			
			if ((component.getStyle() & RowViewComponent.ROW_VIEW_TOP) != 0)
				posY = this.verticalPadding;
			else if ((component.getStyle() & RowViewComponent.ROW_VIEW_BOTTOM) != 0)
				posY = this.getPreferredHeight() - component.getPreferredHeight();
			else
				posY = (this.getPreferredHeight() - component.getPreferredHeight()) / 2;
			
			int drawPosX = posX;
			int offset = (component.getPreferredWidth() - component.getContentWidth());
			if (offset > 0) {
				if ((component.getStyle() & RowViewComponent.ROW_VIEW_CONTENT_RIGHT) != 0) {
					drawPosX = posX + offset;
				} else if ((component.getStyle() & RowViewComponent.ROW_VIEW_CONTENT_CENTER) != 0) {
					drawPosX = posX + offset / 2;
				} 
			}
			component.drawComponent(graphics, drawPosX, posY, this.wrapText_flag, this.isFocus());
			posX += component.getPreferredWidth() + CMFieldCommon.DEFAULT_COLUMN_SPACE;
		}
	}

	/**
	 * Compute the height of this field, which is the height of the highest
	 * component included in this field.
	 */
	private int calculateHeight() {
		int maxHeight = 0;
		int tempHeight = 10;
		
		RowViewComponent component;
		for (int i = 0; i < components.size(); i++) {
			component = ((RowViewComponent) (components.elementAt(i)));
			tempHeight = component.getPreferredHeight();
			if (tempHeight > maxHeight)
				maxHeight = tempHeight;
		}
		
		return maxHeight;
	}

	/**
	 * assign width for each component, only one component has the style ROW_VIEW_USEALLWIDTH.
	 * 
	 * @param width ------ the width of the whole field.
	 */
	private void assignWidth(int width) {
		RowViewComponent allWidthComponent = null;

		int widthAvailable = width - this.horizontalPadding * 2 - CMFieldCommon.DEFAULT_COLUMN_SPACE;
		for (int i = 0; i < components.size(); i++) {
			RowViewComponent component = (RowViewComponent) components.elementAt(i);
			
			if (component.isCenterComponent()) {
				allWidthComponent = component;
			} else {
				widthAvailable = widthAvailable - component.getPreferredWidth() 
							- CMFieldCommon.DEFAULT_COLUMN_SPACE;
			}
		}
		
		if (allWidthComponent != null)
			allWidthComponent.setPreferredWidth(widthAvailable);
	}

	private int backgroundColor = -1;
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
	}

	public void setTextColor(int color) {
		// TODO Auto-generated method stub
		
	}

	public void setFocusColor(int color) {
		// TODO Auto-generated method stub
		
	}
	
	public void linearUpComponents() {
		Vector linearUpComponents = new Vector();
		
		//only line up components with the same number of contents
		for (int i =0; i < components.size(); i++) {
			RowViewComponent component = (RowViewComponent) components.elementAt(i);
			if (component.linearUpable()) {
				linearUpComponents.addElement(component);
			}
		}
		
		//for the linearUpable mixedContent, the size must be the same and the maximum size
		int maximumSize = 0;
		for (int i = linearUpComponents.size()-1; i >= 0; i--) {
			RowViewComponent component = (RowViewComponent)linearUpComponents.elementAt(i);
			
			MixedContent content = (MixedContent) component.getContent();
			Vector data = content.getContentData();
			if (maximumSize != 0 ) {
				if (data.size() < maximumSize) {
					//delete this content from the vector, which will not be lineared up
					linearUpComponents.removeElementAt(i);
				} else if (data.size() > maximumSize) {
					//delete all the checked the content, since they are not suitable any more 
					for (int delIndex = linearUpComponents.size()-1; delIndex > i; delIndex--) {
						linearUpComponents.removeElementAt(delIndex);
					}
					
					maximumSize = data.size();
				}
			} else {
				maximumSize = data.size();
			}
		}
		
		//linear up
		if (linearUpComponents.size() > 2) {
			for (int idx_element = 0; idx_element < maximumSize; idx_element ++) {
				//get the maximum height
				int maximumHeight = 0;
				for (int idx_component = 0; idx_component < linearUpComponents.size(); idx_component++) {
					RowViewComponent component = (RowViewComponent)linearUpComponents.elementAt(idx_component);
					
					MixedContent content = (MixedContent) component.getContent();
					Vector data = content.getContentData();
					
					RowViewElement element = (RowViewElement)data.elementAt(idx_element);
					if (element.getHeight() > maximumHeight) {
						maximumHeight = element.getHeight();
					}
				}
				
				//assign the maximumHeight
				for (int idx_component = 0; idx_component < linearUpComponents.size(); idx_component++) {
					RowViewComponent component = (RowViewComponent)linearUpComponents.elementAt(idx_component);
					
					MixedContent content = (MixedContent) component.getContent();
					Vector data = content.getContentData();
					
					RowViewElement element = (RowViewElement)data.elementAt(idx_element);
					element.setHeight(maximumHeight);
				}
			}
			
			//recalculate the height of component
			for (int idx_component = 0; idx_component < linearUpComponents.size(); idx_component++) {
				RowViewComponent component = (RowViewComponent)linearUpComponents.elementAt(idx_component);
				component.recalculatePreferredHeight();
			}
			
			//update the size of the rowviewbutton
			this.recalculateSize();
		}
	}

}
