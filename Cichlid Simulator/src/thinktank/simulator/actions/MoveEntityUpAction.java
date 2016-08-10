package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;

public class MoveEntityUpAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8555304625394354578L;
	public static final String NAME = "move-entity-up";
	
	//---------------------static variables----------------------------
	private static MoveEntityUpAction instance = null;
	
	//---------------------instance constants--------------------------
	private final Vector3f translateVector;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private MoveEntityUpAction(){
		translateVector = new Vector3f(0f, 0.1f, 0f);
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO if scenario => is editing && is moving
		//TODO get selected entity
		//TODO if selected entity != null
		//TODO move selected entity by translateVector
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static MoveEntityUpAction getInstance(){
		if(instance == null){
			instance = new MoveEntityUpAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityUpAction class