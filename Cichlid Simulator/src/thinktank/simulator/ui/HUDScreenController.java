package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.SaveScenarioAction;

/**
 * Stores and Maintains data and operations for the "HUD"
 * of the User Interface for scenario building and editing.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class HUDScreenController extends AbstractAppState implements ScreenController{
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
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a <code>HUDScreenController</code>.
	 */
	public HUDScreenController(){
		super();
		isBound = false;
		selectedObjNum = -1;
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
	 * Saves the current scenario.
	 */
	public void saveScenario(){
		if(isBound){
//			SaveScenarioAction.getInstance().actionPerformed(null);
		}
	}//end of saveScenaio method
	
	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Loads a saved scenario.
	 */
	public void loadScenaio(){
		if(isBound){
			//TODO load scenario
		}
	}//end of loadScenario method

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
	 * Sets the currently selected object as movable (if not already), 
	 * or sets it as not movable (if already movable).
	 */
	public void moveObject(){
		if(isBound){
			//TODO enable movement of selected object
		}
	}//end of move method

	/**
	 * Method called when the assigned button is clicked.
	 * 
	 * Deletes the currently selected object.
	 */
	public void deleteObject(){
		if(isBound){
			//TODO delete selected object
		}
	}//end of deleteObject method
	
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
}//end of HUDScreenController class