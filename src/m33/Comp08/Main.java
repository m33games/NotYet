package m33.Comp08;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import m33.entities.Camera;
import m33.entities.Death;
import m33.entities.EntityManager;
import m33.entities.Hero;
import m33.entities.Map;
import m33.util.FontManager;
import m33.util.KeyInput;

@SuppressWarnings("serial")
public class Main extends Applet implements Runnable {
	// CONSTANTS
	private final int SCREEN_W = 640;
	private final int SCREEN_H = 480;
	private final int FPS = 100;

	// Utilities
	private int framesPerSec = 0;
	public FontManager fontManager;

	// Double buffer and Thread
	private BufferedImage backBuffer;
	private Graphics2D g2d;
	private Thread gameLoop;

	// Useful or not?
	// private AffineTransform identity;
	private Random rand;

	// KeyListener
	KeyInput keyboard;

	private Hero hero;
	private Map level;
	private Camera camera;
	private MainMenu mainMenu;
	private Death death;
	private EntityManager entities;

	private GeneratingScreen generate;
	private int currentLevel = 1;

	// game state
	private boolean isRunning = false;
	private int gameState;
	private final int MAIN_MENU = 0;
	private final int PLAYING = 1;
	private final int PAUSE = 2;
	private final int GENERATE = 3;
	private final int GAMEOVER = 4;

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

		gameState = MAIN_MENU;
		mainMenu = new MainMenu(SCREEN_W, SCREEN_H);

		fontManager = new FontManager();
		
		currentLevel = 1;
		generate = new GeneratingScreen(SCREEN_W, SCREEN_H, currentLevel);
		//generate.setLevel(currentLevel);
		

		//entities = new EntityManager();
	}

	public void gameReset() {
		// should I create a reset method in entityManager?
		entities = new EntityManager();
		
		hero = new Hero();
		hero.load("hero.png");

		level = new Map(entities);
		hero.updateMap(level); // I also set spawn point here

		camera = new Camera();

		hero.setCamera(camera);
		level.setCamera(camera);
		entities.setCamera(camera);

		death = new Death();
		death.setCamera(camera);
		
	}

	public void update(Graphics g) {
		switch (gameState) {
		case MAIN_MENU:
			mainMenu.drawMenu(g2d);
			break;
		case GENERATE:
			generate.drawMenu(g2d, currentLevel);
			break;
		case PLAYING:
			camera.update(hero);

			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, SCREEN_W, SCREEN_H);

			level.drawMap(g2d, this, hero);
			entities.draw(g2d);
			
			//hero.drawBox(g2d, this);
			hero.draw(g2d, this);
			death.draw(g2d, this);

			g2d.setFont(new Font("Courier", Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			g2d.drawString("FPS: " + framesPerSec, 10, 460);
			break;
		}

		g.drawImage(backBuffer, 0, 0, this);
	}

	/*
	 * PAINT doesn't seem useful - do painting in Update() public void
	 * paint(Graphics g) { }
	 */

	public void start() {
		isRunning = true;

		gameLoop = new Thread(this);
		gameLoop.start();
	}

	public void run() {
		final int SLEEP_MIL = 1000 / FPS;
		final int SLEEP_NANO = 1000000000 / FPS;

		long lastFrame = System.nanoTime();
		int fps = 0;
		long lastFPSTime = 0;

		while (isRunning) {
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
			if (keyboard.keyState().isEsc()) {
				isRunning = false;
			}

			switch (gameState) {
			case MAIN_MENU:
				if (keyboard.keyState().isEnter()) {
					gameState = GENERATE;
				}
				break;
			case GENERATE:
				generate = new GeneratingScreen(SCREEN_W, SCREEN_H, currentLevel);
				long temp = System.currentTimeMillis();
				
				//generate.setLevel(currentLevel);
				System.out.println("current level "+ currentLevel);
				gameReset();
				
				while(System.currentTimeMillis() - temp < 1000){}
				
				delta = 0.0;
				if (level.isLoaded()) {
					gameState = PLAYING;
				}
				break;
			case PLAYING:
				if(delta > 0.2){
					delta = 0.2;
				}
				gameUpdate(delta);
				
				
				break;
			}

			repaint();

			try {
				// if (elapsed < SLEEP_NANO) {
				if ((System.nanoTime() - lastFrame) < SLEEP_NANO) {
					Thread.sleep(SLEEP_MIL - (System.nanoTime() - lastFrame)
							/ 1000000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	public void gameUpdate(double delta) {
		//System.out.println("delta is " +delta);
		entities.update(delta, hero);
		
		hero.move(keyboard.keyState(), delta);
		death.move(delta); // DEATH DISABLED

		if (death.heroReached(hero)) {
			gameState = MAIN_MENU;
		}
		if (hero.isDead()){
			currentLevel = 1;
			gameState = MAIN_MENU;
		}
		// If the level is finished, generate the next one
		if (hero.getPosX() > level.getEnd()){
			currentLevel++;
			gameState = GENERATE;
		}
		
	}

}
