package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;

import Game.Main;
import gameAssets.Cichlid;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.scenario.Scenario;

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
	/**
	 * Singleton instance for the action.
	 */
	private static AddFishAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>AddFishAction</code>.
	 */
	private AddFishAction(){
		
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
		Scenario scenario = Starter.getClient().getWorkingScenario();
		if(!Starter.getClient().isInMenus() && scenario != null && scenario.isEditingMode()){
			Cichlid fish = EntityFactory.createCichlid();
			scenario.addFish(fish);
			float heightMax = scenario.getEnvironment().getTank().getWolrdUnitHeight();
			float depthMax = scenario.getEnvironment().getTank().getWorldUnitDepth();
			float widthMax = scenario.getEnvironment().getTank().getWorldUnitWidth();
			float widthShift = widthMax / 2;
			float depthShift = depthMax / 2;
			float x = Main.RNG.nextFloat() * depthMax;
			float y = Main.RNG.nextFloat() * heightMax;
			float z = Main.RNG.nextFloat() * widthMax;
			z -= widthShift;
			x -= depthShift;
			fish.getObj().setLocalTranslation(x, y, z);
		}
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns a reference to the singleton instance of the action.
	 * 
	 * @return the action object
	 */
	public static AddFishAction getInstance(){
		if(instance == null){
			instance = new AddFishAction();
		}
		return instance;
	}//end of getInstance method
	
}//end of AddFishAction class