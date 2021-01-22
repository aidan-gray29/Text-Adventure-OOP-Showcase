
abstract class Entity {
	/**
	 * name of entity
	 */
	protected String name;
	/**
	 * Max health of entity
	 */
	protected int maxHp;
	/**
	 * Current health of entity
	 */
	protected int hp;
	
	/**
	 * Constructor
	 * Creates an entity to interact with in the game
	 *
	 * @param n   name of entity
	 * @param mHP the maximum health points entity can have
	 */
	Entity(String n, int mHp) {
		name = n;
		maxHp = mHp;
		hp = maxHp;
	}
	
	/**
	 * Deals damage with an attack from the entity
	 *
	 * @param The entity receiving the hit
	 */
	abstract String attack(Entity e);
	
	/**
	 * Gets the name of the entity
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the current health of entity
	 *
	 * @return hp
	 */
	public int getHP() {
		return hp;
	}
	
	/**
	 * Gets the maximum possible health of entity
	 *
	 * @return maxHp
	 */
	public int getMaxHP() {
		return maxHp;
	}
	
	/**
	 * Heals entity a specific amount, or fully restores health, whichever difference is smaller
	 *
	 * @param h the amount to heal health by
	 */
	public void heal(int h) {
		if (hp + h <= maxHp) {
			hp += h;
		} else {
			hp = maxHp;
		}
	}
	
	/**
	 * Subtracts from entity's health
	 *
	 * @param h the amount of health to lose
	 */
	public void takeDamage(int h) {
		hp -= h;
	}
	
	/**
	 * Converts Entity's information to a string
	 *
	 * @return string of the Entity
	 */
	@Override
	public String toString() {
		return name + "\nHP: " + hp + "/" + maxHp;
	}
}