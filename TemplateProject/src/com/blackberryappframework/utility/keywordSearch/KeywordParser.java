package com.blackberryappframework.utility.keywordSearch;

import java.util.Vector;

import com.blackberryappframework.utility.StringUtils;

public class KeywordParser {
	private static KeywordParser parser = null;
	public static KeywordParser getKeywordParser() {
		if (parser == null)
			parser = new KeywordParser();
		
		return parser;
	}
	
	public Vector parserAddress(String address) {
		Vector keywords = new Vector();
		
		//add address
		int curTokenIndex = 0;
		for (int i = 0; i < address.length(); i++) {
			char curChar = address.charAt(i);
			if (isSeparator(curChar)) {
				String token = address.substring(curTokenIndex, i);
				if (isValidKeyword(token)) {
					token = validateKeyword(token);
					if (token.length() > 0)
						keywords.addElement(token);
				}
				
				curTokenIndex = i+1;
			}
		}
		
		if (curTokenIndex < address.length()) {
			String token = address.substring(curTokenIndex);
			if (isValidKeyword(token)) {
				token = validateKeyword(token);
				if (token.length() > 0)
					keywords.addElement(token);
			}
		}
		
		return keywords;
	}

	private Vector systemReservedKeywords;
	private KeywordParser() {
		systemReservedKeywords = new Vector();
		initializeReservedKeywordsTable();
	}
	
	private String validateKeyword(String token) {
		String result;
		
		int startIndex = 0;
		for (int i = 0; i < token.length(); i++) {
			char ch = token.charAt(i);
			if (StringUtils.isDigitAlphebet(ch)) {
				startIndex = i;
				break;
			}
		}
		
		int endIndex = token.length() - 1;
		for (int i = token.length()-1; i > startIndex; i--) {
			char ch = token.charAt(i);
			if (StringUtils.isDigitAlphebet(ch)) {
				endIndex = i;
				break;
			}
		}
		
		result = token.substring(startIndex, endIndex + 1).toLowerCase();
		return result;
	}
	
	/**
	 * To check whether a character is a keyword separator or not.
	 * A valid seperator can be " " or ","
	 * */
	private final char SEPERATOR_SPACE = ' ';
	private final char SEPERATOR_COMMA = ',';
	private boolean isSeparator(char ch) {
		boolean result = false;
		
		switch (ch) {
		case SEPERATOR_SPACE:
		case SEPERATOR_COMMA:
			result = true;
			break;
		}
		
		return result;
	}
	
	
	private boolean isValidKeyword(String keyword) {
		boolean result = true;
		
		if (keyword == null)
			result = false;
		else {
			keyword = keyword.trim().toLowerCase();
			if (keyword.length() < 2)
				result = false;
			else if (StringUtils.isAllDigit(keyword))
				result = false;
			else if (systemReservedKeywords.contains(keyword))
				result = false;
		}
		
		return result;
	}
	
	private void initializeReservedKeywordsTable() {
		//road type and abbr
        systemReservedKeywords.addElement( "street"    );
        systemReservedKeywords.addElement( "st"        );
        systemReservedKeywords.addElement( "st."       );
        systemReservedKeywords.addElement( "road"      );
        systemReservedKeywords.addElement( "rd"        );
        systemReservedKeywords.addElement( "rd."       );
        systemReservedKeywords.addElement( "r.d."      );
        systemReservedKeywords.addElement( "drive"     );
        systemReservedKeywords.addElement( "dr"        );
        systemReservedKeywords.addElement( "dr."       );
        systemReservedKeywords.addElement( "place"     );
        systemReservedKeywords.addElement( "pl"        );
        systemReservedKeywords.addElement( "pl."       );
        systemReservedKeywords.addElement( "avenue"    );
        systemReservedKeywords.addElement( "ave"       );
        systemReservedKeywords.addElement( "ave."      );
        systemReservedKeywords.addElement( "av"        );
        systemReservedKeywords.addElement( "av."       );
        systemReservedKeywords.addElement( "cresent"   );
        systemReservedKeywords.addElement( "cres"      );
        systemReservedKeywords.addElement( "cres."     );
        systemReservedKeywords.addElement( "driveway"  );
        systemReservedKeywords.addElement( "alley"     );
        systemReservedKeywords.addElement( "boulevard" );
        systemReservedKeywords.addElement( "blvd"      );
        systemReservedKeywords.addElement( "blvd."     );
        systemReservedKeywords.addElement( "terrace"   );
        systemReservedKeywords.addElement( "highway"   );
        systemReservedKeywords.addElement( "hwy"       );
        systemReservedKeywords.addElement( "lane"      );
        systemReservedKeywords.addElement( "parkway"   );
        systemReservedKeywords.addElement( "pky"       );
        systemReservedKeywords.addElement( "bay"       );
        systemReservedKeywords.addElement( "campus"    );

        //road direction
        systemReservedKeywords.addElement( "east"      );
        systemReservedKeywords.addElement( "south"     );
        systemReservedKeywords.addElement( "west"      );
        systemReservedKeywords.addElement( "north"     );
        systemReservedKeywords.addElement( "northeast" );
        systemReservedKeywords.addElement( "ne"        );
        systemReservedKeywords.addElement( "northwest" );
        systemReservedKeywords.addElement( "nw"        );
        systemReservedKeywords.addElement( "southeast" );
        systemReservedKeywords.addElement( "se"        );
        systemReservedKeywords.addElement( "southwest" );
        systemReservedKeywords.addElement( "sw"        );
	}
	
}
