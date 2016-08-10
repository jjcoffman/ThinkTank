package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import thinktank.simulator.Starter;

public class MoveEntityLeftAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -6081333671175795774L;
	public static final String NAME = "move-entity-left";
	
	//---------------------static variables----------------------------
	private static MoveEntityLeftAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Vector3f translateVector;
	
	//---------------------constructors--------------------------------
	private MoveEntityLeftAction(){
		translateVector = new Vector3f(0f, 0f, 0f);
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO if scenario => is editing && is moving
		//TODO get selected entity
		//TODO if selected entity != null
		Camera cam = Starter.getClient().getCamera();
		translateVector = cam.getLeft().clone();
		translateVector.setY(0f);
		//TODO move selected entity by translateVector
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static MoveEntityLeftAction getInstance(){
		if(instance == null){
			instance = new MoveEntityLeftAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityLeftAction class