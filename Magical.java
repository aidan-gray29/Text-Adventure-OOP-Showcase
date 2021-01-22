/**
 * Interface for Magical Attacks
*/
interface Magical {
  /** The magic menu whenever hero wants to use Magic Attack*/
	static final String MAGIC_MENU = "1. Magic Missle\n2. Fireball\n3. Thunderclap";
	
  /**
  * Mutator that is used to attack with magicMissile
  * @param e the entity that is being damaged with
  * @return attack
  */
	String magicMissle(Entity e);
	
  /**
  * Mutator that is used to attack with fireball
  * @param e the entity that is being damaged with
  * @return attack
  */
	String fireball(Entity e);
	
  /**
  * Mutator that is used to attack with thunderclap
  * @param e the entity that is being damaged with
  * @return attack
  */
	String thunderclap(Entity e);
}