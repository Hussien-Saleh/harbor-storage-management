package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StorageElement;

public class Packet implements StorageElement{
	
	private int id;               // the packet identification number
	private int width;            // the width dimension of the packet
	private int height;           // the height dimension of the packet
	private int depth;            // the depth dimension of the packet
	private String description;   // short textual description of the packet
	private int weight;           // the packet weight
	private Slot location;        // the location of the packet inside the slot
	
	 public Packet(int id, int width, int height, int depth,
			       String description, int weight, Slot location) {
		 
		 this.id=id;
		 this.width = width;
		 this.height= height;
		 this.depth = depth;
		 this.description = description;
		 this.weight = weight;
		 this.location=location;
	 }
	 
	 
	 // returns the identification number of the packet
	public int getId() {
		return this.id; 
	}
	
	// returns the width of the packet
	public int getWidth() {
		return this.width;
		}
    
	// returns the height of the packet
	public int getHeight() {
		return this.height; 
	}

	 // returns the depth of the packet
	public int getDepth() {
		return this.depth;  
	}
	
	// returns a short textual description of the packet
	public String getDescription() {
		return this.description; 
	}
	
	// returns the weight of the packet
	public int getWeight() {
		return this.weight;  
	}
	
	// returns location of the packet in the slot
	public Slot getLocation() {
		return this.location;
	}
	
	// sets the location of the packet in the slot
	public void setLocation(Slot s) {
		this.location = s;
	}
		
}
