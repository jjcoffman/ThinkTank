package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;

public class ToggleMouselookAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -6826704529254389675L;
	public static final String NAME = "toggle-mouselook";
	
	//---------------------static variables----------------------------
	private static ToggleMouselookAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private ToggleMouselookAction(){
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	@Override
	public void actionPerformed(ActionEvent evt){
			Starter.getClient().toggleMouseMode();
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static ToggleMouselookAction getInstance(){
		if(instance == null){
			instance = new ToggleMouselookAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of ToggleMouselookAction class