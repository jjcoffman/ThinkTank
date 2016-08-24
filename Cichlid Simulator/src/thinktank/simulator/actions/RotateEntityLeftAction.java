package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RotateEntityLeftAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 6744490700400317249L;
	public static final String NAME = "rotate-entity-left";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotateEntityLeftAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateEntityLeftAction</code>.
	 */
	private RotateEntityLeftAction(){
		
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
		//TODO rotate selected entity
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static RotateEntityLeftAction getInstance(){
		if(instance == null){
			instance = new RotateEntityLeftAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of RotateEntityLeftAction class