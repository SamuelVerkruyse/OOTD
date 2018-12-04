package helloworld;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SavedOutfits {
	private HashMap<String, ArrayList<Item>>  outfits  = new HashMap<>();
	private static final Logger LOGGER = Logger.getLogger( ShowOutfits.class.getName() );
	String black = "Black";
	String greyshirt = "ClothesImages/Shirts/greyshirt.png";
	String heelshoe = "ClothesImages/Shoes/heelshoe.png";
	@SuppressWarnings("unchecked")
	public SavedOutfits() {
		 ArrayList<Item> warmBlue = new ArrayList<>();
		 ArrayList<Item> warmGrey = new ArrayList<>();
		 ArrayList<Item> warmBlack = new ArrayList<>();
		 ArrayList<Item> windyBlue = new ArrayList<>();
		 ArrayList<Item> windyGrey = new ArrayList<>();
		 ArrayList<Item> windyBlack = new ArrayList<>();
        String outfitFile = "outfits.ser";
        HashMap<String, ArrayList<Item>> object1 = null; 
        try(FileInputStream file = new FileInputStream(outfitFile)){ 
            try(ObjectInputStream in = new ObjectInputStream(file)){           
	            // Method for deserialization of object 
	            object1 = (HashMap<String, ArrayList<Item>>)in.readObject();
	            outfits = object1;
            }
        }
        catch(IOException|ClassNotFoundException ex) 
        { 
			LOGGER.log(Level.SEVERE, "Exception occured", ex);
        } 
	     warmBlue.add(new Item(70, 100, "Grey", "ClothesImages/Shirts/stripedtee.png"));
	     warmBlue.add(new Item(70, 110, "Blue", "ClothesImages/Shorts/jeanshortsone.png"));
	     warmBlue.add(new Item(70, 100, black, "ClothesImages/Shoes/slides.png"));
	     warmGrey.add(new Item(50, 80, "Grey", greyshirt));
	     warmGrey.add(new Item(70, 110, "Grey", "ClothesImages/Shorts/sweatshorts.png"));
	     warmGrey.add(new Item(60, 80, black, heelshoe));
	     warmBlack.add(new Item(50, 80, black, "ClothesImages/Shirts/blackshirt.png"));
	     warmBlack.add(new Item(70, 110, black, "ClothesImages/Shorts/floralshorts.png"));
	     warmBlack.add(new Item(50, 80, black, heelshoe));
	     windyBlue.add(new Item(50, 80, "Grey", greyshirt));
	     windyBlue.add(new Item(40, 65, "Blue", "ClothesImages/Jackets/jeanjacket.png"));
	     windyBlue.add(new Item(30, 75, "Blue", "ClothesImages/Pants/rippedjeans.png"));
	     windyBlue.add(new Item(50, 80, "Black", heelshoe));
	     windyGrey.add(new Item(50, 80, "Grey", greyshirt));
	     windyGrey.add(new Item(20, 50, "Grey", "ClothesImages/Jackets/sweaterjacket.png"));
	     windyGrey.add(new Item(20, 65, "Grey", "ClothesImages/Pants/sweatpants.png"));
	     windyGrey.add(new Item(50, 80, black, "ClothesImages/Shoes/sneakers.png"));
	     windyBlack.add(new Item(50, 80, black, "ClothesImages/Shirts/blackshirt.png"));
	     windyBlack.add(new Item(35, 65, black, "ClothesImages/Jackets/leatherjacket.png"));
	     windyBlack.add(new Item(30, 70, black, "ClothesImages/Pants/yogapants.png"));
	     windyBlack.add(new Item(50, 80, black, "ClothesImages/Shoes/sneakers.png"));
	     outfits.put("Warm Blue", warmBlue);
	     outfits.put("Warm Grey", warmGrey);
	     outfits.put("Warm Black", warmBlack);
	     outfits.put("Windy Blue", windyBlue);
	     outfits.put("Windy Grey", windyGrey);
	     outfits.put("Windy Black", windyBlack);
	}
	
	public Map<String, ArrayList<Item>>  getList() {
		return outfits;
	}
	
	public void addOutfit(Map<String, Item> newOutfit, String name){
		if(outfits != null && newOutfit != null) {
			ArrayList<Item> newArray = new ArrayList<>();
	        for (Map.Entry<String, Item> item : newOutfit.entrySet()) {
	        	newArray.add(item.getValue());
			}
			outfits.put(name, newArray);
	        String outfitFile = "outfits.ser";
	          
	        // Serialization  
	        try(FileOutputStream file = new FileOutputStream(outfitFile)){
	            try(ObjectOutputStream out = new ObjectOutputStream(file)){ 
	            // Method for serialization of object 
	            out.writeObject(outfits);
	            }
	        } 
	          
	        catch(IOException ex) 
	        { 
				LOGGER.log(Level.SEVERE, "Exception occured", ex);
	        } 
		}
	}

}
