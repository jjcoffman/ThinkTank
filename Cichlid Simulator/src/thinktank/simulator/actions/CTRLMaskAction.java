package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;

/**
 * 
 * @author Bob
 *
 */
public class CTRLMaskAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 7215356946921258900L;
	/**
	 * 
	 */
	public static final String NAME = "ctrl-mask";
	/**
	 * 
	 */
	public static final String DOWN_COMMAND = "ctrl-down";
	/**
	 * 
	 */
	public static final String UP_COMMAND = "ctrl-up";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static CTRLMaskAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>SelectEntityAction</code>.
	 */
	private CTRLMaskAction(){
		
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
		if(evt != null){
			if(evt.getActionCommand().equals(DOWN_COMMAND)){
				Starter.getClient().setCTRLDown(true);
			}
			else if(evt.getActionCommand().equals(UP_COMMAND)){
				Starter.getClient().setCTRLDown(false);
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
	public static CTRLMaskAction getInstance(){
		if(instance == null){
			instance = new CTRLMaskAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of CTRLMaskAction class