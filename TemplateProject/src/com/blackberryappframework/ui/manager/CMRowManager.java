package com.blackberryappframework.ui.manager;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;
import com.blackberryappframework.ui.Interface.CMManagerInterface;


import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class CMRowManager extends HorizontalFieldManager implements CMManagerInterface{

	public CMRowManager() {
		super(Manager.USE_ALL_WIDTH | Manager.NO_HORIZONTAL_SCROLL | Manager.NO_HORIZONTAL_SCROLLBAR);	
	}
	
	public void sublayout(int width, int height) {

		//calculate the width for the centered field and the max height
		int maxFieldHeight = 0;
//		int maxBtnHeight = 0;
		int spareWidth = width - CMFieldCommon.DEFAULT_PADDING * 2;
		CMFieldInterface centerField_i = null;
		for (int idx = 0; idx < this.getFieldCount(); idx++ ) {
			Field field_i = getField(idx);
			
			if (field_i.getPreferredHeight() > maxFieldHeight)
				maxFieldHeight = field_i.getPreferredHeight();
//			if ((field_i instanceof CMImageButton) || (field_i instanceof CustomerButton)) {
//				if (((CMFieldInterface)field_i).getFieldHeight() > maxBtnHeight)
//					maxBtnHeight = ((CMFieldInterface)field_i).getFieldHeight();
//			}
				

			if (((CMFieldInterface)field_i).getLayoutStyle() == DrawStyle.HCENTER) {
				centerField_i = (CMFieldInterface) field_i;
				continue;
			}

			spareWidth -= (field_i.getPreferredWidth() + CMFieldCommon.DEFAULT_COLUMN_SPACE);
		}
		
		if (centerField_i != null)
			centerField_i.setFieldWidth(spareWidth);

		//layout fields
		maxFieldHeight += CMFieldCommon.DEFAULT_PADDING * 2;

		int posX = CMFieldCommon.DEFAULT_PADDING;
		int posY = 0;
		for (int idx = 0; idx < this.getFieldCount(); idx++) {
			Field field = getField(idx);
//			if ((field instanceof CMImageButton) || (field instanceof CustomerButton)) {
//				((CMFieldInterface)field).setFieldHeight(maxBtnHeight);
//			}
			
			posY = (maxFieldHeight - field.getPreferredHeight()) / 2;
			setPositionChild(field, posX, posY);
	    	layoutChild(field, field.getPreferredWidth(), field.getPreferredHeight());

			posX += field.getPreferredWidth() + CMFieldCommon.DEFAULT_COLUMN_SPACE;
		}
	
		setExtent(width, maxFieldHeight);
	}

	private boolean active = false;
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
