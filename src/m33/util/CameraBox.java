package m33.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import m33.entities.Camera;
import m33.entities.Hero;

public class CameraBox {

	private double width = 100;
	private double height = 150;
	private Point2D pos;
	
	public CameraBox(){
		pos = new Point2D.Double();
		setPos(0.0,0.0);
	}
	
	public void setPos(double x, double y){
		pos.setLocation(x,	y);
	}
	
	public double getX(){
		return pos.getX();
	}
	
	public double getY(){
		return pos.getY();
	}
	
	public void update(Hero hero){
		if(hero.getPosX() < pos.getX()){
			setPos(hero.getPosX(), pos.getY());
		} else if (hero.getPosX() + hero.getWidth() > pos.getX() + width){
			setPos(hero.getPosX() - (width- hero.getWidth()), pos.getY());
		}
		
		if(hero.getPosY() < pos.getY()){
			setPos(pos.getX(), hero.getPosY());
		} else if (hero.getPosY() + hero.getHeight() > pos.getY() + height){
			setPos(pos.getX(), hero.getPosY() - (height - hero.getHeight()));
		}
	}
	
	public void draw(Graphics2D g, Camera camera){
		int localX = (int) (pos.getX() - camera.getXOff());
		int localY = (int) (pos.getY() - camera.getYOff());
		
		g.setColor(Color.PINK);
		g.drawRect(localX, localY, (int) width, (int) height);
	}
}
