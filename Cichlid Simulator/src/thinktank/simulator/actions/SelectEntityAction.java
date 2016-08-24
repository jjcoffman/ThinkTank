package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

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

public class SelectEntityAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 239077110820272376L;
	public static final String NAME = "select-entity";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static SelectEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>SelectEntityAction</code>.
	 */
	private SelectEntityAction(){
		
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
		//TODO if mouse look == false && activeCam == CAM_MODE.FLY && !inMenues
		CollisionResults results = new CollisionResults();
		InputManager inputManager = Starter.getClient().getInputManager();
		Vector2f click2d = inputManager.getCursorPosition();
		
		Camera cam = Starter.getClient().getCamera();
		Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		Ray ray = new Ray(click3d, dir);
		
		Node entityNode = Starter.getClient().getWorkingScenario().getEntityNode();
		entityNode.collideWith(ray, results);

        if(results.size() > 0){
        	CollisionResult closest = results.getClosestCollision();
        	String selected = closest.getGeometry().getName();
        	//TODO get selected entity from scene
        	//TODO highlight selection in scene
        	//TODO enable movement/alteration of entity
        }
	}//end of actionPerformed method
	
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