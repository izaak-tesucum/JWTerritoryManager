package org.jw.territorymanager;

//import java.text.DateFormat;
//import java.util.Date;

//import java.util.Calendar;
//import java.util.Date;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
//import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AddTerritory extends Activity {
	//private String username;
	public static final String T_DATA_KEY = "territory";
	public static final String description_DATA_KEY = "description";
	public static final String type_DATA_KEY = "type";
	public static final String level_DATA_KEY = "level";
	public static final String in_DATA_KEY = "N/A";
	public static final String out_DATA_KEY = "N/A";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_territory);
		
		//username = getIntent().getStringExtra(Login.username);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_territory, menu);
		return true;
	}
	
	public void Add(View view)
	{
	    String check1= ((TextView)findViewById(R.id.editCheckedIn)).getText().toString();
	    String check2= ((TextView)findViewById(R.id.editCheckedOut)).getText().toString();
	    String c1= check1.toUpperCase(Locale.getDefault());
	    String c2= check2.toUpperCase(Locale.getDefault());
	    Log.w("JWTM", "c1 = "+c1);
	    Log.w("JWTM", "c2 = "+c2);
	    if(((c1!="YES")||(c1!="NO"))&&((c2!="YES")||(c2!="NO")))
	    {
	    	Intent intent = new Intent();
		    //intent.putExtra(Login.username, username);
		    intent.putExtra(AddTerritory.T_DATA_KEY,((TextView)findViewById(R.id.editTerritory)).getText().toString());
		    intent.putExtra(AddTerritory.description_DATA_KEY,((TextView)findViewById(R.id.editDescription)).getText().toString());
		    intent.putExtra(AddTerritory.type_DATA_KEY,((TextView)findViewById(R.id.editType)).getText().toString());
		    intent.putExtra(AddTerritory.level_DATA_KEY,((TextView)findViewById(R.id.editLevel)).getText().toString());
		    intent.putExtra(AddTerritory.in_DATA_KEY,((TextView)findViewById(R.id.editCheckedIn)).getText().toString());
		    intent.putExtra(AddTerritory.out_DATA_KEY,((TextView)findViewById(R.id.editCheckedOut)).getText().toString());
	    	setResult(RESULT_OK, intent);  //Set the result data that is returned to the parent activity when this activity dies.
	    	finish();
	    }
	    
	    else
	    {
	    	final String[] service = {"OK"};
            AlertDialog.Builder adb = new AlertDialog.Builder(AddTerritory.this);
            adb.setTitle("Checked out and Checked in must have a value of 'yes' or 'no'");
            adb.setItems(service, new OnClickListener() 
            {

                public void onClick(DialogInterface dialog, int which) 
                {
                    switch(which)
                    {
                    	case 0:
        				
                    }

                }
            });
            adb.show();
	    }
	    
	}
}
	



