package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;

public class MoveEntityDownAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 201389170013758967L;
	public static final String NAME = "move-entity-down";
	
	//---------------------static variables----------------------------
	private static MoveEntityDownAction instance = null;
	
	//---------------------instance constants--------------------------
	private final Vector3f translateVector;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private MoveEntityDownAction(){
		translateVector = new Vector3f(0f, -0.1f, 0f);
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
	public static MoveEntityDownAction getInstance(){
		if(instance == null){
			instance = new MoveEntityDownAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityDownAction class