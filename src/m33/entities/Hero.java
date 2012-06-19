package m33.entities;

import m33.util.PressedKey;

public class Hero extends Entity {
	private final int GROUND = 0;
	private final int JUMPING = 1;
	private final int AIR = 2;
	private final int FALLING = 3;

	private final int ACC_GRAVITY = 50;
	private final int ACC_X = 50;
	private final int MAX_VEL_Y = 400;
	private final int MAX_VEL_X = 400;

	private int jumpState = FALLING;
	private long startJumpTime;

	private double delta;
	private Map currentLevel;

	public Hero() {
		super();
		jumpState = FALLING;
	}

	public void updateMap(Map level) {
		currentLevel = level;
	}

	public void move(PressedKey key, double delta) {
		updateOldPos();
		this.delta = delta;

		switch (jumpState) {
		case GROUND:
			groundMove(key);
			break;
		case JUMPING:
			jumpingMove(key);
			break;
		case AIR:
			airMove(key);
			break;
		case FALLING:
			fallingMove(key);
			break;
		}
	}

	public void groundMove(PressedKey key) {
		if (key.isUp()) {
			startJumpTime = System.currentTimeMillis();
			jumpState = JUMPING;
			setVelY(-400);
			incPosY(getVelY()*delta);
		}
		if (key.isDown()) {

		}
		if (key.isRight()) {
			if (getVelX() < MAX_VEL_X) {
				incVelX(ACC_X);
			} else if (getVelX() > MAX_VEL_X) {
				setVelX(MAX_VEL_X);
			}
		}

		if (key.isLeft()) {
			if (getVelX() > -MAX_VEL_X) {
				incVelX(-ACC_X);
			} else if (getVelX() < -MAX_VEL_X) {
				setVelX(-MAX_VEL_X);
			}
		}
		if (!key.isRight() && !key.isLeft()) {
			if (getVelX() > 0) {
				incVelX(-ACC_X);
			} else if (getVelX() < 0) {
				incVelX(ACC_X);
			}
		}

		incPosX(getVelX() * delta);
	}

	public void jumpingMove(PressedKey key) {
		incPosY(getVelY()*delta);
		if((System.currentTimeMillis() - startJumpTime) > 100){
			jumpState = FALLING;
		}
	}

	public void airMove(PressedKey key) {

	}

	public void fallingMove(PressedKey key) {
		if (getVelY() < MAX_VEL_Y) {
			incVelY(ACC_GRAVITY);
		} else if (getVelY() > MAX_VEL_Y) {
			setVelY(MAX_VEL_Y);
		}

		incPosY(getVelY() * delta);

		if (currentLevel.bottomCollision(this)) {
			jumpState = GROUND;
		}
	}
}
