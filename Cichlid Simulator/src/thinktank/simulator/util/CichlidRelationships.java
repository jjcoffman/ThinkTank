package thinktank.simulator.util;

import gameAssets.Cichlid;
import thinktank.simulator.entity.Entity;

public class CichlidRelationships{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	private final Cichlid cichlid;
	private final Entity otherEntity;
	
	//---------------------instance variables--------------------------
	private double range;
	private int visibility;
	
	//---------------------constructors--------------------------------
	public CichlidRelationships(Cichlid cichlid, Entity otherEntity){
		this.cichlid = cichlid;
		this.otherEntity = otherEntity;
		range = Double.POSITIVE_INFINITY;
		visibility = 0;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public Cichlid getCichlid(){
		return cichlid;
	}//end of getCichlid method
	
	public Entity getOtherEntity(){
		return otherEntity;
	}//end of getOtherEntity method
	
	public double getRange(){
		return range;
	}//end of getRange method
	
	public int getVisibility(){
		return visibility;
	}//end of getVisibility method
	
	//SETTERS
	public void setRange(double range){
		this.range = range;
	}//end of setRange method
	
	public void setVisibility(int visibility){
		this.visibility = visibility;
	}//end of setVisibility method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of CichlidRelationships class