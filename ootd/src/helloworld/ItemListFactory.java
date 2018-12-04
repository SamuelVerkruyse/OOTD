package helloworld;

import java.util.List;

public class ItemListFactory {
	public List<Item> getItemList(String itemName){
		ItemList items = null;
		if(itemName == null) {
			items = new NullList();
			 return items.getList();
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
			items = new PantsList();
			return items.getList();
		}
		if(itemName.equalsIgnoreCase("shoes")) {
			items = new ShoesList();
			return items.getList();
		}
		items = new NullList();
		return items.getList();
	}
}
