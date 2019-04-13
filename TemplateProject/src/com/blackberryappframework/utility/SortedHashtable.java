package com.blackberryappframework.Utility;

import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.util.Comparator;
import net.rim.device.api.util.SimpleSortingVector;

public class SortedHashtable {
	private SimpleSortingVector keys;
	private Hashtable table;
	
	/**
	 * Construct a new, empty hashtable with a default capacity and load factor.
	 */
	public SortedHashtable() {
		clear();
	}
	
	/**
	 * Flush this hashtable
	 */
	public void clear() {
		keys = new SimpleSortingVector();
		table = new Hashtable();
		
		keys.setSortComparator(new ComparatorForString());
	}
	
	/**
	 * Determines if some key maps into the specified value in this hashtable.
	 * @param value Value to search for.
	 * @return True if some key maps to the provided value in this hashtable; otherwise, false.
	 */
	public boolean contains(Object value) {
		return table.contains(value);
	}
	
	/**
	 * Determines if the specified object is a key in this hashtable.
	 * @param key Key to test
	 * @return True if provided object is a key in this hashtable; otherwise, false.
	 */
	public boolean containsKey(Object key) {
		return table.containsKey(key);
	}
	
	/**
	 * Retrieves value by key.
	 * @param key Key in this hashtable.
	 * @return Object value associated with provided key, or null if no object associated with the key.
	 */
	public Object get(Object key) {
		return table.get(key);
	}
	
	/**
	 * Retrieves an enumeration of the keys in a sorted order in this hashtable.
	 * @return Enumeration of keys in this hashtable.
	 */
	public Enumeration keys() {
		keys.reSort();
		return keys.elements();
	}
	
	/**
	 * Maps value to key in this hashtable
	 * @param key
	 * @param value
	 * @return Previous value associated with key, or null if key had no previous associated value.
	 */
	public Object put(Object key, Object value) {
		keys.addElement(key);
		return table.put(key, value);
	}
	
	/**
	 * Maps value to key in this hashtable, and returns a reference to this object so that method calls can be chained together
	 * @param key
	 * @param value
	 * @return A reference to this object so that method calls can be chained together
	 */
	public SortedHashtable with(Object key, Object value) {
		put(key, value);
		return this;
	}
	
	/**
	 * Removes value by key.
	 * @param key Key for value to remove.
	 * @return Value that had been associated with key, or null if no value had been associated with key.
	 */
	public Object remove(Object key) {
		keys.removeElement(key);
		return table.remove(key);
	}
	
	/**
	 * Retrieves the number of keys in this hashtable
	 * @return Number of keys in this hashtable.
	 */
	public int size() {
		return keys.size();
	}
	
	/**
	 * A comparator for strings
	 */
	class ComparatorForString implements Comparator {

		public int compare(Object o1, Object o2) {
			return ((String) o1).compareTo((String) o2);
		}
		
	}
}
