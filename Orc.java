
class Orc extends Enemy {
  /**
  * Orc constructor
  * creates a Orc enemy with 4 health with a randomly generated item
  */
	Orc() {
		super("Orc", 4, ItemGenerator.getInstance().generateItem());
	}
}