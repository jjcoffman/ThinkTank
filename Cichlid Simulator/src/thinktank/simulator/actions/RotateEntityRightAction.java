package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RotateEntityRightAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -2733138701468358319L;
	public static final String NAME = "rotate-entity-right";
	
	//---------------------static variables----------------------------
	private static RotateEntityRightAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private RotateEntityRightAction(){
		
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO if scenario => is editing && is moving
		//TODO get selected entity
		//TODO if selected entity != null
		//TODO rotate selected entity
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static RotateEntityRightAction getInstance(){
		if(instance == null){
			instance = new RotateEntityRightAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of RotateEntityRightAction class