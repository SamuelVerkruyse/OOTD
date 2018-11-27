package helloworld;

import java.util.List;

public class ItemListFactory {
	public List<Item> getItemList(String itemName){
		ItemList items = null;
		if(itemName == null) {
			return null;
		}
		if(itemName.equalsIgnoreCase("shirts")) {
			items = new ShirtList();
			return items.getList();
		}
		if(itemName.equalsIgnoreCase("shorts")) {
			items = new ShortsList();
			return items.getList();
		}
		if(itemName.equalsIgnoreCase("jackets")) {
			items = new JacketList();
			return items.getList();
		}
		if(itemName.equalsIgnoreCase("pants")) {
			items = new ShortsList();
			return items.getList();
		}
		if(itemName.equalsIgnoreCase("shoes")) {
			items = new ShoesList();
			return items.getList();
		}
		return null;
	}
}
