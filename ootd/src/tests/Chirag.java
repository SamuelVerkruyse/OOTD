package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
import helloworld.ShowOutfits;
import helloworld.WeatherView;

public class Chirag extends Mockito {
	private static final Logger LOGGER = Logger.getLogger( WeatherView.class.getName() );
	Random rand = new Random();

	@Test
	public void saveoutfitstest() {
    	SavedOutfits outfits = new SavedOutfits();
    	Map<String, ArrayList<Item>> outfitsList = outfits.getList();
    	assertTrue(outfitsList.size() != 0);
	}
	
	@Test
	public void addnulloutfit() {
    	SavedOutfits outfits = new SavedOutfits();
    	Map<String, ArrayList<Item>> outfitsList = outfits.getList();
    	outfits.addOutfit(null, null);
    	Map<String, ArrayList<Item>> modifiedOutfitsList = outfits.getList();
    	assertTrue(outfitsList == modifiedOutfitsList);
	}

    @Test
    public void showoutfitswithnojacket() throws IOException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shirts");
        int temp = clothing.get(rand.nextInt(clothing.size())).minTemp;
        while(temp > 40) {
            temp = clothing.get(rand.nextInt(clothing.size())).minTemp;
        }
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
        when(request.getParameter("tempInput")).thenReturn(String.valueOf(temp));
        when(request.getParameter("weatherInput")).thenReturn("clear");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (ServletException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		} 
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains("src=\"ClothesImages/Shirts/pinksweater.png\""));
		assertFalse(stringWriter.toString().contains("src=\"ClothesImages/Jackets"));
    }
    
    @Test
    public void showoutfitswithjacketbyconditions() throws IOException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("jackets");
        String jacketlocation = String.valueOf(clothing.get(rand.nextInt(clothing.size())).location);
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn("");
        when(request.getParameter("shoeInput")).thenReturn("");
        when(request.getParameter("shortInput")).thenReturn("");
        when(request.getParameter("pantInput")).thenReturn("");
        when(request.getParameter("jacketInput")).thenReturn(jacketlocation);
        when(request.getParameter("tempInput")).thenReturn("65");
        when(request.getParameter("weatherInput")).thenReturn("");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (ServletException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains("src=\"ClothesImages/Jackets/"));
    }
}
