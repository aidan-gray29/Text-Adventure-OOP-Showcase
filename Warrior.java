class Warrior extends EnemyDecorator {
	
  /**
  * Warrior constructor
  * adds on Warrior to an Enemy while avoiding duplicate names
  */
	public Warrior(Enemy e) {
		super(e, (e.getName().contains("Warrior") ? e.getName() : e.getName() + " Warrior"), e.getMaxHP() + 2);
	} 
	
  /**
  * @return enemy name + " Warrior" if enemy's name does not contain Warrior. else returns enemy name
  */
	@Override
	public String getName() {
		if (!enemy.name.contains("Warrior")) {
			return this.enemy.name + " Warrior";
		} else {
			return this.enemy.name;
		}
	}
	
  /**
  * @return Warrior's maximum health 
  */
	@Override
	public int getMaxHP() {
		return this.enemy.getMaxHP() + 2;
	}
	
  /** Override
	 * Deals damage with an attack from the entity
	 * @param The entity receiving the hit
	 */
	@Override
	public String attack(Entity e) {
		super.attack(e);
		int damageTaken = ((int) Math.random() * 4) + 1 + (this.enemy.getMaxHP() / 4);
		e.takeDamage(damageTaken);
		return super.enemy.attack(e)+" then\nstrikes "+ e.getName() + " for "+ damageTaken + " damage";
	}
}
