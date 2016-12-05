package thinktank.simulator.ui;

import java.io.File;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * Stores and Maintains data and operations for the "HUD"
 * of the User Interface for scenario building and editing.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class HUDScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	/**
	 * 
	 */
	public static final String NAME = "hud";
	
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
	/**
	 * The number representing the currently selected world object.
	 */
	private int selectedObjNum;
	/**
	 * 
	 */
	private Button playButton;
	/**
	 * 
	 */
	private Button pauseButton;
	/**
	 * @deprecated
	 */
	private Button rewindButton;
	/**
	 * @deprecated
	 */
	private Button fastForwardButton;
	/**
	 * @deprecated
	 */
	private Button skipBackButton;
	/**
	 * @deprecated
	 */
	private Button skipForwardButton;
	/**
	 * @deprecated
	 */
	private Button saveBreakpointButton;
	/**
	 * @deprecated
	 */
	private Button goToBreakpointButton;
	/**
	 * @deprecated
	 */
	private Button savePlaybackButton;
	/**
	 * 
	 */
	private Button endSimulationButton;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>HUDScreenController</code>.
	 */
	public HUDScreenController(){
		super();
		isBound = false;
		selectedObjNum = -1;
		playButton = null;
		pauseButton = null;
		rewindButton = null;
		fastForwardButton = null;
		skipBackButton = null;
		skipForwardButton = null;
		saveBreakpointButton = null;
		goToBreakpointButton = null;
		savePlaybackButton = null;
		endSimulationButton = null;
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

		playButton = screen.findNiftyControl("play-button", Button.class);
		playButton.setFocusable(false);
		pauseButton = screen.findNiftyControl("pause-button", Button.class);
		pauseButton.setFocusable(false);
		/* Unimplemented element statements disabled */
		//rewindButton = screen.findNiftyControl("rewind-button", Button.class);
		//rewindButton.setFocusable(false);
		//fastForwardButton = screen.findNiftyControl("fast-forward-button", Button.class);
		//fastForwardButton.setFocusable(false);
		//skipBackButton = screen.findNiftyControl("skip-back-button", Button.class);
		//skipBackButton.setFocusable(false);
		//skipForwardButton = screen.findNiftyControl("skip-forward-button", Button.class);
		//skipForwardButton.setFocusable(false);
		//saveBreakpointButton = screen.findNiftyControl("save-breakpoint-button", Button.class);
		//saveBreakpointButton.setFocusable(false);
		//goToBreakpointButton = screen.findNiftyControl("goto-breakpoint-button", Button.class);
		//goToBreakpointButton.setFocusable(false);
		//savePlaybackButton = screen.findNiftyControl("save-playback-button", Button.class);
		//savePlaybackButton.setFocusable(false);
		endSimulationButton = screen.findNiftyControl("end-simulation-button", Button.class);
		endSimulationButton.setFocusable(false);
		
		isBound = true;
	}//end of bind method

	/**
	 * Called as a result of the screen no longer being displayed.
	 */
	@Override
	public void onEndScreen(){
		System.out.println("HUD: onEndScreen called!");
	}//end of onEndScreen method

	/**
	 * Called as a result of the screen initially being displayed.
	 */
	@Override
	public void onStartScreen(){
		System.out.println("HUD: onStartScreen called!");
	}//end of onStartScreen method
	
	//ACTION METHODS
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * 
	 */
	public void play(){
		if(isBound){
			if(Starter.getClient().isPaused()){
				Starter.getClient().onAction("Pause", true, -1);
			}
		}
	}//end of play method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * 
	 */
	public void pause(){
		if(isBound){
			if(!Starter.getClient().isPaused()){
				Starter.getClient().onAction("Pause", true, -1);
			}
		}
	}//end of pause method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * @deprecated
	 */
	public void rewind(){
		if(isBound){
			//TODO rewind the simulation
		}
	}//end of rewind method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * @deprecated
	 */
	public void fastForward(){
		if(isBound){
			//TODO fast forward the simulation
		}
	}//end of fastForward method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * @deprecated
	 */
	public void skipBack(){
		if(isBound){
			//TODO skill back in the simulation
		}
	}//end of skipBack method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * @deprecated
	 */
	public void skipForward(){
		if(isBound){
			//TODO skip forward in the simulation
		}
	}//end of skipForward method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * @deprecated
	 */
	public void saveBreakpoint(){
		if(isBound){
			//TODO save a breakpoint
		}
	}//end of saveBreakpoint method
	
	/**
	 * Sets the object indicated by the provided number as the selected object.
	 * 
	 * @deprecated
	 */
	public void goToBreakpoint(){
		if(isBound){
			//TODO go to breakpoint
		}
	}//end of goToBreakpoint method
	
	/**
	 * @deprecated
	 */
	public void savePlayback(){
		if(isBound){
			//TODO save playback
		}
	}//end of savePlayback method
	
	/**
	 * 
	 */
	public void endSimulation(){
		if(isBound){
			if(!Starter.getClient().isPaused()){
				Starter.getClient().onAction("Pause", true, -1);
			}
			Scenario reloadedScenario = null;
			String scenName = Starter.getClient().getWorkingScenario().getName();
			if(ScenarioDefinition.isDefault(scenName)){
				reloadedScenario = ScenarioDefinition.genScenario(scenName);
			}
			else{
				reloadedScenario = ScenarioIO.loadScenario(new File(scenName));
			}
			if(reloadedScenario != null){
				Starter.getClient().setWorkingScenario(reloadedScenario);
			}
			else{
				Starter.getClient().setWorkingScenarioToDefault();
			}
			nifty.gotoScreen(StartScreenController.NAME);
		}
	}//end of endSimulation method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of HUDScreenController class