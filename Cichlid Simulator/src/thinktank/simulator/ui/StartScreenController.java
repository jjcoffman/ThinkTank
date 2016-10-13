package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.actions.ToggleMouselookAction;

/**
 * Stores and Maintains data and operations for the "Start Screen"
 * of the User Interface.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class StartScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
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
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>StartScreenController</code>.
	 */
	public StartScreenController(){
		super();
		isBound = false;
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
		isBound = true;
	}//end of bind method

	/**
	 * Called as a result of the screen no longer being displayed.
	 */
	@Override
	public void onEndScreen(){
		System.out.println("Start: onEndScreen called!");
	}//end of onEndScreen method

	/**
	 * Called as a result of the screen initially being displayed.
	 */
	@Override
	public void onStartScreen(){
		System.out.println("Start: onStartScreen called!");
	}//end of onStartScreen method
	
	//ACTION METHODS
	public void enterScenarioBuilderNew(String builderScreen){
		if(isBound){
			//TODO go to builder screen
		}
	}//end of enterScenarioBuilderNew method
	
	public void scenarioMenu(String mainMenuScreen){
		if(isBound){
			//TODO go to main menu
		}
	}//end of scenarioMenu method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Transitions the screen to the "HUD" screen and allows the game to run.
	 * 
	 * @param nextScreen denotes the next screen to display.
	 */
	public void startGame(String nextScreen){
		if(isBound){
			ToggleMouselookAction.getInstance().actionPerformed(null);
			Starter.getClient().setInMenus(false);
			nifty.gotoScreen(nextScreen);  // switch to another screen
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