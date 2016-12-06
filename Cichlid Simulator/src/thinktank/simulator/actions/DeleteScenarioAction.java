package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * Removes the current target scenario from the scenario list and deletes 
 * the associated file.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class DeleteScenarioAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -666257834922863482L;
	/**
	 * Constant String identifying this action.
	 */
	public static final String NAME = "delete-scenario";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static DeleteScenarioAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The target scenario to be deleted.
	 */
	private String scenarioName;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>DeleteScenarioAction</code>.
	 */
	private DeleteScenarioAction(){
		scenarioName = null;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	/**
	 * Sets the target scenario upon which the action will operate.
	 * 
	 * @param scenarioName the name of the scenario.
	 */
	public void setScenario(String scenarioName){
		this.scenarioName = scenarioName;
	}//end of setScenario method
	
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		if(scenarioName != null && !ScenarioDefinition.isDefault(scenarioName)){
			ScenarioIO.deleteSavedScenario(new File(scenarioName));
			if(Starter.getClient().isWorkingScenario(scenarioName)){
				Starter.getClient().setWorkingScenarioToDefault();
			}
			Starter.getClient().removeScenario(scenarioName);
		}
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static DeleteScenarioAction getInstance(){
		if(instance == null){
			instance = new DeleteScenarioAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of DeleteScenarioAction class