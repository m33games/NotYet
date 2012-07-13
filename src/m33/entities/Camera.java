package m33.entities;

public class Camera {

	private double xOff, yOff;
	
	public Camera(){
		xOff = 0.0;
		yOff = 0.0;
	}
	
	public void update(Hero hero){
		xOff = hero.cameraBox.getX() - 200;
		yOff = hero.cameraBox.getY() - 150;
	}
	
	// ACCESSOR
	public double getXOff(){
		return xOff;
	}
	
	public double getYOff(){
		return yOff;
	}
	
}
