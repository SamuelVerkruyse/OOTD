package helloworld;

import java.util.Collections;
import java.util.List;

public class NullList implements ItemList{

	@Override
	public List<Item> getList() {
		return Collections.emptyList();
	}
}
