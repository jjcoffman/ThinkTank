package thinktank.simulator.actions;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import gameAssets.Cichlid;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.Fish;

public class TestVisibility extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 9006479281871536189L;
	public static final String NAME = "test-visibility";
	
	//---------------------static variables----------------------------
	private static TestVisibility instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private TestVisibility(){
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Method invoked when the associated action occurs. 
	 * 
	 * @param evt the object for the triggering event.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		System.out.println("~~~~~~~~~~~~~~~~TEST FIRED~~~~~~~~~~~~~~~~~~");
		Iterator<Fish> it = Starter.getClient().getWorkingScenario().getFish();
		Fish fish1 = null;
		Fish fish2 = null;
		if(it.hasNext()){
			fish1 = it.next();
		}
		if(it.hasNext()){
			fish2 = it.next();
		}
		if(fish1 instanceof Cichlid){
			Cichlid cic = (Cichlid)fish1;
			int vis = cic.visibilityFactor(fish2);
			System.out.println("Visibility = "+vis);
		}
	}//end of actionPerformed method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static TestVisibility getInstance(){
		if(instance == null){
			instance = new TestVisibility();
		}
		return instance;
	}//end of getInstance method
	
}//end of TestVVisibility class