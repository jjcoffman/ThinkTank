package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.Starter;
import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.MoveEntityAction;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;

public class ScenarioBuilderScreenController extends AbstractAppState implements ScreenController{
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
	/**
	 * The number representing the currently selected world object.
	 */
	private int selectedObjNum;
	private Element savePopup;
	private DropDown<String> tankSizeDropDown;
	private DropDown<String> tempDropDown;
	private DropDown<String> colorDropDown;
	private DropDown<String> sizeDropDown;
	private Button saveButton;
	private Button cancelButton;
	private Button doneButton;
	private Button addFishButton;
	private Button addPotButton;
	private Button addPlantButton;
	private Button deleteButton;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>ScenarioBuilderScreenController</code>.
	 */
	public ScenarioBuilderScreenController(){
		super();
		isBound = false;
		selectedObjNum = -1;
		savePopup = null;
		tankSizeDropDown = null;
		tempDropDown = null;
		colorDropDown = null;
		sizeDropDown = null;
		saveButton = null;
		cancelButton = null;
		doneButton = null;
		addFishButton = null;
		addPotButton = null;
		addPlantButton = null;
		deleteButton = null;
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

		tankSizeDropDown = screen.findNiftyControl("tank-size-drop-down", DropDown.class);
		tankSizeDropDown.setFocusable(false);
		tempDropDown = screen.findNiftyControl("temp-drop-down", DropDown.class);
		tempDropDown.setFocusable(false);
		colorDropDown = screen.findNiftyControl("color-drop-down", DropDown.class);
		colorDropDown.setFocusable(false);
		sizeDropDown = screen.findNiftyControl("size-drop-down", DropDown.class);
		sizeDropDown.setFocusable(false);
		saveButton = screen.findNiftyControl("save-button", Button.class);
		saveButton.setFocusable(false);
		cancelButton = screen.findNiftyControl("cancel-button", Button.class);
		cancelButton.setFocusable(false);
		doneButton = screen.findNiftyControl("done-button", Button.class);
		doneButton.setFocusable(false);
		addFishButton = screen.findNiftyControl("add-fish-button", Button.class);
		addFishButton.setFocusable(false);
		addPotButton = screen.findNiftyControl("add-pot-button", Button.class);
		addPotButton.setFocusable(false);
		addPlantButton = screen.findNiftyControl("add-plant-button", Button.class);
		addPlantButton.setFocusable(false);
		deleteButton = screen.findNiftyControl("delete-button", Button.class);
		deleteButton.setFocusable(false);
		
		isBound = true;
	}//end of bind method

	/**
	 * Called as a result of the screen no longer being displayed.
	 */
	@Override
	public void onEndScreen(){
		System.out.println("Scenario Builder: onEndScreen called!");
		tankSizeDropDown.clear();
		tempDropDown.clear();
		MoveEntityAction.getInstance().setTargetState(false);
		MoveEntityAction.getInstance().actionPerformed(null);
	}//end of onEndScreen method

	/**
	 * Called as a result of the screen initially being displayed.
	 */
	@Override
	public void onStartScreen(){
		System.out.println("Scenario Builder: onStartScreen called!");
		for(TANK_TYPE tank : TANK_TYPE.values()){
			tankSizeDropDown.addItem(tank.DISPLAY_NAME);
		}
		for(float temp : Environment.POSSIBLE_TEMPS){
			tempDropDown.addItem(temp+" C");
		}
		MoveEntityAction.getInstance().setTargetState(true);
		MoveEntityAction.getInstance().actionPerformed(null);
	}//end of onStartScreen method
	
	//ACTION METHODS
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Saves the current scenario.
	 */
	public void saveScenario(){
		if(isBound){
			TANK_TYPE tankType = TANK_TYPE.values()[tankSizeDropDown.getSelectedIndex()];
			float tankTemp = Environment.POSSIBLE_TEMPS[tempDropDown.getSelectedIndex()];
			if(savePopup == null){
				savePopup = nifty.createPopup("save-scenario");
			}
			Starter.getClient().setInMenus(true);
			nifty.showPopup(nifty.getCurrentScreen(), savePopup.getId(), null);
//			SaveScenarioAction.getInstance().actionPerformed(null);
		}
	}//end of saveScenaio method
	
	public void completeSave(){
		if(isBound){
			//TODO check if name is valid (not default, used, null)
			TextField saveField = screen.findNiftyControl("scenario-name-field", TextField.class);
			String saveName = saveField.getDisplayedText();
			System.out.println("Save name = "+saveName);
			Starter.getClient().setInMenus(false);
			nifty.closePopup(savePopup.getId());
		}
	}//end of completeSave method
	
	public void cancelSave(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(savePopup.getId());
		}
	}//end of cancelSave method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Discard the scenario that is being built or the changes that 
	 * have been made to the selected scenario and return to the 
	 * main menu screen.
	 */
	public void cancel(String mainMenuScreen){
		if(isBound){
			//TODO discard changes
			if(isBound){
				nifty.gotoScreen(mainMenuScreen);
			}
		}
	}//end of loadScenario method
	
	public void done(String mainMenuScreen){
		if(isBound){
			TANK_TYPE tankType = TANK_TYPE.values()[tankSizeDropDown.getSelectedIndex()];
			float tankTemp = Environment.POSSIBLE_TEMPS[tempDropDown.getSelectedIndex()];
			//TODO save changes
			//TODO make saved scenario working scenario
			if(isBound){
				nifty.gotoScreen(mainMenuScreen);
			}
		}
	}//end of done method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Adds a new fish to the scenario.
	 */
	public void addFish(){
		if(isBound){
			AddFishAction.getInstance().actionPerformed(null);
		}
	}//end of addFish method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Adds a new pot to the scenario.
	 */
	public void addPot(){
		if(isBound){
			AddPotAction.getInstance().actionPerformed(null);
		}
	}//end of addPot method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Adds a new plant to the scenario.
	 */
	public void addPlant(){
		if(isBound){
			AddPlantAction.getInstance().actionPerformed(null);
		}
	}//end of addPlant method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Deletes the currently selected object.
	 */
	public void deleteEntity(){
		if(isBound){
			//TODO delete selected object
		}
	}//end of deleteEntity method
	
	/**
	 * Sets the object indicated by the provided number as the selected object.
	 * 
	 * Parameter is provided as a string, but must parse to an integer as in 
	 * {@link Integer#parseInt(String)}.
	 * 
	 * @param objNum the <code>String</code> representation of the object number.
	 */
	public void setSelectedObject(String objNum){
		if(isBound){
			int objectNum = -1;
			try{
				objectNum = Integer.parseInt(objNum);
			}
			catch(NumberFormatException ex){
				ex.printStackTrace();
			}
			if(objectNum != -1){
				selectedObjNum = objectNum;
				//TODO highlight selected object in game world
			}
		}
	}//end of setSelectedObject method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioBuilderScreenController class