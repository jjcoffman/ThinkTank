package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import thinktank.simulator.Starter;

public class MoveEntityLeftAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -6081333671175795774L;
	public static final String NAME = "move-entity-left";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveEntityLeftAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * <code>Vector3f</code> representation of the direction in the game 
	 * world in which the entity will be moved upon invocation.
	 */
	private Vector3f translateVector;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveEntityLeftAction</code>.
	 */
	private MoveEntityLeftAction(){
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
		//TODO if scenario => is editing && is moving
		//TODO get selected entity
		//TODO if selected entity != null
		Camera cam = Starter.getClient().getCamera();
		translateVector = cam.getLeft().clone();
		translateVector.setY(0f);
		//TODO move selected entity by translateVector
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static MoveEntityLeftAction getInstance(){
		if(instance == null){
			instance = new MoveEntityLeftAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityLeftAction class