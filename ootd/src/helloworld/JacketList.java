package helloworld;

import java.util.ArrayList;
import java.util.List;

public class JacketList implements ItemList{
	private List<Item> jackets;

	public JacketList() {
		 jackets = new ArrayList<Item>();
		 jackets.add(new Item(0, 60, "Black", "ClothesImages/jackets/blackcoat.png"));
	     jackets.add(new Item(40, 65, "Black", "ClothesImages/jackets/blackjacket.png"));
	     jackets.add(new Item(40, 65, "Blue", "ClothesImages/jackets/jeanjacket.png"));
	     jackets.add(new Item(35, 65, "Black", "ClothesImages/jackets/leatherjacket.png"));
	     jackets.add(new Item(0, 60, "Pink", "ClothesImages/jackets/pinkcoat.png"));
	     jackets.add(new Item(20, 50, "Grey", "ClothesImages/jackets/sweaterjacket.png"));
	}
	
	@Override
	public List<Item> getList() {
		return jackets;
	}
}
