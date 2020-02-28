package de.tuhh.diss.harborstorage;


import de.tuhh.diss.harborstorage.sim.HighBayStorage;
import de.tuhh.diss.harborstorage.sim.PhysicalHarborStorage;
import de.tuhh.diss.harborstorage.sim.StorageException;
import de.tuhh.diss.harborstorage.sim.StoragePlace;


public class HarborStorageManagement implements HighBayStorage {
	
	
	private PhysicalHarborStorage physicalStorage;
	private CraneControl crane;
	private Packet[] packets;  
	private Slot[] slots;
	private StoragePlace[] rack;   // the high rack
	
	
	private int packetCount;
  //  private int mostSuitable;
		
	public HarborStorageManagement() {
		
		this.physicalStorage = new PhysicalHarborStorage();
		this.crane = new CraneControl(this.physicalStorage.getCrane());
		this.rack =this.physicalStorage.getStoragePlacesAsArray();  // returns the storage slots of the high rack as an array
		this.slots = new Slot[this.rack.length];
		
		for (int i=0; i< slots.length; i++) {
			
			slots[i]= new Slot(rack[i].getNumber(), rack[i].getPositionX(), 
				   	           rack[i].getPositionY(),rack[i].getWidth(),
				   	           rack[i].getHeight(), rack[i].getDepth(), 
				   	           rack[i].getLoadCapacity(), null);
		}	
		
		
		//this.packetCount=0;
	//	this.mostSuitable=-1;
	}
	
	
	public Packet[] getPackets() {
	
		this.packetCount=0;
		//int count=0;
		this.packets = new Packet[this.slots.length];
		
	     for (int j=0; j<slots.length; j++) {
			
		  	 if (slots[j].getContainedPacket()!=null) {
			      packets[packetCount]= new Packet(slots[j].getContainedPacket().getId(),
					                              slots[j].getContainedPacket().getWidth(),
					                              slots[j].getContainedPacket().getHeight(),
					                              slots[j].getContainedPacket().getDepth(),
					                              slots[j].getContainedPacket().getDescription(),
					                              slots[j].getContainedPacket().getWeight(),
					                              slots[j].getContainedPacket().getLocation());
			     
			    packetCount++;
			  //   count++;
			   }
		  	 
		  	 
	     }
			 
		 return packets;		 	
		 
	}
	
	
	private int findSuitableSlot(Packet p) {
		
	     int minSum = Integer.MAX_VALUE;
	     int mostSuitable=-1;
	     
		 for(int k=0; k<slots.length;k++) {
			
			
			 if (slots[k].getContainedPacket()==null &&
			     slots[k].getDepth()>= p.getDepth()  &&
			     slots[k].getHeight()>= p.getHeight()&&
			     slots[k].getWidth()>= p.getWidth()  &&
			     slots[k].getLoadCapacity() >= p.getWeight()) {
				
				
			      if(slots[k].getPositionX() + slots[k].getPositionY() < minSum) {
				
				      minSum = slots[k].getPositionX() + slots[k].getPositionY();
				      mostSuitable =slots[k].getNumber();
				    
				      for (int j=k+1; j<slots.length;j++) {
						  
						   if (slots[j].getContainedPacket()==null &&
								     slots[j].getDepth()>= p.getDepth()  &&
								     slots[j].getHeight()>= p.getHeight()&&
								     slots[j].getWidth()>= p.getWidth()  &&
								     slots[j].getLoadCapacity() >= p.getWeight()) {
							   
							   if(slots[j].getPositionX() + slots[j].getPositionY() == minSum) {
								   
								    if(slots[k].getDepth()<slots[j].getDepth()&& 
								    slots[k].getHeight()< slots[j].getHeight()&&
								    slots[k].getWidth()<slots[j].getWidth()&&
								    slots[k].getLoadCapacity()<slots[j].getLoadCapacity()) {
								    			
								    		minSum = slots[k].getPositionX() + slots[k].getPositionY();
										    mostSuitable =slots[k].getNumber();
										    
								       }  
								    	else {
								    		minSum = slots[j].getPositionX() + slots[j].getPositionY();
										    mostSuitable =slots[j].getNumber();
										    
								    	}
								    	
								    }
								    		   
							   }
     		               }		     
			         }
			  
			 }
					   
	 }
		 
		 return mostSuitable;	
  } 
	
	
	public int storePacket(int width, int height, int depth, String description, int weight) throws StorageException {
	 
		Packet newPacket = new Packet(packetCount+1, width, height,depth, description, weight,null);

		
		int mostSuitable= findSuitableSlot(newPacket);
		
		
		     if (mostSuitable>-1)
		      {  
                 slots[mostSuitable].setContainedPacket(newPacket);
                 newPacket.setLocation(slots[mostSuitable]);
		         this.crane.storePacket(slots[mostSuitable].getPositionX(),
	    		                        slots[mostSuitable].getPositionY(),
	    		                        newPacket);
	       	  }
		      
		      else {
		    	  throw new StorageException("can not find suitable slot");
		      }
		     packetCount++;
		return newPacket.getId();
	}
	
	
	
	public void retrievePacket(String description) throws StorageException {

		int locationFound=-1;
		for(int k=0;k<slots.length;k++) {
			
			if(slots[k].getContainedPacket()!=null) {
					if(slots[k].getContainedPacket().getDescription().equals(description)) {
						locationFound=slots[k].getNumber();
						break;
						
						}
			}
		}
		if (locationFound==-1) {
			throw new StorageException("can not find similar described packet");
		}
		
		this.crane.retrievePacket(slots[locationFound].getPositionX(), slots[locationFound].getPositionY());
		slots[locationFound].setContainedPacket(null);
		packetCount--;
		
		
	}
	
	
	public void shutdown() {
		this.crane.shutdown();	
	}
	

	 
}
