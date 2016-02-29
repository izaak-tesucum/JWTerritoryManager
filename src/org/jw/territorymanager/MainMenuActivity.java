package org.jw.territorymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public void Overview(View view)
	{
		Intent startMenu = new Intent(this, Overview.class);
		startActivity(startMenu);
		//finish();
	}
	
	//check if shopper has been made. if not do not start activity
	public void TerritoriesCheckedOut(View view)
	{
		Intent goToTerritoriesCheckedOut = new Intent(this, CheckedOutOverview.class);
		startActivity(goToTerritoriesCheckedOut);
	}

	
	@Override
	public void onStop(){
		super.onStop();
		//As a precaution.  Just to make sure the user's data is saved when this Activity is stopped.
		//CardHolderContent.saveTerritoryInfo(getApplicationContext(), "territories_checked_out");
	}
	
	
	/*public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}*/
}
