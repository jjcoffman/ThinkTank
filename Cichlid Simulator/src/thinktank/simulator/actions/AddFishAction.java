package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Vector3f;

import Game.Main;
import gameAssets.Cichlid;
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
		Cichlid fish = EntityFactory.createCichlid();
		Starter.getClient().getWorkingScenario().addFish(fish);
		Starter.getClient().attachToRootNode(fish.getNode());
		float heightMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWolrdUnitHeight();
		float depthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitDepth();
		float widthMax = Starter.getClient().getWorkingScenario().getEnvironment().getTank().getWorldUnitWidth();
		float widthShift = widthMax / 2;
		float x = Main.RNG.nextFloat() * depthMax;
		float y = Main.RNG.nextFloat() * heightMax;
		float z = Main.RNG.nextFloat() * widthMax;
		z -= widthShift;
		fish.getObj().setLocalTranslation(x, y, z);
		//TODO improve constraints
		System.out.println("Added fish");
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