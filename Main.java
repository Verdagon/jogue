import java.io.IOException;
import java.util.Random;


class Coord {
	int row, col;
	
	public Coord(int row, int col) {
		this.row = row;
		this.col = col;
	}
}

class Player {
	Coord location = new Coord(0, 0);
}

class Goblin {
	Coord location = new Coord(0, 0);
}

class Engine {
	int LEFT = 1;
	int RIGHT = 2;
	int UP = 3;
	int DOWN = 4;
	int QUIT = 5;
	
	char[][] map;
	Goblin[][] goblins;
	Player player;

	public Engine(char[][] map) {
		this.map = map;
		this.goblins = new Goblin[map.length][map[0].length];
		player = new Player();
	}

	private char[][] updateAndDisplay(Coord newPlayerLocation) {
		if (goblins[newPlayerLocation.row][newPlayerLocation.col] == null) {
			player.location = newPlayerLocation;
		}
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				if (player.location.row == y && player.location.col == x) {
					System.out.print('@');
				} else if (goblins[y][x] != null) {
					System.out.print('g');
				} else {
					System.out.print(map[y][x]);
				}
			}
			System.out.println();
		}
		return map;
	}
	
	public void run() throws IOException {
		Random rand = new Random(1341);
		
		for (int i = 0; i < 20; i++) {
			goblins[rand.nextInt(20)][rand.nextInt(80)] = new Goblin();
		}
		
		while (true) {
			System.out.print("Enter direction! ");
			char input = (char)System.in.read();
			switch (input) {
			case 'a':
				map = updateAndDisplay(new Coord(player.location.row, player.location.col - 1));
				break;
			case 's':
				map = updateAndDisplay(new Coord(player.location.row + 1, player.location.col));
				break;
			case 'd':
				map = updateAndDisplay(new Coord(player.location.row, player.location.col + 1));
				break;
			case 'w':
				map = updateAndDisplay(new Coord(player.location.row - 1, player.location.col));
				break;
			case 'q':
				return;
			}

			for (int row = 0; row < goblins.length; row++) {
				for (int col = 0; col < goblins[0].length; col++) {
					Goblin goblin = goblins[row][col];
					if (goblin != null) {
						Coord[] directions = new Coord[] {
								new Coord(row, col + 1),
								new Coord(row + 1, col),
								new Coord(row, col - 1),
								new Coord(row - 1, col)
						};
						Coord newLocation = directions[rand.nextInt(4)];
						newLocation.row = Math.max(newLocation.row, 0);
						newLocation.col = Math.max(newLocation.col, 0);
						newLocation.row = Math.min(newLocation.row, goblins.length - 1);
						newLocation.col = Math.min(newLocation.col, goblins[0].length - 1);
						if (this.goblins[newLocation.row][newLocation.col] == null) {
							this.goblins[row][col] = null;
							this.goblins[newLocation.row][newLocation.col] = new Goblin();
							this.goblins[newLocation.row][newLocation.col].location = newLocation;
						}
					}
				}
			}
		}
		
	}
}


public class Main {
	public static void main(String[] args) throws IOException {
		char[][] map = new char[][] {
			"################################################################################".toCharArray(),
			"#..............#...............................................................#".toCharArray(),
			"#..............#.........#.....................................................#".toCharArray(),
			"#..............#.........#.....................................................#".toCharArray(),
			"#..............#.........#.....................................................#".toCharArray(),
			"#..............###########.....................................................#".toCharArray(),
			"#....................#.........................................................#".toCharArray(),
			"#....................#.........................................................#".toCharArray(),
			"#....................#.........................................................#".toCharArray(),
			"#....................#.........................................................#".toCharArray(),
			"#....................#.........................................................#".toCharArray(),
			"#...##################.........................................................#".toCharArray(),
			"#.................................................######.......................#".toCharArray(),
			"#.................................................#....#.......................#".toCharArray(),
			"#.................................................#....#.......................#".toCharArray(),
			"#.................................................#............................#".toCharArray(),
			"#.................................................######.......................#".toCharArray(),
			"#..............................................................................#".toCharArray(),
			"#..............................................................................#".toCharArray(),
			"#..............................................................................#".toCharArray(),
			"#..............................................................................#".toCharArray(),
			"#..............................................................................#".toCharArray(),
			"################################################################################".toCharArray(),
		};
		new Engine(map).run();
	}
}
