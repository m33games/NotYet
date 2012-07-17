package m33.entities;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import m33.util.Loader;

public class Tile {
	private final int SIZE = 32;
	private Image tileImg;
	private Loader loader;

	private Point pos;
	private Rectangle2D hitBox;
	private int index;

	private boolean checked = false;
	private boolean solid = false;
	private boolean visible = false;
	private boolean death = false;
	private boolean animated = false;

	public Tile(int index, int x, int y) {
		// I can't load them each time, it takes too long
		
		//loader = new Loader();
		//Toolkit tk = Toolkit.getDefaultToolkit();
		//tileImg = tk.getImage(loader.getURL("level1.png"));
		
		//tileImg = levelImg;
		this.index = index;

		//checked = false;
		//solid = false;

		pos = new Point(x * SIZE, y * SIZE);

		//hitBox = new Rectangle2D.Double(pos.getX(), pos.getY(), (double) SIZE,
		//		(double) SIZE);
		
		visible = true;
	}

	public Tile() {
		visible = false;
	}

	public void setSolid(boolean s) {
		solid = s;
	}
	
	public void setDeath(boolean d){
		death = d;
	}

	public void draw(Graphics2D g, Applet a, Point offset) {
		
		if (animated) {
			//checks if it needs to be animated
		}

		if (visible) {
			//g.drawImage(tileImg, pos.x - offset.x, pos.y - offset.y, a);
			g.drawImage(tileImg, pos.x - offset.x, pos.y - offset.y, pos.x - offset.x + SIZE, pos.y - offset.y + SIZE, 32*2, 0, 32*(2+1) -1, 31, a);
			//g.fillRect(pos.x, pos.y, SIZE, SIZE);
			if (checked) {
				g.setColor(Color.white);
				g.drawRect(pos.x - offset.x, pos.y - offset.y, SIZE, SIZE);
				checked = false;
			}
		}
	}

	// ACCESSORS
	public Rectangle2D getHitBox() {
		return hitBox;
	}

	public void checked() {
		checked = true;
	}

	public boolean isSolid() {
		return solid;
	}

	public double getTop() {
		return pos.getY();
	}

	public double getBottom() {
		return pos.getY() + SIZE;
	}

	public double getLeft() {
		return pos.getX();
	}

	public double getRight() {
		return pos.getX() + SIZE;
	}
	
	public int index(){
		return index;
	}
	
	public boolean isDeath(){
		return death;
	}

}
