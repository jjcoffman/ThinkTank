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
	private Nifty nifty;
	private Screen screen;
	private boolean isBound;
	
	//---------------------constructors--------------------------------
	public StartScreenController(){
		super();
		isBound = false;
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
		System.out.println("Start: onEndScreen called!");
	}//end of onEndScreen method

	@Override
	public void onStartScreen(){
		System.out.println("Start: onStartScreen called!");
	}//end of onStartScreen method
	
	//ACTION METHODS
	public void startGame(String nextScreen){
		if(isBound){
			ToggleMouselookAction.getInstance().actionPerformed(null);
			nifty.gotoScreen(nextScreen);  // switch to another screen
		}
	}//end of startGame method
	 
	public void quitGame(){
		if(isBound){
			Starter.getClient().stop();
		}
	}//end of quitGame method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of StartScreenController class