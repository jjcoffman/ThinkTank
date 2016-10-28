package thinktank.simulator.ui;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import Game.Main;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.listbox.ListBoxControl;
import de.lessvoid.nifty.controls.listbox.builder.CreateListBoxControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.actions.ToggleMouselookAction;

/**
 * Stores and Maintains data and operations for the "Scenario List 
 * Screen" of the User Interface.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class ScenarioListScreenController extends AbstractAppState implements ScreenController{
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
	private ListBox<String> scenarioListBox;
	/**
	 * Whether or not the controller has yet been bound to the screen.
	 */
	private boolean isBound;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>StartScreenController</code>.
	 */
	public ScenarioListScreenController(){
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
		
		scenarioListBox = screen.findNiftyControl("scenario-list", ListBox.class);
		
		isBound = true;
	}//end of bind method

	/**
	 * Called as a result of the screen no longer being displayed.
	 */
	@Override
	public void onEndScreen(){
		System.out.println("Scenario List: onEndScreen called!");
	}//end of onEndScreen method

	/**
	 * Called as a result of the screen initially being displayed.
	 */
	@Override
	public void onStartScreen(){
		System.out.println("Scenario List: onStartScreen called!");
//		ArrayList<String> scenarioList = Main.getScenarioNames();
//		for(String scenarioName : scenarioList){
//			scenarioListBox.addItem(scenarioName);
//		}
	}//end of onStartScreen method
	
	//ACTION METHODS
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Transitions the screen to the "Start" screen.
	 * 
	 * @param nextScreen denotes the start screen to display.
	 */
	public void loadSelected(String startScreen){
		if(isBound){
			List<String> selected = scenarioListBox.getSelection();
			if(selected.size() > 0){
//				Main.setWorkingScenario(selected.get(0));
			}
			nifty.gotoScreen(startScreen);
		}
	}//end of startGame method
	 
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Loads the Scenario that is currently selected in the list, 
	 * and returns to the "Start" screen.
	 * 
	 * @param startScreen denotes the start screen to display.
	 */
	public void mainMenu(String startScreen){
		if(isBound){
			nifty.gotoScreen(startScreen);
		}
	}//end of quitGame method
	 
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Deletes the Scenario that is currently selected in the list.
	 */
	public void deleteSelected(){
		if(isBound){
			//TODO delete selected scenario
		}
	}//end of quitGame method
	
	/**
	 * This event handler is directly listening to the ListBoxSelectionChangedEvent that is generated when
	 * the ListBox selection is changed. This method is subscribed to the ListBox with the id="listBox".
	 */
	@NiftyEventSubscriber(id="listBox")
	public void onListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event){
		//available for selection changed reactions
	}//end of onListBoxSelectionChanged method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioListScreenController class