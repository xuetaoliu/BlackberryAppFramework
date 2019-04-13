package com.blackberryappframework.utility;

import java.util.Vector;

import javax.microedition.global.Formatter;

import net.rim.device.api.ui.Font;
import net.rim.device.api.util.MathUtilities;

public class StringUtils {
	public static Vector wrapText (String text, int width, boolean forwardBreak, Font font, int maxLines)  {
		Vector wrappedLines = wrapText(text, width, forwardBreak, font);
		
		//if the wrapped result has too many lines, we will combine the last result contain all the lines
		if (wrappedLines.size() > maxLines) {
			StringBuffer lastLine = new StringBuffer();
			for (int i = wrappedLines.size()-1; i >= maxLines; i--) {
				lastLine.insert(0, " " + (String)wrappedLines.elementAt(i));
				wrappedLines.removeElementAt(i);
			}
			
			lastLine.insert(0, (String)wrappedLines.elementAt(maxLines-1));
			wrappedLines.removeElementAt(maxLines-1);
			wrappedLines.addElement(lastLine.toString());
		}
		
		return wrappedLines;
	}

	public static Vector wrapText (String text, int width, boolean forwardBreak, Font font)  {
		Vector result = new Vector ();
		if (text ==null) return result;
		
	    // The current index of the cursor
	    int current = 0;
	    // The next line break index
	    int lineBreak = -1;
	    // The space after line break
	    int nextSpace = -1;
	 
		boolean hasMore = true;
	    while (hasMore) {
	    	//Find the line break
	    	while (true) {
	           lineBreak = nextSpace;
	           if (lineBreak == text.length() - 1) 
	           {
	               // We have reached the last line
	               hasMore = false;
	               break;
	           } 
	           else 
	           {
	               nextSpace = text.indexOf(' ', lineBreak+1);
	               if (nextSpace == -1) {
	                  nextSpace = text.length()-1;
	                  if (forwardBreak) hasMore = false;
	               }
	               int linewidth = font.getAdvance(text.substring(current, nextSpace+1));
	               // If too long, break out of the find loop
	               if (linewidth > width) 
	                  break;
	           }
	      }
	
	      String line ;
	      if (forwardBreak) {
	    	  line = text.substring(current, nextSpace + 1);
		      lineBreak = nextSpace;
	      } else {
	    	  line = text.substring(current, lineBreak + 1);
	      }
	      result.addElement(line);
	      
	      current = lineBreak + 1;
	    }

	    return result;
	}
	
	public static String floatToStr(float f, int decimal, double maxNumber) {
		Formatter formatter = new Formatter(); 

		double valueI = (double)(f * MathUtilities.pow(10, decimal) + 0.5);
		String valueS = formatter.formatNumber((double)valueI / MathUtilities.pow(10, decimal),decimal);
		if (f > maxNumber)
			valueS = "inf";
		
		return valueS;
	}
	
	public static String doubleToStr(double f, int decimal, double maxNumber) {
		Formatter formatter = new Formatter(); 

		double round = 0.5;
		if (f < 0) round = -0.5;
		double valueI = (double)(f * MathUtilities.pow(10, decimal) + round);
		String valueS = formatter.formatNumber((double)valueI / MathUtilities.pow(10, decimal),decimal);
//		double valueI = (double)(f);
//		String valueS = formatter.formatNumber((double)valueI,decimal);
		if (f > maxNumber)
			valueS = "inf";
		
		return valueS;
	}
	
	public static int StringToInt(String str) throws Exception {
		int result = 0 ;
		
		if (str == null || str.length() != 0 ) {
			result = (Integer.valueOf(str)).intValue();
		} else {
			throw(new Exception ("empty string"));
		}
		
		return result;
	}

	public static boolean StringToBoolean(String str) throws Exception {
		boolean result = false ;
		if (str == null)
			return result;
		
		if (str.length() != 0 ) {
			if (str.equalsIgnoreCase("true"))
				result = true;
			else if (str.equalsIgnoreCase("false"))
				result = false;
			else 
				throw(new Exception ("invalid string"));
		} else
			throw (new Exception ("empty string"));
		
		return result;
	}
	
	public static int compareStringAlphabetic(String stringA, String stringB) {
		int result = -1;
		if (stringA == null && stringB == null)
			result = 0;
		else if (stringA == null)
			result = -1;
		else if (stringB == null)
			result = 1;
		else if (stringA.equals(stringB)) 
			result = 0;
		else {
			boolean finished = false;
			for (int i = 0; i < stringA.length(); i++) {
				char charA = stringA.charAt(i);
				if (i >= stringB.length()) { //StringB is a subString of StringA
					result = 1;
					finished = true;
					break;
				}
				
				char charB = stringB.charAt(i);
				if (charA < charB) {
					result = -1;
					finished = true;
				} else if ( charA > charB) {
					result = 1;
					finished = true;
				}
				if (finished) break;
			}
			
			if (!finished) { //String A is a subString of StringB
				result = -1;
			}
		}
		
		return result;
	}
	
	public static String replace(String sourceStr, String subStr, String replaceStr, boolean all) {
		
		StringBuffer buffer = new StringBuffer();
		
		int beginIndex = 0;
		int idx = sourceStr.indexOf(subStr, beginIndex);
		
		while (idx > 0) {
			buffer.append(sourceStr.substring(beginIndex, idx));
			buffer.append(replaceStr);

			beginIndex = idx + subStr.length();
			idx = sourceStr.indexOf(subStr, beginIndex);
			
			if (!all) break;
		}
		
		if (beginIndex < sourceStr.length()) {
			buffer.append(sourceStr.substring(beginIndex));
		}
		
		return buffer.toString();
	}
	
	public static boolean isAllDigit(String token) {
		boolean result = false;
		
		try{
			Double value = Double.valueOf(token);
			if (!value.isNaN())
				result = true;
		} catch (NumberFormatException  e)  { 
			;
		} catch (NullPointerException e) { 
			;
		} 

		return result;
	}
	
	public static boolean isDigitAlphebet(char ch) {
		boolean result = ch >= '0' && ch <= 9;
		result |= ch >= 'A' && ch <= 'Z';
		result |= ch >= 'a' && ch <= 'z';
		
		return result;
	}
}
