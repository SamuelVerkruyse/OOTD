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
import helloworld.ShowOutfits;
import helloworld.WeatherView;

public class Somya extends Mockito{
	private static final Logger LOGGER = Logger.getLogger( WeatherView.class.getName() );

	Random rand = new Random();

	@Test
	public void jackettest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("jackets");
    	assertTrue(!clothing.isEmpty());
	}

	@Test
	public void shirtstest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shirts");
    	assertTrue(!clothing.isEmpty());
	}
	
    @Test
    public void showoutfitswithshorts() throws IOException, ServletException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shorts");
        String shortlocation = String.valueOf(clothing.get(rand.nextInt(clothing.size())).location);
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn("");
        when(request.getParameter("shoeInput")).thenReturn("");
        when(request.getParameter("shortInput")).thenReturn(shortlocation);
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
		assertTrue(stringWriter.toString().contains(shortlocation));
    }
    
    @Test
    public void showoutfitswithpantsbytemp() throws IOException, ServletException {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("pants");
        String pantslocation = String.valueOf(clothing.get(rand.nextInt(clothing.size())).location);
    	HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("shirtInput")).thenReturn("");
        when(request.getParameter("shoeInput")).thenReturn("");
        when(request.getParameter("shortInput")).thenReturn("");
        when(request.getParameter("pantInput")).thenReturn(pantslocation);
        when(request.getParameter("jacketInput")).thenReturn("");
        when(request.getParameter("tempInput")).thenReturn(String.valueOf(clothing.get(rand.nextInt(clothing.size())).minTemp));
        when(request.getParameter("weatherInput")).thenReturn("");
        try {
        	new ShowOutfits().doPost(request, response);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}
        writer.flush(); // it may not have been flushed yet...
		assertTrue(stringWriter.toString().contains(pantslocation));
    }
}
