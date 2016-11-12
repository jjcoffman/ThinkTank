package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;
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
	private TANK_TYPE tankType;
	private float temp;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>SaveScenarioAction</code>.
	 */
	private SaveScenarioAction(){
		tankType = null;
		temp = 0.0f;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//SETTERS
	public void setTankType(TANK_TYPE type){
		this.tankType = type;
	}//end of setTankType method
	
	public void setTemp(float temp){
		this.temp = temp;
	}//end of setTemp method
	
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
			scenario.getEnvironment().setTempCelcius(temp);
			scenario.getEnvironment().setTank(Tank.createTank(tankType));
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