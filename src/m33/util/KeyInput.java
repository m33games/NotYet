package m33.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput extends Object implements KeyListener {
	private PressedKey keyboard;

	public KeyInput() {
		keyboard = new PressedKey();
	}

	@Override
	public void keyPressed(KeyEvent k) {
		int keyCode = k.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			keyboard.Up(true);
			break;
		case KeyEvent.VK_DOWN:
			keyboard.Down(true);
			break;
		case KeyEvent.VK_LEFT:
			keyboard.Left(true);
			if (keyboard.isRight()) {
				keyboard.Right(false);
				keyboard.setWasRight(true);
			}
			break;
		case KeyEvent.VK_RIGHT:
			keyboard.Right(true);
			if (keyboard.isLeft()) {
				keyboard.Left(false);
				keyboard.setWasLeft(true);
			}
			break;
		case KeyEvent.VK_ENTER:
			keyboard.Enter(true);
			break;
		case KeyEvent.VK_SPACE:
			keyboard.Space(true);
			break;
		case KeyEvent.VK_ESCAPE:
			keyboard.Esc(true);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		int keyCode = k.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			keyboard.Up(false);
			break;
		case KeyEvent.VK_DOWN:
			keyboard.Down(false);
			break;
		case KeyEvent.VK_LEFT:
			keyboard.Left(false);
			keyboard.setWasLeft(false);
			if(keyboard.wasRight()){
				keyboard.Right(true);
				keyboard.setWasRight(false);
			}
			break;
		case KeyEvent.VK_RIGHT:
			keyboard.Right(false);
			keyboard.setWasRight(false);
			if(keyboard.wasLeft()){
				keyboard.Left(true);
				keyboard.setWasLeft(false);
			}
			break;
		case KeyEvent.VK_ENTER:
			keyboard.Enter(false);
			break;
		case KeyEvent.VK_SPACE:
			keyboard.Space(false);
			break;
		case KeyEvent.VK_ESCAPE:
			keyboard.Esc(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub

	}

	public PressedKey keyState() {
		return keyboard;
	}

}
