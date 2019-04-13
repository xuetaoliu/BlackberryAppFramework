package com.blackberryappframework.TemplateProject.appConfiguration;

import java.util.Hashtable;

public class AppCommands {
	
	private static AppCommands appCommands = null;
	public static AppCommands getAppCommands() {
		if (appCommands == null)
			appCommands = new AppCommands();
		
		return appCommands;
	}

	public static int getApplicationCmdID (String actionName) {
        int id = -1;
        if (actionName == null)
            return id;
        
        Integer appCmdID = (Integer)getAppCommands().commands.get(actionName);
        if (appCmdID != null)
            id = appCmdID .intValue();

        return id;
    }
	
    private static final int KEY_SCRN_DEF = 0;
    public  static final int KEY_SCRN_WELCOME     = KEY_SCRN_DEF +  0;
    public  static final int KEY_SCRN_TERMS       = KEY_SCRN_DEF +  1;
    public  static final int KEY_SCRN_HOME        = KEY_SCRN_DEF +  2;
    public  static final int KEY_SCRN_LOCATION    = KEY_SCRN_DEF +  3;
    public  static final int KEY_SCRN_FILTER      = KEY_SCRN_DEF +  4;
    public  static final int KEY_SCRN_FAVORITE    = KEY_SCRN_DEF +  5;
    public  static final int KEY_SCRN_FLYERS      = KEY_SCRN_DEF +  6;
    public  static final int KEY_SCRN_DETAIL      = KEY_SCRN_DEF +  7;
    public  static final int KEY_SCRN_REPORT      = KEY_SCRN_DEF +  8;
    public  static final int KEY_SCRN_RETAIL_POPUP= KEY_SCRN_DEF +  9;

    private static final int KEY_SCRN_OPERATION_INDEX                 = 50;
    
    /* commands for Terms and Conds screen*/
    private static final int KEY_SCRN_TERMS_OPERATION_START                    = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_TERMS;
    public  static final int KEY_SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT        = KEY_SCRN_TERMS_OPERATION_START +  0;    
    public  static final int KEY_SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE       = KEY_SCRN_TERMS_OPERATION_START +  1;    

    /* commands for Home screen*/
    private static final int KEY_SCRN_HOME_OPERATION_START                     = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_HOME;
    public  static final int KEY_SCRN_HOME_LOCATIONS 						   = KEY_SCRN_HOME_OPERATION_START +  0;    
    public  static final int KEY_SCRN_HOME_FAVORITES 						   = KEY_SCRN_HOME_OPERATION_START +  1;    
    public  static final int KEY_SCRN_HOME_FLYERS	 						   = KEY_SCRN_HOME_OPERATION_START +  2;    
    public  static final int KEY_SCRN_HOME_TERMSCONDS	 					   = KEY_SCRN_HOME_OPERATION_START +  3;    
    public  static final int KEY_SCRN_HOME_PRIVACY	 						   = KEY_SCRN_HOME_OPERATION_START +  4;    
    public  static final int KEY_SCRN_HOME_REPORT	 						   = KEY_SCRN_HOME_OPERATION_START +  5;    
    
    /* commands for Location screen*/
    private static final int KEY_SCRN_LOCATION_OPERATION_START                 = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_LOCATION;
    public  static final int KEY_SCRN_LOCATION_OPERATION_LIST                  = KEY_SCRN_LOCATION_OPERATION_START + 0;
    public  static final int KEY_SCRN_LOCATION_OPERATION_MAP                   = KEY_SCRN_LOCATION_OPERATION_START + 1;
    public  static final int KEY_SCRN_LOCATION_OPERATION_LOC_SEARCH            = KEY_SCRN_LOCATION_OPERATION_START + 2;
    public  static final int KEY_SCRN_LOCATION_OPERATION_FILTER                = KEY_SCRN_LOCATION_OPERATION_START + 3;
    public  static final int KEY_SCRN_LOCATION_OPERATION_SELECT_LOCATION       = KEY_SCRN_LOCATION_OPERATION_START + 4;
    public  static final int KEY_SCRN_LOCATION_OPERATION_SELECT_MAP            = KEY_SCRN_LOCATION_OPERATION_START + 5;

    /* commands for Filter screen*/
    private static final int KEY_SCRN_FILTER_OPERATION_START                   = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_FILTER;

    /* commands for Favorite screen*/
    private static final int KEY_SCRN_FAVORITE_OPERATION_START                 = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_FAVORITE;
    public  static final int KEY_SCRN_FAVORITE_OPERATION_LISTMAP               = KEY_SCRN_FAVORITE_OPERATION_START + 0;
    public  static final int KEY_SCRN_FAVORITE_NEARBYLOCATIONS                 = KEY_SCRN_FAVORITE_OPERATION_START + 1;

    /* commands for Flyers screen*/
    private static final int KEY_SCRN_FLYERS_OPERATION_START                   = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_FLYERS;
    public  static final int KEY_SCRN_FLYERS_OPERATION_LISTMAP                 = KEY_SCRN_FLYERS_OPERATION_START + 0;
    public  static final int KEY_SCRN_FLYERS_OPERATION_SEARCHNEARBY            = KEY_SCRN_FLYERS_OPERATION_START + 1;

    /* commands for details screen*/
    private static final int KEY_SCRN_DETAILS_OPERATION_START                  = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_DETAIL;
    public  static final int KEY_SCRN_DETAILS_SERVICE_DETAIL_ON                = KEY_SCRN_DETAILS_OPERATION_START + 0;
    public  static final int KEY_SCRN_DETAILS_SERVICE_DETAIL_OFF               = KEY_SCRN_DETAILS_OPERATION_START + 1;
    public  static final int KEY_SCRN_DETAILS_FAVORITE 			               = KEY_SCRN_DETAILS_OPERATION_START + 2;
    public  static final int KEY_SCRN_DETAILS_WEBSITE 			               = KEY_SCRN_DETAILS_OPERATION_START + 3;
    public  static final int KEY_SCRN_DETAILS_FLYERS 			               = KEY_SCRN_DETAILS_OPERATION_START + 4;
    public  static final int KEY_SCRN_DETAILS_DIRECTIONS 			           = KEY_SCRN_DETAILS_OPERATION_START + 5;
    public  static final int KEY_SCRN_DETAILS_REPORT_ERROR 			           = KEY_SCRN_DETAILS_OPERATION_START + 6;

    /* commands for report screen*/
    private static final int KEY_SCRN_REPORT_OPERATION_START                  = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_REPORT;
    public  static final int KEY_SCRN_REPORT_SUBMIT                           = KEY_SCRN_REPORT_OPERATION_START + 0;

    /* commands for popup screen*/
    private static final int KEY_SCRN_RETAIL_POPUP_OPERATION_START            = KEY_SCRN_OPERATION_INDEX * KEY_SCRN_RETAIL_POPUP;
    public  static final int KEY_SCRN_RETAIL_POPUP_OK                         = KEY_SCRN_RETAIL_POPUP_OPERATION_START + 0;

    
    public static Object[] SCREEN_WELCOME     = {"scrn_welcome"    , new Integer(KEY_SCRN_WELCOME    )}; 
    
    /* commands for Terms and Conds screen*/
    public static Object[] SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT        = {"accept"          , new Integer(KEY_SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT       )}; 
    public static Object[] SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE       = {"decline"         , new Integer(KEY_SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE      )}; 

    /* commands for home screen*/
    public static Object[] SCRN_HOME_LOCATIONS        					 = {"locations"          , new Integer(KEY_SCRN_HOME_LOCATIONS       )}; 
    public static Object[] SCRN_HOME_FAVORITES        					 = {"favorites"          , new Integer(KEY_SCRN_HOME_FAVORITES       )}; 
    public static Object[] SCRN_HOME_FLYERS	        					 = {"flyers"          	 , new Integer(KEY_SCRN_HOME_FLYERS		     )}; 
    public static Object[] SCRN_HOME_TERMSCONDS	        				 = {"termsconds"         , new Integer(KEY_SCRN_HOME_TERMSCONDS		     )}; 
    public static Object[] SCRN_HOME_PRIVACY	        				 = {"privacy"          	 , new Integer(KEY_SCRN_HOME_PRIVACY		     )}; 
    public static Object[] SCRN_HOME_REPORT	        					 = {"technicalReport"    , new Integer(KEY_SCRN_HOME_REPORT		     )}; 

    /* commands for Location screen*/
    public static Object[] SCRN_LOCATION_OPERATION_LIST                  = {"listView"        , new Integer(KEY_SCRN_LOCATION_OPERATION_LIST      )}; 
    public static Object[] SCRN_LOCATION_OPERATION_MAP                   = {"mapView"         , new Integer(KEY_SCRN_LOCATION_OPERATION_MAP         )}; 
    public static Object[] SCRN_LOCATION_OPERATION_LOC_SEARCH            = {"searchLocation"  , new Integer(KEY_SCRN_LOCATION_OPERATION_LOC_SEARCH      )}; 
    public static Object[] SCRN_LOCATION_OPERATION_FILTER                = {"filter"          , new Integer(KEY_SCRN_LOCATION_OPERATION_FILTER      )}; 
    public static Object[] SCRN_LOCATION_OPERATION_SELECT_LOCATION       = {"selectLocation"  , new Integer(KEY_SCRN_LOCATION_OPERATION_SELECT_LOCATION      )}; 
    public static Object[] SCRN_LOCATION_OPERATION_SELECT_MAP            = {"selectMap"       , new Integer(KEY_SCRN_LOCATION_OPERATION_SELECT_MAP      )}; 

    /* commands for Filter screen*/

    /* commands for Favorite screen*/
    public static Object[] SCRN_FAVORITE_OPERATION_LISTMAP               = {"fovarite_list"   , new Integer(KEY_SCRN_FAVORITE_OPERATION_LISTMAP      )}; 
    public static Object[] SCRN_FAVORITE_NEARBYLOCATIONS                 = {"fovarite_nearby"   , new Integer(KEY_SCRN_FAVORITE_NEARBYLOCATIONS      )}; 

    /* commands for Flyers screen*/
    public static Object[] SCRN_FLYERS_OPERATION_LISTMAP                 = {"flyers_list"     , new Integer(KEY_SCRN_FLYERS_OPERATION_LISTMAP      )}; 
    public static Object[] SCRN_FLYERS_OPERATION_SEARCHNEARBY            = {"search_nearby"   , new Integer(KEY_SCRN_FLYERS_OPERATION_SEARCHNEARBY )}; 

    /* commands for details screen*/
    public static Object[] SCRN_DETAILS_SERVICE_DETAIL_ON                = {"service_details_on"  , new Integer(KEY_SCRN_DETAILS_SERVICE_DETAIL_ON      )}; 
    public static Object[] SCRN_DETAILS_SERVICE_DETAIL_OFF               = {"service_details_off" , new Integer(KEY_SCRN_DETAILS_SERVICE_DETAIL_OFF      )}; 
    public static Object[] SCRN_DETAILS_FAVORITE                		 = {"setFavorite"         , new Integer(KEY_SCRN_DETAILS_FAVORITE	      )}; 
    public static Object[] SCRN_DETAILS_WEBSITE	                		 = {"location_website" 	  , new Integer(KEY_SCRN_DETAILS_WEBSITE	      )}; 
    public static Object[] SCRN_DETAILS_FLYERS	                		 = {"location_flyers" 	  , new Integer(KEY_SCRN_DETAILS_FLYERS	      )}; 
    public static Object[] SCRN_DETAILS_DIRECTIONS	                	 = {"directions" 		  , new Integer(KEY_SCRN_DETAILS_DIRECTIONS	      )}; 
    public static Object[] SCRN_DETAILS_REPORT_ERROR	                 = {"reporterror" 		  , new Integer(KEY_SCRN_DETAILS_REPORT_ERROR	      )}; 

    /* commands for report screen*/
    public static Object[] SCRN_REPORT_SUBMIT	                         = {"submitReport" 		  , new Integer(KEY_SCRN_REPORT_SUBMIT	      )}; 
    
    /* commands for popup screen*/
    public static Object[] SCRN_RETAIL_POPUP_OK	                         = {"OK" 		  		  , new Integer(KEY_SCRN_RETAIL_POPUP_OK	      )}; 

    
    protected AppCommands() {
        initializeCmdDefinition();
    }
    
    protected Hashtable commands = new Hashtable();
    protected void initializeCmdDefinition() {
        //---------Node Definition--------//
        commands.put(SCREEN_WELCOME[0]                       			, SCREEN_WELCOME[1]                     );

        /* commands for Terms and Conds screen*/
        commands.put(SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT[0]          , SCRN_TERMS_OPERATION_TERMNCONDS_ACCEPT[1]        );
        commands.put(SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE[0]         , SCRN_TERMS_OPERATION_TERMNCONDS_DECLINE[1]       );

        /* commands for Home screen*/
        commands.put(SCRN_HOME_LOCATIONS[0]          					, SCRN_HOME_LOCATIONS[1]        				   );
        commands.put(SCRN_HOME_FAVORITES[0]          					, SCRN_HOME_FAVORITES[1]        				   );
        commands.put(SCRN_HOME_FLYERS[0]          					    , SCRN_HOME_FLYERS[1]        				   	   );
        commands.put(SCRN_HOME_TERMSCONDS[0]          					, SCRN_HOME_TERMSCONDS[1]        				   );
        commands.put(SCRN_HOME_PRIVACY[0]          					    , SCRN_HOME_PRIVACY[1]        				   	   );
        commands.put(SCRN_HOME_REPORT[0]          					    , SCRN_HOME_REPORT[1]        				   	   );

        /* commands for Location screen*/
        commands.put(SCRN_LOCATION_OPERATION_LIST[0]                    , SCRN_LOCATION_OPERATION_LIST[1]                  );
        commands.put(SCRN_LOCATION_OPERATION_MAP[0    ]                 , SCRN_LOCATION_OPERATION_MAP[1]                   );
        commands.put(SCRN_LOCATION_OPERATION_LOC_SEARCH[0]              , SCRN_LOCATION_OPERATION_LOC_SEARCH[1]            );
        commands.put(SCRN_LOCATION_OPERATION_FILTER[0]                  , SCRN_LOCATION_OPERATION_FILTER[1]                );
        commands.put(SCRN_LOCATION_OPERATION_SELECT_LOCATION[0]         , SCRN_LOCATION_OPERATION_SELECT_LOCATION[1]       );
        commands.put(SCRN_LOCATION_OPERATION_SELECT_MAP[0]              , SCRN_LOCATION_OPERATION_SELECT_MAP[1]            );

        /* commands for Filter screen*/

        /* commands for Favorite screen*/
        commands.put(SCRN_FAVORITE_OPERATION_LISTMAP[0]                 , SCRN_FAVORITE_OPERATION_LISTMAP[1]            	);
        commands.put(SCRN_FAVORITE_NEARBYLOCATIONS[0]                 	, SCRN_FAVORITE_NEARBYLOCATIONS[1]            	);

        /* commands for Flyers screen*/
        commands.put(SCRN_FLYERS_OPERATION_LISTMAP[0]                   , SCRN_FLYERS_OPERATION_LISTMAP[1]           	 	);
        commands.put(SCRN_FLYERS_OPERATION_SEARCHNEARBY[0]              , SCRN_FLYERS_OPERATION_SEARCHNEARBY[1]           	 	);
        
        /* commands for details screen*/
        commands.put(SCRN_DETAILS_SERVICE_DETAIL_ON[0]              	, SCRN_DETAILS_SERVICE_DETAIL_ON[1]            		);
        commands.put(SCRN_DETAILS_SERVICE_DETAIL_OFF[0]              	, SCRN_DETAILS_SERVICE_DETAIL_OFF[1]            	);
        commands.put(SCRN_DETAILS_FAVORITE[0]         			     	, SCRN_DETAILS_FAVORITE[1]            				);
        commands.put(SCRN_DETAILS_WEBSITE[0]         			     	, SCRN_DETAILS_WEBSITE[1]            				);
        commands.put(SCRN_DETAILS_FLYERS[0]         			     	, SCRN_DETAILS_FLYERS[1]            				);
        commands.put(SCRN_DETAILS_DIRECTIONS[0]         			    , SCRN_DETAILS_DIRECTIONS[1]            			);
        commands.put(SCRN_DETAILS_REPORT_ERROR[0]         			    , SCRN_DETAILS_REPORT_ERROR[1]            			);

        /* commands for report screen*/
        commands.put(SCRN_REPORT_SUBMIT[0]         			            , SCRN_REPORT_SUBMIT[1]            			);
        
        /* commands for popup screen*/
        commands.put(SCRN_RETAIL_POPUP_OK[0]         			        , SCRN_RETAIL_POPUP_OK[1]            			);

    }

	
}
