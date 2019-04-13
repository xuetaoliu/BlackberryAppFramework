package com.blackberryappframework.utility.GPS;

import javax.microedition.location.Coordinates;

public interface GPSChangeListener {
	/**
	 * Response to the GPS location update.
	 * GPS timer will call this function when an update is ready
	 * */
	public void updateCurrentLocation(Coordinates coordinates) ;
}
