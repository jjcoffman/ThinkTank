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
	/**
	 * Singleton instance for the action.
	 */
	private static AddPlantAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>AddPlantAction</code>.
	 */
	private AddPlantAction(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		Plant plant = EntityFactory.createPlant();
		Starter.getClient().getWorkingScenario().addEnvironmentObject(plant);
		float depthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitDepth() - 0.175f;
		float widthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitWidth() - 0.175f;
		float widthShift = widthMax / 2;
		float depthShift = depthMax / 2;
		float x = Main.RNG.nextFloat() * depthMax;
		float z = Main.RNG.nextFloat() * widthMax;
		z -= widthShift;
		x -= depthShift;
		plant.getObj().setLocalTranslation(x, 0, z);
//		Starter.getClient().attachToRootNode(plant.getObj());		//NOTE: this shouldn't be necessary, as the obj is attached to scenario's entityNode, but it is retained in case something breaks somewhere.
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static AddPlantAction getInstance(){
		if(instance == null){
			instance = new AddPlantAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of AddPlantAction class