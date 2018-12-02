package helloworld;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int minTemp;
	public int maxTemp;
	public String color;
	public String location;
	public Item(int itemMinTemp, int itemMaxTemp, String itemColor, String itemLocation) {
		minTemp = itemMinTemp;
		maxTemp = itemMaxTemp;
		color = itemColor;
		location = itemLocation;
	}
}
