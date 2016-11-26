package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.main.Main;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.ui.ScenarioBuilderScreenController;

public class DeleteEntityAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -4620099631091829000L;
	public static final String NAME = "delete-entity";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static DeleteEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>DeleteEntityAction</code>.
	 */
	private DeleteEntityAction(){
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
				if(entity instanceof EnvironmentObject){
					scenario.removeEnvironmentObject((EnvironmentObject)entity);
				}
				else if(entity instanceof Fish){
					scenario.removeFish((Fish)entity);
				}
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
	public static DeleteEntityAction getInstance(){
		if(instance == null){
			instance = new DeleteEntityAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of DeleteEntityAction class