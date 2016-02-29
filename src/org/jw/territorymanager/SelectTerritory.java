package org.jw.territorymanager;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/*This class handles the updating of data in sql when a user changes the status of the data.*/
public class SelectTerritory extends Activity implements LoaderManager.LoaderCallbacks<Cursor>
{
    public static final String Position = "territory_position";
    final public static String DELETE = "DELETE_TERRITORY";
    private ArrayList<Territories> itemList=new ArrayList<Territories>();;
    private SelectAdapter listAdapter;
    private Territories Territory;
    public static String TERRITORY_LIST_KEY = "territory_list";
    private final int CURRENT_LIST_ACT = 0;
    final Context context = this;
    public  static final String territory = "This_territory";
    public static final  String description = "This_description";
    public static final  String type = "This_type";
    public static final  String dlevel = "This_level";
    public static final  String checkedIn = "This_in";
    public static final  String checkedOut = "This_out";
    public static final  String holder = "This_holder";
    public static final  String date_in = "This_date_in";
    public static final  String date_out = "This_date_out";
    public TerritoryContentProvider tc;
    private ArrayList<Territories> CheckedOutTerritories;
    private CardHolder currentCart;
    private SharedPerferencesExecutor<CardHolder> executor= new SharedPerferencesExecutor<CardHolder>(SelectTerritory.this);
    private EditText inputSearch;
    private Button searchButton;
    private boolean searchMode;
    
    public void setTerritory(Territories t)
    {
        Territory=t;
    }
    
    public Territories getTerritory()
    {
        return Territory;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_select_territory);
        
        inputSearch= (EditText) findViewById(R.id.searchTerritory2);
        
        searchButton= (Button) findViewById(R.id.searchButton2);
        
        //Create a new Territory Cart:
        currentCart = new CardHolder();
        CheckedOutTerritories = currentCart.territoryCart.getTerritoriesinCart();
        
        ListView listview = (ListView) findViewById(R.id.select_territory);
        
        //Create the adapter and assign it to the list view:
        listAdapter = new SelectAdapter(SelectTerritory.this, R.layout.select_territorylist, itemList);
        listview.setAdapter(listAdapter);
        listview.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id)
            {
                Territories t=(Territories)parent.getItemAtPosition(position);
                setTerritory(t);
                checkout();
            }
        });
        
        checkInputSearch(inputSearch);
        
        if(iSearch.length() == 0)
        {
            //disable at app start
            searchButton.setEnabled(false);
        }
        
        getLoaderManager().initLoader(0, null, this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_territory, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        
        if(itemId== R.id.action_done)
        {
            currentCart.territoryCart.setTerritoriesInCart(CheckedOutTerritories);
            Intent intent = new Intent(SelectTerritory.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // Call once you redirect to another activity
        }
        
        return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode== RESULT_OK)
        {
            finish();
        }
    }
    
    public void checkInputSearch(EditText iSearch)
    {
        iSearch.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s)
            {
                //disable send button if no text entered
                if(iSearch.length() == 0)
                {
                    searchButton.setEnabled(false);
                }
                else //otherwise enable
                {
                    searchButton.setEnabled(true);
                }
            }
            
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                //this is a built-in function, and needs to be declared to avoid compilation error.
            }
            
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                clearSearch();
            }
        });
    }
    
    public void checkout()
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        
        View promptsView = li.inflate(R.layout.nameprompt, null);
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        
        // set dialog message
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int id)
                {
                    // get user input and set it to result
                    Territories t=getTerritory();
                    t.setName(userInput.getText().toString());
                    t.setCheckIn("no");
                    t.setCheckOut("yes");
                    t.setCheckedOutDate(t.Date_Checked_Out());
                    t.setCheckedInDate("N/A");
                    CheckedOutTerritories.add(t);
                    toast(t.getTerritory());
                    executor.save("territories", currentCart);
                    update(t.getRow(), t.getCheckedIn(), t.getCheckedOut(),t.getName(),t.getCheckedInDate(),t.getCheckedOutDate());
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int id)
                {
                    dialog.cancel();
                }
            });
        
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    
    public void getSearchArray(boolean searchMode)
    {
        int i;
        
        // retrieve and display the appropriate data entered in search text
        if(searchMode)
        {
            Log.d("Search1", inputSearch.getText().toString());
            ArrayList<Territories> newList= new ArrayList<Territories>();
            
            for(i=0;i<itemList.size();i++)
            {
                newList.add(itemList.get(i));
            }
            
            displaySearchArray(newList);
        }
        else //else reload and display all data
        {
            getLoaderManager().restartLoader(0, null, this);
        }
    }
    
    public void displaySearchArray(ArrayList<Territories> searchList)
    {
        itemList.clear();
        
        for(i=0;i<searchList.size();i++)
        {
            Log.d("Searching", inputSearch.getText().toString());
            if(searchList.get(i).getTerritory().equalsIgnoreCase(inputSearch.getText().toString()))
            {
                itemList.add(searchList.get(i));
            }
        }
        
        listAdapter.notifyDataSetChanged();
    }
    
    public void clearSearch()
    {
        Log.d("SearchMode: ", "false");
        getSearchArray(false);
    }
    
    public void search(View view)
    {
        Log.d("SearchMode: ", "true");
        getSearchArray(true);
    }
    
    public void update(int id,String checkIn,String checkOut, String name, String date_in, String date_out)
    {
        //take content the user entered and store in db
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(TerritoryContentProvider.KEY_IN,checkIn);
        values.put(TerritoryContentProvider.KEY_OUT,checkOut);
        values.put(TerritoryContentProvider.KEY_NAME, name);
        values.put(TerritoryContentProvider.KEY_DATE_IN, date_in);
        values.put(TerritoryContentProvider.KEY_DATE_OUT, date_out);
        cr.update(TerritoryContentProvider.CONTENT_URI, values, TerritoryContentProvider.CID+"=?", new String[] {String.valueOf(id)});
    }
    
    public void toast(String t)
    {
        Toast.makeText(this, "Territory No. "+t+ " checked out", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
    
    public void onResume()
    {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        @SuppressWarnings("static-access")
        String selection = tc.KEY_OUT + "=?";
        String[] selectionArgs = {String.valueOf("no")};
        CursorLoader loader = new CursorLoader(SelectTerritory.this,TerritoryContentProvider.CONTENT_URI, null,selection,selectionArgs,TerritoryContentProvider.KEY_TERRITORY);
        
        return loader;
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor)
    {
        //retrieve all data from DB for display
        int key_ID = cursor.getColumnIndexOrThrow(TerritoryContentProvider.CID);
        int key_territory= cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TERRITORY);
        int key_description= cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DESCRIPTION);
        int key_type=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_TYPE);
        int key_level=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_LEVEL);
        int key_checked_in=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_IN);
        int key_checked_out=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_OUT);
        int key_name=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_NAME);
        int key_date_in=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_IN);
        int key_date_out=cursor.getColumnIndexOrThrow(TerritoryContentProvider.KEY_DATE_OUT);
        
        while (cursor.moveToNext()) 
        {
            Territories newitem = new Territories(cursor.getString(key_territory), cursor.getString(key_description), cursor.getString(key_type), cursor.getString(key_level), cursor.getString(key_checked_in), cursor.getString(key_checked_out));
            
            newitem.setRow(cursor.getInt(key_ID));
            newitem.setName(cursor.getString(key_name));
            newitem.setCheckedInDate(cursor.getString(key_date_in));
            newitem.setCheckedOutDate(cursor.getString(key_date_out));
            itemList.add(newitem);
        }
        
        listAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> arg0)
    {
        //this is a built-in function, and needs to be declared to avoid compilation error.
        
    }
}
