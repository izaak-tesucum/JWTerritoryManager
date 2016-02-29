package org.jw.territorymanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Territories implements Parcelable {
	
	/*Needed by the Parcel message passing framework:*/
	public static Parcelable.Creator<Territories> CREATOR = new Parcelable.Creator<Territories>(){

		@Override
		public Territories createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Territories(source);
		}

		@Override
		public Territories[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Territories[size];
		}
	};

	 String territory;
	 String type;
	 String level;
	 String checked_in;
	 String checked_out;
	 int row;
	 String description;
	 String checked_out_date;
	String checked_in_date;
	String name;
	 
	 
	 public Territories(String _territory,String _description,String _type,String _level,String _checkedIn,String _checkedOut) 
	 {
			//setTerritoryInfo(_territory,_description,_type,_level,_checkedIn,_checkedOut);
		 setTerritory(_territory);
		 setDescription(_description);
		 setType(_type);
		 setLevel(_level);
		 setCheckIn(_checkedIn);
		 setCheckOut(_checkedOut);
	}
	 
	 public Territories (Parcel src){
			territory = src.readString();
			row = src.readInt();
			type=src.readString();
			 level=src.readString();
			 checked_in=src.readString();
			 checked_out=src.readString();
			  description=src.readString();
			  name=src.readString();
			  checked_in_date=src.readString();
			  checked_out_date=src.readString();
		}
	 
	 public String Date_Checked_In()
		{
			Date d;
			d = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
			checked_in_date = sdf.format(d).toString();
			return checked_in_date;
		}
		
		public String Date_Checked_Out()
		{
			Date d;
			d = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
			checked_out_date = sdf.format(d).toString();
			return checked_out_date;
		}
		
		public void setName(String _name)
		{
			name=_name;
		}
		
		public void setCheckedInDate(String _dateIn)
		{
			checked_in_date= _dateIn;
		}
		
		public void setCheckedOutDate(String _dateOut)
		{
			checked_out_date= _dateOut;
		}
		
		public String getCheckedInDate(){
			return checked_in_date;
		}
		
		public String getName()
		{
			return name;
		}
		
		public String getCheckedOutDate()
		{
			return checked_out_date;
		}
	 
	public int getRow()
	{
		return row;
	}
	
	public String getTerritory() {
		return territory;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getLevel()
	{
		return level;
	}
	
	public String getCheckedIn()
	{
		return checked_in;
	}
	
	public String getCheckedOut()
	{
		return checked_out;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setRow(int _row)
	{
		row=_row;
	}
	
	public void setTerritory(String _terr)
	{
		territory=_terr;
	}
	
	public void setDescription(String _desc)
	{
		description=_desc;
	}
	
	public void setType(String _type)
	{
		type=_type;
	}
	
	public void setLevel(String _level)
	{
		level=_level;
	}
	
	public void setCheckIn(String ci)
	{
		checked_in=ci;
	}
	
	public void setCheckOut(String co)
	{
		checked_out=co;
	}
	
	public void setTerritoryInfo(String _territory, String _description,  String _type, String _level, String _checkedIn, String _checkedOut) {
		territory = _territory;
		description= _description;
		type = _type;
		level = _level;
		checked_in= _checkedIn;
		checked_out= _checkedOut;
		//name= _name;
		//checked_out_date = _dateCO;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(row);
		dest.writeString(territory);
		dest.writeString(description);
		dest.writeString(type);
		dest.writeString(checked_in);
		dest.writeString(checked_out);
		dest.writeString(name);
		dest.writeString(checked_in_date);
		dest.writeString(checked_out_date);
	}
	
	
	/*@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, HH:mm:ss");
		String dateString = sdf.format(createdDate);
		return dateString;
	}*/
}
