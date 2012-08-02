package m33.entities;

public class Chunk {

	// private variables
	private char[][] localChunk;
	private int id;
	private int time;
	private int anchorIn;
	private int anchorOut;
	private int numW = 1;
	private int numH = 1;
	private int spawnX, spawnY;

	public int[] sx, ex, sy, ey, vel;

	
	private int movNum = 0;
	private int iter = 0;

	// public variables

	// constructor
	public Chunk() {
	}

	public Chunk(int r, int c) {
		localChunk = new char[r][c];
	}

	public Chunk(int r, int c, int id) {
		this(r, c);
		setID(id);
	}

	// accessors
	public int getID() {
		return id;
	}

	public int getRowNum() {
		return localChunk.length;
	}

	public int getColNum() {
		return localChunk[0].length;
	}

	public char[][] getArray() {
		return localChunk;
	}

	public int getAnchorIn() {
		return anchorIn;
	}

	public int getAnchorOut() {
		return anchorOut;
	}
	
	public int getMovNum(){
		return movNum;
	}
	
	public int getNumW(){
		return numW;
	}
	
	public int getNumH(){
		return numH;
	}
	
	public int getSpawnX(){
		return spawnX;
	}

	public int getSpawnY(){
		return spawnY;
	}
	
	// modificators
	public void setID(int id) {
		this.id = id;
	}

	public void setChunk(int r, int c) {
		localChunk = new char[r][c];
	}

	public void setAnchors(int aIn, int aOut) {
		anchorIn = aIn;
		anchorOut = aOut;
	}
	
	public void setSpawn(int x, int y){
		spawnX = x;
		spawnY = y;
	}

	public void setPlatformNum(int n) {
		movNum = n;
		sx = new int[n];
		ex = new int[n];
		sy = new int[n];
		ey = new int[n];
	}

	public void setPlatform(int sx, int ex, int sy, int ey) {
		this.sx[iter] = sx;
		this.ex[iter] = ex;
		this.sy[iter] = sy;
		this.ey[iter] = ey;
		iter++;
	}
	
	public void platformWH(int w, int h){
		numW = w;
		numH = h;
	}
}
