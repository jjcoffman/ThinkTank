package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;

import Game.Main;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Plant;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class AddPlantAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -4818294471156486406L;
	public static final String NAME = "add-plant";
	
	//---------------------static variables----------------------------
	private static AddPlantAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private AddPlantAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	@Override
	public void actionPerformed(ActionEvent evt){
		Plant plant = EntityFactory.createPlant();
		float depthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitDepth();
		float widthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitWidth();
		float widthShift = widthMax / 2;
		float depthShift = depthMax / 2;
		float x = Main.RNG.nextFloat() * depthMax;
		float z = Main.RNG.nextFloat() * widthMax;
		z -= widthShift;
		x -= depthShift;
		plant.getObj().setLocalTranslation(x, 0, z);
		Starter.getClient().getWorkingScenario().addEnvironmentObject(plant);
		Starter.getClient().attachToRootNode(plant.getObj());
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static AddPlantAction getInstance(){
		if(instance == null){
			instance = new AddPlantAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of AddPlantAction class