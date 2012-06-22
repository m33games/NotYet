package m33.util;

public class PressedKey {
	private boolean isUp = false;
	private boolean isDown = false;
	private boolean isRight = false;
	private boolean isLeft = false;

	private boolean isEnter = false;
	private boolean isSpace = false;
	private boolean isEsc = false;
	
	private boolean wasRight = false;
	private boolean wasLeft = false;
	
	/*
	 * Constructor
	 */
	public PressedKey(){
		
	}

	// Accessor
	public boolean isUp() {
		return isUp;
	}

	public boolean isDown() {
		return isDown;
	}

	public boolean isRight() {
		return isRight;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public boolean isEnter() {
		return isEnter;
	}

	public boolean isSpace() {
		return isSpace;
	}

	public boolean isEsc() {
		return isEsc;
	}
	
	public boolean wasLeft(){
		return wasLeft;
	}
	
	public boolean wasRight(){
		return wasRight;
	}

	// Modifiers
	public void Up(boolean up) {
		isUp = up;
	}

	public void Down(boolean down) {
		isDown = down;
	}

	public void Right(boolean right) {
		isRight = right;
	}

	public void Left(boolean left) {
		isLeft = left;
	}

	public void Enter(boolean enter) {
		isEnter = enter;
	}

	public void Space(boolean space) {
		isSpace = space;
	}

	public void Esc(boolean esc) {
		isEsc = esc;
	}
	
	public void setWasLeft(boolean wl){
		wasLeft = wl;
	}
	
	public void setWasRight(boolean wr){
		wasRight = wr;
	}
}
