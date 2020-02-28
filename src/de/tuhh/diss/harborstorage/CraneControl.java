package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.PhysicalCrane;
import de.tuhh.diss.harborstorage.sim.StorageElement;
import de.tuhh.diss.harborstorage.sim.CraneException;

public class CraneControl {
	
	private PhysicalCrane cr;
    
	
	public CraneControl(PhysicalCrane cr) {
	    this.cr=cr;
	    this.cr.start();
	}
	
	public void storePacket(int x, int y, StorageElement packet) throws CraneException {
		
		boolean flag = false; // crane is not loaded
		try {
			if (!flag) {
		 	      // checking the position to load the packet 
		       if(this.cr.getLoadingPosX() != this.cr.getPositionX()) {
		    	  moveToX(this.cr.getLoadingPosX());
		       }
		    
		       if(this.cr.getLoadingPosY()!=this.cr.getPositionY()) {
		    	  moveToY(this.cr.getLoadingPosY());
		       }
		       this.cr.loadElement(packet);
		       flag = true;  //crane is loaded
		    }  
	      }
		
		catch (CraneException e) {
			System.out.println("loading element problem" + e.getMessage());
		}
		
		try {  
	        if(this.cr.getPositionX()!= x) {		   
		        moveToX(x);
			 }   
		    if(this.cr.getPositionY()!=y) {
		    	moveToY(y);
			 } 
		     this.cr.storeElement();
		     flag = false;  
		 }
		catch(CraneException e) {
			System.out.println("storing packet problem" +e.getMessage());
		}		
	}
	
   public StorageElement retrievePacket(int x, int y) throws CraneException {

		StorageElement element=null;
		boolean flag =false;  //crane is not loaded
		try{
			if(!flag) {
		      if(this.cr.getPositionX() != x) {
			     moveToX(x);
		       }
		      if(this.cr.getPositionY() != y) {
		         moveToY(y);
		    }
		   this.cr.retrieveElement();
		   flag=true;
			}
		}
		catch (CraneException e) {
			System.out.println("Retrieving a packet problem" + e.getMessage());	
		}
		
		try{
		moveToX(this.cr.getLoadingPosX());
	    moveToY(this.cr.getLoadingPosY());
	    element=this.cr.unloadElement();
	    flag=false; 
		}
		catch(CraneException e) {
			System.out.println("unloading element problem" + e.getMessage());
		}
		return element;	
	 }
	
	// switching off all the motors of the crane before terminating the program
	public void shutdown() {
		this.cr.shutdown();	
	}
	
	
	// move the crane forward() or backward() along x-direction
	private void moveToX(int x) throws CraneException {
		
	try{
		while(x>this.cr.getPositionX() && !(this.cr.isStalledX())) {
			this.cr.forward();
		}
		
		while(x<this.cr.getPositionX() && !(this.cr.isStalledX())) {
			this.cr.backward();
		}
		
		if(x==this.cr.getPositionX()) {
		     this.cr.stopX();
		}
	}
	catch(CraneException e) {
		
		System.out.println("horizontal movement of crane problem" + e.getMessage());
	}
				
   } 
	
	//move the crane up() or down() along y-direction
	 private void moveToY(int y)throws CraneException {
			 
	try {
       while(y > this.cr.getPositionY() && !(this.cr.isStalledY())) {
    	    this.cr.up();
		}
	   while(y < this.cr.getPositionY() && !(this.cr.isStalledY())) {
			this.cr.down();
		}
		if ( y==this.cr.getPositionY()) {
			this.cr.stopY();
		}
	 } 
	catch(CraneException e) {
		System.out.println("vertical movement of crane problem" + e.getMessage());
	 }
	
	}
	
}
