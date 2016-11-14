package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
 * @deprecated
 *
 */
public class NewScenarioAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -630281665703987655L;
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static NewScenarioAction instance;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>NewScenarioAction</code>.
	 */
	private NewScenarioAction(){
		
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO implement
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static NewScenarioAction getInstance(){
		if(instance == null){
			instance = new NewScenarioAction();
		}
		return instance;
	}//end of getInstance method
}//end of NewScenarioAction class