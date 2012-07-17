package m33.entities;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import m33.util.CameraBox;
import m33.util.PressedKey;

public class Hero extends Entity {
	private final int TILE_SIZE = 32;
	private final int TSHEET_SIZE = 16;

	private final int GROUND = 0;
	private final int JUMPING = 1;
	private final int AIR = 2;
	private final int FALLING = 3;

	private final int ACC_GRAVITY = 20;
	private final int ACC_X = 40;
	private final int MAX_VEL_Y = 400;
	private final int MAX_VEL_X = 400;
	private final int AIR_ACC_X = 20;
	
	private final int ANIM_RATE = 1000/10;
	private double lastAnim;
	private int animIndex = 0;
	private final int ANIM_FRAME_NUM = 3;

	private int jumpState = FALLING;
	private long startJumpTime;

	private double delta;
	private Map currentLevel;

	public CameraBox cameraBox;
	
	private boolean dead = false;

	public Hero() {
		super();
		setPosX(100.0);
		setPosY(TILE_SIZE*985);
		jumpState = FALLING;

		load("hero.png");
		lastAnim = System.currentTimeMillis();

		cameraBox = new CameraBox();
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
		// case AIR:
		// airMove(key);
		// break;
		case FALLING:
			fallingMove(key);
			break;
		default:
			jumpState = FALLING;
			break;
		}

		cameraBox.update(this);
		animate();
	}

	public void groundMove(PressedKey key) {

		if (currentLevel.hole(this)) {
			jumpState = FALLING;
		}

		if (key.isUp()) {
			startJumpTime = System.currentTimeMillis();
			jumpState = JUMPING;
			setVelY(-400);
			incPosY(getVelY() * delta);

			if (currentLevel.topCollision(this)) {
				jumpState = FALLING;
				setVelY(0);
			}
		}
		if (key.isDown()) {
			// TODO: what?
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
			if (getVelX() > 20) {
				incVelX(-ACC_X);
			} else if (getVelX() < -20) {
				incVelX(ACC_X);
			} else {
				setVelX(0.0);
			}
		}

		// Move hor and check hor collision
		incPosX(getVelX() * delta);
		currentLevel.horizontalCollision(this);

	}

	public void jumpingMove(PressedKey key) {
		if (!key.isUp()) {
			jumpState = FALLING;
		} else if ((System.currentTimeMillis() - startJumpTime) > 130) {
			jumpState = FALLING;
		}

		incPosY(getVelY() * delta);

		if (currentLevel.topCollision(this)) {
			setVelY(0.0);
			jumpState = FALLING;
		}
		currentLevel.bottomCollision(this);

		airHorMove(key);
		currentLevel.horizontalCollision(this);

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
			// Error...if it hits the ceiling it is put to ground state
		}

		currentLevel.topCollision(this);

		airHorMove(key);
		currentLevel.horizontalCollision(this);
	}

	public void airHorMove(PressedKey key) {
		if (key.isRight()) {
			if (getVelX() < MAX_VEL_X) {
				incVelX(AIR_ACC_X);
			} else if (getVelX() > MAX_VEL_X) {
				setVelX(MAX_VEL_X);
			}
		}

		if (key.isLeft()) {
			if (getVelX() > -MAX_VEL_X) {
				incVelX(-AIR_ACC_X);
			} else if (getVelX() < -MAX_VEL_X) {
				setVelX(-MAX_VEL_X);
			}
		}
		if (!key.isRight() && !key.isLeft()) {
			if (getVelX() > 0) {
				incVelX(-AIR_ACC_X);
			} else if (getVelX() < 0) {
				incVelX(AIR_ACC_X);
			}
		}

		incPosX(getVelX() * delta);
	}

	public void animate() {
		if(System.currentTimeMillis() - lastAnim > ANIM_RATE){
			animIndex = (animIndex + 1)% ANIM_FRAME_NUM;
			lastAnim = System.currentTimeMillis();
		}
	}
	
	public void dies(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}

	public void what() {
		System.out.println("hero is at " + getPosX());
	}

	public void draw(Graphics2D g, Applet a) {
		int localX = (int) (getPosX() - camera.getXOff());
		int localY = (int) (getPosY() - camera.getYOff());

		g.drawImage(image, localX, localY, localX + TILE_SIZE, localY
				+ TILE_SIZE, TSHEET_SIZE*animIndex, 0, (TSHEET_SIZE*(animIndex +1))-1, TSHEET_SIZE-1, a);
	}

	public void drawBox(Graphics2D g, Applet a) {
		int localX = (int) (cameraBox.getX() - camera.getXOff());
		int localY = (int) (cameraBox.getY() - camera.getYOff());

		cameraBox.draw(g, camera);
	}
}
