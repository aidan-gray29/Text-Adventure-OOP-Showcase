import java.io.IOException;

abstract class EnemyDecorator extends Enemy {
	protected Enemy enemy;
	
	/**
	 * Enemy Decorator constructor
	 *
	 * @param e,     object of type Enemy
	 * @param name,  neme of the Enemy
	 * @param maxHP, Maximum health of the Enemy
	 */
	EnemyDecorator(Enemy e, String name, int maxHP) {
		super(name, maxHP, ItemGenerator.getInstance().generateItem());
		this.enemy = e;
	}
	
	// Overridden Methods
	
	/**
	 * @return name of the Enemy object held by this decorator
	 */
	@Override
	public String getName() {
		return this.enemy.getName();
	}
	
	/**
	 * @return the max health points of the Enemy object held by this decorator
	 */
	@Override
	public int getMaxHP() {
		return this.enemy.getMaxHP();
	}

	@Override
	public String attack(Entity e){
		int damageTaken = ((int) Math.random() * 4) + 1 + (this.getMaxHP() / 4);
		e.takeDamage(damageTaken);
		//changes to this.enemy.getName() over this.getName() so that titles are preserved.
		return this.enemy.getName() + " attacks " + e.getName() + " for " + damageTaken + " damage";
	}
}
