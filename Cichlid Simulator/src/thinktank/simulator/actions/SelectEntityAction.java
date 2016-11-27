package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.main.Main;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.ui.ScenarioBuilderScreenController;
import thinktank.simulator.util.IObservable;
import thinktank.simulator.util.IObserver;

/**
 * 
 * @author Bob
 *
 */
public class SelectEntityAction extends AbstractAction implements IObservable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 239077110820272376L;
	/**
	 * 
	 */
	public static final String NAME = "select-entity";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static SelectEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private ArrayList<IObserver> observers;
	/**
	 * 
	 */
	private Entity selectedEntity;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>SelectEntityAction</code>.
	 */
	private SelectEntityAction(){
		observers = new ArrayList<IObserver>();
		selectedEntity = null;
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
		if(!client.isInMenus() && 
				!client.isMouselookActive() && 
				client.getActiveCam().equals(Main.CAM_MODE.FLY) &&
				client.getWorkingScenario() != null && 
				client.getWorkingScenario().isEditingMode()){//if mouse look == false && activeCam == CAM_MODE.FLY && !inMenues && editMode
			ScenarioBuilderScreenController.selecting = true;
			CollisionResults results = new CollisionResults();
			InputManager inputManager = client.getInputManager();
			Vector2f click2d = inputManager.getCursorPosition();
			
			Camera cam = client.getCamera();
			Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
			Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
			Ray ray = new Ray(click3d, dir);
			
			Node entityNode = client.getWorkingScenario().getEntityNode();
			entityNode.collideWith(ray, results);
		
        	if(results.size() > 0){
        		CollisionResult closest = results.getClosestCollision();
        		String selected = closest.getGeometry().getName();
        		Scenario scenario = client.getWorkingScenario();
        		Entity selectedEntity = scenario.getEntity(selected);//get selected entity from scene
        		if(selectedEntity != null){
        			if(!client.isCTRLDown()){
        				scenario.selectEntity(selectedEntity);
        				this.selectedEntity = selectedEntity;
        			}
        			else{
        				scenario.deselectEntity(selectedEntity);
        				this.selectedEntity = null;
        			}
        		}
        		notifyObservers();
        	}
			ScenarioBuilderScreenController.selecting = false;
		}
	}//end of actionPerformed method

	/**
	 * 
	 */
	@Override
	public void addObserver(IObserver obs){
		if(obs != null){
			observers.add(obs);
		}
	}//end of addObserver method

	/**
	 * 
	 */
	@Override
	public void notifyObservers(){
		for(IObserver obs : observers){
			obs.update(this, selectedEntity);
		}
		selectedEntity = null;
	}//end of notifyObservers method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static SelectEntityAction getInstance(){
		if(instance == null){
			instance = new SelectEntityAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of SelectEntityAction