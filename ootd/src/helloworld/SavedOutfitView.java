package helloworld;
 
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/savedoutfits.html")
public class SavedOutfitView extends HttpServlet {
	private static final long serialVersionUID = -3043090869162897502L;
	String button = "	  </button>\n";
    String navItem = "	      <li class=\"nav-item\">\n";
    String list = "	      </li>\n";
    String div = "</div>\n";
    String br = "<br>\n";
    
    protected Item findClothingItem(String clothingType, String desiredItem) {
    	ItemListFactory factory = new ItemListFactory();
    	for(Item jacketpair : factory.getItemList(clothingType)) {
			if(jacketpair.location.equals(desiredItem)) {
				return jacketpair;
			}
		}
    	return null;
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
        // build HTML code
        StringBuilder htmlRespone = new StringBuilder();
        htmlRespone.append("<html>");
        htmlRespone.append("<head>\n" + 
        		"    <!-- Required meta tags -->\n" + 
        		"    <meta charset=\"utf-8\">\n" + 
        		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
        		"\n" + 
        		"    <!-- Bootstrap CSS -->\n" + 
        		"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" + 
        		"\n" + 
        		"    <title>Hello, world!</title>\n" + 
        		"<style type=\"text/css\">\n" + 
        		".card-img-top {\n" + 
        		"min-width: 100%;\n" + 
        		"width: auto;\n" + 
        		"height: auto;\n" + 
        		"}" +
        		"	</style>"+
        		"  </head>\n" + 
        		"  <body>\n" + 
        		"<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" + 
        		"	  <a class=\"navbar-brand\" href=\"index.html\">Home</a>\n" + 
        		"	  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" + 
        		"	    <span class=\"navbar-toggler-icon\"></span>\n" + 
        		button + 
        		"	\n" + 
        		"	  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" + 
        		"	    <ul class=\"navbar-nav mr-auto\">\n" + 
        		navItem + 
        		"	        <a class=\"nav-link active\" href=\"map.html\">Suggested Outfit</a>\n" + 
        		list + 
        		navItem + 
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
        		"    <div class=\"container h-100\">\n");
        		SavedOutfits outfitBuilder = new SavedOutfits();
        		Map<String, ArrayList<Item>> outfits = outfitBuilder.getList();
		        for (Map.Entry<String, ArrayList<Item>> items : outfits.entrySet()) {
		        	htmlRespone.append("<div class=\"alert w-50 mt-5 alert-warning alert-dismissible fade show\" role=\"alert\">\n" 
		        			+ items.getKey() + 
		        			"	  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" + 
		        			"	    <span aria-hidden=\"true\">&times;</span>\n" + 
		        			button + 
		        			"	</div>" +
		        	"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">" +
		        	"<div class=\"card-deck\">\n" +
		        	"<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n");
		        	for(Item item : items.getValue()) {
			        	htmlRespone.append
	        			("              <div class=\"card h-20\" style=\"width: 10rem;\">\n" + 
	        			"  <img class=\"card-img-top img-fluid\" src=\"" + item.location + "\">\n" + 
	        			div);
		        	}
		        	htmlRespone.append(div
		        	+ div
		        	+ div);
				}
		        htmlRespone.append(
		        		div + 
	        			"            </div>" +
		        		br +
		        		 br +
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
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	Item shirts = null;
    	Item pants = null; 
    	Item shoes = null;
    	Item jackets = null;
    	Item shorts = null;
    	String shirtPersist = request.getParameter("inputShirts");
    	String shoePersist = request.getParameter("inputShoes");
    	String shortPersist = request.getParameter("inputShorts");
    	String pantPersist = request.getParameter("inputPants");
    	String jacketPersist = request.getParameter("inputJackets");
    	String outfitName = request.getParameter("outfitName");

		SavedOutfits outfitBuilder = new SavedOutfits();
    	HashMap<String, Item> outfit = new HashMap<>();
    	jackets = findClothingItem("jackets", jacketPersist);
		if(jackets != null) {
			outfit.put("Jacket", jackets);   
		}
    	shirts = findClothingItem("shirts", shirtPersist);
    	if(shirts != null) {
    		outfit.put("Shirt", shirts);
    	}
    	shorts = findClothingItem("shorts", shortPersist);
    	if(shorts != null) {
    		outfit.put("Shorts", shorts);
    	}
    	pants = findClothingItem("pants", pantPersist);
		if(pants != null) {
			outfit.put("Pants", pants);
		}
    	shoes = findClothingItem("shoes", shoePersist);
    	if(shoes != null) {
    		outfit.put("Shoes", shoes);
    	}
		outfitBuilder.addOutfit(outfit, outfitName);
		Map<String, ArrayList<Item>> outfits = outfitBuilder.getList();
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
         
        // build HTML code
        StringBuilder htmlRespone = new StringBuilder();
        htmlRespone.append("<html>");
        htmlRespone.append("<head>\n" + 
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
        		button + 
        		"	\n" + 
        		"	  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" + 
        		"	    <ul class=\"navbar-nav mr-auto\">\n" + 
        		navItem + 
        		"	        <a class=\"nav-link\" href=\"map.html\">Suggested Outfit</a>\n" + 
        		list + 
        		navItem + 
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
        		"    <div class=\"container h-100\">\n");
		        for (Map.Entry<String, ArrayList<Item>> items : outfits.entrySet()) {
		        	htmlRespone.append("<div class=\"alert w-50 mt-5 alert-warning alert-dismissible fade show\" role=\"alert\">\n" 
		        			+ items.getKey() + 
		        			"	  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" + 
		        			"	    <span aria-hidden=\"true\">&times;</span>\n" + 
		        			button + 
		        			"	</div>" +
		        	"        <div class=\"row card-group align-items-center mx-auto mt-5 d-flex align-items-stretch\">" +
		        	"<div class=\"card-deck\">\n" +
		        	"<div class=\"col-xs-2 mx-auto d-flex align-items-stretch\">\n");
		        	for(Item item : items.getValue()) {
			        	htmlRespone.append(
	        			"              <div class=\"card h-20\" style=\"width: 10rem;\">\n" + 
	        			"  <img class=\"card-img-top img-fluid\" src=\"" + item.location + "\">\n" + 
	        			div);
		        	}
		        	htmlRespone.append(div
		        	+ div
		        	+ div);
				}
		        htmlRespone.append(
		        		div + 
	        			"            </div>" +
		        		br +
		        		 br +
		        		"	<br></br>\n" + 
		        		"	<br></br>	\n");
        htmlRespone.append(
        		"    <!-- Optional JavaScript -->\n" + 
        		"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" + 
        		"    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" + 
        		"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" + 
        		"  </body>");
        htmlRespone.append("</html>");
         
        // return response
        writer.println(htmlRespone);
         
    }
}