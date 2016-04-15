package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController extends AbstractAppState implements ScreenController{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Application app;
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
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
    }//end of initialize method
 
    @Override
    public void update(float tpf) {
    }//end of update method
 
    @Override
    public void cleanup() {
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
		System.out.println("onEndScreen called!");
	}//end of onEndScreen method

	@Override
	public void onStartScreen(){
		System.out.println("onStartScreen called!");
	}//end of onStartScreen method
	
	//ACTION METHODS
	public void startGame(String nextScreen){
		if(this.isInitialized() && isBound){
			nifty.gotoScreen(nextScreen);  // switch to another screen
		}
	}//end of startGame method
	 
	public void quitGame(){
		if(this.isInitialized() && isBound){
			app.stop(); 
		}
	}//end of quitGame method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of StartScreenController class