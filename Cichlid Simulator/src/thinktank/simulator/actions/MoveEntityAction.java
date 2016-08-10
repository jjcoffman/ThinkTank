package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.scenario.Scenario;

public class MoveEntityAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8019229449921970576L;
	public static final String NAME = "move-entity";
	
	//---------------------static variables----------------------------
	private static MoveEntityAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private MoveEntityAction(){
		
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		Scenario scenario = Starter.getClient().getWorkingScenario();
		//TODO if editing scenario
		//TODO set scenario to moving mode
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static MoveEntityAction getInstance(){
		if(instance == null){
			instance = new MoveEntityAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of MoveEntityAction class