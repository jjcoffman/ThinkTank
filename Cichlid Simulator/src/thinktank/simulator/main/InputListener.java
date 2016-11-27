package thinktank.simulator.main;

import java.awt.event.ActionEvent;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.CTRLMaskAction;
import thinktank.simulator.actions.MoveEntityBackwardAction;
import thinktank.simulator.actions.MoveEntityDownAction;
import thinktank.simulator.actions.MoveEntityForwardAction;
import thinktank.simulator.actions.MoveEntityLeftAction;
import thinktank.simulator.actions.MoveEntityRightAction;
import thinktank.simulator.actions.MoveEntityUpAction;
import thinktank.simulator.actions.RotateEntityLeftAction;
import thinktank.simulator.actions.RotateEntityRightAction;
import thinktank.simulator.actions.SaveScenarioAction;
import thinktank.simulator.actions.SelectEntityAction;
import thinktank.simulator.actions.ToggleCamModeAction;
import thinktank.simulator.actions.ToggleMouselookAction;

/**
 * Main listener class for user actions and scenario management actions.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class InputListener implements AnalogListener, ActionListener{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the listener.
	 */
	private static InputListener instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>InputListener</code>.
	 */
	private InputListener(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	/**
	 * Method triggered by associated key presses to trigger the 
	 * assigned action.
	 * 
	 * @param name the name for the triggered action
	 * @param keyPressed the key that was pressed to trigger the event
	 * @param tpf
	 */
	@Override
	public void onAction(String name, boolean keyPressed, float tpf){
		if(!keyPressed){
			switch(name){
			case CTRLMaskAction.NAME:
				ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_LAST, CTRLMaskAction.UP_COMMAND);
				CTRLMaskAction.getInstance().actionPerformed(evt);
				break;
			case SelectEntityAction.NAME:
				SelectEntityAction.getInstance().actionPerformed(null);
				break;
			case AddPotAction.NAME:
				AddPotAction.getInstance().actionPerformed(null);
				break;
			case AddFishAction.NAME:
				AddFishAction.getInstance().actionPerformed(null);
				break;
			case AddPlantAction.NAME:
				AddPlantAction.getInstance().actionPerformed(null);
				break;
			case SaveScenarioAction.NAME:
				SaveScenarioAction.getInstance().actionPerformed(null);
				break;
			case ToggleMouselookAction.NAME:
				ToggleMouselookAction.getInstance().actionPerformed(null);
				break;
			case ToggleCamModeAction.NAME:
				ToggleCamModeAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityLeftAction.NAME:
				MoveEntityLeftAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityRightAction.NAME:
				MoveEntityRightAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityForwardAction.NAME:
				MoveEntityForwardAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityBackwardAction.NAME:
				MoveEntityBackwardAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityUpAction.NAME:
				MoveEntityUpAction.getInstance().actionPerformed(null);
				break;
			case MoveEntityDownAction.NAME:
				MoveEntityDownAction.getInstance().actionPerformed(null);
				break;
			case RotateEntityRightAction.NAME:
				RotateEntityRightAction.getInstance().actionPerformed(null);
				break;
			case RotateEntityLeftAction.NAME:
				RotateEntityLeftAction.getInstance().actionPerformed(null);
				break;
			}
		}
		else{
			switch(name){
			case CTRLMaskAction.NAME:
				ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_LAST, CTRLMaskAction.DOWN_COMMAND);
				CTRLMaskAction.getInstance().actionPerformed(evt);
				break;
			}
		}
	}//end of onAction method

	/**
	 * Method triggered by associated analog input to trigger the 
	 * assigned action.
	 * 
	 * @param name the name for the triggered action
	 * @param value the value for the analog input
	 * @param tpf
	 */
	@Override
	public void onAnalog(String name, float value, float tpf){
		switch(name){
		}
	}//end of onAnalog method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the listener.
	 * 
	 * @return the listener object
	 */
	public static InputListener getInstance(){
		if(instance == null){
			instance = new InputListener();
		}
		return instance;
	}//end of getInstance method
	
}//end of InputListener class