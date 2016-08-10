package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import thinktank.simulator.Starter;

public class MoveEntityForwardAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -5592152456528971059L;
	public static final String NAME = "move-entity-forward";
	
	//---------------------static variables----------------------------
	private static MoveEntityForwardAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Vector3f translateVector;
	
	//---------------------constructors--------------------------------
	private MoveEntityForwardAction(){
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
		translateVector = cam.getDirection().clone();
		translateVector.setY(0f);
		//TODO move selected entity by translateVector
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static MoveEntityForwardAction getInstance(){
		if(instance == null){
			instance = new MoveEntityForwardAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityForwardAction class