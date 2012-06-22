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

	public boolean bottomCollision(Hero hero) {
		int heroR = (int) hero.getPosY() / TILE_SIZE;
		int heroC = (int) hero.getPosX() / TILE_SIZE;
		int dC = (int) hero.getWidth() / TILE_SIZE;
		int dR = (int) hero.getHeight() / TILE_SIZE;

		int r = heroR + dR;

		for (int c = heroC; c <= heroC + dC; c++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
				if (currentLevel[r][c].getHitBox().contains(hero.getPosX(),
						hero.getPosY() + hero.getHeight())
						|| currentLevel[r][c].getHitBox().contains(
								hero.getPosX() + hero.getWidth(),
								hero.getPosY() + hero.getHeight())) {
					hero.setPosY((double) (r * TILE_SIZE) - hero.getHeight());

					return true;
				}
			}
		}
		return false;
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
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {

				if (r == heroTop) {
					// check if it is closer to bottom or left border of the
					// tile. it is horizontal collision only if it is closer to
					// the left side
					if (hero.getPosX() - currentLevel[r][c].getLeft() < currentLevel[r][c]
							.getBottom() - hero.getPosY()) {
						hero.setPosX((double) (c * TILE_SIZE) - hero.getWidth());
						return true;
					}
				} else if (r == heroBottom) {
					if (hero.getPosX() - currentLevel[r][c].getLeft() < hero
							.getPosY()
							+ hero.getHeight()
							- currentLevel[r][c].getTop()) {
						hero.setPosX((double) (c * TILE_SIZE) - hero.getWidth());
						return true;
					}
				}
			}
		}

		// LEFT col
		c = heroLeft;

		for (r = heroTop; r <= heroBottom; r++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
				if (r == heroTop) {
					if (currentLevel[r][c].getRight() - hero.getPosX() < currentLevel[r][c]
							.getBottom() - hero.getPosY()) {
						hero.setPosX((double) (c * TILE_SIZE) + hero.getWidth());
						return true;
					}
				} else if (r == heroBottom) {
					if (currentLevel[r][c].getRight() - hero.getPosX() < hero
							.getPosY()
							+ hero.getHeight()
							- currentLevel[r][c].getTop()) {
						hero.setPosX((double) (c * TILE_SIZE) + hero.getWidth());
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean verticalCollision(Hero hero) {
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroTop = (int) hero.getPosY() / TILE_SIZE;
		int heroBottom = (int) (hero.getPosY() + hero.getHeight() - 1)
				/ TILE_SIZE;

		int c;
		int r;

		// TOP collision
		r = heroTop;

		for (c = heroLeft; c <= heroRight; c++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
				if( c == heroLeft ){
					if( currentLevel[r][c].getBottom() - hero.getPosY() < 
					currentLevel[r][c].getRight() - hero.getPosX()){
						hero.setPosY((double) (r * TILE_SIZE) + hero.getHeight());
						return true;
					}
				} else if (c == heroRight){
					if(currentLevel[r][c].getBottom() - hero.getPosY() <
					hero.getPosX() + hero.getWidth() - currentLevel[r][c].getLeft() ){
						hero.setPosY((double) (r * TILE_SIZE) + hero.getHeight());
						return true;
					}
				}
			}
		}

		// BOTTOM collision
		r = heroBottom;

		for (c = heroLeft; c <= heroRight; c++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
				if( c == heroLeft ){
					if( hero.getPosY() + hero.getHeight() - currentLevel[r][c].getTop() < 
					currentLevel[r][c].getRight() - hero.getPosX()){
						hero.setPosY((double) (r * TILE_SIZE) - hero.getHeight());
						return true;
					}
				} else if (c == heroRight){
					if( hero.getPosY() + hero.getHeight() - currentLevel[r][c].getTop() <
					hero.getPosX() + hero.getWidth() - currentLevel[r][c].getLeft() ){
						hero.setPosY((double) (r * TILE_SIZE) - hero.getHeight());
						return true;
					}
				}				
			}
		}

		return false;
	}
	
	public boolean hole(Hero hero){
		int heroLeft = (int) hero.getPosX() / TILE_SIZE;
		int heroRight = (int) (hero.getPosX() + hero.getWidth() - 1)
				/ TILE_SIZE;
		int heroUnderFeet = (int) (hero.getPosY() + hero.getHeight() )
				/ TILE_SIZE;

		int c;
		int r = heroUnderFeet;
		
		boolean ground = true;
		
		for (c = heroLeft; c <= heroRight; c++){
			ground = ground && currentLevel[r][c].isSolid();
		}
		
		return !ground;
	}

	public void drawMap(Graphics2D g, Applet a) {
		for (int i = 0; i < level1.getCol(); i++) {
			for (int j = 0; j < level1.getRows(); j++) {
				currentLevel[j][i].draw(g, a);
			}
		}
	}
}
