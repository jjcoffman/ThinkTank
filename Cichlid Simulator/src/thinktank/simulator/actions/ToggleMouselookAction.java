package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;

public class ToggleMouselookAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -6826704529254389675L;
	public static final String NAME = "toggle-mouselook";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static ToggleMouselookAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>ToggleMouselookAction</code>.
	 */
	private ToggleMouselookAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
			Starter.getClient().toggleMouseMode();
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static ToggleMouselookAction getInstance(){
		if(instance == null){
			instance = new ToggleMouselookAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of ToggleMouselookAction class