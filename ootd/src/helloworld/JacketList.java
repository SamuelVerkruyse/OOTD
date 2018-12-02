package helloworld;

import java.util.ArrayList;
import java.util.List;

public class JacketList implements ItemList{
	private List<Item> jackets = new ArrayList<>();
	String black = "Black";
	public JacketList() {
		 jackets.add(new Item(0, 60, black, "ClothesImages/Jackets/blackcoat.png"));
	     jackets.add(new Item(40, 65, black, "ClothesImages/Jackets/blackjacket.png"));
	     jackets.add(new Item(40, 65, "Blue", "ClothesImages/Jackets/jeanjacket.png"));
	     jackets.add(new Item(35, 65, black, "ClothesImages/Jackets/leatherjacket.png"));
	     jackets.add(new Item(0, 60, "Pink", "ClothesImages/Jackets/pinkcoat.png"));
	     jackets.add(new Item(20, 50, "Grey", "ClothesImages/Jackets/sweaterjacket.png"));
	}
	
	@Override
	public List<Item> getList() {
		return jackets;
	}
}
