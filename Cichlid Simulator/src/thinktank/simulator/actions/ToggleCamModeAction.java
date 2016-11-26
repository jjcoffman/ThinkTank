package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.main.Main;
import thinktank.simulator.main.Main.CAM_MODE;
import thinktank.simulator.scenario.Scenario;

public class ToggleCamModeAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -3276445904615503162L;
	public static final String NAME = "toggle-cam-mode";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static ToggleCamModeAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The <code>Main.CAM_MODE</code> which the application is currently 
	 * set to. Upon invoking, the applications camera mode will be changed 
	 * to the opposite of this value.
	 */
	private Main.CAM_MODE targetMode;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>ToggleCamModeAction</code>.
	 */
	private ToggleCamModeAction(){
		targetMode = null;
	}//end of constuctor
	
	//---------------------instance methods----------------------------
	//SETTERS
	/**
	 * Sets the value of the target mode.
	 * 
	 * @param mode the current mode of the camera.
	 */
	public void setTargetMode(Main.CAM_MODE mode){
		targetMode = mode;
	}//end of setTargetMode method
	
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		Scenario scenario = Starter.getClient().getWorkingScenario();
		//TODO undo comment when done testing
		//if(scenario != null && scenario.hasPlayer() && !scenario.isEditingMode()){
			if(targetMode == CAM_MODE.FLY){
				Starter.getClient().setCamMode(CAM_MODE.FOLLOW);
			}
			else if(targetMode == CAM_MODE.FOLLOW){
				Starter.getClient().setCamMode(CAM_MODE.FLY);
			}
		//}
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static ToggleCamModeAction getInstance(){
		if(instance == null){
			instance = new ToggleCamModeAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of SetCamModeAction class