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

public class HUDScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Nifty nifty;
	private Screen screen;
	private boolean isBound;
	private int selectedObjNum;
	
	//---------------------constructors--------------------------------
	public HUDScreenController(){
		super();
		isBound = false;
		selectedObjNum = -1;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
    }//end of initialize method
 
    @Override
    public void update(float tpf){
    }//end of update method
 
    @Override
    public void cleanup(){
        super.cleanup();
    }//end of cleanup method
 
	@Override
	public void bind(Nifty nifty, Screen screen){
		this.nifty = nifty;
		this.screen = screen;
		isBound = true;
	}//end of bind method

	@Override
	public void onEndScreen(){
		System.out.println("HUD: onEndScreen called!");
	}//end of onEndScreen method

	@Override
	public void onStartScreen(){
		System.out.println("HUD: onStartScreen called!");
	}//end of onStartScreen method
	
	//ACTION METHODS
	public void saveScenario(){
		if(isBound){
//			SaveScenarioAction.getInstance().actionPerformed(null);
		}
	}//end of saveScenaio method
	
	public void loadScenaio(){
		if(isBound){
			//TODO load scenario
		}
	}//end of loadScenario method
	
	public void addFish(){
		if(isBound){
			AddFishAction.getInstance().actionPerformed(null);
		}
	}//end of addFish method
	
	public void addPot(){
		if(isBound){
			AddPotAction.getInstance().actionPerformed(null);
		}
	}//end of addPot method
	
	public void addPlant(){
		if(isBound){
			AddPlantAction.getInstance().actionPerformed(null);
		}
	}//end of addPlant method
	
	public void moveObject(){
		if(isBound){
			//TODO enable movement of selected object
		}
	}//end of move method
	
	public void deleteObject(){
		if(isBound){
			//TODO delete selected object
		}
	}//end of deleteObject method
	
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