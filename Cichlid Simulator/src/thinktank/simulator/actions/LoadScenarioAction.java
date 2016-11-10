package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * @deprecated
 * @author Bob
 *
 */
public class LoadScenarioAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8272785805353027409L;
	public static final String NAME = "load-scenario";
	
	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static LoadScenarioAction instance;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>LoadScenarioAction</code>.
	 */
	private LoadScenarioAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 * @deprecated
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO temporary. needs file selector.
//		Starter.getClient().addScenario(ScenarioIO.loadScenario(new File("Test_Scenario"+ScenarioIO.SCENARIO_FILE_EXTENSION)));;
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static LoadScenarioAction getInstance(){
		if(instance == null){
			instance = new LoadScenarioAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of LoadScenarioAction class