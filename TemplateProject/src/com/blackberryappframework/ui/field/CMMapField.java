package com.blackberryappframework.ui.field;

import java.util.Vector;

import javax.microedition.location.Coordinates;

import com.blackberryappframework.ui.definitions.CMCoordinates;


import net.rim.device.api.lbs.MapField;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.ContextMenu;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.TouchGesture;
import net.rim.device.api.ui.XYPoint;
import net.rim.device.api.ui.XYRect;

public class CMMapField extends MapField {
	
	public static final int FOCUS_MODE  = 0;
	public static final int PAN_MODE    = 1;
	public static final int SELECT_MODE = 2;
	public static final int IDLE_MODE   = 3;
	
	public static final int NOTIFICATION_POINT_SELECTED = 0x1;
	public static final int NOTIFICATION_MAP_CENTER_CHANGED = 0x2;
	public static final int NOTIFICATION_MAP_CLICKED = 0x04;
	
	private final int MOVE_NOTIFICATION_DISTANCE = 2000; //2km
	private final int DEFAULT_ZOOM_LEVEL = 4;
	
	private Bitmap modeBg;
	private String focusMode;
	private String panMode;
	private String selectMode;

	private String zoomin_menu = "zoom in";
	private String zoomout_menu = "zoom out";
	
	private Bitmap defaultPin_icon;
	private Bitmap mapCenter_icon;
	private Bitmap zoomIn_icon;
	private Bitmap zoomOut_icon;
	private Bitmap zoomIn_focus_icon;
	private Bitmap zoomOut_focus_icon;

	private XYRect zoomIn_Rect;
	private XYRect zoomOut_Rect;
	private boolean zoomIn_focused;
	private boolean zoomOut_focused;
	
	private CMCoordinates gpsPoint;
	private Vector pinPoints;
    private Vector pinDestRects;
    
	private int curMode;
    private int focusPin;
    private XYRect highlightRect = null;
    
    private Coordinates lastMoveCoord = null;
    private boolean workAsButton = false;
    
    //private Object data = null;
    //public Object getData() { return data;}

    public CMMapField(Bitmap defaultPin) {
    	
    	super(USE_ALL_WIDTH | USE_ALL_HEIGHT);
    	
    	curMode = IDLE_MODE;

    	modeBg = null;
    	focusMode = null;
    	panMode = null;
    	selectMode = null;

    	setDefaultPin(defaultPin);
    	mapCenter_icon = null;
    	zoomIn_icon = null;
    	zoomOut_icon = null;
    	zoomIn_focus_icon = null;
    	zoomOut_focus_icon = null;
    	zoomIn_Rect = null;
    	zoomOut_Rect = null;
    	zoomIn_focused = false;
    	zoomOut_focused = false;
    	
    	focusPin = -1;
    	pinPoints = new Vector();
    	pinDestRects = new Vector();
    	
    	setZoom(DEFAULT_ZOOM_LEVEL);
    }

    
    public void setDefaultPin(Bitmap pin) { this.defaultPin_icon = pin; }
    public void setMapCenterImage(Bitmap center) { this.mapCenter_icon = center; }
    public void setZoomImage(Bitmap zoomIn, Bitmap zoomOut) {
    	if (zoomIn != null && zoomOut != null) {
	    	this.zoomIn_icon = zoomIn;
	    	this.zoomOut_icon = zoomOut;
	    	
	    	zoomIn_Rect=new XYRect(0, 0, zoomIn.getWidth(), zoomIn.getHeight());
	    	zoomOut_Rect=new XYRect(0, zoomOut.getHeight(), zoomOut.getWidth(), zoomOut.getHeight());
    	}
    }
    public void setZoomImage_Focus(Bitmap zoomIn, Bitmap zoomOut) {
    	if (zoomIn != null && zoomOut != null) {
	    	this.zoomIn_focus_icon = zoomIn;
	    	this.zoomOut_focus_icon = zoomOut;
    	}
    }

    public void setModeBg(Bitmap modeBg) { this.modeBg = modeBg; }
	public void setFocusModeStr(String focusMode) { this.focusMode = focusMode; }
	public void setPanModeStr(String panMode) { this.panMode = panMode; }
	public void setSelectModeStr(String selectMode) { this.selectMode = selectMode; }
    public void setZoomMenu(String zoomIn, String zoomOut) {
    	this.zoomin_menu = zoomIn;
    	this.zoomout_menu = zoomOut;
    }
    
    public void setWorkAsButton(boolean on) { this.workAsButton = on;}
    
    protected void makeContextMenu(ContextMenu contextMenu) {
    	if (workAsButton) return;
    	
    	if (   zoomin_menu == null  || zoomin_menu.trim().length() == 0 
    		|| zoomout_menu == null || zoomout_menu.trim().length() == 0)
    		return;
    	
    	MenuItem zoomIn_m = new MenuItem(zoomin_menu, 10, 2) {
    		public void run() {
    			zoomIn();
    		}
    	};
    	
    	MenuItem zoomOut_m = new MenuItem(zoomout_menu, 10, 1) {
    		public void run() {
    			zoomOut();
    		}
    	};
    	
    	contextMenu.addItem(zoomOut_m);
    	contextMenu.addItem(zoomIn_m);
  }

    /**
     * To dispaly a point in the map, which might be one of the pin points 
     * */
    public boolean showPoint(int index, boolean centerHere) {
    	if (index >= 0 && index < this.pinPoints.size()) {
    		return showPoint((CMCoordinates)pinPoints.elementAt(index), centerHere);
    	} else {
    		return false;
    	}
    }
    
    /**
     * To dispaly a coordinates in the map, which might be one of the pin points 
     * */
    public boolean showPoint(CMCoordinates coordinates, boolean centerHere) {
    	boolean result = false;
    	
    	int index = this.pinPoints.indexOf(coordinates); 
    	if (index > 0) {
    		this.setFocusPoint(index);
    	} else {
    		this.addPoint(coordinates);
			this.setFocusPoint(this.pinPoints.size() - 1);
    	}

    	if (this.outOfBound(coordinates)) {
			this.moveTo(coordinates);
		}
    	
    	if (centerHere) {
    		this.setMapCenter(coordinates);
    	}
    	
    	return result;
    }
    
    public boolean isFocusable() { return true; }
    public void setFocus() {
    	super.setFocus();
    	if (this.curMode == IDLE_MODE)
    		this.curMode = FOCUS_MODE;
    	this.invalidate();
    }
    
    protected  void onUnfocus() {
    	super.onUnfocus();
//		if (this.curMode == FOCUS_MODE) {
			this.curMode = IDLE_MODE;
			this.invalidate();
//		}
    }
    
    protected void onFocus(int direction) {
    	super.onFocus(direction);
    	
    	if (this.curMode == IDLE_MODE)
    		this.curMode = FOCUS_MODE;
    	this.invalidate();
    }
	
    public boolean keyChar(char character, int status, int time) {

    	if (! this.isFocus())
    		return false;
    	
    	boolean consumed = false;
    	if (workAsButton) {
    		if (character == Characters.ENTER) {
    			consumed = this.fieldChangeNotification(NOTIFICATION_MAP_CLICKED);
    		}
    		
    		return consumed;
    	}
    	
        if( (character == 'i') || (character == 'I')) { 
        	zoomIn();
            consumed = true;
        } else if( (character == 'o') || (character == 'O')) {
        	zoomOut();
        	consumed = true;
        } else if (character == Characters.ENTER) {
    		if (curMode == SELECT_MODE) {
    			consumed = this.fieldChangeNotification(NOTIFICATION_POINT_SELECTED);
    		}	        	
        } else if (character == Characters.ESCAPE) {
        	if (this.curMode != FOCUS_MODE) {
	        	this.clearHighlightPoint();
	        	
	        	this.zoomIn_focused = false;
	        	this.zoomOut_focused = false;
	        	this.curMode = FOCUS_MODE;

	        	consumed = true;
        	}
        }

        if (consumed) {
        	this.invalidate();
        	return consumed;
        } else 
        	return super.keyChar(character, status, time);
    }
    
    /**
     * When performed navigation click, we will switch among the different modes
     * When current mode is SELECT_MODE, we will switch modes only when no item is highlighted
     * */
    protected boolean navigationClick(int status, int time) {
    	
		boolean consumed = false;

		if (this.isFocus()) {
	    	if (workAsButton) {
	    		consumed = this.fieldChangeNotification(NOTIFICATION_MAP_CLICKED);
	    		return consumed;
	    	}
	    	
    		//if in select mode, we will perform field change notification first
    		//only when no item is highlighted, we will switch mode
    		if (curMode == SELECT_MODE) {
    			consumed = this.fieldChangeNotification(NOTIFICATION_POINT_SELECTED);
    		}
    		
    		if (!consumed)
    			curMode = (curMode + 1) % IDLE_MODE;
    		
    		consumed = true;
    	}
    	
		if (consumed) this.invalidate();
    	return consumed;
    } 

    /**
     * @see net.rim.device.api.ui.Field#navigationMovement(int, int, int, int)
     */
    public boolean navigationMovement(int dx, int dy, int status, int time)  {

    	boolean consumed = false;
    	
    	if (this.isFocus()) {
    		if (this.workAsButton)
    			return false;
    		
    		if (this.curMode == PAN_MODE ) {
    			move(dx*5, dy*10);
    			consumed = true;
    		} else if (this.curMode == SELECT_MODE ){
    			selectFocusPoint(dx, dy);
    			consumed = true;
    		}
    	}
    	
    	if (consumed) this.invalidate();
    	return consumed;
    } 
    
    public boolean touchEvent(TouchEvent message) {
        
        boolean isConsumed = false;

        // Get the screen coordinates of the touch event           
        int touchX =  message.getX(1);
        int touchY =  message.getY(1);
        int eventCode = message.getEvent();
        if (workAsButton) {
        	if (eventCode == TouchEvent.CLICK)
        		isConsumed = this.fieldChangeNotification(NOTIFICATION_MAP_CLICKED);

        	return isConsumed;
        }
	    	
        switch (eventCode) {
        case TouchEvent.CLICK:
	    	if (workAsButton) {
	    		isConsumed = this.fieldChangeNotification(NOTIFICATION_MAP_CLICKED);
	    		return isConsumed;
	    	}

        	if (zoomIn_Rect.contains(touchX, touchY)){
        		zoomIn();
        		this.zoomIn_focused = true;
        	} else if (zoomOut_Rect.contains(touchX, touchY)){
        		zoomOut();
        		this.zoomOut_focused = true;
        	} else {
        		if (highlightRect != null) {
        			if (highlightRect.contains(touchX, touchY)) {
        				isConsumed = fieldChangeNotification(NOTIFICATION_POINT_SELECTED);
        			}
        		}
        		
        		if (! isConsumed) {
        			this.clearHighlightPoint();
		        	for (int i = 0; i < this.pinDestRects.size(); i++) {
		        		XYRect rect = (XYRect) this.pinDestRects.elementAt(i);
		        		if (rect.contains(touchX, touchY)) {
		        			setFocusPoint(i);
		        			break;
		        		}
	        		}
        		}
        	}

        	isConsumed = true;
        	break;
        	//end of Touch CLICK
        	
        case TouchEvent.UP:
        	if (this.zoomIn_focused || this.zoomOut_focused) {
        		this.zoomIn_focused = false;
        		this.zoomOut_focused = false;
        		
        		isConsumed = true;
        	}
        	break;
        	
        default:
        	if (eventCode == TouchEvent.GESTURE) {
        		//case TouchEvent.GESTURE:
	        	TouchGesture touchGesture = message.getGesture();           
	            if (touchGesture != null) {                
	                // If the user has performed a swipe gesture we will move the map accordingly.
	                if (touchGesture.getEvent() == TouchGesture.SWIPE) {      
	                    // Retrieve the swipe magnitude so we know how far to move the map.
	                    int magnitude = touchGesture.getSwipeMagnitude();
	                    
	                    // Move the map in the direction of the swipe.
	                    int dx = 0;
	                    int dy = 0;
	                	switch(touchGesture.getSwipeDirection()) {
	                	case TouchGesture.SWIPE_NORTH:
	                		dy = magnitude;
	                    	break;
	                	case TouchGesture.SWIPE_SOUTH:
	                		dy = - magnitude;
	                    	break;
	                    case TouchGesture.SWIPE_EAST:
	                		dx = - magnitude;
	                    	break;
	                    case TouchGesture.SWIPE_WEST:
	                		dx = magnitude;
	                    	break;
	                    }
	                	
	                	move(dx, dy);
	                	isConsumed = true;
	                }
	            }
        	}
            break;
        } //end of switch

        if (isConsumed) this.invalidate();
        return isConsumed;       
    }

    protected void paint(Graphics graphics) {
    	super.paint(graphics);
    	
    	if(this.workAsButton)
    	{
    		if(this.isFocus())
    		{
    			graphics.setColor(Color.BLUE);
    			//graphics.setDrawingStyle(Graphics., on)
    			graphics.drawRect(0, 0, this.getWidth(), this.getHeight());
    			graphics.drawRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    			graphics.drawRect(2, 2, this.getWidth()-2, this.getHeight()-2);

    		}
    	}
    	
    	
    	paintPoints(graphics);
    	paintPointDesc(graphics);
    	paintMode(graphics);
    	paintMapImage(graphics);
    }
    
    
    public void zoomIn() {
		setZoom(Math.max(getZoom() - 1, getMinZoom()));
		this.invalidate();
    }
    
    public void zoomOut() {
		setZoom(Math.min(getZoom() + 1, getMaxZoom()));
		this.invalidate();
    }
    
    public void setMapCenter(CMCoordinates coordinates) {
    	moveTo(coordinates);
    	if (this.lastMoveCoord == null)
    		this.lastMoveCoord = coordinates;
    }

    public void setGPSPoint(CMCoordinates coordinates, boolean centerHere) {
    	gpsPoint = coordinates;
    	if (centerHere) 
    		setMapCenter(gpsPoint);
    	//this.zoomToFitPoints();
    	//this.invalidate();
    }
    public CMCoordinates getGPSLocation() { return this.gpsPoint;}
    
    public CMCoordinates getHighlightLocation() {
    	CMCoordinates result = null;
    	
    	if (focusPin >= 0 && focusPin < pinPoints.size()) {
    		result = (CMCoordinates) this.pinPoints.elementAt(focusPin);
    	}
    	
    	return result;
    }
    
    public void addPoint(CMCoordinates coordinates) {
    	if (!pinPoints.contains(coordinates)) {
			pinPoints.addElement(coordinates);
			pinDestRects.addElement(getPinXYRect(coordinates));
			
			//zoomToFitPoints();
	    	invalidate();
    	}
    }
    
    public void removeAllPoints() {
    	pinPoints.removeAllElements();
    	pinDestRects.removeAllElements();
		clearHighlightPoint();
    }
    
    public void removeCoordinates(CMCoordinates coordinate) {
    	int idx = -1;
    	for (int i = 0; i < pinPoints.size(); i++) {
    		if (pinPoints.elementAt(i) == coordinate) {
    			idx = i;
    			pinPoints.removeElementAt(i);
    			pinDestRects.removeElementAt(i);
    			break;
    		}
    	}
    	
    	if (this.focusPin >= 0 && this.focusPin == idx)
    		this.setFocusPoint(idx - 1);

    	//zoomToFitPoints();
    	invalidate();
    }

    public void setMapMode(int mapMode) { this.curMode = mapMode; }
    public boolean inFocusMode() { return (curMode == FOCUS_MODE);}

    private void paintMode(Graphics graphics) {
    	String modeStr = "";
    	switch (this.curMode) {
    	case FOCUS_MODE: 
    		modeStr = this.focusMode;
    		break;
    	case PAN_MODE: 
    		modeStr = this.panMode;
    		break;
    	case SELECT_MODE: 
    		modeStr = this.selectMode;
    		break;
    	}
    	
    	int posX ;
    	int posY = 0;
    	if (modeBg != null && this.curMode != IDLE_MODE) {
    		posX = (this.getWidth() - modeBg.getWidth()) / 2;
    		XYRect destRect = new XYRect(posX, posY, modeBg.getWidth(), modeBg.getHeight());
    		graphics.drawBitmap(destRect, modeBg, 0, 0);
    	}

    	Font font = this.getFont();
    	posX = (this.getWidth() - font.getAdvance(modeStr)) / 2;
    	if (modeBg != null)
    		posY = (modeBg.getHeight() - font.getHeight()) / 2;
    	graphics.setFont(font);
    	graphics.setColor(Color.WHITE);
    	graphics.drawText(modeStr, posX, posY);
    }
    
    private void paintMapImage(Graphics graphics) {
    	if (mapCenter_icon != null) {
    		int posX = ( this.getWidth() - mapCenter_icon.getWidth() ) / 2;
    		int posY = ( this.getHeight() - mapCenter_icon.getHeight() ) / 2;
    		XYRect destRect = new XYRect(posX, posY, mapCenter_icon.getWidth(), mapCenter_icon.getHeight());
    		graphics.drawBitmap(destRect, mapCenter_icon, 0, 0);
    	}

    	//draw zoom images
    	Bitmap b_zoomIn = zoomIn_icon;
    	Bitmap b_zoomOut = zoomOut_icon;
    	if (zoomIn_focused && zoomIn_focus_icon != null) {
    		b_zoomIn = zoomIn_focus_icon;
    	}
    	if ( zoomOut_focused && zoomOut_focus_icon != null ) {
    		b_zoomOut = zoomOut_focus_icon;
    	} 
    	if (b_zoomIn != null && b_zoomOut != null) {
    		graphics.drawBitmap(zoomIn_Rect, b_zoomIn, 0, 0);
    		graphics.drawBitmap(zoomOut_Rect, b_zoomOut, 0, 0);
    	}
    }
    
    private void paintPoints(Graphics graphics) {
    	if (gpsPoint != null) {
    		if (! outOfBound(gpsPoint)) {
    			Bitmap mapPin = getCoordinatePin(gpsPoint);
    			XYRect destRect = getPinXYRect(gpsPoint);
    			graphics.drawBitmap(destRect, mapPin, 0, 0);
    		}
    	}
    	
    	recalculatePointsRect();

    	for (int i = 0; i < pinPoints.size(); i++) {
    		CMCoordinates coordinate = (CMCoordinates) pinPoints.elementAt(i); 
    		Bitmap mapPin = getCoordinatePin(coordinate);
    		graphics.drawBitmap((XYRect) pinDestRects.elementAt(i), mapPin, 0, 0);
    	}
    }
    
	private void paintPointDesc(Graphics graphics) {
    	if (this.focusPin > -1 && this.focusPin < this.pinPoints.size()) {
    		CMCoordinates coordinates = (CMCoordinates)pinPoints.elementAt(focusPin);
    		if (!outOfBound(coordinates)) {
	    		XYRect xyRect = (XYRect) this.pinDestRects.elementAt(focusPin);
	    		highlightRect = coordinates.drawDetailDesc(graphics,xyRect);
    		} else {
    			highlightRect = null;
    		}
    	}
	}

    /**
     * To set the current focused map point, which might be one of the pin points 
     * */
    private boolean setFocusPoint(int pointIndex) {
    	boolean result = false;
    	
    	int index = pointIndex;
    	if (index >= 0 ) { 
	    	while (index < pinPoints.size()) {
	    		CMCoordinates coordinates = (CMCoordinates) this.pinPoints.elementAt(index);
	    		if (this.outOfBound(coordinates)) {
	    			index ++;
	    		} else {
		    		this.focusPin = pointIndex ;
		    		result = true;
		    		break;
	    		}
	    	}
    	}
    	
    	if (!result) this.focusPin = -1;
    	/*
    	if (pointIndex < pinPoints.size() && pointIndex >= 0) {
    		this.focusPin = pointIndex ;
    		result = true;
    	} else {
    		this.focusPin = -1;
    	}
    	*/
    	this.invalidate();
    	return result;
    }
    
    private boolean outOfBound(CMCoordinates coordinates) {
    	boolean outOfBounds = false;
    	
    	if (coordinates != null) {
			try {
				XYPoint point = new XYPoint();
				convertWorldToField(coordinates, point);

				if (   point.x < 0 || point.x > getWidth()
					|| point.y < 0 || point.y > getHeight() )
					outOfBounds = true;
			} catch (IllegalArgumentException ex) {
				outOfBounds = true;
			}
    	}

    	return outOfBounds;
    }
    
    private void clearHighlightPoint() {
		setFocusPoint(-1);
    	highlightRect = null;
    	//data = null;
    }
    
    private Bitmap getCoordinatePin(CMCoordinates coordinates){
		Bitmap result = null;
		
		if (coordinates != null)
			result = coordinates.getMapPin();
		if (result == null)
			result = defaultPin_icon;
		
		return result;
	}
    
    private void recalculatePointsRect() {
    	this.pinDestRects.removeAllElements();
    	for (int i = 0; i < this.pinPoints.size(); i++) {
    		CMCoordinates coordinates = (CMCoordinates) this.pinPoints.elementAt(i);
    		
    		this.pinDestRects.addElement(getPinXYRect(coordinates));
    	}
    }
    
    private XYRect getPinXYRect(CMCoordinates coordinates) {
    	if (coordinates == null) return null;
    	
    	XYPoint fieldOut = new XYPoint();
		convertWorldToField(coordinates, fieldOut);

		Bitmap mapPin = getCoordinatePin(coordinates);
		int imgW = mapPin.getWidth();
		int imgH = mapPin.getHeight();
		XYRect destRect = new XYRect(fieldOut.x-imgW/2, fieldOut.y-imgH, imgW, imgH);

		return destRect;
    }

    public void move(int dx, int dy) {

    	super.move(dx, dy);
        this.fieldChangeNotification(NOTIFICATION_MAP_CENTER_CHANGED);
    	
    	/*
    	Coordinates coord = this.getCoordinates();
    	
    	// The map is shifted in relation to the current zoom level.
    	int zoom = getZoom();
        double latitude = coord.getLatitude() - ((dy << 3) << zoom); // << 3 is equivalent to multiplication by 8.
        double longitude = coord.getLongitude() + ((dx << 3) << zoom);                  
        
        moveTo((int)latitude, (int)longitude);
        this.fieldChangeNotification();
        	        
        invalidate();
        */
    }
    
    private void selectFocusPoint(int dx, int dy) {
    	int nextPin;
    	boolean forward = true;
    	if (dx < 0 || dy < 0) {
    		forward = false;
    	}
    	
    	if (forward) {
    		nextPin = focusPin + 1;
			if (zoomIn_focused) {
				zoomIn_focused = false;
				zoomOut_focused = true;
			} else if (zoomOut_focused) {
				zoomOut_focused = false; 
			} else if ( ! this.setFocusPoint(nextPin) ) {
				zoomIn_focused = true;
			}
    	} else {
    		nextPin = focusPin - 1;
    		if (zoomIn_focused) {
    			zoomIn_focused = false;
    			this.setFocusPoint(this.pinPoints.size() - 1);
    		} else if (zoomOut_focused) {
    			zoomIn_focused = true;
    			zoomOut_focused = false;
    		} else if (focusPin < 0) {
    			zoomOut_focused = true;
    		} else {
    			this.setFocusPoint(nextPin);
    		}
    	}
    }
    
    private boolean fieldChangeNotification(int action) {
    	boolean consumed = false;
    	boolean notifyListener = false;
    	int changeReason = action;
    	
    	if (this.workAsButton) {
    		if (action == NOTIFICATION_MAP_CLICKED) {
    			notifyListener = true;
    		}
    	} else {
	    	if (this.zoomIn_focused) {
	    		zoomIn();
	    		consumed = true;
	    	} else if (this.zoomOut_focused) {
	    		zoomOut();
	    		consumed = true;
	    	} else {
	    		if (action == NOTIFICATION_MAP_CENTER_CHANGED ) {
		    		if (lastMoveCoord.distance(getCoordinates()) > this.MOVE_NOTIFICATION_DISTANCE) {
			        	lastMoveCoord = getCoordinates();
			        	notifyListener = true;
		    		}
	    		} else if (action == NOTIFICATION_POINT_SELECTED) {
	    			if (this.focusPin > -1 && this.focusPin < this.pinPoints.size()) {
						CMCoordinates coordinates = (CMCoordinates) this.pinPoints.elementAt(focusPin);
						//data = coordinates.getDetailData();
			    		notifyListener = true;
		    		}
	    		}
	    	}
    	}
    	
    	if (notifyListener) {
			FieldChangeListener listener = this.getChangeListener(); 
			if (listener != null) {
				listener.fieldChanged(this, changeReason);
			}
    		
    		consumed = true;
    	}
    	
    	return consumed;
    }
    
/*
    private void zoomToFitPoints() {
    	// zoom to max
    	//setZoom(getMaxZoom());

    	// get pixels of all points
    	int minLeft = getWidth();
    	int minUp = getHeight();
    	int maxRight = 0;
    	int maxDown = 0;
    	
    	CMCoordinates minLeftCoordinates = null;
    	CMCoordinates minUpCoordinates = null;
    	CMCoordinates maxRightCoordinates = null;
    	CMCoordinates maxDownCoordinates = null;
    	for (int i = 0; i < pinPoints.size(); i++) {
    		CMCoordinates coordinate = (CMCoordinates) pinPoints.elementAt(i);
    		
    		XYPoint point = new XYPoint();
    		convertWorldToField(coordinate, point);
    		if (point.x <= minLeft) {
    			minLeft = point.x;
    			minLeftCoordinates = coordinate;
    		}
    		if (point.x >= maxRight) {
    			maxRight = point.x;
    			maxRightCoordinates = coordinate;
    		}
    		if (point.y <= minUp) {
    			minUp = point.y;
    			minUpCoordinates = coordinate;
    		}
    		if (point.y >= maxDown) {
    			maxDown = point.y;
    			maxDownCoordinates = coordinate;
    		}
    	}
    	
    	if (gpsPoint != null)  {
    		XYPoint point = new XYPoint();
    		convertWorldToField(gpsPoint, point);
    		if (point.x <= minLeft) {
    			minLeft = point.x;
    			minLeftCoordinates = gpsPoint;
    		}
    		if (point.x >= maxRight) {
    			maxRight = point.x;
    			maxRightCoordinates = gpsPoint;
    		}
    		if (point.y <= minUp) {
    			minUp = point.y;
    			minUpCoordinates = gpsPoint;
    		}
    		if (point.y >= maxDown) {
    			maxDown = point.y;
    			maxDownCoordinates = gpsPoint;
    		}
    	}

    	minLeftCoordinates  =  minLeftCoordinates == null  ? maxRightCoordinates : minLeftCoordinates;
    	maxRightCoordinates =  maxRightCoordinates == null ? minLeftCoordinates  : maxRightCoordinates;
    	minUpCoordinates    =  minUpCoordinates == null    ? maxDownCoordinates  : minUpCoordinates;
    	maxDownCoordinates  =  maxDownCoordinates == null  ? minUpCoordinates    : maxDownCoordinates;
    	
    	double moveToLat = maxDownCoordinates.getLatitude()
    			+ (minUpCoordinates.getLatitude() - maxDownCoordinates.getLatitude())/2;
    	double moveToLong = minLeftCoordinates.getLongitude()
    			+ (maxRightCoordinates.getLongitude() - minLeftCoordinates.getLongitude())/2;
    	CMCoordinates moveTo = new CMCoordinates(moveToLat, moveToLong, 0);
    	moveTo(moveTo);
    	
    	// zoom to min left up, max right down pixels + 1
    	int zoom = getZoom();
    	boolean outOfBounds = outOfBound(minLeftCoordinates)
			 	 || outOfBound(minUpCoordinates)
				 || outOfBound(maxRightCoordinates)
				 || outOfBound(maxDownCoordinates);
    	while (!outOfBounds && zoom > getMinZoom()) {
    		zoomOut();
    		XYPoint point = new XYPoint();
   			outOfBounds =   outOfBound(minLeftCoordinates)
   						 || outOfBound(minUpCoordinates)
   						 || outOfBound(maxRightCoordinates)
   						 || outOfBound(maxDownCoordinates);
    	}
		zoomIn();

    }
    */
    public void zoomToFitPoints() {
    	// zoom to max
    	setZoom(getMinZoom());
    	
    	if (this.gpsPoint == null && this.pinPoints.size() < 2)
    		return;


    	// get pixels of all points
    	int minLeft = getWidth();
    	int minUp = getHeight();
    	int maxRight = 0;
    	int maxDown = 0;
    	CMCoordinates minLeftCoordinates = null;
    	CMCoordinates minUpCoordinates = null;
    	CMCoordinates maxRightCoordinates = null;
    	CMCoordinates maxDownCoordinates = null;
    	for (int i = 0; i < pinPoints.size(); i++) {
    		CMCoordinates coordinate = (CMCoordinates) pinPoints.elementAt(i);
    		
    		XYPoint point = new XYPoint();
    		convertWorldToField(coordinate, point);
    		if (point.x <= minLeft) {
    			minLeft = point.x;
    			minLeftCoordinates = coordinate;
    		}
    		if (point.x >= maxRight) {
    			maxRight = point.x;
    			maxRightCoordinates = coordinate;
    		}
    		if (point.y <= minUp) {
    			minUp = point.y;
    			minUpCoordinates = coordinate;
    		}
    		if (point.y >= maxDown) {
    			maxDown = point.y;
    			maxDownCoordinates = coordinate;
    		}
    	}
    	
    	if (gpsPoint != null)  {
    		XYPoint point = new XYPoint();
    		convertWorldToField(gpsPoint, point);
    		if (point.x <= minLeft) {
    			minLeft = point.x;
    			minLeftCoordinates = gpsPoint;
    		}
    		if (point.x >= maxRight) {
    			maxRight = point.x;
    			maxRightCoordinates = gpsPoint;
    		}
    		if (point.y <= minUp) {
    			minUp = point.y;
    			minUpCoordinates = gpsPoint;
    		}
    		if (point.y >= maxDown) {
    			maxDown = point.y;
    			maxDownCoordinates = gpsPoint;
    		}
    	}

    	if (minUpCoordinates == null || maxDownCoordinates == null || minLeftCoordinates == null || maxRightCoordinates == null)
    		return;
    	
    	double moveToLat = maxDownCoordinates.getLatitude()
    			+ (minUpCoordinates.getLatitude() - maxDownCoordinates
    					.getLatitude()) / 2;
    	double moveToLong = minLeftCoordinates.getLongitude()
    			+ (maxRightCoordinates.getLongitude() - minLeftCoordinates
    					.getLongitude()) / 2;
    	Coordinates moveTo = new Coordinates(moveToLat, moveToLong, 0);
    	moveTo(moveTo);
    	int zoom = getZoom();
    	
    	boolean outOfBounds = outOfBound(minLeftCoordinates)
	 	 || outOfBound(minUpCoordinates)
		 || outOfBound(maxRightCoordinates)
		 || outOfBound(maxDownCoordinates);
    	while (outOfBounds && getZoom() < getMaxZoom()) {
    		zoomOut();
			outOfBounds =   outOfBound(minLeftCoordinates)
						 || outOfBound(minUpCoordinates)
						 || outOfBound(maxRightCoordinates)
						 || outOfBound(maxDownCoordinates);
    	}
    	zoomOut();

    }
    
}