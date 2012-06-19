package m33.entities;

import m33.util.PressedKey;

public class Hero extends Entity {
	private final int GROUND = 0;
	private final int JUMPING = 1;
	private final int AIR = 2;
	private final int FALLING = 3;
	
	private int jumpState = FALLING;

	public Hero() {
		super();
		jumpState = FALLING;
	}

	public void move(PressedKey key, Map level, double delta){
		updateOldPos();
		
		if(key.isUp()){
			incPosY(-300*delta);
			if(level.collision(this)){
				moveBack();
			}
		}
		if(key.isDown()){
			incPosY(300*delta);
			if(level.collision(this)){
				moveBack();
			}
		}
		if(key.isRight()){
			incPosX(300*delta);
			if(level.collision(this)){
				moveBack();
			}
		}
		if(key.isLeft()){
			incPosX(-300*delta);
			if(level.collision(this)){
				moveBack();
			}
		}
		
	}
	
}
