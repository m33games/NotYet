package m33.entities;

public class Chunk {

	// private variables
	private char[][] localChunk;
	private int id;
	private int time;
	private int anchorIn;
	private int anchorOut;
	
	// public variables
	
	// constructor
	public Chunk(){
		
	}
	
	public Chunk(int r, int c){
		localChunk = new char[r][c];
	}
	
	public Chunk(int r, int c, int id){
		this(r, c);
		setID(id);
	}
	
	// accessors
	public int getID(){
		return id;
	}
	
	public int getRowNum(){
		return localChunk.length;
	}
	
	public int getColNum(){
		return localChunk[0].length;
	}
	
	public char[][] getArray(){
		return localChunk;
	}
	
	public int getAnchorIn(){
		return anchorIn;
	}
	
	public int getAnchorOut(){
		return anchorOut;
	}
	
	// modificators
	public void setID(int id){
		this.id = id;
	}

	public void setChunk(int r, int c){
		localChunk = new char[r][c];
	}
	
	public void setAnchors(int aIn, int aOut){
		anchorIn = aIn;
		anchorOut = aOut;
	}
}
