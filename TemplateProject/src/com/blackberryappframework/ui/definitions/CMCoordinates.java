package com.blackberryappframework.ui.definitions;

import java.util.Vector;

import javax.microedition.location.Coordinates;


import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;

public class CMCoordinates extends Coordinates {

	private final int maxDescWidth = Display.getWidth() * 3 /4;
	
	private int dataId;
	
	private Bitmap mapPin;
	private String mapDesc;
	private Vector detailDesc;
	private Bitmap detailBackground;
	
	private Object detailData;
	public Object getDetailData() { return detailData; }
	public void setDetailData(Object data) { detailData = data; }
	
	
	private int fieldWidth;
	private int fieldHeight;
	
	public CMCoordinates(Coordinates coordinates) { 
		this(coordinates.getLatitude(), coordinates.getLongitude(), coordinates.getAltitude());
	}
	public CMCoordinates(double latitude,double longitude,float altitude){
		super(latitude, longitude, altitude);
		
		setId(-1);
		
		detailDesc = new Vector();
		detailBackground = null;
		fieldWidth = 0;
		fieldHeight= 0;
	}

	public void setId(int id) { this.dataId = id; }
	public int getId() { return this.dataId; }
	
	public void setMapPin(Bitmap mapPin) { this.mapPin = mapPin; }
	public Bitmap getMapPin() { return mapPin; }

	public void setMapDes(String mapDes) { this.mapDesc = mapDes; }
	public String getMapDes() { return mapDesc; }
	
	public void setDetailInfoBackground(Bitmap background) {
		this.detailBackground = background;
	}
	public void addDetailDescription( RowViewComponent component) { 
		detailDesc.addElement(component);
		layoutComponent();
	} 
	public Vector getDetailDes() { return detailDesc; }
	
	public void recalculateSize() { 
		this.layoutComponent();
	}
	
	/**
	 * To draw the description of a pin on the map. The area to draw MUST be inside the visible area.
	 * It will try to center with the map pin it is associated with
	 * 
	 * @param pinRect the rect area of the map pin
	 * 
	 * */
	public XYRect drawDetailDesc(Graphics graphics, XYRect pinRect) {
		int LEFT_ALIGNMENT = 5;  //the default left-most/right-most gap with the screen
		int v_gapPinDesc = 15; //the distance between the bottom line of the description area and the top line of the map PIN 
		
		int middleX = pinRect.x + pinRect.width / 2;
		int middleY = pinRect.y;
		
		int posX = middleX - fieldWidth / 2;
		if (posX < 0) posX = LEFT_ALIGNMENT;
		if (posX + fieldWidth >= Display.getWidth())
			posX = Display.getWidth() - fieldWidth - LEFT_ALIGNMENT;

		boolean below = false;
		int posY = middleY - fieldHeight - v_gapPinDesc;
		if (posY <= 0) {
			below = true;
			middleY = middleY + pinRect.height; // the middle of the bottom of the mapPin
			posY = middleY + v_gapPinDesc;
		}

    	XYRect dest = new XYRect(posX, posY, fieldWidth, fieldHeight);
		if (detailBackground != null) {
	    	graphics.drawBitmap(dest, detailBackground, 0, 0);
		} else {
			graphics.setColor(Color.GRAY);
	    	graphics.fillRoundRect(posX, posY, fieldWidth, fieldHeight, 15, 15);
	    	
	    	int middlePosX = posX+fieldWidth/2;
	    	int xGap = Math.abs(middlePosX-middleX) > 30 ? 30 : 10; 
	    	int[] xPts = {middlePosX-xGap, middleX, middlePosX+xGap, middlePosX-xGap};
	    	
	    	int yGap = below ? v_gapPinDesc : -v_gapPinDesc;
	    	int[] yPts = {middleY+yGap, middleY, middleY+yGap, middleY+yGap};
	    	graphics.drawFilledPath(xPts, yPts, null, null);
		}
		
		for (int i = 0; i < detailDesc.size(); i++) {
			RowViewComponent component = (RowViewComponent)detailDesc.elementAt(i);
			component.drawComponent(graphics, posX, posY, false, true);
		}

		return dest;
	}
	
	private void layoutComponent() {
		int DEFAULT_GAP = 5;

		int width = 0;
		int height = 0;

		RowViewComponent centerComponent = null;
		for (int i = 0; i < detailDesc.size(); i++) {
			
			RowViewComponent component = (RowViewComponent) detailDesc.elementAt(i);

			width += component.getPreferredWidth();
			height = component.getPreferredHeight();
			if (height > fieldHeight) 
				fieldHeight = height;
			
			if (component.isCenterComponent()) {
				centerComponent = component;
			}
		}
		
		if (width > maxDescWidth) {
			fieldWidth = maxDescWidth;
			centerComponent.setPreferredWidth(centerComponent.getPreferredWidth() - (width - maxDescWidth ));
		} else
			fieldWidth = width;
		fieldWidth += DEFAULT_GAP * 2;
		fieldHeight += DEFAULT_GAP * 2;

		int posX = DEFAULT_GAP;
		int posY = 0;
		for (int i = 0; i < detailDesc.size(); i++) {

			RowViewComponent component = (RowViewComponent) detailDesc.elementAt(i);
			width = component.getPreferredWidth();
			height = component.getPreferredHeight();
			
			posY = 0;
			if (height < fieldHeight) {
				posY = (fieldHeight - height) / 2;
			}
			component.setPosX(posX);
			component.setPosY(posY);
			
			posX += width;
		}
	}

}
