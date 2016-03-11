/*****************************************************************************************
 * Class: Mate Strategy
 * Purpose: To give a Cichlid a base strategy that takes no particular stance
 * Author: Think Tank
 * Revisions:
 * 3/10/16 - JC - Creation
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/


package gameAssets.strategies;

import gameAssets.Cichlid;

public class Mate implements IStrategy{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Cichlid cichlid;
	
	//---------------------constructors--------------------------------
	
	//---------------------instance methods----------------------------
	
	@Override
	public void assign(Cichlid d) {
		cichlid = d;
		
		
		//TODO control cichlid movement etc.
		
		
	}
	
	public String toString(){
		return("Strategy: Mate ");
	}
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}