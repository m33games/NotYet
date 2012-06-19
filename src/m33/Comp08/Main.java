package m33.Comp08;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import m33.entities.Hero;
import m33.entities.Map;
import m33.util.KeyInput;

public class Main extends Applet implements Runnable {
	// CONSTANTS
		private final int SCREEN_W = 640;
		private final int SCREEN_H = 480;
		private final int FPS = 100;

		// Utilities
		private int framesPerSec = 0;

		// Double buffer and Thread
		private BufferedImage backBuffer;
		private Graphics2D g2d;
		private Thread gameLoop;

		// Useful or not?
		private AffineTransform identity;
		private Random rand;

		// KeyListener
		KeyInput keyboard;

		private Hero hero;
		private Map level;

		/**
		 * MAIN
		 */
		public void init() {
			// create double buffer
			backBuffer = new BufferedImage(SCREEN_W, SCREEN_H,
					BufferedImage.TYPE_INT_RGB);
			g2d = backBuffer.createGraphics();

			// Tools
			keyboard = new KeyInput();
			addKeyListener(keyboard);

			framesPerSec = 0;

			hero = new Hero();
			hero.load("hero.png");
			
			level = new Map();
			hero.updateMap(level);
		}

		public void update(Graphics g) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, SCREEN_W, SCREEN_H);

			g2d.setColor(Color.WHITE);
			g2d.drawString("FPS: " + framesPerSec, 10, 460);

			level.drawMap(g2d, this);
			hero.draw(g2d, this);

			g.drawImage(backBuffer, 0, 0, this);
		}

		/*
		 * PAINT doesn't seem useful - do painting in Update() public void
		 * paint(Graphics g) { }
		 */

		public void start() {
			gameLoop = new Thread(this);
			gameLoop.start();
		}

		public void run() {
			Thread t = Thread.currentThread();
			final int SLEEP_MIL = 1000 / FPS;
			final int SLEEP_NANO = 1000000000 / FPS;

			long lastFrame = System.nanoTime();
			int fps = 0;
			long lastFPSTime = 0;

			while (t == gameLoop) {
				long elapsed = System.nanoTime() - lastFrame;

				lastFrame = System.nanoTime();

				double delta = elapsed / 1000000000d;

				lastFPSTime += elapsed;
				fps++;

				// Count FPS
				if (lastFPSTime >= 1000000000) {
					lastFPSTime = 0;
					framesPerSec = fps;
					fps = 0;
				}

				// Loop Core
				gameUpdate(delta);
				repaint();

				try {
					if (elapsed < SLEEP_NANO) {
						Thread.sleep(SLEEP_MIL - (System.nanoTime() - lastFrame)
								/ 1000000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		public void gameUpdate(double delta) {
			hero.move(keyboard.keyState(), delta);
		}
		
}
