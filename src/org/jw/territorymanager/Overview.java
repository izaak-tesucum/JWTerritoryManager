package org.jw.territorymanager;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Overview extends Activity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	final private int NEW_TERRITORY = 1;
	public static final String Position = "edu.uark.csce.iitesucu.razorsquare.overview.postindex";
	final public static String DELETE = "DELETE_TERRITORY";
	private ArrayList<Territories> itemList = new ArrayList<Territories>();
	private TerritoryAdapter listAdapter = null;
	public static String TERRITORY_LIST_KEY = "territory_list";
	final Context context = this;
	public static final String territory = "This_territory";
	public static final String description = "This_description";
	public static final String type = "This_type";
	public static final String dlevel = "This_level";
	public static final String checkedIn = "This_in";
	public static final String checkedOut = "This_out";
	public static final String holder = "This_holder";
	public static final String date_in = "This_date_in";
	public static final String date_out = "This_date_out";
	public TerritoryContentProvider tc;
	private boolean searchMode = false;
	//String c = null;
	int r;
	String t;
	private EditText inputSearch;
	private Button searchButton;

	public void setRow(int i) {
		r = i;
	}

	public int getRow() {
		return r;
	}

	public void setTerr(String i) {
		t = i;
	}

	public String getTerr() {
		return t;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		// Initialize the list and the list adapter:
		searchButton= (Button) findViewById(R.id.searchButton);
		ListView itemView = (ListView) findViewById(R.id.TerritoryListView);
		listAdapter = new TerritoryAdapter(this, itemList);

		// Set the BaseAdapter for the ListView:
		itemView.setAdapter(listAdapter);
		inputSearch = (EditText) findViewById(R.id.searchTerritory);


		itemView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Territories territory = (Territories) parent
						.getItemAtPosition(position);
	
				String terr = territory.getTerritory();
				String description = territory.getDescription();
				String type = territory.getType();
				String level = territory.getLevel();
				String checkedIn = territory.getCheckedIn();
				String checkedOut = territory.getCheckedOut();
				setRow(territory.getRow());
				String holder = territory.getName();
				String date_in = territory.getCheckedInDate();
				String date_out = territory.getCheckedOutDate();

				// Create the text view
				TextView showterritory = (TextView) findViewById(R.id.numbertextView);
				showterritory.setText(terr);

				TextView showdescription = (TextView) findViewById(R.id.desctextView);
				showdescription.setText(description);

				TextView showtype = (TextView) findViewById(R.id.typetext);
				showtype.setText(type);

				TextView showDifficultyLevel = (TextView) findViewById(R.id.leveltext);
				showDifficultyLevel.setText(level);

				TextView showCheckedIn = (TextView) findViewById(R.id.intext);
				showCheckedIn.setText(checkedIn);

				TextView showCheckedOut = (TextView) findViewById(R.id.outtext);
				showCheckedOut.setText(checkedOut);

				TextView showName = (TextView) findViewById(R.id.HolderText);
				showName.setText(holder);

				TextView dateCheckedOut = (TextView) findViewById(R.id.date2);
				dateCheckedOut.setText(date_out);

				TextView dateCheckedIn = (TextView) findViewById(R.id.date1);
				dateCheckedIn.setText(date_in);

			}

		});

		/**
		 * Enabling Search Filter
		 * */

		inputSearch.addTextChangedListener(new TextWatcher() 
		{

			public void afterTextChanged(Editable s) 
	        { 
	            if(inputSearch.length() == 0)
	            {
	                searchButton.setEnabled(false); //disable send button if no text entered 
	            	reset();
	            }
	            else
	                searchButton.setEnabled(true);  //otherwise enable

	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after)
	        {
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count)
	        {
	        	clearSearch();
	        	//reset();
	        }
				
			
		});

		if(inputSearch.length() == 0) 
		{
			searchButton.setEnabled(false);//disable at app start
		}
		getLoaderManager().initLoader(0, null, this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		if (itemId == R.id.action_delete) {
			// final int selectedPosition = position;
			final String[] service = { "Yes", "Cancel" };
			AlertDialog.Builder adb = new AlertDialog.Builder(Overview.this);
			adb.setTitle("Do you wish to delete Territory No. " + getTerr()
					+ "?");
			adb.setItems(service, new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						prepareListView();

					case 1:
					}

				}
			});
			adb.show();

		}

		else if (itemId == R.id.action_add) {
			Intent intent = new Intent(this, AddTerritory.class);
			// intent.putExtra(Login.username, message);
			startActivityForResult(intent, NEW_TERRITORY);
		}

		else if (itemId == R.id.action_checkout) {
			Intent intent = new Intent(Overview.this, SelectTerritory.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
		    finish(); 
		}

		return true;

	}
	
	public void reset()
	{
		 TextView showterritory = (TextView) findViewById(R.id.numbertextView);
			showterritory.setText("N/A");

			TextView showdescription = (TextView) findViewById(R.id.desctextView);
			showdescription.setText("N/A");

			TextView showtype = (TextView) findViewById(R.id.typetext);
			showtype.setText("N/A");

			TextView showDifficultyLevel = (TextView) findViewById(R.id.leveltext);
			showDifficultyLevel.setText("N/A");

			TextView showCheckedIn = (TextView) findViewById(R.id.intext);
			showCheckedIn.setText("N/A");

			TextView showCheckedOut = (TextView) findViewById(R.id.outtext);
			showCheckedOut.setText("N/A");

			TextView showName = (TextView) findViewById(R.id.HolderText);
			showName.setText("N/A");

			TextView dateCheckedOut = (TextView) findViewById(R.id.date2);
			dateCheckedOut.setText("N/A");

			TextView dateCheckedIn = (TextView) findViewById(R.id.date1);
			dateCheckedIn.setText("N/A");
	}
	
	public void clearSearch()
	{
		Log.d("SearchMode: ", "false");
		searchMode=false;
		getLoaderManager().restartLoader(0, null, this);
		//reset();
		
	}

	public void search(View view) 
	{
		Log.d("SearchMode: ", "true");
		searchMode=true;
		getLoaderManager().restartLoader(0, null, this);
		 reset(); 
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		if (resultCode != RESULT_CANCELED) {

			/*
			 * if(data.getBooleanExtra(TerritoryInfo.CHECKOUT_NAME, false)) {
			 * Territories territory = new Territories();
			 * territory.setName(data.getStringExtra(Checkout.N_DATA_KEY)); }
			 */
			if (requestCode == NEW_TERRITORY) {
				Territories territory = new Territories(
						data.getStringExtra(AddTerritory.T_DATA_KEY),
						data.getStringExtra(AddTerritory.description_DATA_KEY),
						data.getStringExtra(AddTerritory.type_DATA_KEY),
						data.getStringExtra(AddTerritory.level_DATA_KEY),
						data.getStringExtra(AddTerritory.in_DATA_KEY),
						data.getStringExtra(AddTerritory.out_DATA_KEY));
			
				values.put(TerritoryContentProvider.KEY_TERRITORY,
						territory.getTerritory());
				values.put(TerritoryContentProvider.KEY_DESCRIPTION,
						territory.getDescription());
				values.put(TerritoryContentProvider.KEY_TYPE,
						territory.getType());
				values.put(TerritoryContentProvider.KEY_LEVEL,
						territory.getLevel());
				values.put(TerritoryContentProvider.KEY_IN,
						territory.getCheckedIn());
				values.put(TerritoryContentProvider.KEY_OUT,
						territory.getCheckedOut());
				values.put(TerritoryContentProvider.KEY_NAME, "N/A");
				values.put(TerritoryContentProvider.KEY_DATE_IN, "N/A");
				values.put(TerritoryContentProvider.KEY_DATE_OUT, "N/A");
				cr.insert(TerritoryContentProvider.CONTENT_URI, values);

				getLoaderManager().restartLoader(0, null, this);
			}


		}
	}


	public void prepareListView() 
	{
		ContentResolver cr = getContentResolver();
		int position = getRow();
		if (position != -2) 
		{
			Uri select = ContentUris.withAppendedId(
					TerritoryContentProvider.CONTENT_URI, position);
			cr.delete(select, null, null);
			getLoaderManager().restartLoader(0, null, this);
			reset();

		}
	}

	
	public void onBackPressed()
	{
		super.onBackPressed();
		//Intent intent = new Intent(Overview.this, MainMenuActivity.class);
	    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    //startActivity(intent);
	    finish(); 
	}

	public void onResume() {

		super.onResume();
		getLoaderManager().restartLoader(0, null, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		
		String selection = TerritoryContentProvider.KEY_TERRITORY + "=?";
		 String[] selectionArgs = new String[] { String.valueOf(inputSearch.getText().toString()) };
		
		 if(searchMode==true)
		 {
			CursorLoader loader =  new CursorLoader(Overview.this,TerritoryContentProvider.CONTENT_URI,
					null, selection,
					selectionArgs,
					TerritoryContentProvider.KEY_TERRITORY);
			return loader;
		 }
	
		 else if (searchMode==false)
		 {
			CursorLoader loader = new CursorLoader(Overview.this,
					TerritoryContentProvider.CONTENT_URI, null, null, null,
					TerritoryContentProvider.KEY_TERRITORY);
			return loader;
		 }
		 
		 else
		 {
			 return null; 
		 }
		
		
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		itemList.clear();
		int key_ID = cursor.getColumnIndexOrThrow(TerritoryContentProvider.CID);
		int key_territory = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TERRITORY);
		int key_description = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DESCRIPTION);
		int key_type = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TYPE);
		int key_level = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_LEVEL);
		int key_checked_in = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_IN);
		int key_checked_out = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_OUT);
		int key_name = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_NAME);
		int key_date_in = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_IN);
		int key_date_out = cursor
				.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_OUT);

		while (cursor.moveToNext()) {

			Territories newitem = new Territories(
					cursor.getString(key_territory),
					cursor.getString(key_description),
					cursor.getString(key_type), cursor.getString(key_level),
					cursor.getString(key_checked_in),
					cursor.getString(key_checked_out));
			newitem.setRow(cursor.getInt(key_ID));
			newitem.setName(cursor.getString(key_name));
			newitem.setCheckedInDate(cursor.getString(key_date_in));
			newitem.setCheckedOutDate(cursor.getString(key_date_out));
			itemList.add(newitem);

		}

		listAdapter.notifyDataSetChanged();

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}


}
