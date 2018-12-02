package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import helloworld.Item;

public class Sam {

	@Test
	public void valuetest() {
		Item itemTest = new Item(70, 100, "Grey", "ClothesImages/Shirts/stripedtee.png");
		assertTrue(itemTest.color.equals("Grey"));		
		assertTrue(itemTest.location.equals("ClothesImages/Shirts/stripedtee.png"));
		assertTrue(itemTest.minTemp == 70);
		assertTrue(itemTest.maxTemp == 100);
	}

	@Test
	public void nulltest() {
		Item itemTest = new Item(0, 0, null, null);
		assertTrue(itemTest.color == null);		
		assertTrue(itemTest.location == null);
		assertTrue(itemTest.minTemp == 0);
		assertTrue(itemTest.maxTemp == 0);
	}

}
