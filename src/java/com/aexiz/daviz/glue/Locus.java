package com.aexiz.daviz.glue;

import java.util.HashMap;
/**
 * The Locus class which is focued on manipulating and storing of properties of other class properties such as edgemodel,nodemodel or a node's coordinate
 * @author Hans-Dieter
 *
 */
public abstract class Locus {
	
	private HashMap<String,Object> clientProperties = new HashMap<String,Object>();
	/**
	 * Places a certain object property into the object HashMap
	 * @param key The type of property that the object carries
	 * @param value The object property itself
	 */
	public void putClientProperty(String key, Object value) {
		if (value == null) {
			clientProperties.remove(key);
		} else {
			clientProperties.put(key, value);
		}
	}
	/**
	 * Checks if a certain object is of said property
	 * @param key A string value that could determine if object is the desired type
	 * @return If the object has the property listed in the key
	 */
	public boolean hasClientProperty(String key) {
		return clientProperties.containsKey(key);
	}
	/**
	 * Checks if a certain object is of said property
	 * @param key A string value that could determine if object is the desired type
	 * @param type A class value to check if the property is also of said class type
	 * @return If the object has the property listed in the key
	 */
	public boolean hasClientProperty(String key, Class<?> type) {
		if (!clientProperties.containsKey(key)) return false;
		Object value = clientProperties.get(key);
		return type.isInstance(value);
	}
	/**
	 * Retrieves the properties of an object by the determined key
	 * @param key A string value of the requested object type that needs to be returned
	 * @return The requested object property
	 */
	public Object getClientProperty(String key) {
		return clientProperties.get(key);
	}
	
}
