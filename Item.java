
class Item {
	/**
	 * the name of the item
	 */
	private String name;

	/**
	 * amount of gold item is worth at a store
	 */
	private int value;
	
	/**
	 * the type of item it is (p potion | k key | m money | a armor)
	 */
	private char type;
	
	/**
	 * Constructor Creates an item for the entity
	 *
	 * @param n the name of the item
	 */
	Item(String n) {
		String[] tokens = n.split(",");
		name = tokens[0];
		value = Integer.parseInt(tokens[1]);
		type = tokens[2].charAt(0); // does this work? r: I don't see why not
	}
	
	/**
	 * Gets the name of the item
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the value of the item
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Gets the type of the item
	 *
	 * @return type
	 */
	public char getType() {
		return type;
	}
	
	/**
	 * Copy constructor for Item
	 */
	public Item(Item i) {
		if (i != null) {
			this.name = i.name;
			this.value = i.value;
			this.type = i.type;
		}
	}
	
	/**
	 * Clone function for Item
	 *
	 * @return a clone of the implicit object
	 */
	@Override
	public Item clone() {
		return new Item(this);
	}
}