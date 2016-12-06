package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.main.Main;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.ui.ScenarioBuilderScreenController;

/**
 * Moves the currently selected entity forward, relative to the camera perspective.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class MoveEntityForwardAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -5592152456528971059L;
	/**
	 * Constant String identifying this action.
	 */
	public static final String NAME = "move-entity-forward";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveEntityForwardAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * <code>Vector3f</code> representation of the direction in the game 
	 * world in which the entity will be moved upon invocation.
	 */
	private Vector3f translateVector;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveEntityForwardAction</code>.
	 */
	private MoveEntityForwardAction(){
		translateVector = new Vector3f(0f, 0f, 0f);
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
				Camera cam = client.getCamera();
				translateVector = cam.getDirection().clone();
				translateVector.setY(0f);
				translateVector = translateVector.normalize().mult(0.01f);
				entity.translate(translateVector);
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
	public static MoveEntityForwardAction getInstance(){
		if(instance == null){
			instance = new MoveEntityForwardAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityForwardAction class