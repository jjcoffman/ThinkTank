package thinktank.simulator.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.actions.DeleteScenarioAction;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.scenario.ScenarioIO;
import thinktank.simulator.util.ConfigLoader;

/**
 * Stores and Maintains data and operations for the "Scenario List 
 * Screen" of the User Interface.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class ScenarioListScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	/**
	 * The name of the screen this controller belongs to.
	 */
	public static final String NAME = "scenario-list";
	
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
	 * Reference to the "Error" nifty popup.
	 */
	private Element errorPopup;
	/**
	 * Reference to the "Confirm" nifty popup.
	 */
	private Element confirmPopup;
	/**
	 * Reference to the "Loading" nifty popup.
	 */
	private Element loadingPopup;
	/**
	 * Reference to the "Scenario List" nifty element.
	 */
	private ListBox<String> scenarioListBox;
	/**
	 * Reference to the "Load" button nifty element.
	 */
	private Button loadScenarioButton;
	/**
	 * Reference to the "Delete" button nifty element.
	 */
	private Button deleteScenarioButtion;
	/**
	 * Reference to the "Back" button nifty element.
	 */
	private Button backButton;
	/**
	 * Whether or not the controller has yet been bound to the screen.
	 */
	private boolean isBound;
	/**
	 * The name of the scenario to be deleted by the delete scenario action.
	 */
	private String toDelete;
	/**
	 * The value of the message to be displayed by the error popup.
	 */
	private String errorMessage;
	/**
	 * The value of the message to be displayed by the loading popup.
	 */
	private String loadingMessage;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>StartScreenController</code>.
	 */
	public ScenarioListScreenController(){
		super();
		isBound = false;
		confirmPopup = null;
		errorPopup = null;
		loadingPopup = null;
		loadScenarioButton = null;
		deleteScenarioButtion = null;
		backButton = null;
		toDelete = "";
		errorMessage = "Error!";
		loadingMessage = "Loading, please wait...";
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
	@SuppressWarnings("unchecked")
	@Override
	public void bind(Nifty nifty, Screen screen){
		this.nifty = nifty;
		this.screen = screen;
		
		scenarioListBox = screen.findNiftyControl("scenario-list", ListBox.class);
		scenarioListBox.setFocusable(false);
		loadScenarioButton = screen.findNiftyControl("load-scenario-button", Button.class);
		loadScenarioButton.setFocusable(false);
		deleteScenarioButtion = screen.findNiftyControl("delete-scenario-button", Button.class);
		deleteScenarioButtion.setFocusable(false);
		backButton = screen.findNiftyControl("back-button", Button.class);
		backButton.setFocusable(false);
		
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
		Element background = screen.findElementByName("bg-img");
		background.setHeight(ConfigLoader.getWindowHeight());
		background.setWidth(ConfigLoader.getWindowWidth());
		Starter.getClient().refreshScenarioList();
		ArrayList<String> scenarioList = Starter.getClient().getScenarioNames();
		scenarioListBox.clear();
		for(String scenarioName : scenarioList){
			scenarioListBox.addItem(scenarioName);
		}
	}//end of onStartScreen method
	
	//ACTION METHODS
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Transitions the screen to the "Start" screen.
	 * 
	 * @param nextScreen denotes the start screen to display.
	 */
	public void loadSelected(){
		if(isBound){
			List<String> selected = scenarioListBox.getSelection();
			if(selected.size() > 0){
				Scenario scenario = null;
				String scenName = selected.get(0);
				loadingMessage = "Loading scenario, please wait...";
				if(loadingPopup == null){
					loadingPopup = nifty.createPopup("loading-popup");
				}
				nifty.showPopup(nifty.getCurrentScreen(), loadingPopup.getId(), null);
				if(ScenarioDefinition.isDefault(scenName)){
					scenario = ScenarioDefinition.genScenario(scenName);
				}
				else{
					scenario = ScenarioIO.loadScenario(new File(scenName));
				}
				nifty.closePopup(loadingPopup.getId());
				loadingPopup = null;
				if(scenario != null){
					Starter.getClient().setWorkingScenario(scenario);
					nifty.gotoScreen(StartScreenController.NAME);
				}
				else{
					errorMessage = "Unable to load the selected scenario.\n"
							+ "Please try again or make a new selection.";
					if(errorPopup == null){
						errorPopup = nifty.createPopup("general-error");
					}
					nifty.showPopup(nifty.getCurrentScreen(), errorPopup.getId(), null);
				}
			}
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
	public void mainMenu(){
		if(isBound){
			nifty.gotoScreen(StartScreenController.NAME);
		}
	}//end of quitGame method
	 
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Deletes the Scenario that is currently selected in the list.
	 */
	public void deleteSelected(){
		if(isBound){
			List<String> selected = scenarioListBox.getSelection();
			String scenName = selected.get(0);
			if(ScenarioDefinition.isDefault(scenName)){
				errorMessage = "Cannot delete default scenarios.\n"
						+ "Please make a new selection.";
				if(errorPopup == null){
					errorPopup = nifty.createPopup("general-error");
				}
				nifty.showPopup(nifty.getCurrentScreen(), errorPopup.getId(), null);
			}
			else{
				if(confirmPopup == null){
					toDelete = scenName;
					confirmPopup = nifty.createPopup("delete-confirm");
				}
				nifty.showPopup(nifty.getCurrentScreen(), confirmPopup.getId(), null);
			}
		}
	}//end of quitGame method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Completes the action initiated by the user, acknowledging 
	 * their acceptance of the terms.
	 */
	public void confirm(){
		if(isBound){
			scenarioListBox.removeItem(toDelete);
			DeleteScenarioAction.getInstance().setScenario(toDelete);
			DeleteScenarioAction.getInstance().actionPerformed(null);
			nifty.closePopup(confirmPopup.getId());
			confirmPopup = null;
		}
	}//end of confirm method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Cancels the action initiated by the user, due to 
	 * their non-acceptance of the terms.
	 */
	public void cancel(){
		if(isBound){
			toDelete = "";
			nifty.closePopup(confirmPopup.getId());
			confirmPopup = null;
		}
	}//end of cancel method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Closes the error popup.
	 */
	public void errorOK(){
		if(isBound){
			nifty.closePopup(errorPopup.getId());
			errorPopup = null;
		}
	}//end of errorOK method
	
	/**
	 * Returns the value of the error message. Allows the message to 
	 * be accessed and updated from elsewhere by nifty.
	 * 
	 * @return the error message.
	 */
	public String errorMessage(){
		return errorMessage;
	}//end of errorMessage method
	
	/**
	 * Returns the value of the loading message. Allows the message to 
	 * be accessed and updated from elsewhere by nifty.
	 * 
	 * @return the loading message.
	 */
	public String loadingMessage(){
		return loadingMessage;
	}//end of loadingMessage method
	
	/**
	 * This event handler is directly listening to the ListBoxSelectionChangedEvent that is generated when
	 * the ListBox selection is changed. This method is subscribed to the ListBox with the id="listBox".
	 */
	@NiftyEventSubscriber(id="listBox")
	public void onListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> evt){
		//available for selection changed reactions
	}//end of onListBoxSelectionChanged method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioListScreenController class