package helloworld;

import java.util.ArrayList;
import java.util.List;

public class PantsList implements ItemList{
	private List<Item> pants = new ArrayList<>();
	String black = "Black";
	public PantsList() {
		 pants.add(new Item(30, 75, black, "ClothesImages/Pants/blackjeans.png"));
	     pants.add(new Item(30, 75, black, "ClothesImages/Pants/blackpants.png"));
	     pants.add(new Item(30, 75, "Blue", "ClothesImages/Pants/bluejeans.png"));
	     pants.add(new Item(30, 75, "Blue", "ClothesImages/Pants/rippedjeans.png"));
	     pants.add(new Item(20, 65, "Grey", "ClothesImages/Pants/sweatpants.png"));
	     pants.add(new Item(30, 70, black, "ClothesImages/Pants/yogapants.png"));
	}
	
	@Override
	public List<Item> getList() {
		return pants;
	}
}
