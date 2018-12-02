package helloworld;

import java.util.ArrayList;
import java.util.List;

public class ShortsList implements ItemList{
	private List<Item> shorts  = new ArrayList<>();

	public ShortsList() {
		 shorts.add(new Item(70, 110, "Black", "ClothesImages/Shorts/blackshorts.png"));
	     shorts.add(new Item(70, 110, "Black", "ClothesImages/Shorts/floralshorts.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/Shorts/jeanshortsone.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/Shorts/jeanshortsthree.png"));
	     shorts.add(new Item(70, 110, "Blue", "ClothesImages/Shorts/jeanshortstwo.png"));
	     shorts.add(new Item(70, 110, "Grey", "ClothesImages/Shorts/sweatshorts.png"));
	}
	
	@Override
	public List<Item> getList() {
		return shorts;
	}
}
