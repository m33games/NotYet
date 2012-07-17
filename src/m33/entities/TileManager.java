package m33.entities;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import m33.util.Loader;

public class TileManager {
	private final int TILE_SIZE = 32;
	private final int TSHEET_SIZE = 16;

	public Tile[][] cl; // Current Level
	private Image levelImg;
	private Loader loader;
	private int rows, cols;

	public TileManager() {
		loader = new Loader();

		// Load level 1 tileSheet
		Toolkit tk = Toolkit.getDefaultToolkit();
		levelImg = tk.getImage(loader.getURL("level1b.png"));
	}

	public void newLevel(int r, int c) {
		rows = r;
		cols = c;
		cl = new Tile[r][c];
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

					g.drawImage(levelImg, (int) currentTile.getLeft()
							- offset.x, (int) currentTile.getTop() - offset.y,
							(int) currentTile.getRight() - offset.x,
							(int) currentTile.getBottom() - offset.y,
							TSHEET_SIZE * currentTile.index(), 0,
							TSHEET_SIZE * (currentTile.index() + 1) - 1, TSHEET_SIZE-1, null);
				}
			}
		}
	}

}
