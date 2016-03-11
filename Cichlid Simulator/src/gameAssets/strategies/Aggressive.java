/*****************************************************************************************
 * Class: Aggressive Strategy
 * Purpose: To give a Cichlid a base strategy that takes no particular stance
 * Author: Jonathan Coffman via Think Tank
 * Revisions:
 * Jonathan Coffman   -    Creation     -    3/10/16
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/


package gameAssets.strategies;

import gameAssets.Cichlid;

public class Aggressive implements IStrategy{
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
		return("Strategy: Aggressive ");
	}
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}