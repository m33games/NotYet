package m33.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {
	public static Font visitorBig = null;
	public static Font visitorSmall = null;

	public FontManager() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// register all the needed font here
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					"data/font/visitor1.ttf")));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Uncomment to see list of available fonts
		/*
		 * for(String s: ge.getAvailableFontFamilyNames()){
		 * System.out.println(s); }
		 */

		// Initialize the font containers
		visitorBig = new Font("Visitor TT1 BRK", Font.BOLD, 30);
		visitorSmall = new Font("Visitor TT1 BRK", Font.PLAIN, 18);
	}

	/*
	 * drawString() - it draws the input string adding newlines whenever the max
	 * width is surpassed. If x = 0 the text will be centered in the middle of
	 * the screen, otherwise x will be the horizontal offset
	 */
	public static void drawString(Graphics g, String s, int x, int y, int width) {

		// initialize the font metrics, to determine the font height, and the
		// initial position of the string to draw
		FontMetrics fm = g.getFontMetrics();
		int h = fm.getHeight();
		int curY = y;

		// Temp variable for the for loop
		String temp = "";
		int w = 0;

		for (String words : s.split(" ")) {

			if (w + fm.stringWidth(words) > x + width) {
				// When x = 0 the text will be centered
				if (x == 0) {
					g.drawString(temp, 320 - fm.stringWidth(temp) / 2, curY);
				} else {
					g.drawString(temp, x, curY);
				}
				curY += h;
				temp = words + " ";
				w = fm.stringWidth(temp);
			} else {
				temp = temp + words + " ";
				w = fm.stringWidth(temp);
			}
		}

		if (x == 0) {
			g.drawString(temp, 320 - fm.stringWidth(temp) / 2, curY);
		} else {
			g.drawString(temp, x, curY);
		}

	}
}
