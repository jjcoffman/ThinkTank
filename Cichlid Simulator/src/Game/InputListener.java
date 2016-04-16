package Game;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.LoadScenarioAction;
import thinktank.simulator.actions.MoveForward;
import thinktank.simulator.actions.SaveScenarioAction;
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
			case LoadScenarioAction.NAME:
				LoadScenarioAction.getInstance().actionPerformed(null);
				break;
			case ToggleMouselookAction.NAME:
				ToggleMouselookAction.getInstance().actionPerformed(null);
				break;
			case ToggleCamModeAction.NAME:
				ToggleCamModeAction.getInstance().actionPerformed(null);
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