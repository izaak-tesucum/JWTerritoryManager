package org.jw.territorymanager;
//***************************
//filename: FoodItemListAdapter.java
//Purpose: A simple ArrayAdapter extension
//that maps data stored in a FoodItem object
//to TextViews defined in R.layout.food_item_layout.
//***************************


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TerritoryAdapter extends ArrayAdapter<Territories> {

	public TerritoryAdapter(Context context,
			 List<Territories> objects) {
		super(context, R.layout.territorylist, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		RelativeLayout itemView;
		Territories item = getItem(position);
		
		if (convertView == null) {
			itemView = new RelativeLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater lifr = (LayoutInflater)getContext().getSystemService(inflater);
			lifr.inflate(R.layout.territorylist, itemView, true);
		}
		else {
			itemView = (RelativeLayout)convertView;
		}
		((TextView)itemView.findViewById(R.id.number)).setText("Territory No. "+item.getTerritory());
		((TextView)itemView.findViewById(R.id.name)).setText("Card Holder: "+item.getName());
		return itemView;
	}

}
