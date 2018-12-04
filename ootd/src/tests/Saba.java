package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
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
import helloworld.SavedOutfitView;
import helloworld.SavedOutfits;
import helloworld.ShowOutfits;
import helloworld.WeatherView;

public class Saba extends Mockito {
	Random rand = new Random();
	private static final Logger LOGGER = Logger.getLogger( WeatherView.class.getName() );

	@Test
	public void shortstest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shorts");
    	assertTrue(!clothing.isEmpty());
	}

	@Test
	public void pantstest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("pants");
    	assertTrue(!clothing.isEmpty());
	}
	
    @Test
    public void showoutfitswithshortsbytemp() throws ServletException, IOException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shorts");
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn("");
        when(request.getParameter("shoeInput")).thenReturn("");
        when(request.getParameter("shortInput")).thenReturn("");
        when(request.getParameter("pantInput")).thenReturn("");
        when(request.getParameter("jacketInput")).thenReturn("");
        when(request.getParameter("tempInput")).thenReturn(String.valueOf(clothing.get(rand.nextInt(clothing.size())).maxTemp));
        when(request.getParameter("weatherInput")).thenReturn("");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains("src=\"ClothesImages/Shorts/"));
    }
    
    @Test
    public void savedoutfitview() throws ServletException, IOException {
    	SavedOutfits outfitMap = new SavedOutfits();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        try {
        	new SavedOutfitView().doPost(request, response);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
        for ( String key : outfitMap.getList().keySet()) {
        	if(key != null) {
        		assertTrue(stringWriter.toString().contains(key));
        	}
        }

    }
}
