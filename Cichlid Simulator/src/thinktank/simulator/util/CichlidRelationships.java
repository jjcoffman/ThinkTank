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
	 * 
	 */
	private final Cichlid cichlid;
	/**
	 * 
	 */
	private final Entity otherEntity;
	
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private double range;
	/**
	 * 
	 */
	private int visibility;
	
	//---------------------constructors--------------------------------
	/**
	 * 
	 * @param cichlid
	 * @param otherEntity
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
	 * 
	 * @return
	 */
	public Cichlid getCichlid(){
		return cichlid;
	}//end of getCichlid method
	
	/**
	 * 
	 * @return
	 */
	public Entity getOtherEntity(){
		return otherEntity;
	}//end of getOtherEntity method
	
	/**
	 * 
	 * @return
	 */
	public double getRange(){
		return range;
	}//end of getRange method
	
	/**
	 * 
	 * @return
	 */
	public int getVisibility(){
		return visibility;
	}//end of getVisibility method
	
	//SETTERS
	/**
	 * 
	 * @param range
	 */
	public void setRange(double range){
		this.range = range;
	}//end of setRange method
	
	/**
	 * 
	 * @param visibility
	 */
	public void setVisibility(int visibility){
		this.visibility = visibility;
	}//end of setVisibility method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of CichlidRelationships class