package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.Scenario;

/**
 * Enables or disables the ability for entities to be moved by the user.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class MoveEntityAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8019229449921970576L;
	/**
	 * Constant String identifying this action.
	 */
	public static final String NAME = "move-entity";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The state to which the movement flag will be set when the action 
	 * is next performed.
	 */
	private boolean targetState;
	/**
	 * Whether or not there is a target state waiting to be applied.
	 */
	private boolean targetStateConsumed;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveEntityAction</code>.
	 */
	private MoveEntityAction(){
		targetState = true;
		targetStateConsumed = true;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//SETTINGS
	/**
	 * Sets the state to which the action will next set the movement flag.
	 * 
	 * @param targetState the state that will be set.
	 */
	public void setTargetState(boolean targetState){
		this.targetState = targetState;
		targetStateConsumed = false;
	}//end of setTargetState method
	
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		Scenario scenario = Starter.getClient().getWorkingScenario();
		if(!Starter.getClient().isInMenus() && scenario != null && scenario.isEditingMode()){
			if(targetStateConsumed == false){
				scenario.setMovingMode(targetState);
				targetStateConsumed = true;
			}
			else{
				scenario.setMovingMode(!scenario.isMovingMode());
			}
		}
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