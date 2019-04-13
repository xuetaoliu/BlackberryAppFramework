package com.blackberryappframework.Utility;

import java.util.Enumeration;
import java.util.Hashtable;

public class CollectionUtils {
	public static Hashtable cloneHashtable(Hashtable source) {
		Hashtable target = null;
		
		if (source != null) {
			target = new Hashtable();
			
			Enumeration e = source.keys();
			while (e.hasMoreElements()) {
				Object key = e.nextElement();
				Object value = source.get(key);
				
				target.put(key, value);
			}
		}
		
		return target;
	}
	
	public static void copyHashtable(Hashtable src, Hashtable tgt) {
		if (src != null && tgt != null) {
			
			Enumeration e = src.keys();
			while (e.hasMoreElements()) {
				Object key = e.nextElement();
				Object value = src.get(key);
				
				tgt.put(key, value);
			}
		}
	}
}
