package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import helloworld.Item;
import helloworld.ItemListFactory;
import helloworld.SavedOutfits;
import helloworld.ShirtList;
import helloworld.ShoesList;
import helloworld.ShowOutfits;
import helloworld.WeatherView;

public class Dave extends Mockito {
	private static final Logger LOGGER = Logger.getLogger( WeatherView.class.getName() );

	Random rand = new Random();

	@Test
	public void addoutfit() {
    	SavedOutfits outfits = new SavedOutfits();
    	HashMap<String, Item> testMap = new HashMap<>();
	    Item shirt = new Item(70, 100, "Grey", "ClothesImages/Shirts/stripedtee.png");
	    testMap.put("Test Outfit", shirt);
    	outfits.addOutfit(testMap, "Test Outfit");
    	Map<String, ArrayList<Item>> modifiedOutfitsList = outfits.getList();
    	assertTrue(modifiedOutfitsList.size() > 0);
	}
	
	@Test
	public void nonexistentitemtest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList(null);
    	assertTrue(clothing.isEmpty());
	}

    @Test
    public void showoutfitswithsavedshirt() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ShirtList savedshirt = new ShirtList();
        List<Item> shirts = savedshirt.getList();
        String shirtlocation = String.valueOf(shirts.get(rand.nextInt(shirts.size())).location);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn(shirtlocation);
        when(request.getParameter("shoeInput")).thenReturn("");
        when(request.getParameter("shortInput")).thenReturn("");
        when(request.getParameter("pantInput")).thenReturn("");
        when(request.getParameter("jacketInput")).thenReturn("");
        when(request.getParameter("tempInput")).thenReturn("80");
        when(request.getParameter("weatherInput")).thenReturn("");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(shirtlocation));
    }
    
    @Test
    public void showoutfitswithsavedshoe() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        ShoesList savedshoe = new ShoesList();
        List<Item> shoes = savedshoe.getList();
        String shoelocation = String.valueOf(shoes.get(rand.nextInt(shoes.size())).location);
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn("");
        when(request.getParameter("shoeInput")).thenReturn(shoelocation);
        when(request.getParameter("shortInput")).thenReturn("");
        when(request.getParameter("pantInput")).thenReturn("");
        when(request.getParameter("jacketInput")).thenReturn("");
        when(request.getParameter("tempInput")).thenReturn("80");
        when(request.getParameter("weatherInput")).thenReturn("");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(shoelocation));

    }
    
}
