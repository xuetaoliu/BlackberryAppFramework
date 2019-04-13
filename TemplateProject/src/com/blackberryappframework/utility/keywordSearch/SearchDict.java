package com.blackberryappframework.utility.keywordSearch;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.util.Persistable;

public class SearchDict implements Persistable{

	private Hashtable dict;

	public SearchDict() {
		dict = new Hashtable();
	}

	/**
	 * add a reference object (can be any type the project wanted), which has keywords of {keywords}
	 * @param keywords the set of keywords of the reference object
	 * @param reference the reference object
	 * */
	public void add(String[] keywords, Object reference) {
		if (reference == null)
			return;
		
		for (int i=0;i<keywords.length;i++) {
			Vector list = null;
			if (!dict.containsKey(keywords[i])) {
				list = new Vector();
				list.addElement(reference);
				dict.put(keywords[i], list);
			} else {
				list = (Vector)dict.get(keywords[i]);
				list.addElement(reference);
			}
		}
	}

	/**
	 * remove a reference object (can be any type the project wanted), which has keywords of {keywords}
	 * @param keywords the set of keywords of the reference object
	 * @param reference the reference object
	 * */
	public void remove(String[] keywords, Object reference) {
		for (int i=0;i<keywords.length;i++) {
			if (dict.containsKey(keywords[i])) {
				Vector list = (Vector)dict.get(keywords[i]);
				list.removeElement(reference);
			}
		}
	}

	/**
	 * Search for the references which has the given keywords
	 * @param keywords the set of keywords to search
	 * @return the set of references
	 * */
	public Vector search(String[] keywords) {
		Vector results = new Vector();
		
		for (int i=0; i<keywords.length; i++) {
			if (dict.containsKey(keywords[i])) {
				Vector list = (Vector)dict.get(keywords[i]);
				for(int j=0;j<list.size();j++){
					Object reference = list.elementAt(j);
					if(!results.contains(reference)){
						results.addElement(reference);
					}
				}
			}
		}
		
		return results;
	}
}
