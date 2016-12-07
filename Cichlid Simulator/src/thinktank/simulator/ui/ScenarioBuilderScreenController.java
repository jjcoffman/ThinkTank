package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
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
import thinktank.simulator.actions.SaveScenarioAction;
import thinktank.simulator.actions.SelectEntityAction;
import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.Cichlid.POSSIBLE_COLORS;
import thinktank.simulator.entity.Cichlid.POSSIBLE_SIZES;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.DEFAULT_SCENARIO;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.util.IObservable;
import thinktank.simulator.util.IObserver;

/**
 * Stores and maintains data and operations for the user interface for 
 * scenario building and editing.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class ScenarioBuilderScreenController extends AbstractAppState implements ScreenController, IObserver{
	//---------------------static constants----------------------------
	/**
	 * The name of the screen this controller belongs to.
	 */
	public static final String NAME = "scenario-builder";
	
	//---------------------static variables----------------------------
	/**
	 * Flag for whether a select operation is currently being processed.
	 */
	public static boolean selecting = false;
	/**
	 * Flag for whether there are currently changes to the scenario that 
	 * have not yet been saved.
	 */
	public static boolean unsaved_changes = false;
	
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
	@SuppressWarnings("unused")
	private int selectedObjNum;
	/**
	 * Reference to the "Save" nifty popup.
	 */
	private Element savePopup;
	/**
	 * Reference to the "Confirm" nifty popup.
	 */
	private Element confirmPopup;
	/**
	 * Reference to the "Error" nifty popup.
	 */
	private Element errorPopup;
	/**
	 * Reference to the scenario name nifty element.
	 */
	private Label nameLabel;
	/**
	 * Reference to the "Tank Size" drop down menu nifty element.
	 */
	private DropDown<String> tankSizeDropDown;
	/**
	 * Reference to the "Tank Temperature" drop down menu nifty element.
	 */
	private DropDown<String> tempDropDown;
	/**
	 * Reference to the "Fish Color" drop down menu nifty element.
	 */
	private DropDown<String> colorDropDown;
	/**
	 * Reference to the "Fish Size" drop down menu nifty element.
	 */
	private DropDown<String> sizeDropDown;
	/**
	 * Reference to the "Save" button nifty element.
	 */
	private Button saveButton;
	/**
	 * Reference to the "Cancel" button nifty element.
	 */
	private Button cancelButton;
	/**
	 * Reference to the "Done" button nifty element.
	 */
	private Button doneButton;
	/**
	 * Reference to the "Add Fish" button nifty element.
	 */
	private Button addFishButton;
	/**
	 * Reference to the "Add Pot" button nifty element.
	 */
	private Button addPotButton;
	/**
	 * Reference to the "Add Plant" button nifty element.
	 */
	private Button addPlantButton;
	/**
	 * Reference to the "Delete" button nifty element.
	 */
	private Button deleteButton;
	/**
	 * Flag for whether or not the user is in the process of leaving 
	 * this screen.
	 */
	private boolean leaving;
	/**
	 * Flag for whether or not this screen is in the process of 
	 * loading.
	 */
	private boolean loading;
	/**
	 * The value of the message to be displayed by the confirm popup.
	 */
	private String confirmMessage;
	/**
	 * The value of the message to be displayed by the error popup.
	 */
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
		nameLabel = null;
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
		leaving = false;
		loading = false;
		confirmMessage = "Are you sure?";
		errorMessage = "Error!";
		SelectEntityAction.getInstance().addObserver(this);
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
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

		nameLabel = screen.findNiftyControl("scenario-name-label", Label.class);
		nameLabel.setFocusable(false);
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
		loading = true;
		Scenario scenario = Starter.getClient().getWorkingScenario();
		nameLabel.setText(scenario.getName());
		for(TANK_TYPE tank : TANK_TYPE.values()){
			tankSizeDropDown.addItem(tank.DISPLAY_NAME);
		}
		for(float temp : Environment.POSSIBLE_TEMPS){
			tempDropDown.addItem(temp+" C");
		}
		tankSizeDropDown.selectItem(scenario.getEnvironment().getTank().getType().DISPLAY_NAME);
		tempDropDown.selectItem(scenario.getEnvironment().getTempCelcius()+" C");
		colorDropDown.clear();
		colorDropDown.disable();
		sizeDropDown.clear();
		sizeDropDown.disable();
		if(scenario.getName().equals(Scenario.DEFAULT_NEW_SCENARIO_NAME)){
			unsaved_changes = true;
		}
		else{
			unsaved_changes = false;
		}
		MoveEntityAction.getInstance().setTargetState(true);
		MoveEntityAction.getInstance().actionPerformed(null);
		loading = false;
	}//end of onStartScreen method
	
	//ACTION METHODS
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Initiates the process to save the current scenario.
	 */
	public void saveScenario(){
		if(isBound){
			leaving = false;
			if(savePopup == null){
				savePopup = nifty.createPopup("save-scenario");
			}
			String currentScenarioName = Starter.getClient().getWorkingScenario().getName();
			TextField saveField = screen.findNiftyControl("scenario-name-field", TextField.class);
			if(!currentScenarioName.equals("Scenario Name") && !currentScenarioName.equals(Scenario.DEFAULT_NEW_SCENARIO_NAME)){
				saveField.setText(currentScenarioName);
			}
			Starter.getClient().setInMenus(true);
			nifty.showPopup(nifty.getCurrentScreen(), savePopup.getId(), null);
		}
	}//end of saveScenaio method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Finishes the process to save the current scenario.
	 */
	public void completeSave(){
		if(isBound){
			TANK_TYPE tankType = TANK_TYPE.values()[tankSizeDropDown.getSelectedIndex()];
			float tankTemp = Environment.POSSIBLE_TEMPS[tempDropDown.getSelectedIndex()];
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
				Starter.getClient().setInMenus(false);
				Starter.getClient().getWorkingScenario().setName(saveName);
				SaveScenarioAction.getInstance().setTankType(tankType);
				SaveScenarioAction.getInstance().setTemp(tankTemp);
				SaveScenarioAction.getInstance().actionPerformed(null);
				nifty.closePopup(savePopup.getId());
				savePopup = null;
				leaving = false;
				unsaved_changes = false;
				nifty.gotoScreen(StartScreenController.NAME);
			}
			else{
				Starter.getClient().setInMenus(false);
				Starter.getClient().getWorkingScenario().setName(saveName);
				SaveScenarioAction.getInstance().setTankType(tankType);
				SaveScenarioAction.getInstance().setTemp(tankTemp);
				SaveScenarioAction.getInstance().actionPerformed(null);
				nameLabel.setText(saveName);
				unsaved_changes = false;
				nifty.closePopup(savePopup.getId());
				savePopup = null;
			}
		}
	}//end of completeSave method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Cancels the process to shave the current scenario.
	 */
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
			if(unsaved_changes){
				confirmMessage = "Are you sure? \nAny unsaved changes will be lost.";
				if(confirmPopup == null){
					confirmPopup = nifty.createPopup("general-confirm");
				}
				Starter.getClient().setInMenus(true);
				nifty.showPopup(nifty.getCurrentScreen(), confirmPopup.getId(), null);
			}
			else{
				nifty.gotoScreen(StartScreenController.NAME);
			}
		}
	}//end of loadScenario method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Returns to the main menu, pending confirmation by the user to save 
	 * any unsaved changes.
	 */
	public void done(){
		if(isBound){
			leaving = true;
			if(unsaved_changes){
				if(savePopup == null){
					savePopup = nifty.createPopup("save-scenario");
				}
				String currentScenarioName = Starter.getClient().getWorkingScenario().getName();
				TextField saveField = screen.findNiftyControl("scenario-name-field", TextField.class);
				if(!currentScenarioName.equals("Scenario Name") && !currentScenarioName.equals(Scenario.DEFAULT_NEW_SCENARIO_NAME)){
					saveField.setText(currentScenarioName);
				}
				Starter.getClient().setInMenus(true);
				nifty.showPopup(nifty.getCurrentScreen(), savePopup.getId(), null);
			}
			else{
				nifty.gotoScreen(StartScreenController.NAME);
			}
		}
	}//end of done method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Completes the action initiated by the user, acknowledging 
	 * their acceptance of the terms.
	 */
	public void confirmYes(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(confirmPopup.getId());
			nifty.gotoScreen(StartScreenController.NAME);
			confirmPopup = null;
		}
	}//end of confirmYes method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Cancels the action initiated by the user, due to 
	 * their non-acceptance of the terms.
	 */
	public void confirmNo(){
		if(isBound){
			Starter.getClient().setInMenus(false);
			nifty.closePopup(confirmPopup.getId());
			confirmPopup = null;
		}
	}//end of confirmNo method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Closes the error popup.
	 */
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
	
	/**
	 * Returns the value of the confirm message. Allows the message to 
	 * be accessed and updated from elsewhere by nifty.
	 * 
	 * @return the confirm message.
	 */
	public String confirmMessage(){
		return confirmMessage;
	}//end of confirmMessage method
	
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
	 * Reacts to a change of the selected value of one of the drop 
	 * down menus.
	 * 
	 * @param id the id value of the event.
	 * @param evt the triggering event.
	 */
	@NiftyEventSubscriber(pattern=".*drop-down")
	public void onDropDownSelectionChanged(final String id, final DropDownSelectionChangedEvent<String> evt){
		if(!loading){
			if(evt.getDropDown().equals(tankSizeDropDown)){
				int index = evt.getSelectionItemIndex();
				int i = 0;
				for(TANK_TYPE tankType : TANK_TYPE.values()){
					if(index == i){
						Starter.getClient().getWorkingScenario().getEnvironment().setTank(Tank.createTank(tankType));
						Starter.getClient().setGrid();
						unsaved_changes = true;
						break;
					}
					i++;
				}
			}
			else if(evt.getDropDown().equals(tempDropDown)){
				int index = evt.getSelectionItemIndex();
				float newTemp = Environment.POSSIBLE_TEMPS[index];
				Environment environ = Starter.getClient().getWorkingScenario().getEnvironment();
				if(environ.getTempCelcius() != newTemp){
					environ.setTempCelcius(newTemp);
					unsaved_changes = true;
				}
			}
			else if(!selecting && evt.getDropDown().equals(colorDropDown)){
				Entity selectedEntity = Starter.getClient().getWorkingScenario().getSelectedEntity();
				if(selectedEntity instanceof Cichlid){
					int index = colorDropDown.getSelectedIndex();
					((Cichlid)selectedEntity).setColor(POSSIBLE_COLORS.values()[index]);
					unsaved_changes = true;
				}
			}
			else if(!selecting && evt.getDropDown().equals(sizeDropDown)){
				Entity selectedEntity = Starter.getClient().getWorkingScenario().getSelectedEntity();
				if(selectedEntity instanceof Cichlid){
					int index = sizeDropDown.getSelectedIndex();
					((Cichlid)selectedEntity).setSize(POSSIBLE_SIZES.values()[index]);
					unsaved_changes = true;
				}
			}
		}
	}//end of onListBoxSelectionChanged method

	/**
	 * Notifies this object that another object which it is observing has 
	 * fired an important event or performed and important action.
	 */
	@Override
	public void update(IObservable o, Object arg) {
		if(o.equals(SelectEntityAction.getInstance())){
			if(arg == null){
				colorDropDown.clear();
				colorDropDown.disable();
				sizeDropDown.clear();
				sizeDropDown.disable();
			}
			else if(arg instanceof Cichlid){
				colorDropDown.clear();
				sizeDropDown.clear();
				int i = 0;
				int cIndex = -1;
				for(POSSIBLE_COLORS possibleColor : Cichlid.POSSIBLE_COLORS.values()){
					colorDropDown.addItem(possibleColor.NAME);
					if(possibleColor.COLOR.equals(((Cichlid) arg).getColor())){
						cIndex = i;
					}
					i++;
				}
				i = 0;
				int sIndex = -1;
				for(POSSIBLE_SIZES possibleSize : Cichlid.POSSIBLE_SIZES.values()){
					sizeDropDown.addItem(possibleSize.NAME);
					if(possibleSize.LENGTH_INCHES == ((Cichlid)arg).getSize()){
						sIndex = i;
					}
					i++;
				}
				if(cIndex != -1){
					colorDropDown.selectItemByIndex(cIndex);
				}
				if(sIndex != -1){
					sizeDropDown.selectItemByIndex(sIndex);
				}
			}
		}
	}//end of update method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of ScenarioBuilderScreenController class