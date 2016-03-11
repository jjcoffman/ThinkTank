package thinktank.simulator.actions;
/*****************************************************************************************
 * Class: NewScenarioAction
 * Purpose: Scenario Actions based on the scenario
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
public class NewScenarioAction{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	private static NewScenarioAction instance;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private NewScenarioAction(){
		
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static NewScenarioAction getInstance(){
		if(instance == null){
			instance = new NewScenarioAction();
		}
		return instance;
	}//end of getInstance method
}//end of NewScenarioAction class