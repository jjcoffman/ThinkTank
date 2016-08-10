package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ScaleEntityDownAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8101514332020386262L;
	public static final String NAME = "scale-entity-down";
	
	//---------------------static variables----------------------------
	private static ScaleEntityDownAction instance = null;
	
	//---------------------instance constants--------------------------
	private final float scaleFactor;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private ScaleEntityDownAction(){
		scaleFactor = 0.1f;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		//TODO if scenario => is editing && is moving
		//TODO get selected entity
		//TODO if selected entity != null
		//TODO scale selected entity by scaleFactor
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static ScaleEntityDownAction getInstance(){
		if(instance == null){
			instance = new ScaleEntityDownAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of ScaleEntityDownAction class