package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ScaleEntityUpAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 298863534515430618L;
	public static final String NAME = "scale-entity-up";
	
	//---------------------static variables----------------------------
	private static ScaleEntityUpAction instance = null;
	
	//---------------------instance constants--------------------------
	private final float scaleFactor;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private ScaleEntityUpAction(){
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
	public static ScaleEntityUpAction getInstance(){
		if(instance == null){
			instance = new ScaleEntityUpAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of ScaleEntityUpAction class