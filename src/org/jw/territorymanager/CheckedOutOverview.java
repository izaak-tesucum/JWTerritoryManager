//******************************
//filename: CurrentListActivity.java
//Purpose: Displays the users current
//selection of foods while the user
//is still selecting foods.
//******************************

/*NOTE: There as an issue where this Activity is exhibiting strange
*behavior when run on a Nexus 4 that is not present when run on a
*Samsung Galaxy S4 (which is our normal testing device).
*The issue is that the calculated totals as displayed are being
*shown up to the hundredth decimal place on the Galaxy S4 (
*which is the desired behavior for this Activity on all devices), 
*whereas, when run on the Nexus 4, this only happens some of the time.
*For the most part, the Nexus 4 truncates all but the most significant digit.
*At this time, it is unknown why the app behaves this way when run on the Nexus 4,
*but not on the Galaxy S4.  Also, it should be noted that occasionally the Nexus 4
*does display the numbers correctly.*/

package org.jw.territorymanager;

import java.util.ArrayList;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.Undoable;

public class CheckedOutOverview extends Activity implements EnhancedListView.OnDismissCallback {

	private static String DEBUG_TAG = "org.jw.territorymanager.CurrentListActivity";
	private TerritoryContentProvider tc;
	
	EnhancedListView territorylistview;
	static ArrayList<Territories> TotalArrayList = new ArrayList<Territories>();
	static TerrCheckedOutAdapter TotalAdapter = null;
	static ArrayList<Territories> PostTotal = new ArrayList<Territories>();
	private SharedPerferencesExecutor<CardHolder> executor=new SharedPerferencesExecutor<CardHolder>(CheckedOutOverview.this);
	private CardHolder All2;
	private CheckedOutLoadCallBack load= new CheckedOutLoadCallBack();
	private EditText inputSearch;
	private Button searchButton;
	private boolean searchMode;
	
	
	//EnhancedListView listView;
	public  static final String territory = "This_territory";
	public static final  String description = "This_description";
	public static final  String type = "This_type";
	public static final  String dlevel = "This_level";
	public static final  String checkedIn = "This_in";
	public static final  String checkedOut = "This_out";
	public static final  String holder = "This_holder";
	public static final  String date_in = "This_date_in";
	public static final  String date_out = "This_date_out";
	int r;
	
	public void setRow(int i)
	{
		r=i;
	}
	public int getRow()
	{
		return r;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checked_out_territories);
		inputSearch= (EditText) findViewById(R.id.searchTerritory3);
		searchButton= (Button) findViewById(R.id.searchButton3);
		
		 All2 = executor.retreive("territories", CardHolder.class);
		 
		
				territorylistview=(EnhancedListView) findViewById(R.id.current_list);
				TotalArrayList=new ArrayList<Territories>();
				TotalAdapter = new TerrCheckedOutAdapter(CheckedOutOverview.this, R.layout.checked_out_item, TotalArrayList);
				territorylistview.setAdapter(TotalAdapter);
		
				
				//Initialize the current list status TextView objects:
				territorylistview = (EnhancedListView)findViewById(R.id.current_list);
				territorylistview.setUndoStyle(EnhancedListView.UndoStyle.MULTILEVEL_POPUP);
				territorylistview.setDismissCallback(this);
				territorylistview.enableSwipeToDismiss();
				
				//Set the adapter to the EnhancedListView.  Also, initialize the EnhancedListView:
				
				territorylistview.setOnItemClickListener(new OnItemClickListener()
			    {
		
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
					{
						Territories territory =(Territories)parent.getItemAtPosition(position);
						Intent intent = new Intent(CheckedOutOverview.this, TerritoryInfo.class);
						intent.putExtra(CheckedOutOverview.territory, territory.getTerritory());
						intent.putExtra(CheckedOutOverview.description,territory.getDescription());
						intent.putExtra(CheckedOutOverview.type, territory.getType());
						intent.putExtra(CheckedOutOverview.dlevel, territory.getLevel());
						intent.putExtra(CheckedOutOverview.checkedIn, territory.getCheckedIn());
						intent.putExtra(CheckedOutOverview.checkedOut, territory.getCheckedOut());
						intent.putExtra(CheckedOutOverview.holder, territory.getName());
						intent.putExtra(CheckedOutOverview.date_in, territory.getCheckedInDate());
						intent.putExtra(CheckedOutOverview.date_out, territory.getCheckedOutDate());
						
						startActivity(intent);
						
					}
			    	
			    }
			    );
				
				inputSearch.addTextChangedListener(new TextWatcher(){

					public void afterTextChanged(Editable s) 
			        { 
			            if(inputSearch.length() == 0)
			            {
			                searchButton.setEnabled(false); //disable send button if no text entered 
			            	//reset();
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
						// TODO Auto-generated method stub
						
					});
				
				if(inputSearch.length() == 0) 
				{
					searchButton.setEnabled(false);//disable at app start
				}
				
		 
		load.Initialize();

	}
	
	public void clearSearch()
	{
		Log.d("SearchMode: ", "false");
		searchMode=false;
		getSearchArray();
		
	}

	public void search(View view) 
	{
		Log.d("SearchMode: ", "true");
		searchMode=true;
		getSearchArray();
		
	}
	
	public void getSearchArray() //search and display for territory number or text entered
	{
		int i;
		if(searchMode==true)
		{
			Log.d("Search1", inputSearch.getText().toString());
			ArrayList<Territories> newList= new ArrayList<Territories>();
			for(i=0;i<TotalArrayList.size();i++)
			{
				newList.add(TotalArrayList.get(i));
			}
			int d=newList.size();
			System.out.println(d);
			Log.d("Search2", inputSearch.getText().toString());
			//System.out.println(itemList.size());
			TotalArrayList.clear();
			Log.d("Search3", inputSearch.getText().toString());
			for(i=0;i<newList.size();i++)
			{
				Log.d("Searching", inputSearch.getText().toString());
				if(newList.get(i).getTerritory().equalsIgnoreCase(inputSearch.getText().toString()))
				{
					TotalArrayList.add(newList.get(i));
				}
			}
			
			TotalAdapter.notifyDataSetChanged();
		}
		
		else if(searchMode==false)
		{
			//itemList.clear();
			load.RestartLoader();
		}
		
	}
	
	public static void AddtoTotalArrayList(Territories itemName)
	{
		TotalArrayList.add(0, itemName);
		TotalAdapter.notifyDataSetChanged();
	}
	
	
	@Override
	protected void onRestart(){
		super.onRestart();
		//Attach the dismiss listener just before the view is drawn.
		territorylistview.setDismissCallback(this);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		//Remove the listener when the view is no longer visible:
		territorylistview.discardUndo();
		territorylistview.setDismissCallback(null);
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
		//Send the edited ArrayList back to the calling Activity:
		/*Intent passBack = new Intent();
		passBack.putParcelableArrayListExtra("shopping_list", currentList);
		setResult(RESULT_OK, passBack);*/
		finish();
	}
	
	
	 
	
	public void updateOnDismiss(int id, String date_in)
	{
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		 values.put(TerritoryContentProvider.KEY_IN,String.valueOf("yes"));
			values.put(TerritoryContentProvider.KEY_OUT,String.valueOf("no"));
			values.put(TerritoryContentProvider.KEY_NAME, String.valueOf("N/A"));
			values.put(TerritoryContentProvider.KEY_DATE_IN, date_in);
			cr.update(TerritoryContentProvider.CONTENT_URI, values, TerritoryContentProvider.CID+"=?", new String[] {String.valueOf(id)});
	}
	
	public void updateOnUndo(int id,String name)
	{
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		 values.put(TerritoryContentProvider.KEY_IN,String.valueOf("no"));
			values.put(TerritoryContentProvider.KEY_OUT,String.valueOf("yes"));
			values.put(TerritoryContentProvider.KEY_NAME, name);
			values.put(TerritoryContentProvider.KEY_DATE_IN, String.valueOf("N/A"));
			cr.update(TerritoryContentProvider.CONTENT_URI, values, TerritoryContentProvider.CID+"=?", new String[] {String.valueOf(id)});
	}
	
	@Override
	public Undoable onDismiss(EnhancedListView listView, int position) {
		final Territories dismissedItem = TotalAdapter.getItem(position);
		TotalArrayList.remove(position);
		executor.save("territories", All2);
		dismissedItem.setCheckedInDate(dismissedItem.Date_Checked_In());
		updateOnDismiss(dismissedItem.getRow(),dismissedItem.getCheckedInDate());
		//updateCurrentStatusViews();
		Log.d(DEBUG_TAG, "Item dismissed:");
		//checkInternalTotalValues();
		
		TotalAdapter.notifyDataSetChanged();
		
		return new Undoable(){
			@Override
			public void undo() {
				TotalArrayList.add(dismissedItem);
				executor.save("territories", All2);
				updateOnUndo(dismissedItem.getRow(),dismissedItem.getName());
				//updateCurrentStatusViews();
				Log.d(DEBUG_TAG, "Item re-added:");
				//checkInternalTotalValues();
				TotalAdapter.notifyDataSetChanged();
			}
		};
	}
	
	private class CheckedOutLoadCallBack implements LoaderManager.LoaderCallbacks<Cursor>
	{
		public CheckedOutLoadCallBack()
		{
			getLoaderManager();
		}
		
		public void Initialize()
		{
			getLoaderManager().initLoader(0, null, this);
		}
		
		public void RestartLoader()
		{
			getLoaderManager().restartLoader(0, null, this);
		}

		@Override
		public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			//TextView userPassedBy = (TextView) findViewById(R.id.number);
			@SuppressWarnings("static-access")
			String selection = tc.KEY_OUT + "=?";
			String[] selectionArgs = {String.valueOf("yes")};
			 //String.valueOf(userPassedBy.getText().toString())
			
			/*CursorLoader loader = new CursorLoader(Overview.this,TerritoryContentProvider.CONTENT_URI, null, TerritoryContentProvider.KEY_TERRITORY + " = " + 
				    args.getInt(TerritoryContentProvider.KEY_TERRITORY), null, null);*/
			CursorLoader loader = new CursorLoader(CheckedOutOverview.this,TerritoryContentProvider.CONTENT_URI, null,selection,selectionArgs,TerritoryContentProvider.KEY_TERRITORY);
			return loader;
			//return null;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			TotalArrayList.clear();
			int key_ID = cursor.getColumnIndexOrThrow(TerritoryContentProvider.CID);
			int key_territory= cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TERRITORY);
			int key_description= cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DESCRIPTION);
			int key_type=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TYPE);
			int key_level=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_LEVEL);
			int key_checked_in=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_IN);
			int key_checked_out=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_OUT);
			int key_name=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_NAME);
			int key_indate=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_IN);
			int key_outdate=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_OUT);
			
			while (cursor.moveToNext()) 
			{
				
				Territories newitem = new Territories(cursor.getString(key_territory), cursor.getString(key_description), cursor.getString(key_type), cursor.getString(key_level), cursor.getString(key_checked_in), cursor.getString(key_checked_out));
				newitem.setRow(cursor.getInt(key_ID));
				newitem.setName(cursor.getString(key_name));
				newitem.setCheckedInDate(cursor.getString(key_indate));
				newitem.setCheckedOutDate(cursor.getString(key_outdate));
				//newitem.setTerritoryInfo(cursor.getString(key_territory), cursor.getString(key_description), cursor.getString(key_type), cursor.getString(key_level), cursor.getString(key_checked_in), cursor.getString(key_checked_out));
				TotalArrayList.add(newitem); 
				
			}
			
			TotalAdapter.notifyDataSetChanged();
		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}