package m33.entities;

import java.util.HashMap;

public class ChunkManager {

	// private
	private int chunkNumber;
	private HashMap<Integer, Chunk> chunkMap;

	// here I need a map, where it is easy to find a chunk using its ID to access
	// it

	// public

	// constructor
	public ChunkManager() {
		chunkMap = new HashMap<Integer, Chunk>();
	}
	
	public void add(int i, Chunk c){
		chunkMap.put(i, c);
	}
	
	public int size(){
		return chunkMap.size();
	}
	
	// accessor
	public Chunk getChunk(int id){
		return chunkMap.get(id);
	}

}
