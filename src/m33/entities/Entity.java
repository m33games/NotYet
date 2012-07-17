package m33.entities;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import m33.util.*;

public class Entity {
	// Constants
	private final int WIDTH = 32;
	private final int HEIGHT = 32;
	
	private final int TILE_SIZE = 32;

	private Point2D pos;
	private Point2D oldPos;
	private Point2D vel;

	private Rectangle2D hitBox;

	protected Image image;
	private Loader loader;
	
	protected Camera camera;

	private boolean debug;

	// Constructor
	public Entity() {
		loader = new Loader();
		pos = new Point2D.Double(0.0, 0.0);
		oldPos = new Point2D.Double(0.0, 0.0);
		vel = new Point2D.Double(0.0, 0.0);

		hitBox = new Rectangle2D.Double(pos.getX(), pos.getY(), (double) WIDTH,
				(double) HEIGHT);

		debug = true;
	}

	// Accessors
	public Camera getCamera(){
		return camera;
	}
	
	public Rectangle2D getHitBox() {
		return hitBox;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public double getPosX() {
		return pos.getX();
	}

	public double getPosY() {
		return pos.getY();
	}

	public double getOldPosX() {
		return oldPos.getX();
	}

	public double getOldPosY() {
		return oldPos.getY();
	}

	public double getVelX() {
		return vel.getX();
	}

	public double getVelY() {
		return vel.getY();
	}

	public Point2D getPos() {
		return pos;
	}

	public Point2D getVel() {
		return vel;
	}

	// /////////////////////////////////////////

	// Modifiers
	public void setPosX(double x) {
		pos.setLocation(x, pos.getY());
	}

	public void setPosY(double y) {
		pos.setLocation(pos.getX(), y);
	}

	public void incPosX(double x) {
		pos.setLocation(pos.getX() + x, pos.getY());
	}

	public void incPosY(double y) {
		pos.setLocation(pos.getX(), pos.getY() + y);
	}

	public void setPos(double x, double y) {
		pos.setLocation(x, y);
	}

	public void setPos(Point2D p) {
		pos.setLocation(p);
	}

	public void incPos(double x, double y) {
		pos.setLocation(pos.getX() + x, pos.getY() + y);
	}

	public void setVelX(double velX) {
		vel.setLocation(velX, vel.getY());
	}

	public void setVelY(double velY) {
		vel.setLocation(vel.getX(), velY);
	}

	public void incVelX(double velX) {
		vel.setLocation(vel.getX() + velX, vel.getY());
	}

	public void incVelY(double velY) {
		vel.setLocation(vel.getX(), vel.getY() + velY);
	}

	public void setVel(double velX, double velY) {
		vel.setLocation(velX, velY);
	}

	public void setVel(Point2D p) {
		vel.setLocation(p);
	}

	public void incVel(double x, double y) {
		vel.setLocation(vel.getX() + x, vel.getY() + y);
	}
	
	public void setCamera(Camera camera){
		this.camera = camera;
	}

	// ////////////////////////////////////////////////
	public void load(String s) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(loader.getURL(s));
	}

	public void draw(Graphics2D g, Applet a) {
		int localX = (int) (pos.getX() - camera.getXOff());
		int localY = (int) (pos.getY() - camera.getYOff());
		
		g.drawImage(image, localX, localY, a);
		if (debug) {
			g.drawRect(localX, localY, WIDTH, HEIGHT);
		}
	}

	public void moveBack() {
		pos.setLocation(oldPos.getX(), oldPos.getY());
	}

	public void updateOldPos() {
		oldPos.setLocation(pos.getX(), pos.getY());
	}
}
