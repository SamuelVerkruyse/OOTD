package helloworld;
 
import java.io.IOException;
import java.io.PrintWriter;
 
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
import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;


@WebServlet("/showResults")
public class ShowOutfits extends HttpServlet {
	
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String temperature = request.getParameter("tempInput");
    	String conditions = request.getParameter("weatherInput");
    	String color = request.getParameter("options");
    	ItemListFactory factory = new ItemListFactory();
    	Item shirt = factory.getItemList("Shirts").get(0);
    	Item pants = factory.getItemList("pants").get(0);
    	Item shoes = factory.getItemList("shoes").get(0);
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
        		"	        <a class=\"nav-link\" href=\"map.html\">Suggested Outfit</a>\n" + 
        		"	      </li>\n" + 
        		"	      <li class=\"nav-item\">\n" + 
        		"	        <a class=\"nav-link\" href=\"savedoutfits.html\">View Saved Outfit</a>\n" + 
        		"	      </li>\n" + 
        		"	      <li class=\"nav-item dropdown\">\n" + 
        		"	        <a class=\"nav-link dropdown-toggle active\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" + 
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
        		"        <div class=\"row align-items-center h-100 mx-auto mt-5 d-flex align-items-stretch\">";
		        for (int i = 0; i < 3; i++) {
		        	htmlRespone += 
		        			"<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n" + 
		        			"              <div class=\"card\">\n" + 
		        			"<img class=\"card-img-top\" src=\""+ shirt.location + "\">\n" +  
		        			"                <div class=\"card-body d-flex flex-column\">\n" + 
		        			"                  <h5 class=\"card-title\">" + temperature + "</h5>\n" + 
		        			"                  <p class=\"card-text\">Discover the beauty of Hearst Castle through exploring each of the three houses</p>\n" + 
		        			"                  <a href=\"/search?query=house+c\" class=\"btn btn-sm btn-block btn-primary mt-auto\">Explore Casa del Sol Letters</a>\n" + 
		        			"                </div>\n" + 
		        			"              </div>\n" + 
		        			"            </div>";
				}
        htmlRespone +=
        		"    </div>" +
        		"    <!-- Optional JavaScript -->\n" + 
        		"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" + 
        		"    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" + 
        		"  </body>";
        htmlRespone += "</html>";
         
        // return response
        writer.println(htmlRespone);
         
    }
 
}