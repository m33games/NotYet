package m33.entities;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;

public class Death extends Entity {
	// TODO: it must use all Entity variables and methods
	// there are duplicates right now
	
	// private variables
	
	
	private final int TILE_SIZE = 32;
	
	// public variables
	
	public Death(){
		super();
		
		setVelX(70.0);
	}
	
	public boolean heroReached(Hero hero){
		if(getPosX() >= hero.getPosX()){
			return true;
		} else {
			return false;
		}
	}
	
	public void draw(Graphics2D g, Applet a) {
		int localX = (int) (getPosX() - getCamera().getXOff());
		//int localY = (int) (getPosY() - getCamera().getYOff());
		
		g.setColor(Color.RED);
		g.fillRect(localX, 0, 5, 480);
		
	}
	
	public void move(double delta){
		incPosX(getVelX()*delta);
	}
	
	public void what(){
		System.out.println("death is at " + getPosX());
	}
}
