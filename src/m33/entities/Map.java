package m33.entities;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import m33.Comp08.Level;
import m33.util.*;

public class Map {

	private Level level1;
	private Image levelImg;
	private Loader loader;

	private final int TILE_SIZE = 32;

	private Tile[][] currentLevel;

	public Map() {
		loader = new Loader();
		loadLevel();
	}

	public void loadLevel() {
		level1 = new Level();

		currentLevel = new Tile[level1.getRows()][level1.getCol()];
		for (int r = 0; r < level1.getRows(); r++) {
			for (int c = 0; c < level1.getCol(); c++) {
				if (level1.level1[r][c] == 1) {
					currentLevel[r][c] = new Tile("block1.png", c, r);
					currentLevel[r][c].setSolid(true);
				} else {
					currentLevel[r][c] = new Tile("block2.png", c, r);
				}
			}
		}
	}

	public boolean collision(Hero hero) {
		int heroR = (int) hero.getPosY() / TILE_SIZE;
		int heroC = (int) hero.getPosX() / TILE_SIZE;
		int dC = (int) hero.getWidth() / TILE_SIZE;
		int dR = (int) hero.getHeight() / TILE_SIZE;

		for (int c = heroC; c <= heroC + dC; c++) {
			for (int r = heroR; r <= heroR + dR; r++) {
				currentLevel[r][c].checked();
				if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
					if (currentLevel[r][c].getHitBox().contains(hero.getPosX(),
							hero.getPosY())
							|| currentLevel[r][c].getHitBox().contains(
									hero.getPosX() + hero.getWidth(),
									hero.getPosY())
							|| currentLevel[r][c].getHitBox().contains(
									hero.getPosX(),
									hero.getPosY() + hero.getHeight())
							|| currentLevel[r][c].getHitBox().contains(
									hero.getPosX() + hero.getWidth(),
									hero.getPosY() + hero.getHeight())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void drawMap(Graphics2D g, Applet a) {
		for (int i = 0; i < level1.getCol(); i++) {
			for (int j = 0; j < level1.getRows(); j++) {
				currentLevel[j][i].draw(g, a);
			}
		}
	}
}
