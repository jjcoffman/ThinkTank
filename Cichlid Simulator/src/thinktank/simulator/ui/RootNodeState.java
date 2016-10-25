package thinktank.simulator.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

import Game.Main;
import gameAssets.Cichlid;
import thinktank.simulator.entity.Fish;

public class RootNodeState extends AbstractAppState {
	
	private Node rootNode = new Node("Root Node");
	private Main app;
	
	public RootNodeState() {
	}

    public Node getRootNode(){
        return rootNode;
    }

    @Override
    public void update(float tpf) {
    	rootNode = app.getRootNode();
    	java.util.Iterator<Fish> itr = app.getWorkingScenario().getFish();
		while (itr.hasNext()){
			Fish f = (Fish) itr.next();
			//f.move();
			if(f instanceof Cichlid){
				Cichlid c = (Cichlid)f;
				c.move(tpf);
			}
		}
        super.update(tpf);
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        
    }
    
    public void initialize(AppStateManager stateManager, Application app){
    	super.initialize(stateManager, app);
    	this.app = (Main) app;
    	System.out.println("Simulator State initialized");
    }
    
}
