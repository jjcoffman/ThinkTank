package thinktank.simulator.main;

import java.util.Iterator;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Player;

/**
 * 
 * @author Bob
 *
 */
public class RootNodeController extends AbstractAppState{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private Main app;
	/**
	 * 
	 */
	private Node rootNode;
	/**
	 * 
	 */
	private Player player;
	
	//---------------------constructors--------------------------------
	/**
	 * 
	 * @param app
	 */
	public RootNodeController(Application app){
    	this.app = (Main)app;
    	this.rootNode = new Node("Root Node");
    	this.player = null;
	}//end of (Application) constructor
	
	/**
	 * 
	 * @param app
	 * @param player
	 */
	public RootNodeController(Application app, Player player){
    	this.app = (Main)app;
    	this.rootNode = new Node("Root Node");
    	this.player = player;
	}//end of (Application,Player) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * 
	 * @return
	 */
    public Node getRootNode(){
        return rootNode;
    }//end of getRootNode method

    //OPERATIONS
    /**
     * 
     */
    @Override
    public void update(float tpf){
    	rootNode = app.getRootNode();
    	java.util.Iterator<Fish> itr = app.getWorkingScenario().getFish();
    	if(player != null){
        	player.update(tpf);
    	}
		while(itr.hasNext()){
			Fish f = (Fish)itr.next();
			//f.move();
			if(f instanceof Cichlid){
				Cichlid c = (Cichlid)f;
				c.clearRelationships();
				c.move(tpf);
			}
		}
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        super.update(tpf);
    }//end of update method
    
    /**
     * 
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app){
    	super.initialize(stateManager, app);
    	System.out.println("Simulator State initialized");
    }//end of initialize method
    
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of RootNodeController class