
import java.util.ArrayList;

class Hero extends Entity implements Magical {
	/**
	 * Inventory of the hero
	 */
	private ArrayList<Item> items;

	/**
	 * The hero's location on the map
	 */
	private Point location;
	
	/**
	 * The hero's gold stash
	 */
	private int gold;
	
	/**
	 * Constructor
	 * Creates the hero
	 *
	 * @param n name of hero
	 */
	Hero(String n) {
		super(n, 25);
		gold = 50;
		location = Map.getInstance().findStart();
		items = new ArrayList<Item>();
	}
	
	/**
	 * Converts hero information + inventory + gold into a string
	 *
	 * @return hero's information + inventory + gold
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + itemsToString();
	}
	
	/**
	 * Converts hero's inventory to a string + gold
	 *
	 * @return hero's inventory + gold
	 */
	public String itemsToString() {
		String inventory = "Inventory:\n";
		for (int i = 0; i < items.size(); i++) {
			inventory = inventory.concat((i + 1) + ". " + items.get(i).getName() + "\n");
		}
		inventory = inventory.concat("Gold: " + getGold() + "\n");
		return inventory;
	}
	
	
	/**
	 * Gets the hero's inventory size
	 */
	public int getNumItems() {
		return items.size();
	}
	
	/**
	 * Picks up an item on the map
	 *
	 * @param i the item to be picked up
	 * @return True if the item can be added, false if inventory is full
	 */
	public boolean pickUpItem(Item i) {
		if (this.getNumItems() < 5) {
			items.add(i);
			Map.getInstance().removeCharAtLoc(this.getLocation());
			return true;
		}
		System.out.println("Inventory full. ");
		return false;
	}
	
	/**
	 * Checks hero's inventory for potion, if true heals hero's health
	 */
	public void drinkPotion() {
		if (this.hasPotion() == false) {
			System.out.println("No Health Potion held");
			return;
		}
		int firstPotionIndex = 0;
		for (Item i : items) {
			if (i.getName().equals("Health Potion")) {
				this.heal(25);
				items.remove(firstPotionIndex);
				break;
			}
			firstPotionIndex++;
		}
	}
	
	/**
	 * Removes an item from inventory using an int from 0 - 5
	 *
	 * @param index : Chosen index for removal
	 * @return Item discarded
	 */
	public Item dropItem(int index) {
		Item temp = items.get(index).clone();
		items.remove(index);
		return temp;
	}
	
	/**
	 * Checks hero inventory for health potion
	 *
	 * @return true if health potion was found, false if not
	 */
	public boolean hasPotion() {
		for (Item i : items) {
			if (i.getName().equals("Health Potion")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the hero's location on the map
	 *
	 * @return location
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * Moves hero one spot up
	 *
	 * @return the char at the new location
	 */
	public char goNorth() {
		if (location.y != 0) {
			location.y--;
			return Map.getInstance().getCharAtLoc(location);
		} else {
			System.out.println("\nReached map border. Cannot go North");
			return Map.getInstance().getCharAtLoc(location);
		}
	}
	
	/**
	 * Moves hero one spot down
	 *
	 * @return the char at the new location
	 */
	public char goSouth() {
		if (location.y != 4) {
			location.y++;
			return Map.getInstance().getCharAtLoc(location);
		} else {
			System.out.println("\nReached map border. Cannot go South");
			return Map.getInstance().getCharAtLoc(location);
		}
	}
	
	/**
	 * Moves hero one spot to the right
	 *
	 * @return the char at the new location
	 */
	public char goEast() {
		if (location.x != 4) {
			location.x++;
			return Map.getInstance().getCharAtLoc(location);
		} else {
			System.out.println("\nReached map border. Cannot go East");
			return Map.getInstance().getCharAtLoc(location);
		}
	}
	
	/**
	 * Moves hero one spot to the left
	 *
	 * @return the char at the new location
	 */
	public char goWest() {
		if (location.x != 0) {
			location.x--;
			return Map.getInstance().getCharAtLoc(location);
		} else {
			System.out.println("\nReached map border. Cannot go West");
			return Map.getInstance().getCharAtLoc(location);
		}
	}
	
	/**
	 * Returns the hero's gold
	 *
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}
	
	/**
	 * Adds gold to the stash
	 *
	 * @param g, the amount being added
	 */
	public void collectGold(int g) {
		gold += g;
	}
	
	/**
	 * Removes gold from the stash
	 */
	public void spendGold(int g) {
		gold -= g;
	}
	
	/**
	 * Returns the index of the first armor item in the hero's inventory
	 *
	 * @return first armor index in items, -1 if none
	 */
	public int hasArmorItem() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getType() == 'a') {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns key ownership status
	 *
	 * @return true if holds key, false otherwise
	 */
	public boolean hasKey() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getType() == 'k') {
				return true;
			}
		}
		System.out.println("You've found the finish, but you don't have a key.");
		return false;
	}
	
	/**
	 * Uses the key in the hero's inventory
	 */
	public void useKey() {
		for (Item i : items) {
			if (i.getName().equals("Key")) {
				items.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Deals damage with an attack from the entity
	 *
	 * @param The entity receiving the hit
	 */
	@Override
	String attack(Entity e) {
		int damageTaken = (int) (Math.random() * 4 + 1);
		e.takeDamage(damageTaken);
		return this.getName() + " attacks " + e.getName() + " for " + damageTaken + " damage";
	}
	
	/**
	 * Deals damage to entity with Magic Missile
	 *
	 * @param The entity receiving the hit
	 */
	@Override
	public String magicMissle(Entity e) {
		int damage = (int) (Math.random() * 4 + 1);
		e.takeDamage(damage);
		return this.getName() + " pew-pew-pews " + e.getName() + " with a Magic Missle for " + damage + " damage";
	}
	
	/**
	 * Deals damage to entity with Fireball
	 *
	 * @param The entity receiving the hit
	 */
	@Override
	public String fireball(Entity e) {
		int damage = (int) (Math.random() * 4 + 1);
		e.takeDamage(damage);
		return this.getName() + " scorches " + e.getName() + " with a Fireball for " + damage + " damage";
	}
	
	/**
	 * Deals damage to entity with Thunderclap
	 *
	 * @param The entity receiving the hit
	 */
	@Override
	public String thunderclap(Entity e) {
		int damage = (int) (Math.random() * 4 + 1);
		e.takeDamage(damage);
		return this.getName() + " zaps " + e.getName() + " with Thunderclap for " + damage + " damage";
	}
}
