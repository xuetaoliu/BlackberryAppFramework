package com.blackberryappframework.TemplateProject.controller;

import java.util.Vector;

import javax.microedition.location.Coordinates;

import net.rim.device.api.util.Comparator;
import net.rim.device.api.util.SimpleSortingVector;

public class NearestATMS {
	private final double latitudeInterval_100KM = 1.2;
	private final double longtitudeInterval_100KM = 1.6;
	
	private Coordinates _curLoc;
	private SimpleSortingVector _list;
	
	public NearestATMS() {
//		_curLoc = AppSetting.getAppSetting().getAppConfiguration().getCurCoord();
		_list = new SimpleSortingVector();
		_list.setSortComparator(new NearestATMComparator());
		_list.setSort(false);
	}
	
//	public void add(BankATM atm) {
//		double latitude = atm.getLatitude();
//		double longtitude = atm.getLongitude();
//		double interval_lat = latitude - _curLoc.getLatitude();
//		double interval_lon = longtitude - _curLoc.getLongitude();
//		
//		if((Math.abs(interval_lat) <= latitudeInterval_100KM)
//			&& (Math.abs(interval_lon) <= longtitudeInterval_100KM)) {
//			Coordinates coord = new Coordinates(atm.getLatitude(), atm.getLongitude(), 0);
//			float distance = _curLoc.distance(coord);
//			int tmp = (int) (distance/100);
//			float kmDistance = ((float)tmp) / 10;
//			atm.setDistance(kmDistance);
//			_list.addElement(atm);
//		}
//	}
	
	public void sort() {
		_list.reSort();
	}
	
	public Vector getNearestATMS(int num) {
		_list.setSize(num);
		
		return _list;
	}
	
	public Vector getOrderedATMS() {
		return _list;
	}
	
	class NearestATMComparator implements Comparator {
		public int compare(Object obj1, Object obj2) {
//			BankATM atm1 = (BankATM) obj1;
//			BankATM atm2 = (BankATM) obj2;
//			
////			Coordinates cor1 = new Coordinates(atm1.getLatitude(), atm1.getLongitude(), 0);
////			Coordinates cor2 = new Coordinates(atm2.getLatitude(), atm2.getLongitude(), 0);
//			
//			return atm1.getDistance() < atm2.getDistance() ? -1 : 1 ;
			return 1;
		}
	}
}
