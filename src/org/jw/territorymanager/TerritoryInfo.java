package org.jw.territorymanager;

//import java.util.ArrayList;

import android.os.Bundle;
//import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
//import android.view.View;
//import android.widget.EditText;
import android.widget.TextView;

public class TerritoryInfo extends Activity {
	/*final public static String CHECKOUT_NAME = "CHECKOUT_NAME";
	final public static String DELETE = "DELETE_TERRITORY";
	final private int SAVE_SUCCESS = 1;
	public static final String sendposition= "position";
	public  static final String territory = "This_territory";
	public static final  String description = "This_description";
	public static final  String type = "This_type";
	public static final  String dlevel = "This_level";
	public static final  String checkedIn = "This_in";
	public static final  String checkedOut = "This_out";
	public static final  String holder = "This_holder";
	public static final  String date_in = "This_date_in";
	public static final  String date_out = "This_date_out";
	public static String TERRITORY_LIST_KEY = "territory_list";
	Territories t;
	ArrayList<Parcelable> temp;
	public ArrayList<Parcelable> getPosition()
	{
		return temp;
	}
	
	public void setPosition(ArrayList<Parcelable> _temp)
	{
		temp= _temp;
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_territory_info);
		// Get the description from the intent
				Intent intent = getIntent();
			    String territory = intent.getStringExtra(CheckedOutOverview.territory);
			    String description = intent.getStringExtra(CheckedOutOverview.description);
			    String type = intent.getStringExtra(CheckedOutOverview.type);
			    String level = intent.getStringExtra(CheckedOutOverview.dlevel);
			    String checkedIn = intent.getStringExtra(CheckedOutOverview.checkedIn);
			    String checkedOut = intent.getStringExtra(CheckedOutOverview.checkedOut);
			    String holder = intent.getStringExtra(CheckedOutOverview.holder);
			    String date_in = intent.getStringExtra(CheckedOutOverview.date_in);
			    String date_out = intent.getStringExtra(CheckedOutOverview.date_out);
			    //setPosition(intent.getParcelableArrayListExtra(Overview.TERRITORY_LIST_KEY));
			    //t=new Territories(territory, description, type, level, checkedIn, checkedOut);
			    //t.setTerritoryInfo(territory, description, type, level, checkedIn, checkedOut);
			    //ArrayList<Territories> t=intent.getParcelbleArrayListExtra(Overview.TERRITORY_LIST_KEY2);
			    //setPosition(t);

			    // Create the text view
			    TextView showterritory = (TextView)findViewById(R.id.numbertextView);
			    showterritory.setText(territory);
			    
			    TextView showdescription = (TextView)findViewById(R.id.desctextView);
			    showdescription.setText(description);
			    
			    TextView showtype = (TextView)findViewById(R.id.typetext);
			    showtype.setText(type);
			    
			    TextView showDifficultyLevel = (TextView)findViewById(R.id.leveltext);
			    showDifficultyLevel.setText(level);
			    
			    TextView showCheckedIn = (TextView)findViewById(R.id.intext);
			    showCheckedIn.setText(checkedIn);
			    
			    TextView showCheckedOut = (TextView)findViewById(R.id.outtext);
			    showCheckedOut.setText(checkedOut);
			    
			    TextView showCardHolder = (TextView)findViewById(R.id.holdertext);
			    showCardHolder.setText(holder);
			    
			    TextView dateCheckedOut = (TextView)findViewById(R.id.date2);
			    dateCheckedOut.setText(date_out);
			    
			    TextView dateCheckedIn = (TextView)findViewById(R.id.date1);
			    dateCheckedIn.setText(date_in);
			    
			    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.territory_info, menu);
		return true;
	}
	
	
	/*public void Checkout(View view)
	{
		Intent i = new Intent(this, Checkout.class);
		i.putParcelableArrayListExtra(TerritoryInfo.TERRITORY_LIST_KEY, getPosition());
		i.putExtra(TerritoryInfo.territory, t.getTerritory());
		i.putExtra(TerritoryInfo.description, t.getDescription());
		i.putExtra(TerritoryInfo.type, t.getType());
		i.putExtra(TerritoryInfo.dlevel, t.getLevel());
		i.putExtra(TerritoryInfo.checkedIn, t.getCheckedIn());
		i.putExtra(TerritoryInfo.checkedOut, t.getCheckedOut());
		startActivityForResult(i, SAVE_SUCCESS);
		//finish();
	}
	
	public void Delete(View view)
	{
		Intent intent = getIntent();
		intent.putExtra(DELETE, true);
		setResult(RESULT_OK,intent);
		finish();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		if(resultCode != RESULT_CANCELED)
		{
			if(requestCode == SAVE_SUCCESS)
			{
				//Intent cardHolder= getIntent();
				String holder = data.getStringExtra(Checkout.N_DATA_KEY);
			    //String date_in = cardHolder.getStringExtra(Overview.date_in);
			    String date_out = data.getStringExtra(Checkout.TIME_DATA_KEY);
			    
			    TextView showCardHolder = (TextView)findViewById(R.id.holdertext);
			    showCardHolder.setText(holder);
			    
			    TextView dateCheckedOut = (TextView)findViewById(R.id.date2);
			    dateCheckedOut.setText(date_out);
			    
			    TextView dateCheckedIn = (TextView)findViewById(R.id.date1);
			    dateCheckedIn.setText(date_in);
			}
		}
	}*/
	

}

