
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Map {
	/**
	 * The map data that the hero interacts with
	 */
	char[][] map;

	/**
	 * The locations that the hero has gone to; true if the hero has been there, false otherwise
	 */
	boolean[][] revealed;
	
	/**
	 * Singleton instance of Map
	 */
	private static Map instance = null;
	
	/**
	 * Constructor
	 * Begins the hero on Map1, initializes map and revealed
	 */
	private Map() {
		map = new char[5][5];
		revealed = new boolean[5][5];
		try {
			loadMap(1);
		} catch (Exception e) {
			System.out.println("Load map broken.");
		}
	}
	
	/**
	 * Singleton instance retrieval
	 *
	 * @return Returns the singleton instance of Map
	 */
	public static Map getInstance() {
		try {
			if (instance == null) {
				instance = new Map();
			}
		} catch (Exception e) {
			System.out.println("Map class is broken");
		}
		return instance;
	}
	
	/**
	 * Function that loads the map given by the num
	 *
	 * @param mapNum, the number the map to load
	 */
	public void loadMap(int mapNum) {
		try {
			int usedNum = (mapNum == 3 ? 3 : mapNum % 3);
			File f = new File("Map" + usedNum + ".txt");
			FileReader in = new FileReader(f);
			BufferedReader input = new BufferedReader(in);
			String[] rows = new String[5];
			
			//places characters from map . txt into the map[][]
			for (int i = 0; i < 5; i++) {
				rows[i] = input.readLine().trim().replaceAll(" ", "");
				for (int j = 0; j < 5; j++) {
					map[i][j] = rows[i].charAt(j);
				}
			}
			
			//initializes all spaces to not revealed
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					revealed[i][j] = false;
				}
			}
			reveal(findStart());
			input.close();
			in.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Map not found");
		}
	}
	
	/**
	 * Receives the character's location at a specific point
	 *
	 * @param p, the point on the grid
	 * @return char at point
	 */
	public char getCharAtLoc(Point p) {
		return map[p.y][p.x];
	}
	
	/**
	 * Displays the current map with the Hero's location marked by *
	 *
	 * @param p the player's location on the map
	 */
	public void displayMap(Point p) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i == p.y && j == p.x) {
					System.out.print("*\t");
				} else if (revealed[i][j] == true) {
					System.out.print(map[i][j] + "\t");
				} else {
					System.out.print("x\t");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Finds the starting point of the map
	 *
	 * @return the point where s is at
	 */
	public Point findStart() {
		Point location = new Point(0, 0);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (map[i][j] == 's') {
					location = new Point(j, i);
					this.reveal(location);
				}
			}
		}
		return location;
	}
	
	/**
	 * sets the map location P as revealed
	 *
	 * @param p the point on the map to reveal
	 */
	public void reveal(Point p) {
		revealed[p.y][p.x] = true;
	}
	
	/**
	 * Changes the char at a location to n once it has been cleared
	 *
	 * @param p the point on the map to clear
	 */
	public void removeCharAtLoc(Point p) {
		map[p.y][p.x] = 'n';
	}
}
