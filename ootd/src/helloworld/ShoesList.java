package helloworld;

import java.util.ArrayList;
import java.util.List;

public class ShoesList implements ItemList{
	private List<Item> shoes  = new ArrayList<>();
	String black = "Black";
	public ShoesList() {
		 shoes.add(new Item(60, 80, black, "ClothesImages/Shoes/heelshoe.png"));
	     shoes.add(new Item(60, 80, "Red", "ClothesImages/Shoes/redheel.png"));
	     shoes.add(new Item(70, 100, black, "ClothesImages/Shoes/slides.png"));
	     shoes.add(new Item(50, 80, black, "ClothesImages/Shoes/sneakers.png"));
	     shoes.add(new Item(0, 55, black, "ClothesImages/Shoes/tallboots.png"));
	     shoes.add(new Item(20, 60, "Tan", "ClothesImages/Shoes/warmheels.png"));
	}
	
	@Override
	public List<Item> getList() {
		return shoes;
	}
}
