abstract class Enemy extends Entity {
	protected Item item;
	
	/**
	 * Constructor
	 * Creates an enemy to defeat with in the game
	 *
	 * @param name   name of enemy
	 * @param maxHP max hp of enemy
	 * @param i   Item for enemy to have
	 */
	Enemy(String name, int maxHP, Item i) {
		super(name, maxHP);
		item = i;
	}
	
	/**
	 * Gets the item on the enemy
	 *
	 * @return item
	 */
	Item getItem() {
		return item;
	}
	
	/**
	 * Deals damage with an attack from the entity
	 *
	 * @param The entity receiving the hit
	 * @return String in the form of "Entity attacks Entity for x damage "
	 */
	@Override
	String attack(Entity e) {
		//damage is based on maxHP as Enemy has no access to current map level, however, when an enemy is generated, their maxHP is based off of the map level.
		int damageTaken = ((int) Math.random() * 4) + 1 + (this.getMaxHP() / 4);
		e.takeDamage(damageTaken);
		return this.getName() + " attacks " + e.getName() + " for " + damageTaken + " damage";
	}
}