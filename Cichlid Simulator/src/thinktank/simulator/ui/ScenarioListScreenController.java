package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
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
		
		// create scenario list box
		Element parentPanel = screen.findElementByName("panel_scenario_list");
		CreateListBoxControl scenarioListboxCreate = new CreateListBoxControl("listBoxDynamic");
//		scenarioListboxCreate.set("horizontal", "false");
		scenarioListboxCreate.setWidth("*");
		scenarioListboxCreate.setHeight("100%");
		scenarioListboxCreate.setChildLayout("vertical");
		ListBox<?> scenarioListbox = scenarioListboxCreate.create(nifty, screen, parentPanel);
//		scenarioListbox.addSelectionListener();
//				new ListBoxControl.SelectionListener(){
//			@Override
//			public void onSelectionChanged(final ListBoxControl listBoxControl, final int newIndex){
//				System.out.println("Selection Changed on ListBoxControl: " + listBoxControl.getElement().getId() + " to index: " + newIndex);
//			}
//		});
//		for (int i=0; i<10; i++) {
//			scenarioListbox.addItem("Listbox Item: " + i);
//		}
		
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
			//TODO get selected scenario
			//TODO set selected scenario as active
			nifty.gotoScreen(startScreen);
		}
	}//end of startGame method
	 
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Loads the Scenario that is currently selected in the list, 
	 * and returns to the "Start" screen.
	 * 
	 * @param nextScreen denotes the start screen to display.
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
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioListScreenController class