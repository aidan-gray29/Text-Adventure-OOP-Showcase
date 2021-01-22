
class Froglok extends Enemy {
	
	/**
	 * Froglok constructor
   * creates a Froglok enemy with 3 health with a randomly generated item
	 */
	Froglok() {
		super("Froglok", 3, ItemGenerator.getInstance().generateItem());
	}
}