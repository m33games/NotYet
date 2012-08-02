package m33.entities;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import m33.util.*;

public class Entity extends BasicEntity {
	// Constants
	
	private int numW = 1;
	private int numH = 1;

	private boolean debug;

	// Constructor
	public Entity() {
		super();

		debug = true;
	}

	// Accessors

	// /////////////////////////////////////////

	// Modifiers
	public void setId(int i){
		id = i;
	}
	
	public void setNumWH(int w, int h){
		numW = w;
		numH = h;
		
		multipleTileHit(w, h);
	}
	
	public void multipleTileHit(int w, int h){
		hitSize.setLocation(w*getWidth(), h*getHeight());
	}
	
	// COLLISION //
	
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
				e.bottomColl(hitBox.getY(), this);
			} else { // left collision
				e.leftColl(hitBox.getX() + hitSize.getX());
			}
			return true;
		} else if (r.contains(eHit.getX() + e.getHitSize().getX(), eHit.getY() + e.getHitSize().getY() )) { 
			// it contains the bottom right corner, it can be top or right
			if(eHit.getY() + e.getHitSize().getY() - hitBox.getY() < eHit.getX() + e.getHitSize().getX() - hitBox.getX() ){ // bottom collision
				e.bottomColl(hitBox.getY(), this);
			} else { // right colllision
				e.rightColl(hitBox.getX());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hole(Entity e){
		if (e.getHitBox().getX() + e.getHitSize().getX() < getHitBox().getX() ||
				e.getHitBox().getX() > getHitBox().getX() + getHitSize().getY()){
			return true;
		} else {
			return false;
		}
	}
	
	public void topColl(double y){
		setPosY(y);
	}
	
	public void bottomColl(double y, Entity anchor){
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
		
		if(localX > 0 && localX < 650){
			
		for(int i = 0; i < numH; i++){
			for(int j = 0; j < numW; j++){
				localX = (int) ((pos.getX() + j*getWidth()) - c.getXOff());
				localY = (int) ((pos.getY() + i*getWidth())- c.getYOff());
				
				g.drawImage(img, localX, localY, localX+TILE_SIZE, localY+TILE_SIZE, 
						TSHEET_SIZE*(id%TSHEET_W), TSHEET_SIZE*(id/TSHEET_SIZE), TSHEET_SIZE*((id%TSHEET_W)+1)-1, TSHEET_SIZE*((id/TSHEET_W)+1)-1, null);
				}
			}
		}
		
		//g.drawImage(img, localX, localY, localX+TILE_SIZE, localY+TILE_SIZE, 
		//		TSHEET_SIZE*(id%TSHEET_W), TSHEET_SIZE*(id/TSHEET_SIZE), TSHEET_SIZE*((id%TSHEET_W)+1)-1, TSHEET_SIZE*((id/TSHEET_W)+1)-1, null);
		//}
	}

	public void moveBack() {
		pos.setLocation(oldPos.getX(), oldPos.getY());
	}

	public void updateOldPos() {
		oldPos.setLocation(pos.getX(), pos.getY());
	}
}
