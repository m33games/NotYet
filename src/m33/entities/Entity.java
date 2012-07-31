package m33.entities;

import java.applet.Applet;
import java.awt.Color;
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
	private final int TSHEET_SIZE = 16;
	private final int TSHEET_W =10;
	
	private int id;

	private Point2D pos;
	private Point2D oldPos;
	private Point2D vel;

	private Point2D hitBox;
	private Point2D hitSize;
	private Point2D boxOff;

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

		hitBox = new Point2D.Double(pos.getX(), pos.getY());
		hitSize = new Point2D.Double((double) WIDTH, (double) (HEIGHT));
		boxOff = new Point2D.Double(0, 0);

		debug = true;
	}

	// Accessors
	public Camera getCamera(){
		return camera;
	}
	
	public Point2D getHitBox() {
		return hitBox;
	}
	
	public Point2D getHitSize(){
		return hitSize;
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
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void setPosY(double y) {
		pos.setLocation(pos.getX(), y);
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void incPosX(double x) {
		pos.setLocation(pos.getX() + x, pos.getY());
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void incPosY(double y) {
		pos.setLocation(pos.getX(), pos.getY() + y);
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void setPos(double x, double y) {
		pos.setLocation(x, y);
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void setPos(Point2D p) {
		pos.setLocation(p);
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
	}

	public void incPos(double x, double y) {
		pos.setLocation(pos.getX() + x, pos.getY() + y);
		hitBox.setLocation(pos.getX() + boxOff.getX(), pos.getY() + boxOff.getY());
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
	
	public void setId(int i){
		id = i;
	}
	
	public void move(){
		incPosX(getVelX());
		incPosY(getVelY());
	}
	
	public void setHitBox(double w, double h){
		double left = this.getPosX() + (this.getWidth()/2) - (w/2);
		double top = this.getPosY() + this.getHeight() - h;
		hitBox.setLocation(left, top);
		hitSize.setLocation(w, h);
	}
	
	public boolean collision(Entity e){
		Rectangle2D r = new Rectangle2D.Double(hitBox.getX(), hitBox.getY(), hitSize.getX(), hitSize.getY());
		Point2D eHit = e.getHitBox();
		
		if( r.contains(eHit.getX(), eHit.getY())){
			// it contains the top-left corner, it can be top or left collision
			if(hitBox.getY() + hitSize.getY() - eHit.getY() < hitBox.getX() + hitSize.getX() - eHit.getX()){ // top collision
				e.topColl(hitBox.getY() + hitSize.getY());
			} else { // left collision
				e.leftColl(hitBox.getX() + hitSize.getX());
			}
			return true;
		} else if (r.contains(eHit.getX() + e.getHitSize().getX(), eHit.getY())) { 
			// it contains the top right corner, it can be top or right
			if(hitBox.getY() + hitSize.getY() - eHit.getY() < eHit.getX() + e.getHitSize().getX() - hitBox.getX() ){ // top collision
				e.topColl(hitBox.getY() + hitSize.getY());
			} else { // right colllision
				e.rightColl(hitBox.getX());
			}
			return true;
		} if( r.contains(eHit.getX(), eHit.getY() + e.getHitSize().getY())){
			// it contains the bottom-left corner, it can be top or left collision
			if(eHit.getY() + e.getHitSize().getY() - hitBox.getY()  < hitBox.getX() + hitSize.getX() - eHit.getX()){ // top collision
				e.bottomColl(hitBox.getY());
			} else { // left collision
				e.leftColl(hitBox.getX() + hitSize.getX());
			}
			return true;
		} else if (r.contains(eHit.getX() + e.getHitSize().getX(), eHit.getY() + e.getHitSize().getY() )) { 
			// it contains the bottom right corner, it can be top or right
			if(eHit.getY() + e.getHitSize().getY() - hitBox.getY() < eHit.getX() + e.getHitSize().getX() - hitBox.getX() ){ // bottom collision
				e.bottomColl(hitBox.getY());
			} else { // right colllision
				e.rightColl(hitBox.getX());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void topColl(double y){
		setPosY(y);
	}
	
	public void bottomColl(double y){
		setPosY(y - HEIGHT);
	}
	
	public void leftColl(double x){
		setPosX(x);
	}
	
	public void rightColl(double x){
		setPosX(x - WIDTH);
	}

	// ////////////////////////////////////////////////
	public void load(String s) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(loader.getURL(s));
	}
	
	public void loadFromSheet(int x, int y){
		//I need to lead the image from the tileSheet
	}

	public void draw(Graphics2D g, Image img, Camera c) {
		int localX = (int) (pos.getX() - c.getXOff());
		int localY = (int) (pos.getY() - c.getYOff());
		
		//g.drawImage(image, localX, localY, a);
		//if (debug) {
		//	g.drawRect(localX, localY, WIDTH, HEIGHT);
		//}
		
		if(localX > 0 && localX < 650){
		//System.out.println("draw " + localX + " " + localY + " " + (localX + TILE_SIZE) + " " + (localY + TILE_SIZE));

		g.drawImage(img, localX, localY, localX+TILE_SIZE, localY+TILE_SIZE, 
				TSHEET_SIZE*(id%TSHEET_W), TSHEET_SIZE*(id/TSHEET_SIZE), TSHEET_SIZE*((id%TSHEET_W)+1)-1, TSHEET_SIZE*((id/TSHEET_W)+1)-1, null);
		}
	}

	public void moveBack() {
		pos.setLocation(oldPos.getX(), oldPos.getY());
	}

	public void updateOldPos() {
		oldPos.setLocation(pos.getX(), pos.getY());
	}
}
