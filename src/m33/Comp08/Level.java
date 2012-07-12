package m33.Comp08;

import java.io.*;
import java.util.Random;

import m33.entities.Chunk;
import m33.entities.ChunkManager;

public class Level {
	public char[][] level;

	private final int ROWS;
	private final int COL;

	private int totRow = 0;
	private int totCol = 0;

	private int tempRow = 0;
	private int tempCol = 0;

	private char[][] nextChunk;
	private char[][][] chunks;
	private ChunkManager cm;

	private Random rand;

	public Level() {
		rand = new Random();
		cm = new ChunkManager();

		// Level size is fixed for now, it may become dynamic
		level = new char[2000][2000];
		
		loadSingleFile("chunkTest");
		
		totRow = tempRow;
		
		/*
		 * for now it load all the chunks in series, from 1 to 4, and it compose
		 * them in a single level
		 */
		for (int i = 1; i < 50; i++) {
			
			int j = rand.nextInt(4) + 1;

			char[][] a = cm.getChunk(j).getArray();

			for (int r = 0; r < a.length; r++) {
				for (int c = 0; c < a[0].length; c++) {
					level[r][c + totCol] = a[r][c];
				}
			}

			totCol += a[0].length;
		}

		ROWS = totRow;
		COL = totCol;
	}

	public int getRows() {
		return ROWS;
	}

	public int getCol() {
		return COL;
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
					for (int i = 0; i < tokens.length; i++){
						String[] command = tokens[i].split(":");
						if(command[0].equals("time")){				// time
							// save time
						} else if (command[0].equals("chunk")){
							int id = Integer.parseInt(command[1]);	// chunk ID
							// create chunk
							tempChunk = new Chunk();
							// save chunk ID
							tempChunk.setID(id);
							cm.add(id, tempChunk);
						} else if (command[0].equals("size")){
							int row = Integer.parseInt(command[1]);
							int col = Integer.parseInt(command[2]);
							
							tempChunk.setChunk(row,col);
							tempArray = tempChunk.getArray();
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
