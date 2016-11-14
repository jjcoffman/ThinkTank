package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import Game.Main;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.actions.ToggleMouselookAction;
import thinktank.simulator.scenario.Scenario;

/**
 * Stores and Maintains data and operations for the "Start Screen"
 * of the User Interface.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class StartScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	public static final String NAME = "start";
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * Reference to the <code>Nifty</code> object for the user interface.
	 */
	private Nifty nifty;
	/**
	 * Reference to the <code>Screen</code> object for the Start Screen.
	 */
	private Screen screen;
	/**
	 * Whether or not the controller has yet been bound to the screen.
	 */
	private boolean isBound;
	private Button newScenarioButton;
	private Button loadScenarioButton;
	private Button editScenarioButton;
	private Button startSimulationButton;
	private Button exitButton;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>StartScreenController</code>.
	 */
	public StartScreenController(){
		super();
		isBound = false;
		newScenarioButton = null;
		loadScenarioButton = null;
		editScenarioButton = null;
		startSimulationButton = null;
		exitButton = null;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	/**
	 * Sets up the controller.
	 * 
	 * @param stateManager the <code>AppStateManager</code> object.
	 * @param app the <code>Application</code> object for the game.
	 */
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
    }//end of initialize method
 
    /**
     * Allows the controller to perform operations as part of the game loop.
     * 
     * @param tpf time elapsed since last update.
     */
    @Override
    public void update(float tpf){
    }//end of update method
 
    /**
     * Cleanup app state.
     */
    @Override
    public void cleanup(){
        super.cleanup();
    }//end of cleanup method
 
    /**
     * Binds the controller to the screen.
     * 
     * @param nifty the <code>Nifty</code> object.
     * @param screen the <code>Screen</code> object.
     */
	@Override
	public void bind(Nifty nifty, Screen screen){
		this.nifty = nifty;
		this.screen = screen;

		newScenarioButton = screen.findNiftyControl("new-scenario-button", Button.class);
		newScenarioButton.setFocusable(false);
		loadScenarioButton = screen.findNiftyControl("load-scenario-button", Button.class);
		loadScenarioButton.setFocusable(false);
		editScenarioButton = screen.findNiftyControl("edit-scenario-button", Button.class);
		editScenarioButton.setFocusable(false);
		startSimulationButton = screen.findNiftyControl("enter-simulation-button", Button.class);
		startSimulationButton.setFocusable(false);
		exitButton = screen.findNiftyControl("exit-button", Button.class);
		exitButton.setFocusable(false);
		
		isBound = true;
	}//end of bind method

	/**
	 * Called as a result of the screen no longer being displayed.
	 */
	@Override
	public void onEndScreen(){
	}//end of onEndScreen method

	/**
	 * Called as a result of the screen initially being displayed.
	 */
	@Override
	public void onStartScreen(){
		Starter.getClient().setInMenus(true);
		if(!Main.isLoading() && Starter.getClient().isMouselookActive()){
			Starter.getClient().toggleMouseMode();
		}
	}//end of onStartScreen method
	
	//ACTION METHODS
	public void enterScenarioBuilderNew(){
		if(isBound){
			//Note* simulation should not be running
			Scenario newScenario = Scenario.createScenario();
			Starter.getClient().setWorkingScenario(newScenario);
			ToggleMouselookAction.getInstance().actionPerformed(null);
			Starter.getClient().setInMenus(false);
			Starter.getClient().getWorkingScenario().setEditingMode(true);
			nifty.gotoScreen(ScenarioBuilderScreenController.NAME);  // switch to another screen
		}
	}//end of enterScenarioBuilderNew method
	
	public void scenarioMenu(){
		if(isBound){
			nifty.gotoScreen(ScenarioListScreenController.NAME);  // switch to another screen
		}
	}//end of scenarioMenu method
	
	public void enterScenarioBuilder(){
		if(isBound){
			//Note* simulation should not be running
			ToggleMouselookAction.getInstance().actionPerformed(null);
			Starter.getClient().setInMenus(false);
			Starter.getClient().getWorkingScenario().setEditingMode(true);
			nifty.gotoScreen(ScenarioBuilderScreenController.NAME);  // switch to another screen
		}
	}//end of enterScenarioBuilder method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Transitions the screen to the "HUD" screen and allows the game to run.
	 * 
	 * @param simulationScreen denotes the next screen to display.
	 */
	public void startGame(){
		if(isBound){
			ToggleMouselookAction.getInstance().actionPerformed(null);
			Starter.getClient().setInMenus(false);
			//TODO start simulation
			nifty.gotoScreen(HUDScreenController.NAME);  // switch to another screen
		}
	}//end of startGame method
	 
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Quits the game.
	 */
	public void quitGame(){
		if(isBound){
			Starter.getClient().stop();
		}
	}//end of quitGame method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of StartScreenController class