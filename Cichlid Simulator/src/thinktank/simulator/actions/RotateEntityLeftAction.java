package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RotateEntityLeftAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 6744490700400317249L;
	public static final String NAME = "rotate-entity-left";
	
	//---------------------static variables----------------------------
	private static RotateEntityLeftAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private RotateEntityLeftAction(){
		
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
	public static RotateEntityLeftAction getInstance(){
		if(instance == null){
			instance = new RotateEntityLeftAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of RotateEntityLeftAction class