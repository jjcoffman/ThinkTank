package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Game.Main;
import Game.Main.CAM_MODE;
import thinktank.simulator.Starter;

public class ToggleCamModeAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -3276445904615503162L;
	public static final String NAME = "toggle-cam-mode";
	
	//---------------------static variables----------------------------
	private static ToggleCamModeAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Main.CAM_MODE targetMode;
	
	//---------------------constructors--------------------------------
	private ToggleCamModeAction(){
		targetMode = null;
	}//end of constuctor
	
	//---------------------instance methods----------------------------
	public void setTargetMode(Main.CAM_MODE mode){
		targetMode = mode;
	}//end of setTargetMode method
	
	@Override
	public void actionPerformed(ActionEvent evt){
		if(targetMode == CAM_MODE.FLY){
			Starter.getClient().setCamMode(targetMode.FOLLOW);
		}
		else if(targetMode == CAM_MODE.FOLLOW){
			Starter.getClient().setCamMode(targetMode.FLY);
		}
		
		
		
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static ToggleCamModeAction getInstance(){
		if(instance == null){
			instance = new ToggleCamModeAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of SetCamModeAction class