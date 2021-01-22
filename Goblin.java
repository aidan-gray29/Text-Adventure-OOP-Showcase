
class Goblin extends Enemy {
	/**
	 * Goblin constructor
   * creates a Goblin enemy with 2 health with a randomly generated item
	 */
	Goblin() {
		super("Goblin", 2, ItemGenerator.getInstance().generateItem());
	}
}