package m33.Comp08;

import java.awt.Color;
import java.awt.Graphics2D;

import m33.util.FontManager;

public class GeneratingScreen {
	private String item1 = "Generating";
	private String level = "";
	private final int WIDTH;
	private final int HEIGHT;
	
	public GeneratingScreen(int w, int h, int level){
		WIDTH = w;
		HEIGHT = h;
	}
	
	public void setLevel(int l){
		level = "" + l;
	}
	
	public void drawMenu(Graphics2D g, int l){
		level = "Level " + l;
		
		g.setFont(FontManager.visitorBig);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.GREEN);
		g.drawString(item1, 100, 50);
		
		g.drawString(level, 100, 100);
	}

}
