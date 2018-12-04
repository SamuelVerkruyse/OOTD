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
import helloworld.SavedOutfitView;
import helloworld.SavedOutfits;
import helloworld.WeatherView;

public class Harvir extends Mockito{
	Random rand = new Random();

	@Test
	public void shoestest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shoes");
    	assertTrue(!clothing.isEmpty());
	}

	@Test
	public void nonexistentitemtest() {
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("doesnotexist");
    	assertTrue(clothing.isEmpty());
	}
	
    @Test
    public void weatherview() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);
    	ItemListFactory factory = new ItemListFactory();
    	List<Item> clothing = factory.getItemList("shirts");
        when(request.getParameter("zipInput")).thenReturn(String.valueOf(clothing.get(rand.nextInt(clothing.size())).minTemp));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new WeatherView().doPost(request, response);
        verify(request, atLeast(1)).getParameter("zipInput"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        verify(response).setContentType("text/html");
        assertTrue(stringWriter.toString().contains("Monday") || stringWriter.toString().contains("Tuesday")  
        		|| stringWriter.toString().contains("Wednesday")  || stringWriter.toString().contains("Thursday")
        		 || stringWriter.toString().contains("Friday")  || stringWriter.toString().contains("Saturday")
        		 || stringWriter.toString().contains("Sunday"));
    }
    
    @Test
    public void savedoutfitview() throws ServletException, IOException {
    	SavedOutfits outfitMap = new SavedOutfits();
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new SavedOutfitView().doPost(request, response);
        writer.flush(); // it may not have been flushed yet...
        for ( String key : outfitMap.getList().keySet()) {
        	if(key != null) {
        		assertTrue(stringWriter.toString().contains(key));
        	}
        }

    }
}
