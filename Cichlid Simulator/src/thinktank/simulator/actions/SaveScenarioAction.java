package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class SaveScenarioAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8834425518749748609L;
	public static final String NAME = "save-scenario";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static SaveScenarioAction instance;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>SaveScenarioAction</code>.
	 */
	private SaveScenarioAction(){
		
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
		Scenario scenario = Starter.getClient().getWorkingScenario();
		if(scenario != null){
			boolean saveSuccess = ScenarioIO.saveScenario(scenario, new File(scenario.getName()));
			if(saveSuccess){
				Starter.getClient().addScenario(scenario.getName());
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
	public static SaveScenarioAction getInstance(){
		if(instance == null){
			instance = new SaveScenarioAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of SaveScenarioAction class