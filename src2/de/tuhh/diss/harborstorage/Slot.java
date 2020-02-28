package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StoragePlace;

public class Slot implements StoragePlace {	
	
	private int number;              // identification number of the slot
	private int positionX;           // the x-coordinate location of the slot
	private int positionY;           // the y-coordinate location of the slot
	private int width;               // width of the slot 
	private int height;              // height of the slot 
	private int depth;               // depth of the slot 
	private int loadCapacity;        // the load capacity of the slot
	private Packet containedPacket;  // the packet contained inside this slot
	
	   public Slot(int number,int positionX, int positionY, int width,
			      int height, int depth, int loadCapacity, Packet containedPacket) {
		   
		   this.number = number;
		   this.positionX = positionX;
		   this.positionY = positionY;
		   this.width = width;
		   this.height = height;
		   this.depth = depth;
		   this.loadCapacity = loadCapacity;
		   this.containedPacket= containedPacket;
	   }
	   
	
    // returns the identification number of this storage place (slot)
	public int getNumber() {
		return this.number;  
	}
	
	// returns the location of this storage place (slot) w.r.t x-axis
	public int getPositionX() {
		return this.positionX; 
	}
	
	//returns the location of this storage place (slot) w.r.t  y-axis
	public int getPositionY() {
		return this.positionY; 
	}
	
	// returns the width of this storage place (slot)
	public int getWidth() {
		return this.width;  
	}
	
    // returns the height of this storage place (slot)
	public int getHeight() {
		return this.height; 
	}
	
	// return the depth of this storage place (slot)
	public int getDepth() {
		return this.depth;  
	}
	
	// returns the load capacity of this storage place (slot)
	public int getLoadCapacity() {
		return this.loadCapacity;  
	}
	
	//unloads the packet from this storage place (slot)
	public Packet getContainedPacket() {
		return this.containedPacket;
	}
	
	//loads the packet into this storage place (slot)
	public void setContainedPacket(Packet p) {
		this.containedPacket = p;
	}	

}
