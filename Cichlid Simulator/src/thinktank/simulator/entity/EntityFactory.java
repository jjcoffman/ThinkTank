package thinktank.simulator.entity;

import thinktank.simulator.main.Main;

/**
 * Provides factory methods for creating new entities of various types.
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
	/**
	 * Provides only static methods.
	 * This class is not meant to be instantiated.
	 */
	private EntityFactory(){}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Creates a basic <code>Chichlid</code> with default values.
	 * 
	 * @return the created <code>Chichlid</code> object.
	 */
	public static Cichlid createCichlid(){
		Cichlid returnValue = new Cichlid();
		String name = "cichlid-"+Main.RNG.nextInt();
		returnValue.setName(name);
		return returnValue;
	}//end of createCichlid method

	/**
	 * Creates a basic <code>Chichlid</code> with default values and 
	 * the specified name.
	 * 
	 * @param name the <code>String</code> to which the cichlid's name 
	 * is to be set.
	 * @return the created <code>Chichlid</code> object.
	 */
	public static Cichlid createCichlid(String name){
		Cichlid returnValue = new Cichlid();
		returnValue.setName(name);
		return returnValue;
	}//end of createCichlid(String) method

	/**
	 * Creates a basic <code>Plant</code> with default values.
	 * 
	 * @return the created <code>Plant</code> object.
	 */
	public static Plant createPlant(){
		Plant returnValue = new Plant();
		String name = "plant-"+Main.RNG.nextInt();
		returnValue.setName(name);
		return returnValue;
	}//end of createPlant method

	/**
	 * Creates a basic <code>Pot</code> with default values.
	 * 
	 * @return the created <code>Pot</code> object.
	 */
	public static Pot createPot(){
		Pot returnValue = new Pot();
		String name = "pot-"+Main.RNG.nextInt();
		returnValue.setName(name);
		return returnValue;
	}//end of createPot method
}//end of EntityFactory class