package helloworld;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static tk.plogitech.darksky.forecast.util.Assert.notNull;
import tk.plogitech.darksky.forecast.util.IOUtil;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;



@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	public static String[] getLatLongPositions(String address) throws Exception
	{
	    int responseCode = 0;
	    String api = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&key=%20AIzaSyB-UvNw8r9VhhVW_ONxuUGeBEpPjUXcFYc";
	    //String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
	URL url = new URL(api);
	HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	httpConnection.connect();
	responseCode = httpConnection.getResponseCode();
	if(responseCode == 200)
	{
	  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
	  Document document = builder.parse(httpConnection.getInputStream());
	  XPathFactory xPathfactory = XPathFactory.newInstance();
	  XPath xpath = xPathfactory.newXPath();
	  XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	  String status = (String)expr.evaluate(document, XPathConstants.STRING);
	  if(status.equals("OK"))
	  {
	     expr = xpath.compile("//geometry/location/lat");
	     String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	     expr = xpath.compile("//geometry/location/lng");
	     String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	     return new String[] {latitude, longitude};
	  }
	  else
	  {
		  return new String[] {"0", "1"};
	      }
	    }
	    return null;
	  }
	
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("zipInput");
    	String latLongs[] = null;
		try {
			latLongs = getLatLongPositions(username);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ForecastRequest weather = new ForecastRequestBuilder()
		        .key(new APIKey("2519e820c228f27e1050b544eba9de22"))
		        .units(ForecastRequestBuilder.Units.us)
		        .exclude(ForecastRequestBuilder.Block.minutely)
		        .exclude(ForecastRequestBuilder.Block.hourly)
		        .exclude(ForecastRequestBuilder.Block.currently)
		        .exclude(ForecastRequestBuilder.Block.flags)
		        .location(new GeoCoordinates(new Longitude(Double.parseDouble(latLongs[1])), new Latitude(Double.parseDouble(latLongs[0])))).build();
			String forecast = "77";
		    DarkSkyClient client = new DarkSkyClient();
		    ArrayList<String> tempList =new ArrayList<String>();
		    ArrayList<String> dateList =new ArrayList<String>();
		    ArrayList<String> iconList =new ArrayList<String>();
		    try {
				forecast = client.forecastJsonString(weather);
				
		    	Matcher temperature = Pattern.compile(
		    	                            Pattern.quote("\"temperatureHigh\":")
		    	                            + "(.*?)"
		    	                            + Pattern.quote(",\"temperatureHighTime\"")
		    	                   ).matcher(forecast);
		    	while(temperature.find()){
		    	    String match = temperature.group(1);
		    	    System.out.println(">"+match+"<");
		    	    tempList.add(match);
		    	    //here you insert 'match' into the list
		    	}
		    	
		    	Matcher time = Pattern.compile(
                        Pattern.quote("\"time\":")
                        + "(.*?)"
                        + Pattern.quote(",\"summary\"")
		        ).matcher(forecast);
				while(time.find()){
					String matchtwo = time.group(1);
					System.out.println(">"+matchtwo+"<");
					long unixSeconds = Long.parseLong(matchtwo);
					// convert seconds to milliseconds
					Date date = new java.util.Date(unixSeconds*1000L); 
					// the format of your date
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE"); 
					// give a timezone reference for formatting (see comment at the bottom)
					String formattedDate = sdf.format(date);
					dateList.add(formattedDate);
					System.out.println(formattedDate);
				//here you insert 'match' into the list
				}
				
				Matcher icon = Pattern.compile(
                        Pattern.quote("\"icon\":\"")
                        + "(.*?)"
                        + Pattern.quote("\",\"sunriseTime\":")
		        ).matcher(forecast);
				while(icon.find()){
					String matchthree = icon.group(1);
					System.out.println(">"+matchthree+"<");
					if(matchthree.contains("\"")) {
						iconList.add(matchthree.split("\"")[0]);
					}
					else {
						iconList.add(matchthree);
					}
				//here you insert 'match' into the list
				}
				//forecast = forecast.split("\"temperature\":")[1];
				//forecast = forecast.split(",")[0];
				System.out.println(forecast);
			} catch (ForecastException e) {
				e.printStackTrace();
			}
		    
        String password = request.getParameter("zipInput");
         
        
        System.out.println("username: " + username);
        System.out.println("password: " + password);
 
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
         
        // build HTML code
        String htmlRespone = "<html>";
        htmlRespone += "<head>\n" + 
        		"    <!-- Required meta tags -->\n" + 
        		"    <meta charset=\"utf-8\">\n" + 
        		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
        		"\n" + 
        		"    <!-- Bootstrap CSS -->\n" + 
        		"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" + 
        		"\n" + 
        		"    <title>Hello, world!</title>\n" + 
        		"<style type=\"text/css\">\n" + 
        		"	.myButton {\n" + 
        		"		-moz-box-shadow:inset 0px 1px 0px 0px #54a3f7;\n" + 
        		"		-webkit-box-shadow:inset 0px 1px 0px 0px #54a3f7;\n" + 
        		"		box-shadow:inset 0px 1px 0px 0px #54a3f7;\n" + 
        		"		background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #007dc1), color-stop(1, #0061a7));\n" + 
        		"		background:-moz-linear-gradient(top, #007dc1 5%, #0061a7 100%);\n" + 
        		"		background:-webkit-linear-gradient(top, #007dc1 5%, #0061a7 100%);\n" + 
        		"		background:-o-linear-gradient(top, #007dc1 5%, #0061a7 100%);\n" + 
        		"		background:-ms-linear-gradient(top, #007dc1 5%, #0061a7 100%);\n" + 
        		"		background:linear-gradient(to bottom, #007dc1 5%, #0061a7 100%);\n" + 
        		"		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#007dc1', endColorstr='#0061a7',GradientType=0);\n" + 
        		"		background-color:#007dc1;\n" + 
        		"		-moz-border-radius:3px;\n" + 
        		"		-webkit-border-radius:3px;\n" + 
        		"		border-radius:3px;\n" + 
        		"		border:2px solid #124d77;\n" + 
        		"		display:inline-block;\n" + 
        		"		cursor:pointer;\n" + 
        		"		color:#ffffff;\n" + 
        		"		font-family:Arial;\n" + 
        		"		font-size:28px;\n" + 
        		"		font-weight:bold;\n" + 
        		"		padding:32px 70px;\n" + 
        		"		text-decoration:none;\n" + 
        		"		text-shadow:0px 1px 0px #154682;\n" + 
        		"	}\n" + 
        		"	.myButton:hover {\n" + 
        		"		background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #0061a7), color-stop(1, #007dc1));\n" + 
        		"		background:-moz-linear-gradient(top, #0061a7 5%, #007dc1 100%);\n" + 
        		"		background:-webkit-linear-gradient(top, #0061a7 5%, #007dc1 100%);\n" + 
        		"		background:-o-linear-gradient(top, #0061a7 5%, #007dc1 100%);\n" + 
        		"		background:-ms-linear-gradient(top, #0061a7 5%, #007dc1 100%);\n" + 
        		"		background:linear-gradient(to bottom, #0061a7 5%, #007dc1 100%);\n" + 
        		"		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#0061a7', endColorstr='#007dc1',GradientType=0);\n" + 
        		"		background-color:#0061a7;\n" + 
        		"	}\n" + 
        		"	.myButton:active {\n" + 
        		"		position:relative;\n" + 
        		"		top:1px;\n" + 
        		"	}\n" + 
        		"\n" + 
        		"	</style>"+
        		"  </head>\n" + 
        		"  <body>\n" + 
        		"<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" + 
        		"	  <a class=\"navbar-brand\" href=\"index.html\">Home</a>\n" + 
        		"	  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" + 
        		"	    <span class=\"navbar-toggler-icon\"></span>\n" + 
        		"	  </button>\n" + 
        		"	\n" + 
        		"	  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" + 
        		"	    <ul class=\"navbar-nav mr-auto\">\n" + 
        		"	      <li class=\"nav-item\">\n" + 
        		"	        <a class=\"nav-link active\" href=\"map.html\">Suggested Outfit</a>\n" + 
        		"	      </li>\n" + 
        		"	      <li class=\"nav-item\">\n" + 
        		"	        <a class=\"nav-link\" href=\"savedoutfits.html\">View Saved Outfit</a>\n" + 
        		"	      </li>\n" + 
        		"	      <li class=\"nav-item dropdown\">\n" + 
        		"	        <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" + 
        		"	          My Closet\n" + 
        		"	        </a>\n" + 
        		"	        <div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n" + 
        		"	          <a class=\"dropdown-item\" href=\"pants.html\">Pants</a>\n" + 
        		"	          <a class=\"dropdown-item\" href=\"jackets.html\">Jackets</a>\n" + 
        		"	          <a class=\"dropdown-item\" href=\"shirts.html\">Shirts</a>\n" + 
        		"	          <a class=\"dropdown-item\" href=\"shoes.html\">Shoes</a>\n" + 
        		"	          <a class=\"dropdown-item\" href=\"shorts.html\">Shorts</a>          \n" + 
        		"	        </div>\n" + 
        		"	      </li>\n" + 
        		"	    </ul>\n" + 
        		"	  </div>\n" + 
        		"	</nav>" +
        		"    <div class=\"container h-100\">\n" + 
        		"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">";
		        for (int i = 0; i < 6; i++) {
		        	htmlRespone += "<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n" + 
        			"              <div class=\"card\" style=\"width: 10rem;\">\n" + 
        			"  <img class=\"card-img-top\" src=\"WeatherImages/"+ iconList.get(i) + ".svg\" alt=\"" + iconList.get(i) + "\">\n" + 
        			"  <div class=\"card-body\">\n" + 
        			"    <h5 class=\"card-title\">" + dateList.get(i) + "</h5>\n" + 
        			"    <p class=\"card-text\">" + tempList.get(i) + "F</p>\n" + 
        			"                </div>\n" + 
        			"              </div>\n" + 
        			"            </div>";
				}
		        htmlRespone += "<div class=\"container h-100\">\n" + 
		        		"<form method=\"post\" action=\"showResults\">" +
		        		"<label for=\"zipInput\">Enter your destination</label>\n" +
		        		"<div class=\"btn-group btn-group-toggle\" data-toggle=\"buttons\">\n" + 
		        		"  <label class=\"btn btn-secondary active\">\n" + 
		        		"    <input type=\"radio\" name=\"options\" id=\"option1\" value=\"Blue\" autocomplete=\"off\" checked> Blue\n" + 
		        		"  </label>\n" + 
		        		"  <label class=\"btn btn-secondary\">\n" + 
		        		"    <input type=\"radio\" name=\"options\" id=\"option2\" value=\"Grey\" autocomplete=\"off\"> Grey\n" + 
		        		"  </label>\n" + 
		        		"  <label class=\"btn btn-secondary\">\n" + 
		        		"    <input type=\"radio\" name=\"options\" id=\"option3\" value=\"Black\" autocomplete=\"off\"> Black\n" + 
		        		"  </label>\n" + 
		        		"</div>" +
		        		"   <input type=\"hidden\" name=\"tempInput\" value=\"" + tempList.get(0) + "\">" +
		        		"   <input type=\"hidden\" name=\"weatherInput\" value=\"" + iconList.get(0) + "\">" +
		        		"	<button type=\"submit\" class=\"btn btn-primary mt-5 btn-lg btn-block\">Get Recommendations</a>\n" + 
		        		"</form>" +
		        		"	</div>" +
		        		"              </div>\n" + 
	        			"            </div>" +
		        		"<br>\n" +
		        		 "<br>\n" +
		        		"	<br></br>\n" + 
		        		"	<br></br>	\n";
        htmlRespone +=
        		"    <!-- Optional JavaScript -->\n" + 
        		"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" + 
        		"    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" + 
        		"  </body>";
        htmlRespone += "</html>";
        String shirtFile = "shirts.ser";
        ArrayList<Item> object1 = null; 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(shirtFile); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            object1 = (ArrayList<Item>)in.readObject(); 
              
            in.close(); 
            file.close(); 
              
            System.out.println("Object has been deserialized ");
            for(Item item : object1) {
   	         System.out.println("a = " + item.location); 
            }
        }
        
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 

        // return response
        writer.println(htmlRespone);
         
    }
 
}