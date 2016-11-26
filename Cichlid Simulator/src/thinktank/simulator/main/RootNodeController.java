package thinktank.simulator.main;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Player;

public class RootNodeController extends AbstractAppState {
	
	private Node rootNode = new Node("Root Node");
	private Main app;
	private Player player = null;
	
	public RootNodeController( Application app, Player player) {
    	this.app = (Main) app;
    	this.player = player;
	}
	public RootNodeController( Application app) {
    	this.app = (Main) app;
	}

    public Node getRootNode(){
        return rootNode;
    }

    @Override
    public void update(float tpf) {
    	rootNode = app.getRootNode();
    	java.util.Iterator<Fish> itr = app.getWorkingScenario().getFish();
    	
    	if (player != null){
        	player.update(tpf);
    	}
		while (itr.hasNext()){
			Fish f = (Fish) itr.next();
			//f.move();
			if(f instanceof Cichlid){
				Cichlid c = (Cichlid)f;
				c.move(tpf);
			}
		}
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        super.update(tpf);
    }
    
    public void initialize(AppStateManager stateManager, Application app){
    	super.initialize(stateManager, app);
    	System.out.println("Simulator State initialized");
    }
    
}
