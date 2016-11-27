package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * 
 * @author Bob
 *
 */
public class DeleteScenarioAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -666257834922863482L;
	/**
	 * 
	 */
	public static final String NAME = "delete-scenario";
	
	//---------------------static variables----------------------------
	/**
	 * 
	 */
	private static DeleteScenarioAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private String scenarioName;
	
	//---------------------constructors--------------------------------
	/**
	 * 
	 */
	private DeleteScenarioAction(){
		scenarioName = null;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	/**
	 * 
	 * @param scenarioName
	 */
	public void setScenario(String scenarioName){
		this.scenarioName = scenarioName;
	}//end of setScenario method
	
	/**
	 * 
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
	 * 
	 * @return
	 */
	public static DeleteScenarioAction getInstance(){
		if(instance == null){
			instance = new DeleteScenarioAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of DeleteScenarioAction class