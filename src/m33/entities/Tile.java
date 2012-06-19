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
	
	private boolean checked;
	private boolean solid;

	public Tile(String s, int x, int y) {
		loader = new Loader();
		Toolkit tk = Toolkit.getDefaultToolkit();
		tileImg = tk.getImage(loader.getURL(s));
		
		checked = false;
		solid = false;
		
		pos = new Point(x*SIZE, y*SIZE);
		
		hitBox = new Rectangle2D.Double(pos.getX(), pos.getY(), (double) SIZE, (double) SIZE);
	}
	
	public void setSolid(boolean s){
		solid = s;
	}

	public void draw(Graphics2D g, Applet a) {
		g.drawImage(tileImg, pos.x, pos.y, a);
		if(checked){
			g.setColor(Color.white);
			g.drawRect(pos.x, pos.y, SIZE, SIZE);
			checked = false;
		}
	}
	
	// ACCESSORS
	public Rectangle2D getHitBox(){
		return hitBox;
	}
	
	public void checked(){
		checked = true;
	}
	
	public boolean isSolid(){
		return solid;
	}

}
