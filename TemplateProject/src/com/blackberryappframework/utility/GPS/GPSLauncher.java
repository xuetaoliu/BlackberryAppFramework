package com.blackberryappframework.utility.GPS;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;


public class GPSLauncher {
    
	private static final int GPS_DELAY = 60;
	
    public static final int GPS_MODE_TOWER   = 1;
    public static final int GPS_MODE_ASSISTED =2 ;
    public static final int GPS_MODE_AUTONOMOUS = 3;
    public static final int GPS_MODE_ASSISTED_AUTONOMOUS =4 ;
    public static final int GPS_ACCURACY_LEVEL_0 = 0;
    public static final int GPS_ACCURACY_LEVEL_500M = 500;
    public static final int GPS_ACCURACY_LEVEL_100M = 100;
    public static final int GPS_ACCURACY_LEVEL_10M = 10;
    
    private LocationProvider locationProvider = null;
    private int curMode;

    public GPSLauncher()  { }
    
    public boolean startGPS(int gpsMode) throws Exception {
		if (   locationProvider == null
    			|| locationProvider.getState()==LocationProvider.OUT_OF_SERVICE 
    			|| locationProvider.getState()==LocationProvider.TEMPORARILY_UNAVAILABLE){
			this.startGPS(gpsMode, GPSLauncher.GPS_ACCURACY_LEVEL_100M, true);

		}

	    if ( locationProvider == null )
	        return false;
	    else 
	        return true;
    }
    
    //try to start GPS
    public boolean startGPS(int gpsMode, int accuracyLevel, boolean costAllowed) throws Exception {
        try {
            if (locationProvider == null) {
                Criteria criteria = new Criteria();
                criteria.setCostAllowed(true);
                switch (gpsMode) {
                case GPS_MODE_TOWER:
                    criteria.setHorizontalAccuracy(Criteria.NO_REQUIREMENT);
                    criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
                    criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);
                    break;
                case GPS_MODE_ASSISTED:
                    criteria.setHorizontalAccuracy(accuracyLevel);
                    criteria.setVerticalAccuracy(accuracyLevel);
                    criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_MEDIUM);
                    break;
                case GPS_MODE_AUTONOMOUS:
                    criteria.setHorizontalAccuracy(accuracyLevel);
                    criteria.setVerticalAccuracy(accuracyLevel);
                    criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_HIGH);
                    break;
                case GPS_MODE_ASSISTED_AUTONOMOUS:
                    criteria.setHorizontalAccuracy(accuracyLevel);
                    criteria.setVerticalAccuracy(accuracyLevel);
                    criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_HIGH);
                    break;
                default:
        			criteria.setHorizontalAccuracy(Criteria.NO_REQUIREMENT);
        			criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
                    criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);
                    break;
                }
                                
                locationProvider = LocationProvider.getInstance(criteria);
                curMode = gpsMode;
            }
        } catch (Exception e) {
            throw new GPS_Exception(GPS_Exception.ERROR_GPS_INITIALIZATION_FAILURE);
        }
                
        if ( locationProvider == null )
            return false;
        else 
            return true;
                
    }
        
    public void stopGPS()
    {
        locationProvider = null;
        System.gc();
    }
        
    public Location curLocation() throws Exception {
        Location loc = null;
        
        if (locationProvider == null) {
        	this.startGPS(this.curMode);
        }
        
        if (locationProvider != null) {
            loc = locationProvider.getLocation(GPS_DELAY );
            
        }
        
        return loc;
    }
    
    public Coordinates getCurCoordiates() throws Exception {
    	Coordinates coordinates = null;
    	
    	Location loc = curLocation();
    	if (loc != null)
    		coordinates = loc.getQualifiedCoordinates();
    	
    	return coordinates;
    }
        
    public float getDistance(Coordinates startPoint, Coordinates endPoint) {
                
        if ( (startPoint == null) || (endPoint != null))
            return -1;
                
        return startPoint.distance(endPoint);
/*      for 6.0 only
                float distance = -1;
                
                try 
                {
                TravelTimeEstimator estimator = TravelTimeEstimator.getInstance();
                TravelTime travelTime = estimator.requestArrivalEstimate(startPoint, endPoint, TravelTime.START_NOW, null);

                distance = (travelTime.getDistance() / 1000);
                } catch (Exception e) {
                        throw new Exception(resource.getString(ID_TRAVELTIME_GETDISTANCE_FAILURE));
                } finally {
                        return distance;
                }
*/              
    }
        
    public float getDistance (double startLatitude, double startLongtitude, double endLatitude, double endLongtitude) {
                
        Coordinates startPoint = new Coordinates(startLatitude, startLongtitude, 0) ;
        Coordinates endPoint = new Coordinates(endLatitude, endLongtitude, 0) ;
                
        return getDistance(startPoint, endPoint);
    }
}
