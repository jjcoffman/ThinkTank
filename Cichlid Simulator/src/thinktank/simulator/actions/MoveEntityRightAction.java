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
 * 
 * @author Bob
 *
 */
public class MoveEntityRightAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -755362658761305594L;
	/**
	 * 
	 */
	public static final String NAME = "move-entity-right";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveEntityRightAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * <code>Vector3f</code> representation of the direction in the game 
	 * world in which the entity will be moved upon invocation.
	 */
	private Vector3f translateVector;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveEntityRightAction</code>.
	 */
	private MoveEntityRightAction(){
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
				translateVector = cam.getLeft().clone();
				translateVector = translateVector.mult(-1);
				translateVector.setY(0f);
				translateVector = translateVector.normalize().mult(0.01f);
				entity.translate(translateVector);
				ScenarioBuilderScreenController.unsaved_changes = true;
				//TODO ~FOR DEBUG~
				System.out.println("Trans: "+entity.getObj().getLocalTranslation());
				System.out.println("Scale: "+entity.getObj().getLocalScale());
				System.out.println("Rot: "+entity.getObj().getLocalRotation());
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
	public static MoveEntityRightAction getInstance(){
		if(instance == null){
			instance = new MoveEntityRightAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityRightAction class