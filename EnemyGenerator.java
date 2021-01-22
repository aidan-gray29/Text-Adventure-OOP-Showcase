import java.util.ArrayList;
import java.util.Random;

class EnemyGenerator {
	
	/**
	 * Singleton instance of EnemyGenerator
	 */
	private static EnemyGenerator instance = null;
	
	/**
	 * Constructor
	 * Creates the engine to create enemies
	 */
	private EnemyGenerator() {
	}
	
	/**
	 * Singleton instance retrieval
	 *
	 * @return Singleton instance of EnemyGenerator
	 */
	public static EnemyGenerator getInstance() {
		if (instance == null) {
			instance = new EnemyGenerator();
		}
		return instance;
	}
	
	/**
	 * Randomly generates enemy. If the map level>1 , decorates the enemy randomly *		* level-1 times
	 *
	 * @return an enemy generated according to the current map level
	 */
	Enemy generateEnemy(int level) {
		
		Random rand = new Random();
		int chosenEnemy = rand.nextInt(4);
		Enemy e;
		
		if (chosenEnemy == 0) {
			e = new Froglok();
		} else if (chosenEnemy == 1) {
			e = new Goblin();
		} else if (chosenEnemy == 2) {
			e = new Orc();
		} else {
			e = new Troll();
		}
		
		if (level > 1) {
			for (int i = 1; i < level; i++) {
				int chosenClass = rand.nextInt(2);
				switch (chosenClass) {
					case 0:
						e = new Warlock(e);
						break;
					case 1:
						e = new Warrior(e);
						break;
				}
			}
		}
		return e;
	}
}
