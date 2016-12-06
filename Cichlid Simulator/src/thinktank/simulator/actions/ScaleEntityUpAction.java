package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @deprecated
 */
public class ScaleEntityUpAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 298863534515430618L;
	/**
	 * Constant String identifying this action.
	 */
	public static final String NAME = "scale-entity-up";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static ScaleEntityUpAction instance = null;
	
	//---------------------instance constants--------------------------
	/**
	 * Constant value by which the entity is scaled upon invocation.
	 */
	private final float scaleFactor;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>ScaleEntityUpAction</code>.
	 */
	private ScaleEntityUpAction(){
		scaleFactor = 0.1f;
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
		//TODO scale selected entity by scaleFactor
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static ScaleEntityUpAction getInstance(){
		if(instance == null){
			instance = new ScaleEntityUpAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of ScaleEntityUpAction class