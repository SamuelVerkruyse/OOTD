package helloworld;
 
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



@WebServlet("/loginServlet")
public class weatherView extends HttpServlet {
	private static final long serialVersionUID = -193899611039436032L;
	String regexAssist = "(.*?)";
	String list = "	      </li>\n";
	String labels = "  </label>\n";
	private static final Logger LOGGER = Logger.getLogger( weatherView.class.getName() );
	public static String[] getLatLongPositions(String address) throws IOException, ParserConfigurationException, XPathExpressionException, SAXException
	{
	    int responseCode = 0;
	    String api = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&key=%20AIzaSyB-UvNw8r9VhhVW_ONxuUGeBEpPjUXcFYc";
	URL url = new URL(api);
	HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	httpConnection.connect();
	responseCode = httpConnection.getResponseCode();
	if(responseCode == 200)
	{
	  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
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
	    return new String[] {};
	  }
	
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("zipInput");
    	String[] latLongs = null;
    	ForecastRequest weather = null;
		try {
			latLongs = getLatLongPositions(username);
			weather = new ForecastRequestBuilder()
			        .key(new APIKey("2519e820c228f27e1050b544eba9de22"))
			        .units(ForecastRequestBuilder.Units.us)
			        .exclude(ForecastRequestBuilder.Block.minutely)
			        .exclude(ForecastRequestBuilder.Block.hourly)
			        .exclude(ForecastRequestBuilder.Block.currently)
			        .exclude(ForecastRequestBuilder.Block.flags)
			        .location(new GeoCoordinates(new Longitude(Double.parseDouble(latLongs[1])), new Latitude(Double.parseDouble(latLongs[0])))).build();
		} catch (Exception e1) {
			LOGGER.log(Level.SEVERE, "Exception occured", e1);
		}
					String forecast = "77";
		    DarkSkyClient client = new DarkSkyClient();
		    ArrayList<String> tempList =new ArrayList<>();
		    ArrayList<String> dateList =new ArrayList<>();
		    ArrayList<String> iconList =new ArrayList<>();
		    try {
				forecast = client.forecastJsonString(weather);
				
		    	Matcher temperature = Pattern.compile(
		    	                            Pattern.quote("\"temperatureHigh\":")
		    	                            + regexAssist
		    	                            + Pattern.quote(",\"temperatureHighTime\"")
		    	                   ).matcher(forecast);
		    	while(temperature.find()){
		    	    String match = temperature.group(1);
		    	    tempList.add(match);
		    	    //here you insert 'match' into the list
		    	}
		    	
		    	Matcher time = Pattern.compile(
                        Pattern.quote("\"time\":")
                        + regexAssist
                        + Pattern.quote(",\"summary\"")
		        ).matcher(forecast);
				while(time.find()){
					String matchtwo = time.group(1);
					long unixSeconds = Long.parseLong(matchtwo);
					// convert seconds to milliseconds
					Date date = new java.util.Date(unixSeconds*1000L); 
					// the format of your date
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE"); 
					// give a timezone reference for formatting (see comment at the bottom)
					String formattedDate = sdf.format(date);
					dateList.add(formattedDate);
				//here you insert 'match' into the list
				}
				
				Matcher icon = Pattern.compile(
                        Pattern.quote("\"icon\":\"")
                        + regexAssist
                        + Pattern.quote("\",\"sunriseTime\":")
		        ).matcher(forecast);
				while(icon.find()){
					String matchthree = icon.group(1);
					if(matchthree.contains("\"")) {
						iconList.add(matchthree.split("\"")[0]);
					}
					else {
						iconList.add(matchthree);
					}
				//here you insert 'match' into the list
				}
			} catch (ForecastException e) {
				LOGGER.log(Level.SEVERE, "Exception occured", e);
			}
		             
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
        StringBuilder htmlRespone = new StringBuilder();
        // build HTML code
        htmlRespone.append("<html>"
        		+"<head>\n" + 
        		"    <!-- Required meta tags -->\n" + 
        		"    <meta charset=\"utf-8\">\n" + 
        		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
        		"\n" + 
        		"    <!-- Bootstrap CSS -->\n" + 
        		"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" + 
        		"\n" + 
        		"    <title>Hello, world!</title>\n" + 
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
        		list + 
        		"	      <li class=\"nav-item\">\n" + 
        		"	        <a class=\"nav-link\" href=\"savedoutfits.html\">View Saved Outfit</a>\n" + 
        		list + 
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
        		list + 
        		"	    </ul>\n" + 
        		"	  </div>\n" + 
        		"	</nav>" +
        		"    <div class=\"container h-100\">\n" + 
        		"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">");
		        for (int i = 0; i < 6; i++) {
		        	htmlRespone.append("<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n" + 
        			"              <div class=\"card\" style=\"width: 10rem;\">\n" + 
        			"  <img class=\"card-img-top\" src=\"WeatherImages/"+ iconList.get(i) + ".svg\" alt=\"" + iconList.get(i) + "\">\n" + 
        			"  <div class=\"card-body\">\n" + 
        			"    <h5 class=\"card-title\">" + dateList.get(i) + "</h5>\n" + 
        			"    <p class=\"card-text\">" + tempList.get(i) + "F</p>\n" + 
        			"                </div>\n" + 
        			"              </div>\n" + 
        			"            </div>");
				}
		        htmlRespone.append("<div class=\"container h-100\">\n" + 
		        		"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">" + 
		        		"<form method=\"post\" action=\"showResults\">" +
		        		"   <input type=\"hidden\" name=\"tempInput\" value=\"" + tempList.get(0) + "\">" +
		        		"   <input type=\"hidden\" name=\"weatherInput\" value=\"" + iconList.get(0) + "\">" +
		        		"   <input type=\"hidden\" name=\"shirtInput\" value=\"empty\">" +
		        		"   <input type=\"hidden\" name=\"shortInput\" value=\"empty\">" +
		        		"   <input type=\"hidden\" name=\"shoeInput\" value=\"empty\">" +
		        		"   <input type=\"hidden\" name=\"jacketInput\" value=\"empty\">" +
		        		"   <input type=\"hidden\" name=\"pantInput\" value=\"empty\">" +
		        		"	<button type=\"submit\" class=\"btn btn-primary mt-5 btn-lg btn-block\">Get Recommendations</a>\n" + 
		        		"</form>" +
		        		"	</div>" +
		        		"	</div>" +
		        		"              </div>\n" + 
	        			"            </div>" +
		        		"<br>\n" +
		        		 "<br>\n" +
		        		"	<br></br>\n" + 
		        		"	<br></br>	\n");
        htmlRespone.append(
        		"    <!-- Optional JavaScript -->\n" + 
        		"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" + 
        		"    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" + 
        		"  </body>"
        + "</html>");
        // return response
        writer.println(htmlRespone);
         
    }
 
}