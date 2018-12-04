package tests;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockito.Mockito;


public class JacketConditions extends Mockito{
	public void configureParameters(HttpServletRequest request,HttpServletResponse response, 
			PrintWriter writer, String jacketlocation, String temp, String weather) throws IOException {
	    when(response.getWriter()).thenReturn(writer);
	    when(request.getParameter("shirtInput")).thenReturn("");
	    when(request.getParameter("shoeInput")).thenReturn("");
	    when(request.getParameter("shortInput")).thenReturn("");
	    when(request.getParameter("pantInput")).thenReturn("");
	    when(request.getParameter("jacketInput")).thenReturn(jacketlocation);
	    when(request.getParameter("tempInput")).thenReturn(temp);
	    when(request.getParameter("weatherInput")).thenReturn(weather);
	}
}