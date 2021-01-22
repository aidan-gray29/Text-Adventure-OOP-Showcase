import java.util.Scanner;
import java.util.Random;

/**
 * Authors: Aidan Gray, Richard Huang
 *
 */
public class Main {
	/**
	 * Creates a monster room where an enemy can be defeated
	 *
	 * @param h     the hero
	 * @param level of the monster
	 * @return true if the hero is defeated or runs away, false if the enemy is killed
	 */
	static boolean monsterRoom(Hero h, int level){
		Map.getInstance().reveal(h.getLocation());
		
		Enemy enemy = EnemyGenerator.getInstance().generateEnemy(level);
		System.out.println("\nYou've encountered a " + enemy.getName() + "\n" + enemy);
		boolean finish = false;
		while (!finish) {    //repeats the fight until fight method returns as finished
			finish = fight(h, enemy);
		}
		if (h.getHP() <= 0) {
			//Player is dead, end game
			System.out.println("\nYou've been defeated by the " + enemy.getName() + "...");
			System.out.println("Game Over");
			return true;
		} else if (enemy.getHP() <= 0) {
			//Enemy is dead, end fight
			System.out.println("\nYou defeated the " + enemy.getName() + "!");
			Item drop = enemy.getItem();
			System.out.println( (drop.getName().equals("Boots") ? "You received " + drop.getName() : "You received a " + drop.getName()) + " from its corpse.");
			h.pickUpItem(drop);
			Map.getInstance().removeCharAtLoc(h.getLocation());
			return false;
		} else {
			//running away ends the fight
			System.out.println("\nGot away safely.");
			return true;
		}
	}
	
	/**
	 * Initiates the fight between player and enemy
	 *
	 * @param h the player
	 * @param e the enemy
	 * @return true if the hero or the enemy is defeated, false if no one has died yet
	 */
	static boolean fight(Hero h, Enemy e){
		boolean done = false;
		//menu in the case of hero having a health potion
		System.out.println("1. Fight \n2. Run Away");
		if (h.hasPotion()) {
			System.out.println("3. Drink Health Potion\n");
		} else {
			System.out.println();
		}
		Scanner in = new Scanner(System.in);
		String input = "0";
		boolean check = false;
		// checks user input
		while (!check) {
			if (in.hasNext()) {
				input = in.next();
				if (input.equals("1") || input.equals("2")) {
					check = true;
				} else if (input.equals("3") && h.hasPotion()) {
					check = true;
				} else if (h.hasPotion()) {
					System.out.println("Must enter 1, 2, or 3.");
				} else {
					System.out.println("Must enter 1 or 2.");
				}
			} else {
				in.nextLine();
				System.out.println("Not a valid input. Enter an integer, and try again: ");
			}
		}
		if (input.equals("3") && h.hasPotion()) {
			h.drinkPotion();
			System.out.println("\n"+h);
		} else {
			switch (input) {
				// Attack
				case "1":
					System.out.println("1. Physical Attack \n2. Magical Attack");
					while (in.hasNext()) {
						String decision = in.next();
						if (decision.equals("1")) {
							System.out.println("\n"+h.attack(e)+"\n");
							break;
						} else if (decision.equals("2")) {
							System.out.println(Magical.MAGIC_MENU);
							boolean keepGoing = true;
							while (keepGoing) {
								switch (in.next()) {
									case "1":
										System.out.println("\n"+h.magicMissle(e)+"\n");
										in.nextLine();
										keepGoing = false;
										break;
									case "2":
										System.out.println("\n"+h.fireball(e)+"\n");
										in.nextLine();
										keepGoing = false;
										break;
									case "3":
										System.out.println("\n"+h.thunderclap(e)+"\n");
										in.nextLine();
										keepGoing = false;
										break;
									default:
										System.out.println("Enter a valid  integer (1, 2, or 3)");
										in.nextLine();
								}
							}
							break;
						} else {
							System.out.println("Enter a valid integer (1 or 2)");
							in.nextLine();
						}
					}
					break;
				// Run Away
				case "2":
					//create an array of options if the random movement occurs at the  border
					return true;
				default:
					System.out.println("Not a valid input for parameters.");
			}
		}
		
		if (e.getHP() <= 0) {
			return true;
		}
		
		if (!done) {
			int armorIndex = h.hasArmorItem();
			if (armorIndex != -1) {
				System.out.println("The " + e.getName() + "'s attack was blocked!");
				h.dropItem(armorIndex);
				System.out.println("You have used an armor item.\n");
			} else {
				System.out.println(e.attack(h)+"\n");
			}
			if (h.getHP() <= 0) {
				return true;
			}
			System.out.println(h + "\n" + e);
		}
		
		return done;
	}
	
	/**
	 * Creates the enemy room where an enemy can be defeated
	 *
	 * @param h the player
	 */
	static void itemRoom(Hero h){
		Item i = ItemGenerator.getInstance().generateItem();
		if (i.getName().equals("Boots")) {
			System.out.println("\nYou found " + i.getName());
		} else {
			System.out.println("\nYou found a " + i.getName());
		}
		
		Scanner input = new Scanner(System.in);
		if (!h.pickUpItem(i)) {
			int selection = 0;
			do {
				System.out.println("Which would you like to discard?\n" + h.itemsToString());
				try {
					Item temp = null;
					selection = (Integer.parseInt(input.nextLine()) - 1);
					if (selection >= 0 && selection <= 5) {
						temp = h.dropItem(selection);
						System.out.println("You dropped your " + temp.getName() + " on the ground.");
					} else {
						System.out.println("Selection must be from [1, 6] ");
					}
				} catch (NumberFormatException e) {
					System.out.println("Selection must be from [1, 6] ");
					selection = -1;
				}
			} while (selection < 1 || selection > 6);
		}
		
		Map.getInstance().reveal(h.getLocation());
		System.out.println("\n" + h);
		Map.getInstance().displayMap(h.getLocation());
		//asks for a move to avoid looping if an item isn't picked up
		System.out.println("\n1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");
		String decision = "0";
		
		do {
			//five is used as the escape as you'll only enter to end program, and for no other reason
			//setting the decision to 5 after the decision is used will end the looping
			//if decision is 5 when taken as input, will end the program
			decision = input.next();
			input.nextLine();
			switch (decision) {
				case "1":
					h.goNorth();
					decision = "5";
					break;
				case "2":
					h.goSouth();
					decision = "5";
					break;
				case "3":
					h.goEast();
					decision = "5";
					break;
				case "4":
					h.goWest();
					decision = "5";
					break;
				case "5":
					System.out.println("Game over.");
					input.close();
					System.exit(1);
				default:
					System.out.println("Enter a valid integer [1,5].");
			}
		} while (!decision.equals("5"));
	}
	
	/**
	 * Hero has the option to visit the store at the start room. Sells potions and keys; buys items from the hero in exchange for gold.
	 *
	 * @param h, the Hero visiting the store
	 */
	static void store(Hero h){
		System.out.println("\nYou have arrived at a store!");
		int decInt = 0;
		do {
			System.out.println("\n1. Buy Health Potion\n2. Buy Key\n3. Sell item\n4. Exit");
			decInt = CheckInput.getIntRange(1, 4);
			switch (decInt) {
				case 1: //Health Potion = 25 Gold
					if (h.getGold() >= 25 && h.getNumItems() < 5) {
						h.pickUpItem(ItemGenerator.getInstance().getPotion());
						h.spendGold(25);
						System.out.println("\nYou have bought a Health Potion.");
						break;
					} else {
						System.out.println("\nYou do not have enough gold, or you do not have enough inventory space.");
						break;
					}
				case 2: //Key = 50 Gold
					if (h.getGold() >= 50 && h.getNumItems() < 5) {
						h.pickUpItem(ItemGenerator.getInstance().getKey());
						h.spendGold(50);
						System.out.println("\nYou have bought a Key.");
						break;
					} else {
						System.out.println("\nYou do not have enough gold, or you do not have enough inventory space.");
						break;
					}
				case 3: //Sell item in inventory
					if (h.getNumItems() > 0) {
						System.out.print("\nPick an item to sell:\n" + h.itemsToString() + "" + (h.getNumItems() + 1) + ". to sell nothing.\n");
						int choice = CheckInput.getIntRange(1, h.getNumItems() + 1);
						if (choice == h.getNumItems() + 1) {
							System.out.println("\nYou didn't sell anything.");
							break;
						}
						choice--;
						Item temp = h.dropItem(choice);
						System.out.println("\nYou sold your " + temp.getName() + ". You collect " + temp.getValue() + " gold.");
						h.collectGold(temp.getValue());
					} else {
						System.out.println("\nYou have no items.");
					}
					break;
				case 4:
					decInt = 4;
					break;
				default:
					System.out.println("Enter a valid integer [1,4].");
			}
		} while (decInt != 4);
	}
	
	public static void main(String[] args){ 
		//prompt user for name, initialize , hero, and generators
		Scanner input = new Scanner(System.in);
		System.out.println("\n\n\nWhat is your name, traveler? ");
		String name = input.nextLine();
		String decision = "0";
		int mapNum = 1;
		Hero hero = new Hero(name);
		boolean heroIsDefeated = false;
		do {
			//options and functions are based on the current room
			char room = Map.getInstance().getCharAtLoc(hero.getLocation());
			//ranAway boolean exists to provide another loop iteration if you run away from a fight, 
			//so the new room can be checked and handled.
			boolean ranAway = false;
			do {
				Map.getInstance().reveal(hero.getLocation());
				switch (room) {
					case 'n':
						System.out.println("\nThere was nothing here.");
						ranAway = false;
						break;
					case 's':
            //Hero starts here on each map. 
						System.out.println("\nYou're at the start. Visit the store? (y/n)");
						String ans = "";
						do {
							ans = input.nextLine().toLowerCase();
							switch (ans) {
								case "y":
									store(hero);
									break;
								case "n":
									break;
								default:
									System.out.println("\nPlease enter either y or n");
							}
						} while (!(ans.equals("y") || ans.equals("n")));
						ranAway = false;
						break;
					case 'i':
						itemRoom(hero);
						//reveals the room gone to afterwards
						Map.getInstance().reveal(hero.getLocation());
						ranAway = false;
						break;
					case 'm':
            //monster room
						heroIsDefeated = monsterRoom(hero, mapNum);
						ranAway = false;
						if (heroIsDefeated && hero.getHP() <= 0) {
							input.close();
							return;
						} else if (heroIsDefeated) {
							ranAway = true;
							//Ejects the hero in a random direction. Checks for validity of move.
							int direc = 0;
							Random rand = new Random();
							int threeWays = rand.nextInt(3);
							if (hero.getLocation().y == 4) {
								int[] optionsIndices = {1, 3, 4};
								direc = optionsIndices[threeWays];
							} else if (hero.getLocation().y == 0) {
								int[] optionsIndices = {2, 3, 4};
								direc = optionsIndices[threeWays];
							} else if (hero.getLocation().x == 4) {
								int[] optionsIndices = {1, 2, 4};
								direc = optionsIndices[threeWays];
							} else if (hero.getLocation().x == 0) {
								int[] optionsIndices = {1, 2, 3};
								direc = optionsIndices[threeWays];
							}else{
								int fourWays = rand.nextInt(4);
								int[] optionsIndices = {1, 2, 3 , 4};
								direc = optionsIndices[fourWays];
							}
							switch (direc) {
								case 1:
									hero.goNorth();
									break;
								case 2:
									hero.goSouth();
									break;
								case 3:
									hero.goEast();
									break;
								case 4:
									hero.goWest();
									break;
							}
							room = Map.getInstance().getCharAtLoc(hero.getLocation());
							Map.getInstance().reveal(hero.getLocation());
						}
						break;
					case 'f':
						//increases the mapNum to generate the next level, and heals the hero to full, and generates the new level. only progress if a key is held
						if (hero.hasKey()) {
							hero.useKey();
							ranAway = false;
							mapNum++;
							hero.heal(hero.getMaxHP() - hero.getHP());
							System.out.println("\nYou found the finish, and your key was used.\nYou've made it to the next level. Healing to full...");
							Map.getInstance().loadMap(mapNum);
							room = Map.getInstance().getCharAtLoc(hero.getLocation());
							continue;
						} else {
							ranAway = false;
						}
						break;
				}
				System.out.println("\n" + hero);
				Map.getInstance().displayMap(hero.getLocation());
			} while (ranAway);
			//movement decisions
			System.out.println("\n1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");
			do {
				if (input.hasNext()) {
					switch (decision = input.next()) {
						case "1":
							hero.goNorth();
							decision = "5";
							break;
						case "2":
							hero.goSouth();
							decision = "5";
							break;
						case "3":
							hero.goEast();
							decision = "5";
							break;
						case "4":
							hero.goWest();
							decision = "5";
							break;
						case "5":
							System.out.println("Game over.");
							input.close();
							return;
						default:
							System.out.println("Enter a valid integer [1,5].");
					}
				}
			} while (!decision.equals("5"));
		} while (!(hero.getHP() <= 0));
		input.close();
		return;
	}
}