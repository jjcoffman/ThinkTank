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
import thinktank.simulator.actions.DeleteEntityAction;
import thinktank.simulator.actions.MoveEntityAction;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.scenario.DEFAULT_SCENARIO;

public class ScenarioBuilderScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	public static final String NAME = "scenario-builder";
	
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
	private Element confirmPopup;
	private Element errorPopup;
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
	private boolean unsavedChanges;
	private boolean leaving;
	private String confirmMessage;
	private String errorMessage;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>ScenarioBuilderScreenController</code>.
	 */
	public ScenarioBuilderScreenController(){
		super();
		isBound = false;
		selectedObjNum = -1;
		savePopup = null;
		confirmPopup = null;
		errorPopup = null;
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
		unsavedChanges = false;
		leaving = false;
		confirmMessage = "Are you sure?";
		errorMessage = "Error!";
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
		for(TANK_TYPE tank : TANK_TYPE.values()){
			tankSizeDropDown.addItem(tank.DISPLAY_NAME);
		}
		for(float temp : Environment.POSSIBLE_TEMPS){
			tempDropDown.addItem(temp+" C");
		}
		unsavedChanges = false;
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
			String currentScenarioName = Starter.getClient().getWorkingScenario().getName();
			TextField saveField = screen.findNiftyControl("scenario-name-field", TextField.class);
			if(!currentScenarioName.equals("Scenario Name")){
				saveField.setText(currentScenarioName);
			}
			Starter.getClient().setInMenus(true);
			nifty.showPopup(nifty.getCurrentScreen(), savePopup.getId(), null);
		}
	}//end of saveScenaio method
	
	public void completeSave(){
		if(isBound){
			TextField saveField = screen.findNiftyControl("scenario-name-field", TextField.class);
			String saveName = saveField.getRealText();
			boolean nameValid = true;
			boolean nameEmpty = false;
			if(saveName.length() > 0 && !saveName.equals("* scenario name *")){
				for(DEFAULT_SCENARIO def : DEFAULT_SCENARIO.values()){
					if(saveName.equals(def.NAME)){
						nameValid = false;
						break;
					}
				}
			}
			else{
				nameEmpty = true;
			}
			if(nameEmpty){
				errorMessage = "Please enter a name for the Scenario.";
				if(errorPopup == null){
					errorPopup = nifty.createPopup("general-error");
				}
				nifty.showPopup(nifty.getCurrentScreen(), errorPopup.getId(), null);
			}
			else if(!nameValid){
				errorMessage = "Cannot use the name of a default Scenario.\nPlease choose another name.";
				if(errorPopup == null){
					errorPopup = nifty.createPopup("general-error");
				}
				nifty.showPopup(nifty.getCurrentScreen(), errorPopup.getId(), null);
			}
			else if(leaving){
				System.out.println("Save name = "+saveName);
				Starter.getClient().setInMenus(false);
//				SaveScenarioAction.getInstance().actionPerformed(null);
				leaving = false;
				nifty.gotoScreen(StartScreenController.NAME);
			}
			else{
				System.out.println("Save name = "+saveName);
				Starter.getClient().setInMenus(false);
//				SaveScenarioAction.getInstance().actionPerformed(null);
			}
			nifty.closePopup(savePopup.getId());
			savePopup = null;
		}
	}//end of completeSave method
	
	public void cancelSave(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(savePopup.getId());
			savePopup = null;
		}
	}//end of cancelSave method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Discard the scenario that is being built or the changes that 
	 * have been made to the selected scenario and return to the 
	 * main menu screen.
	 */
	public void cancel(){
		if(isBound){
			//Note: current implementation requires saving scenario on leaving builder.
			//Therefore, unsaved changes will automatically be discarded.
			confirmMessage = "Are you sure? \nAny unsaved changes will be lost.";
			if(confirmPopup == null){
				confirmPopup = nifty.createPopup("general-confirm");
			}
			Starter.getClient().setInMenus(true);
			nifty.showPopup(nifty.getCurrentScreen(), confirmPopup.getId(), null);
		}
	}//end of loadScenario method
	
	public void done(){
		if(isBound){
			TANK_TYPE tankType = TANK_TYPE.values()[tankSizeDropDown.getSelectedIndex()];
			float tankTemp = Environment.POSSIBLE_TEMPS[tempDropDown.getSelectedIndex()];
			leaving = true;
			//TODO save changes
			//TODO make saved scenario working scenario
			if(isBound){
				nifty.gotoScreen(StartScreenController.NAME);
			}
		}
	}//end of done method
	
	public void confirmYes(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(confirmPopup.getId());
			nifty.gotoScreen(StartScreenController.NAME);
			confirmPopup = null;
		}
	}//end of confirmYes() method
	
	public void confirmNo(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(confirmPopup.getId());
			confirmPopup = null;
		}
	}//end of confirmNo method

	public void errorOK(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(errorPopup.getId());
			errorPopup = null;
		}
	}//end of errorOK method
	
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
			DeleteEntityAction.getInstance().actionPerformed(null);
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
			}
		}
	}//end of setSelectedObject method
	
	public String confirmMessage(){
		return confirmMessage;
	}//end of confirmMessage method
	
	public String errorMessage(){
		return errorMessage;
	}//end of errorMessage method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioBuilderScreenController class