package org.jw.territorymanager;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectAdapter extends ArrayAdapter<Territories> {
	
	String s;
	int resource;
	
	public SelectAdapter(Context context, int resource, List<Territories> itemList) 
	{
		super(context, resource, itemList);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout todoView;
		Territories item = getItem(position);
		
		String tString = item.getTerritory();
		String name = item.getName();
		String date_in= item.getCheckedInDate();
		
		if (convertView == null) {
			todoView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater lifr = (LayoutInflater)getContext().getSystemService(inflater);
			lifr.inflate(resource, todoView, true);
		}
		else {
			todoView = (LinearLayout)convertView;
		}
		
		TextView territoryView = (TextView)todoView.findViewById(R.id.t);
		TextView nameView = (TextView)todoView.findViewById(R.id.h);
		TextView DateInView = (TextView)todoView.findViewById(R.id.date_in);
		
		territoryView.setText("Territory No. "+tString);
		nameView.setText("CardHolder: "+name);
		DateInView.setText("Checked In: "+date_in);
		
		return todoView;
	}
	
}
