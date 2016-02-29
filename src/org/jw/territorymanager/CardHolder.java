package org.jw.territorymanager;
import java.util.ArrayList;


public class CardHolder {
	
	 //String checked_in_date;
	 //String checked_out_date;
	 //String name;
	 TerritoryCart territoryCart;

	public CardHolder() {
		
		//setName(_name);
		//setCheckedInDate(_dateCheckedIn);
		//setCheckedOutDate(_dateCheckedOut);
		//nonValidFields = new ArrayList<String>();
		territoryCart = new TerritoryCart();
		// TODO Auto-generated constructor stub
	}
	
	
	public TerritoryCart getTerritoryCart() {
		return territoryCart;
	}

	public void setTerritoryCart(TerritoryCart _list) {
		this.territoryCart = _list;
	}
	
	public static class TerritoryCart {

		private ArrayList<Territories> TerritoryCart = new ArrayList<Territories>();
		
		public TerritoryCart()
		{
			
		}
		
		public void addItemToCart(Territories item) {
			TerritoryCart.add(item);
		}		
		
		public void clearTerritoryCart(){
			TerritoryCart.clear();
		}

		public ArrayList<Territories> getTerritoriesinCart(){
			return TerritoryCart;
		}
		
		public void setTerritoriesInCart(ArrayList<Territories> _newList){
			TerritoryCart = _newList;
		}
		
	}

}
