
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class ItemGenerator {
	/**
	 * The list of templates for each item to be based on
	 */
	ArrayList<Item> itemList;
	
	/**
	 * Singleton instance of ItemGenerator
	 */
	private static ItemGenerator instance = null;
	
	/**
	 * Constructor
	 * Creates the engine to create items and loads templates
	 */
	private ItemGenerator() {
		try {
			itemList = new ArrayList<>();
			FileReader in = new FileReader("ItemList.txt");
			BufferedReader iList = new BufferedReader(in);
			while (iList.ready()) {
				itemList.add(new Item(iList.readLine()));
			}
			iList.close();
		} catch (Exception e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		}
	}
	
	/*
	 *	Singleton instance retrieval
	 *	@return Returns the singleton instance of ItemGenerator
	 */
	public static ItemGenerator getInstance() {
		if (instance == null) {
			instance = new ItemGenerator();
		}
		return instance;
	}
	
	
	/**
	 * Randomly generates item
	 *
	 * @return Random Item
	 */
	public Item generateItem() {
		Item generated = itemList.get((int) (Math.random() * itemList.size())).clone();
		return generated;
	}
	
	/**
	 * Generates a potion
	 *
	 * @return Health Potion
	 */
	public Item getPotion() {
		return itemList.get(0);
	}
	
	/**
	 * Generates a key
	 *
	 * @return Key
	 */
	public Item getKey() {
		return itemList.get(1);
	}
}
