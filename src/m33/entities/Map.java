package m33.entities;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;

import javax.imageio.ImageIO;

import m33.Comp08.Level;
import m33.util.*;

public class Map {

	private Level level1;
	private Image levelImg;
	private Loader loader;
	private Camera camera;
	private Hero heroPointer;

	private boolean loaded = false;

	private final int TILE_SIZE = 32;
	private TileManager tm;

	private Random rand;

	// private Tile[][] currentLevel;

	public Map() {

		rand = new Random();

		loader = new Loader();
		tm = new TileManager();
		loadLevel();

		// Load level 1 tileSheet
		// Toolkit tk = Toolkit.getDefaultToolkit();
		// levelImg = tk.getImage(loader.getURL("level1.png"));
	}

	public void loadLevel() {
		level1 = new Level();
		loaded = true;

		double milli = System.currentTimeMillis();

		tm.newLevel(level1);

		// for (int r = 0; r < level1.getRows(); r++) {
		// for (int c = 0; c < level1.getCol(); c++) {
		// if (level1.level[r][c] == '#') {
		// //currentLevel[r][c] = new Tile("block1.png", c, r);
		// tm.cl[r][c] = new Tile(2, c, r);
		// tm.cl[r][c].setSolid(true);
		// } else {
		// int x = rand.nextInt(3)+3;
		// tm.cl[r][c] = new Tile(x, c, r);
		// tm.cl[r][c].setSolid(false);
		//
		// //currentLevel[r][c] = new Tile("block2.png", c, r);
		// //tm.cl[r][c] = new Tile();
		// }
		// }
		// }

		System.out.println("a " + (System.currentTimeMillis() - milli));
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public boolean horizontalCollision(Hero hero) {
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroTop = (int) hero.getPosY() / TILE_SIZE;
		int heroBottom = (int) (hero.getPosY() + hero.getHeight() - 1)
				/ TILE_SIZE;

		int c;
		int r;

		// RIGHT col
		c = heroRight;

		for (r = heroTop; r <= heroBottom; r++) {
			tm.cl[r][c].checked();
			if (tm.cl[r][c] != null && tm.cl[r][c].isDeath()) {
				hero.dies();
			} else if (tm.cl[r][c] != null && tm.cl[r][c].isSolid()) {
				hero.setPosX((double) (c * TILE_SIZE) - hero.getWidth());
				return true;
			}
		}

		// LEFT col
		c = heroLeft;

		for (r = heroTop; r <= heroBottom; r++) {
			tm.cl[r][c].checked();
			if (tm.cl[r][c] != null && tm.cl[r][c].isSolid()) {
				hero.setPosX((double) (c * TILE_SIZE) + hero.getWidth());
				return true;
			}
		}

		return false;
	}

	public boolean topCollision(Hero hero) {
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroTop = (int) hero.getPosY() / TILE_SIZE;

		int c;
		int r;

		// TOP collision
		r = heroTop;

		for (c = heroLeft; c <= heroRight; c++) {
			tm.cl[r][c].checked();
			if (tm.cl[r][c] != null && tm.cl[r][c].isDeath()) {
				hero.dies();
			} else if (tm.cl[r][c] != null && tm.cl[r][c].isSolid()) {
				hero.setPosY((double) (r * TILE_SIZE) + hero.getHeight());
				return true;
			}
		}
		return false;
	}

	public boolean bottomCollision(Hero hero) {
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroTop = (int) hero.getPosY() / TILE_SIZE;
		int heroBottom = (int) (hero.getPosY() + hero.getHeight() - 1)
				/ TILE_SIZE;

		int c;
		int r;

		// BOTTOM collision
		r = heroBottom;

		if ((tm.cl[r][heroLeft] != null && tm.cl[r][heroLeft].isDeath())
				|| (tm.cl[r][heroRight] != null && tm.cl[r][heroRight]
						.isDeath())) {
			hero.dies();
		} else {

			for (c = heroLeft; c <= heroRight; c++) {
				tm.cl[r][c].checked();
				if (tm.cl[r][c] != null && tm.cl[r][c].isSolid()) {
					hero.setPosY((double) (r * TILE_SIZE) - hero.getHeight());
					return true;
				}
			}
		}

		return false;
	}

	public boolean hole(Hero hero) {
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroUnderFeet = (int) (hero.getPosY() + hero.getHeight())
				/ TILE_SIZE;

		int c;
		int r = heroUnderFeet;

		boolean ground = true;

		for (c = heroLeft; c <= heroRight; c++) {
			ground = ground && tm.cl[r][c].isSolid();
		}

		return !ground;
	}

	public void drawMap(Graphics2D g, Applet a, Hero hero) {
		Point offset = new Point((int) camera.getXOff(), (int) camera.getYOff());

		tm.draw(g, offset, hero);

		// for (int i = 0; i < level1.getCol(); i++) {
		// for (int j = 0; j < level1.getRows(); j++) {
		// currentLevel[j][i].draw(g, a, offset);
		// }
		// }
	}

	public boolean isLoaded() {
		return loaded;
	}
}
