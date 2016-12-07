package thinktank.simulator.main;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Player;

/**
 * Custom <code>AppState</code> implementation supporting play/pause functionality 
 * for the simulator.
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class RootNodeController extends AbstractAppState{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * A local reference to the main application client.
	 */
	private Main app;
	/**
	 * A local reference to the scene root node.
	 */
	private Node rootNode;
	/**
	 * A local reference to the player fish, if one exists.
	 */
	@SuppressWarnings("deprecation")
	private Player player;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a new <code>RootNodeController</code> object for the 
	 * specified application.
	 * 
	 * @param app the application.
	 */
	public RootNodeController(Application app){
    	this.app = (Main)app;
    	this.rootNode = new Node("Root Node");
    	this.player = null;
	}//end of (Application) constructor
	
	/**
	 * Constructs a new <code>RootNodeController</code> object for the 
	 * specified application, including the specified player.
	 * 
	 * @param app the application.
	 * @param player the player.
	 */
	@SuppressWarnings("deprecation")
	public RootNodeController(Application app, Player player){
    	this.app = (Main)app;
    	this.rootNode = new Node("Root Node");
    	this.player = player;
	}//end of (Application,Player) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Returns the local reference to the root node for the scene.
	 * 
	 * @return the root node.
	 */
    public Node getRootNode(){
        return rootNode;
    }//end of getRootNode method

    //OPERATIONS
    /**
     * The update method called for this app state as part of the game loop.
     * 
     * @param tpf "time per frame" - the duration of the last cycle through the 
     * game loop
     */
    @Override
    public void update(float tpf){
    	rootNode = app.getRootNode();
    	java.util.Iterator<Fish> itr = app.getWorkingScenario().getFish();
    	if(player != null){
        	player.update(tpf);
    	}
		while(itr.hasNext()){
			Fish fish = (Fish)itr.next();
			//f.move();
			if(fish instanceof Cichlid){
				Cichlid cichlid = (Cichlid)fish;
				cichlid.clearRelationships();
				cichlid.move(tpf);
			}
		}
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        super.update(tpf);
    }//end of update method
    
    /**
     * Called during initialization of this app state.
     * 
     * @param stateManager the app state manager.
     * @param app the application.
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app){
    	super.initialize(stateManager, app);
    	System.out.println("Simulator State initialized");
    }//end of initialize method
    
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of RootNodeController class