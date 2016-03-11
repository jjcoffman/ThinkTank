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
}//end of Environment class