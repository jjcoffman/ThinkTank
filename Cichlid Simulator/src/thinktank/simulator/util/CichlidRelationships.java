package thinktank.simulator.util;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Entity;

/**
 * Stores values representing the relationship, in terms of proximity and 
 * visibility, between a cichlid and another entity.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class CichlidRelationships{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	/**
	 * The cichlid involved in the relationship.
	 */
	private final Cichlid cichlid;
	/**
	 * The other entity involved in the relationship.
	 */
	private final Entity otherEntity;
	
	//---------------------instance variables--------------------------
	/**
	 * The value for the range between the two entities.
	 */
	private double range;
	/**
	 * The value for the visibility between the two entities.
	 */
	private int visibility;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a new relationship between the specified Cichlid and entity.
	 * 
	 * @param cichlid the cichlid involved in the relationship.
	 * @param otherEntity the other entity involved in the relationship.
	 */
	public CichlidRelationships(Cichlid cichlid, Entity otherEntity){
		this.cichlid = cichlid;
		this.otherEntity = otherEntity;
		range = Double.POSITIVE_INFINITY;
		visibility = 0;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Returns the cichlid involved in the relationship.
	 * 
	 * @return the cichlid.
	 */
	public Cichlid getCichlid(){
		return cichlid;
	}//end of getCichlid method
	
	/**
	 * Returns the other entity involved in the relationship.
	 * 
	 * @return the other entity.
	 */
	public Entity getOtherEntity(){
		return otherEntity;
	}//end of getOtherEntity method
	
	/**
	 * Returns the range between the two entities.
	 * 
	 * @return the distance between the entities.
	 */
	public double getRange(){
		return range;
	}//end of getRange method
	
	/**
	 * Returns the value representing the visibility between the 
	 * two entities (0-100). A value of 0 indicates that the other 
	 * entities is completely obscured from the perspective of the 
	 * cichlid. A value of 100 indicates a completely clear line of 
	 * sight from the cichlid to the other entitiy.
	 * 
	 * @return the visibility factor.
	 */
	public int getVisibility(){
		return visibility;
	}//end of getVisibility method
	
	//SETTERS
	/**
	 * Sets the range between the two entities to the specified value.
	 * 
	 * @param range the value to which the range is set.
	 */
	public void setRange(double range){
		this.range = range;
	}//end of setRange method
	
	/**
	 * Sets the visibility factor from the cichlid to the other entity 
	 * to the speicified value.
	 * 
	 * @param visibility the visibility factor.
	 */
	public void setVisibility(int visibility){
		this.visibility = visibility;
	}//end of setVisibility method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of CichlidRelationships class