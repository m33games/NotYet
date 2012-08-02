package m33.Comp08;

import java.io.*;
import java.util.Random;

import m33.entities.Chunk;
import m33.entities.ChunkManager;
import m33.entities.EntityManager;
import m33.entities.Platform;

public class Level {
	public char[][] level;
	private final int TILE_SIZE = 32;

	private final int ROWS;
	private final int COL;

	private int totRow = 0;
	private int totCol = 0;

	private int tempRow = 0;
	private int tempCol = 0;
	
	private double spawnX, spawnY;

	private char[][] nextChunk;
	private char[][][] chunks;
	private ChunkManager cm;
	private EntityManager em;

	private Random rand;

	private int anchor;

	public Level(EntityManager entities) {
		rand = new Random();
		cm = new ChunkManager();
		em = entities;

		// Level size is fixed for now, it may become dynamic
		level = new char[1000][2000];
		anchor = 990;

		loadSingleFile("chunkTest");
		Chunk ch = new Chunk();

		totRow = tempRow;
		totCol = 0;

		/*
		 * for now it load all the chunks in series, from 1 to 4, and it compose
		 * them in a single level
		 */
		for (int i = 0; i < 50; i++) {

			int j = rand.nextInt(6) + 1;
			//int j = 2; // to test a single chunk
			if(i == 0){
				ch = cm.getChunk(0);
				
				// Chunk 0 MUST define a spawn point
				spawnX = (totCol + ch.getSpawnX()) * TILE_SIZE;
				spawnY = (anchor - ch.getAnchorIn() + ch.getSpawnY()) * TILE_SIZE;
			} else {
				ch = cm.getChunk(j);
			}
			
			char[][] a = ch.getArray();

			for (int r = 0; r < a.length; r++) {
				for (int c = 0; c < a[0].length; c++) {
					level[r + anchor - ch.getAnchorIn()][c + totCol] = a[r][c];
				}
			}

			// create entities
			for (int k = 0; k < ch.getMovNum(); k++) {
				int sx = (ch.sx[k] + totCol) * TILE_SIZE;
				int ex = (ch.ex[k] + totCol) * TILE_SIZE;
				int sy = (ch.sy[k] + anchor - ch.getAnchorIn()) * TILE_SIZE;
				int ey = (ch.ey[k] + anchor - ch.getAnchorIn()) * TILE_SIZE;
				Platform p = new Platform(sx, ex, sy, ey);
				p.setId(1);
				p.setNumWH(ch.getNumW(), ch.getNumH());
			
				em.add(p);
			}

			anchor = anchor - (ch.getAnchorIn() - ch.getAnchorOut());

			totCol += a[0].length;
		}

		ROWS = totRow;
		COL = totCol;
	}

	public int getRows() {
		return 1000;
	}

	public int getCol() {
		return COL;
	}
	
	public double getSpawnX(){
		return spawnX;
	}
	
	public double getSpawnY(){
		return spawnY;
	}

	private void loadSingleFile(String chunkName) {
		try {
			char[][] temp = new char[50][50];
			Chunk tempChunk = new Chunk();
			char[][] tempArray = new char[0][0];

			// Open the file that is the first command line parameter
			FileInputStream fstream = new FileInputStream("data/levels/"
					+ chunkName + ".txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith("<")) {
					tempRow = 0;
					// command
					String[] tokens = strLine.split("[ ]+");
					for (int i = 0; i < tokens.length; i++) {
						String[] command = tokens[i].split(":");
						if (command[0].equals("time")) { // time
							// save time
						} else if (command[0].equals("chunk")) {
							int id = Integer.parseInt(command[1]); // chunk ID
							// create chunk
							tempChunk = new Chunk();
							// save chunk ID
							tempChunk.setID(id);
							cm.add(id, tempChunk);
						} else if (command[0].equals("size")) { // size
							int row = Integer.parseInt(command[1]);
							int col = Integer.parseInt(command[2]);

							tempChunk.setChunk(row, col);
							tempArray = tempChunk.getArray();
						} else if (command[0].equals("anchor")) { // anchor
							int aIn = Integer.parseInt(command[1]);
							int aOut = Integer.parseInt(command[2]);
							tempChunk.setAnchors(aIn, aOut);
						} else if (command[0].equals("movNum")) { // movable
																	// number
							int num = Integer.parseInt(command[1]);
							tempChunk.setPlatformNum(num);
						} else if (command[0].equals("mov")) { // movable
																// platforms
							int sx = Integer.parseInt(command[1]);
							int ex = Integer.parseInt(command[2]);
							int sy = Integer.parseInt(command[3]);
							int ey = Integer.parseInt(command[4]);
							tempChunk.setPlatform(sx, ex, sy, ey);
						} else if (command[0].equals("numWH")){
							int numW = Integer.parseInt(command[1]);
							int numH = Integer.parseInt(command[2]);
							tempChunk.platformWH(numW, numH);
						} else if (command[0].equals("spawn")){
							int spawnX = Integer.parseInt(command[1]);
							int spawnY = Integer.parseInt(command[2]);
							tempChunk.setSpawn(spawnX, spawnY);
						}
					}

				} else {
					// fill array
					for (int i = 0; i < strLine.length(); i++) {
						tempArray[tempRow][i] = strLine.charAt(i);
					}
					tempRow += 1;
				}

			}
			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error single chunk: " + e.getMessage());
		}
	}

}
