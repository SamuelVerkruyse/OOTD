package helloworld;

import java.util.ArrayList;
import java.util.List;

public class ShirtList implements ItemList{
	private List<Item> shirts;

	public ShirtList() {
		 shirts = new ArrayList<Item>();
		 shirts.add(new Item(50, 80, "Black", "ClothesImages/Shirts/blackshirt.png"));
	     shirts.add(new Item(70, 100, "Red", "ClothesImages/Shirts/croppedshirt.png"));
	     shirts.add(new Item(50, 80, "Grey", "ClothesImages/Shirts/greyshirt.png"));
	     shirts.add(new Item(0, 50, "Pink", "ClothesImages/Shirts/pinksweater.png"));
	     shirts.add(new Item(50, 80, "White", "ClothesImages/Shirts/stripedshirt.png"));
	     shirts.add(new Item(70, 100, "Grey", "ClothesImages/Shirts/stripedtee.png"));
	}
	
	@Override
	public List<Item> getList() {
		return shirts;
	}
}
