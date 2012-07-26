package m33.entities;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;

import m33.Comp08.Level;
import m33.util.Loader;

public class TileManager {
	private final int TILE_SIZE = 32;
	private final int TSHEET_SIZE = 16;

	public Tile[][] cl; // Current Level
	private Image levelImg;
	private Loader loader;
	private int rows, cols;
	private Random rand;

	public TileManager() {
		loader = new Loader();
		rand = new Random();

		// Load level 1 tileSheet
		Toolkit tk = Toolkit.getDefaultToolkit();
		levelImg = tk.getImage(loader.getURL("level1_16.png"));
	}

	public void newLevel(Level level) {
		rows = level.getRows();
		cols = level.getCol();
		cl = new Tile[rows][cols];

		// Here all the static types of blocks must be added
		// Deathly block must still be defined, as well as a death function
		for (int r = 0; r < level.getRows(); r++) {
			for (int c = 0; c < level.getCol(); c++) {
				if (level.level[r][c] == '#') {
					cl[r][c] = new Tile(2, c, r);
					cl[r][c].setSolid(true);
				} else if(level.level[r][c] == 'x'){
					cl[r][c] = new Tile(6, c, r);
					cl[r][c].setDeath(true);
					cl[r][c].setAnimated(true);					
				} else {
					int x = rand.nextInt(3) + 3;
					cl[r][c] = new Tile(x, c, r);
					cl[r][c].setSolid(false);
				}
			}
		}
	}

	public void draw(Graphics2D g, Point offset, Hero hero) {
		int posX = (int) (hero.getPosX() / TILE_SIZE);
		int posY = (int) (hero.getPosY() / TILE_SIZE);

		// for (int j = 0; j < rows; j++) {
		// for (int i = 0; i < cols; i++) {
		for (int j = posY - 15; j < posY + 15; j++) {
			for (int i = posX - 20; i < posX + 20; i++) {

				if (i >= 0 && i < cols && j >= 0 && j < rows) {
					Tile currentTile = cl[j][i];
					
					currentTile.update();

					g.drawImage(levelImg, (int) currentTile.getLeft()
							- offset.x, (int) currentTile.getTop() - offset.y,
							(int) currentTile.getRight() - offset.x,
							(int) currentTile.getBottom() - offset.y,
							TSHEET_SIZE * currentTile.index(), 0, TSHEET_SIZE
									* (currentTile.index() + 1) - 1,
							TSHEET_SIZE - 1, null);
				}
			}
		}
	}

}
