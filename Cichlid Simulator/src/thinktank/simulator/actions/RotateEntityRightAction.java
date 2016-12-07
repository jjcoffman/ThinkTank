package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.main.Main;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.ui.ScenarioBuilderScreenController;

/**
 * Rotates the currently selected entity left, relative to the entity's model.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class RotateEntityRightAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -2733138701468358319L;
	/**
	 * Constant String identifying this action.
	 */
	public static final String NAME = "rotate-entity-right";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotateEntityRightAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateEntityRightAction</code>.
	 */
	private RotateEntityRightAction(){
		
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
		Main client = Starter.getClient();
		Scenario scenario = client.getWorkingScenario();
		if(!client.isInMenus() && scenario != null && scenario.isEditingMode() && !client.isMouselookActive() && scenario.isMovingMode()){
			Entity entity = scenario.getSelectedEntity();
			if(entity != null){
				entity.getObj().rotate(new Quaternion().fromAngles(0.0f, (float)Math.toRadians(-5), 0.0f));
				ScenarioBuilderScreenController.unsaved_changes = true;
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
	public static RotateEntityRightAction getInstance(){
		if(instance == null){
			instance = new RotateEntityRightAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of RotateEntityRightAction class