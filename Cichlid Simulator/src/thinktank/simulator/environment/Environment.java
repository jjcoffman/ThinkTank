package thinktank.simulator.environment;
/*****************************************************************************************
 * Class: Environment
 * Purpose: Inititates the environment and its attributes
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Environment {
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private float tempCelcius;
	private Tank tank;
	
	//---------------------constructors--------------------------------
	public Environment(){
		tempCelcius = 0.0f;
		tank = null;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public float getTempCelcius(){
		return tempCelcius;
	}//end of getTempCelcius method
	
	public Tank getTank(){
		return tank;
	}//end of getTank method
	
	//SETTERS
	public void setTempCelcius(float temp){
		tempCelcius = temp;
	}//end of setTempCelcius method
	
	public void setTank(Tank tank){
		this.tank = tank;
	}//end of setTank method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns the value, in world units, of the value specified in inches.
	 * 
	 * Uses the conversion value of 1.0 world units = 1.1811 inches.
	 * 
	 * @param inches the value to be converted from inches to world units.
	 * @return the converted value, in world units.
	 */
	public static float inchesToWorldUnits(float inches){
		float returnValue = -1.0f;
		if(inches > 0){
			returnValue = inches * 1.1811f;
		}
		return returnValue;
	}//end of inchesToWorldUnits method
}//end of Environment class