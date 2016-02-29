package org.jw.territorymanager;
import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPerferencesExecutor<T> {
 
	private Context context; 
 
	public SharedPerferencesExecutor(Context context) {
		this.context = context;
	}
 
	public void save(String key, T sharedPerferencesEntry) {
 
		  SharedPreferences appSharedPrefs = PreferenceManager
		  .getDefaultSharedPreferences(context.getApplicationContext());
		  Editor prefsEditor = appSharedPrefs.edit();
		  Gson gson = new Gson();
		  String json = gson.toJson(sharedPerferencesEntry);
		  prefsEditor.putString(key, json);
		  prefsEditor.commit();
 
	}
 
	public T retreive(String key, Class<T> clazz) {
 
		SharedPreferences appSharedPrefs = PreferenceManager
		  .getDefaultSharedPreferences(context.getApplicationContext());
 
		 Gson gson = new Gson();
		 String json = appSharedPrefs.getString(key, "");
		 return (T) gson.fromJson(json, clazz);
	}
}
