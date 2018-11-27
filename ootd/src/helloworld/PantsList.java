package helloworld;

import java.util.ArrayList;
import java.util.List;

public class PantsList implements ItemList{
	private List<Item> pants;

	public PantsList() {
		 pants = new ArrayList<Item>();
		 pants.add(new Item(30, 75, "Black", "ClothesImages/pants/blackjeans.png"));
	     pants.add(new Item(30, 75, "Black", "ClothesImages/pants/blackpants.png"));
	     pants.add(new Item(30, 75, "Blue", "ClothesImages/pants/bluejeans.png"));
	     pants.add(new Item(30, 75, "Blue", "ClothesImages/pants/rippedjeans.png"));
	     pants.add(new Item(20, 65, "Grey", "ClothesImages/pants/sweatpants.png"));
	     pants.add(new Item(30, 70, "Black", "ClothesImages/pants/yogapants.png"));
	}
	
	@Override
	public List<Item> getList() {
		return pants;
	}
}
