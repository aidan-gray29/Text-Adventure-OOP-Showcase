import java.util.ArrayList;

class Warlock extends EnemyDecorator implements Magical {
	
  /**
  * Warlock constructor
  * adds on Warlock to an Enemy while avoiding duplicate names
  */
	public Warlock(Enemy e) {
		super(e, (e.getName().contains("Warlock") ? e.getName() : e.getName() + " Warlock"), e.getMaxHP() + 1);
	}
	
  /**
  * @return enemy name + " Warlock" if enemy's name does not contain Warlock. else returns enemy name
  */
	@Override
	public String getName() {
		if (!enemy.name.contains("Warlock")) {
			return this.enemy.name + " Warlock";
		} else {
			return this.enemy.name;
		}
	}
	
  /**
  * @return Warlocks maximum health 
  */
	@Override
	public int getMaxHP() {
		return this.enemy.getMaxHP() + 1;
	}
	
	/**
	 * Deals damage to entity with Magic Missile
	 *
	 * @param e entity receiving the hit
	 */
	@Override
	public String magicMissle(Entity e) {
		int damage = (int) (Math.random() * 4) + 1 + (this.enemy.getMaxHP() / 4);
		e.takeDamage(damage);
		return "\npew-pew-pews " + e.getName() + " with a Magic Missile for " + damage + " damage";
	}
	
	/**
	 * Deals damage to entity with Fireball
	 *
	 * @param e entity receiving the hit
	 */
	@Override
	public String fireball(Entity e) {
		int damage = (int) (Math.random() * 4) + 1 + (this.enemy.getMaxHP() / 4);
		e.takeDamage(damage);
		return "\nscorches " + e.getName() + " with a Fireball for " + damage + " damage";
	}
	
	/**
	 * Deals damage to entity with Thunderclap
	 *
	 * @param e entity receiving the hit
	 */
	@Override
	public String thunderclap(Entity e) {
		int damage = (int) (Math.random() * 4) + 1 + (this.enemy.getMaxHP() / 4);
		e.takeDamage(damage);
		return "\nzaps " + e.getName() + " with Thunderclap for " + damage + " damage";
	}
	
	/**
	 * Override
	 * Deals damage with an attack from the entity
	 *
	 * @param e entity receiving the hit
	 */
	@Override
	public String attack(Entity e) {
		String attackBanner = "";
		int j = (int) (Math.random() * 3);
		switch (j) {
			case 0:
				attackBanner = magicMissle(e);
				break;
			case 1:
				attackBanner = fireball(e);
				break;
			case 2:
				attackBanner = thunderclap(e);
				break;
			default:
				return " magic attack is broken ";
		}
		return super.enemy.attack(e)+" then"+attackBanner;
	}
}
