package helloworld;

import java.util.ArrayList;
import java.util.List;

public class ShortsList implements ItemList{
	private List<Item> shorts;

	public ShortsList() {
		 shorts = new ArrayList<Item>();
		 shorts.add(new Item(70, 110, "Black", "ClothesImages/shorts/blackshorts.png"));
	     shorts.add(new Item(70, 110, "Black", "ClothesImages/shorts/floralshorts.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/shorts/jeanshortsone.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/shorts/jeanshortsthree.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/shorts/jeanshortstwo.png"));
	     shorts.add(new Item(70, 110, "Grey", "ClothesImages/shorts/sweatshorts.png"));
	}
	
	@Override
	public List<Item> getList() {
		return shorts;
	}
}
