package Game;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.LoadScenarioAction;
import thinktank.simulator.actions.SaveScenarioAction;

public class InputListener implements AnalogListener, ActionListener{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	private static InputListener instance = null;
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private InputListener(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	@Override
	public void onAction(String name, boolean keyPressed, float tpf){
		if(!keyPressed){
			switch(name){
			case AddPotAction.NAME:
				AddPotAction.getInstance().actionPerformed(null);
				break;
			case AddFishAction.NAME:
				AddFishAction.getInstance().actionPerformed(null);
				break;
			case AddPlantAction.NAME:
				AddPlantAction.getInstance().actionPerformed(null);
				break;
			case SaveScenarioAction.NAME:
				SaveScenarioAction.getInstance().actionPerformed(null);
				break;
			case LoadScenarioAction.NAME:
				LoadScenarioAction.getInstance().actionPerformed(null);
				break;
			}
		}
	}//end of onAction method

	@Override
	public void onAnalog(String name, float value, float tpf){
		
	}//end of onAnalog method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static InputListener getInstance(){
		if(instance == null){
			instance = new InputListener();
		}
		return instance;
	}//end of getInstance method
	
}//end of InputListener class