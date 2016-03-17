package thinktank.simulator.entity;
/*****************************************************************************************
 * Class: EntityFactory
 * Purpose: Creates the objects used in the simulation
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import gameAssets.Cichlid;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class EntityFactory{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private EntityFactory(){}//Not to be instantiated
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Cichlid createCichlid(){
		Cichlid returnValue = new Cichlid();
		return returnValue;
	}//end of createCichlid method
	
	public static Cichlid createCichlid(String name){
		Cichlid returnValue = new Cichlid();
		//TODO: set name
		return returnValue;
	}//end of createCichlid(String) method
	
	public static Plant createPlant(){
		Plant returnValue = new Plant();

		returnValue.getObj().setLocalTranslation(0, 4.9f, -3);
		
		return returnValue;
	}//end of createPlant method
	
	public static Pot createPot(){
		Pot returnValue = new Pot();

		returnValue.getObj().setLocalTranslation(0, 4.75f, 2.5f);
		
		return returnValue;
	}//end of createPot method
}//end of EntityFactory class