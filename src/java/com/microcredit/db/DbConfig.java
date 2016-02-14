package com.com.microcredit.db;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Get a configuration information from resource bundle(*.properties).
 * @author kwseo
 *
 */
public class DbConfig {

	static ResourceBundle resDbConfiguration = ResourceBundle.getBundle("dbconf");
	
	/**
	 * Get information for DB connection.
	 * @param key	key of resource bundle
	 * @return	String
	 */
	public static String getDbConfiguration(String key){
		return resDbConfiguration.getString(key);
	}
	
	/**
	 * Get a collection of information for DB connection.
	 * @param keys	The set of keys.
	 * @return	{@link HashMap}
	 */
	public static HashMap<String, String> getDbConfiguration(String[] keys){
		HashMap<String, String> resourceMap = new HashMap<String, String>();
		for(int i = 0; i < keys.length; i++){
			resourceMap.put(keys[i], resDbConfiguration.getString(keys[i]));
		}
		return resourceMap;
	}

}
