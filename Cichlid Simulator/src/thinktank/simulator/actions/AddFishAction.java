package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Fish;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class AddFishAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 6170325697839007096L;
	public static final String NAME = "add-fish";
	
	//---------------------static variables----------------------------
	private static AddFishAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private AddFishAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	@Override
	public void actionPerformed(ActionEvent evt){
		Fish fish = EntityFactory.createCichlid();
		Starter.getClient().getWorkingScenario().addFish(fish);
		Starter.getClient().attachToRootNode(fish.getObj());
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static AddFishAction getInstance(){
		if(instance == null){
			instance = new AddFishAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of AddFishAction class