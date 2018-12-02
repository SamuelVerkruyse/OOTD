package helloworld;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet("/showResults")
public class ShowOutfits extends HttpServlet {
	private static final long serialVersionUID = 8468049327108461353L;
	String jacket = "Jacket";
	String shirt = "Shirt";
	String shorts = "Shorts";
	String pants = "Pants";
	String shoes = "Shoes";
	String list = "	      </li>\n";
	String div = "</div>\n";
	String labelOpen = "  <label class=\"form-check-label\">";
	String labelClose = "</label>\n";
	String empty = "empty";
	Random generator = new Random();
	protected Item findClothingItem(String clothingType, String desiredItem, Double temperature) {
    	ItemListFactory factory = new ItemListFactory();
    	Item clothing = null;
    	for(Item clothingItem : factory.getItemList(clothingType)) {
			if(clothingItem.location.equals(desiredItem)) {
				return clothingItem;
			}
			while(clothing == null || clothing.minTemp > temperature || clothing.maxTemp < temperature) {
				clothing = factory.getItemList(clothingType).get(generator.nextInt(factory.getItemList(clothingType).size()));
			}
		}
    	return clothing;
    }
	
	protected String emptyOrExtant(HashMap<String, Item> outfit, String clothingType) {
		if(outfit.get(clothingType) == null) {
			return "\"" + "disabled";
		}
		return outfit.get(clothingType).location + "\"" + (outfit.containsKey(clothingType) ? "" : "disabled");
	}
	
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String shirtPersist = request.getParameter("shirtInput");
    	String shoePersist = request.getParameter("shoeInput");
    	String shortPersist = request.getParameter("shortInput");
    	String pantPersist = request.getParameter("pantInput");
    	String jacketPersist = request.getParameter("jacketInput");
    	Double temperature = Double.parseDouble(request.getParameter("tempInput"));

    	if(temperature < 20.0) {
    		temperature = 20.0;
    	}
    	if(temperature > 100) {
    		temperature = 100.0;
    	}
    	String conditions = request.getParameter("weatherInput");
    	HashMap<String, Item> outfit = new HashMap<>();
    	if(temperature < 70.0 && !conditions.contains("clear")) {
    		outfit.put(jacket, findClothingItem("jackets", jacketPersist, temperature));
    	}
		outfit.put(shirt, findClothingItem("shirts", shirtPersist, temperature));
    	if(temperature > 70.0){
    		outfit.put(shorts, findClothingItem("shorts", shortPersist, temperature));
    	}
    	else {
    		outfit.put(pants, findClothingItem("pants", pantPersist, temperature));
    	}
		outfit.put(shoes, findClothingItem("shoes", shoePersist, temperature));
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
         
        // build HTML code
        StringBuilder htmlRespone = new StringBuilder();
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
        		"	        <a class=\"nav-link\" href=\"map.html\">Suggested Outfit</a>\n" + 
        		list + 
        		"	      <li class=\"nav-item\">\n" + 
        		"	        <a class=\"nav-link\" href=\"savedoutfits.html\">View Saved Outfit</a>\n" + 
        		list + 
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
        		list + 
        		"	    </ul>\n" + 
        		"	  </div>\n" + 
        		"	</nav>" +
        		"    <div class=\"container h-100\">\n" + 
        		"        <div class=\"row align-items-center\">");
		        htmlRespone.append(
			        	"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">" +
			        	"<div class=\"card-deck\">\n" +
			        	"<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n");
		        for (Map.Entry<String, Item> items : outfit.entrySet()) {
			        	htmlRespone.append(
	        			"              <div class=\"card mt-3 h-20\" style=\"width: 10rem;\">\n" + 
	        			"  <img class=\"card-img-top img-fluid\" src=\"" + items.getValue().location + "\">\n" + 
	        			"              </div>\n");
		        	}
		        	htmlRespone.append(div
		        	+div
		        	+div
		        +div
		        		+ "<div class=\"row align-items-center mx-auto\">" +
		        		"<div class=\"container mt-5d-block mx-auto h-100\">\n" + 
		        		"	<form class=\"form-inline justify-content-center\" method=\"post\" action=\"showResults\">\n" + 
		        		"   <input type=\"hidden\" name=\"tempInput\" value=\"" + temperature + "\">" +
		        		"   <input type=\"hidden\" name=\"weatherInput\" value=\"" + conditions + "\">" +
			        	"<div class=\"form-check form-check-inline mx-auto\">\n" + 
		        		"   <input class=\"form-check\" type=\"checkbox\" name=\"shirtInput\" value=\"" + emptyOrExtant(outfit, shirt) + ">" +
		        		labelOpen+ shirt +labelClose + 
		        		"   <input class=\"form-check\" type=\"checkbox\" name=\"shortInput\" value=\""  + emptyOrExtant(outfit, shorts) + ">" +
		        		labelOpen+ shorts +labelClose + 
		        		"   <input class=\"form-check\" type=\"checkbox\" name=\"shoeInput\" value=\""  + emptyOrExtant(outfit, shoes) + ">" +
		        		labelOpen+ shoes +labelClose + 
		        		"   <input class=\"form-check\" type=\"checkbox\" name=\"jacketInput\" value=\""  + emptyOrExtant(outfit, jacket) + ">" +
		        		labelOpen+ jacket +labelClose + 
		        		"   <input class=\"form-check\" type=\"checkbox\"  name=\"pantInput\" value=\""  + emptyOrExtant(outfit, pants) + ">" +
		        		labelOpen+ pants +labelClose + 
		        		div +
		        		"	<button type=\"submit\" class=\"btn btn-primary mt-5 btn-lg btn-block\">Reroll outfit (Check box to keep items)</a>\n" + 		        		"</form>" +
		        		"<form method=\"post\" action=\"savedoutfits.html\">" +
		        		"   <input type=\"hidden\" name=\"inputShorts\" value=\"" + (outfit.containsKey(shorts) ? outfit.get(shorts).location : empty) + "\">" +
		        		"   <input type=\"hidden\" name=\"inputShoes\" value=\"" + outfit.get(shoes).location + "\">" +
		        		"   <input type=\"hidden\" name=\"inputJackets\" value=\"" + (outfit.containsKey(jacket) ? outfit.get(jacket).location : empty) + "\">" +
		        		"   <input type=\"hidden\" name=\"inputPants\" value=\"" + (outfit.containsKey(pants) ? outfit.get(pants).location : empty) + "\">" +
		        		"   <input type=\"hidden\" name=\"inputShirts\" value=\"" + outfit.get(shirt).location + "\">" +
		        		"	<button type=\"submit\" class=\"btn btn-primary mt-5 btn-lg btn-block\">Save Outfit</button>\n" + 
		        		"  <input type=\"text\" name=\"outfitName\" class=\"form-control\" placeholder=\"Type name of outfit\" aria-label=\"\" aria-describedby=\"basic-addon1\">\n" + 
		        		div + 
		        		"</form>" +
		        		div +
		        		"	</div>");
		        
        htmlRespone.append("</html>");
         
        // return response
        writer.println(htmlRespone);
         
    }
 
}