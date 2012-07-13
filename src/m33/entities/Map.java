package m33.entities;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

import m33.Comp08.Level;
import m33.util.*;

public class Map {

	private Level level1;
	private Image levelImg;
	private Loader loader;
	private Camera camera;
	
	private boolean loaded = false;

	private final int TILE_SIZE = 32;

	private Tile[][] currentLevel;

	public Map() {
		loader = new Loader();
		loadLevel();
		
		// Load level 1 tileSheet
		Toolkit tk = Toolkit.getDefaultToolkit();
		levelImg = tk.getImage(loader.getURL("level1.png"));
	}

	public void loadLevel() {
		level1 = new Level();
		loaded = true;

		double milli = System.currentTimeMillis();
		// the generation of all the tiles it too slow, it requires too much time...
		// I should generate them while playing if possible
		
		currentLevel = new Tile[level1.getRows()][level1.getCol()];
		for (int r = 0; r < level1.getRows(); r++) {
			for (int c = 0; c < level1.getCol(); c++) {
				if (level1.level[r][c] == '#') {
					//currentLevel[r][c] = new Tile("block1.png", c, r);
					currentLevel[r][c] = new Tile("level.png", c, r);
					currentLevel[r][c].setSolid(true);
				} else {
					
					
					//currentLevel[r][c] = new Tile("block2.png", c, r);
					currentLevel[r][c] = new Tile();
				}
			}
		}

		System.out.println("a " + (System.currentTimeMillis() - milli));
	}
	
	public void setCamera(Camera camera){
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
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
						hero.setPosX((double) (c * TILE_SIZE) - hero.getWidth());
						return true;
			}
		}

		// LEFT col
		c = heroLeft;

		for (r = heroTop; r <= heroBottom; r++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
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
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
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

		for (c = heroLeft; c <= heroRight; c++) {
			currentLevel[r][c].checked();
			if (currentLevel[r][c] != null && currentLevel[r][c].isSolid()) {
						hero.setPosY((double) (r * TILE_SIZE) - hero.getHeight());
						return true;				
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
		Point offset = new Point((int) camera.getXOff(), (int) camera.getYOff());
		
		for (int i = 0; i < level1.getCol(); i++) {
			for (int j = 0; j < level1.getRows(); j++) {
				currentLevel[j][i].draw(g, a, offset);
			}
		}
	}
	
	public boolean isLoaded(){
		return loaded;
	}
}
