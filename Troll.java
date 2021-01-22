
class Troll extends Enemy {
  /**
  * Troll constructor
  * creates a Troll enemy with 5 health with a randomly generated item
  */
	Troll() {
		super("Troll", 5, ItemGenerator.getInstance().generateItem());
	}
}