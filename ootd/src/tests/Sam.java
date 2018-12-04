package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import helloworld.Item;
import helloworld.ItemListFactory;
import helloworld.ShowOutfits;

public class Sam extends Mockito{
	Random rand = new Random();

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

    @Test
    public void showoutfitswithjacket() throws ServletException, IOException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("jackets");
        String jacketlocation = String.valueOf(clothing.get(rand.nextInt(clothing.size())).location);
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        JacketConditions configureJacket = new JacketConditions();
        configureJacket.configureParameters(request,response, writer, jacketlocation, "50", "windy");
        new ShowOutfits().doPost(request, response);
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains(jacketlocation));
    }
    
    @Test
    public void showoutfitswithjacketbytemperature() throws ServletException, IOException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("jackets");
        String jacketlocation = String.valueOf(clothing.get(rand.nextInt(clothing.size())).location);
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        JacketConditions configureJacket = new JacketConditions();
        configureJacket.configureParameters(request,response, writer, jacketlocation, "50", "");
        new ShowOutfits().doPost(request, response);
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains("src=\"ClothesImages/Jackets/"));
    }
}
