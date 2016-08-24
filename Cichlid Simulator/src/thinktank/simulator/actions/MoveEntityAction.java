package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.Scenario;

public class MoveEntityAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8019229449921970576L;
	public static final String NAME = "move-entity";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveEntityAction</code>.
	 */
	private MoveEntityAction(){
		
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
		Scenario scenario = Starter.getClient().getWorkingScenario();
		//TODO if editing scenario
		//TODO set scenario to moving mode
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static MoveEntityAction getInstance(){
		if(instance == null){
			instance = new MoveEntityAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityAction class