package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
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
	private static SaveScenarioAction instance;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private SaveScenarioAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO temporary. file selection needed.
		ScenarioIO.saveScenario(Starter.getClient().getWorkingScenario(), new File("Test_Scenario"));
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static SaveScenarioAction getInstance(){
		if(instance == null){
			instance = new SaveScenarioAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of SaveScenarioAction class