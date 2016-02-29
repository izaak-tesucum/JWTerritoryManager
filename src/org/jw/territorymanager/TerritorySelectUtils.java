package org.jw.territorymanager;

import java.util.Hashtable;

public class TerritorySelectUtils {
	public static final int TERRITORY_INFO = 1;
	
	public static final Hashtable<Integer, String> TERRITORY_NAMES;
	static{
		TERRITORY_NAMES = new Hashtable<Integer, String>();
		TERRITORY_NAMES.put(TERRITORY_INFO, "Territory Info");
		
	}
}
