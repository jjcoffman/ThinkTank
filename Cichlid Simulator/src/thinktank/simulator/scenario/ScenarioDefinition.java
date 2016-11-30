package thinktank.simulator.scenario;

import java.awt.Color;

import com.bulletphysics.linearmath.Transform;
import com.jme3.math.Quaternion;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Plant;
import thinktank.simulator.entity.Pot;

/**
 * 
 * @author Bob
 *
 */
public final class ScenarioDefinition{
	//---------------------static constants----------------------------
	//DEFAULT SCENARIO 1
	private static final float[] FISH_X_1 = {};//trans x
	private static final float[] FISH_Y_1 = {};//trans y
	private static final float[] FISH_Z_1 = {};//trans z
	private static final float[] FISH_S_1 = {};//size (PLACEHOLDER)
	private static final Quaternion[] FISH_R_1 = null;//rot (PLACEHOLDER)
	private static final Color[] FISH_C_1 = null;//color (PLACEHOLDER)
	private static final float[] POT_X_1 = {};//trans x
	private static final float[] POT_Y_1 = {};//trans y
	private static final float[] POT_Z_1 = {};//trans z
	private static final float[] POT_S_1 = {};//size (PLACEHOLDER)
	private static final Quaternion[] POT_R_1 = null;//rot (PLACEHOLDER)
	private static final float[] PLANT_X_1 = {};//trans x
	private static final float[] PLANT_Y_1 = {};//trans y
	private static final float[] PLANT_Z_1 = {};//trans z
	private static final float[] PLANT_S_1 = {};//size (PLACEHOLDER)
	private static final Quaternion[] PLANT_R_1 = null;//rot (PLACEHOLDER)
	//DEFAULT SCENARIO 2
	private static final float[] FISH_X_2 = {};//trans x
	private static final float[] FISH_Y_2 = {};//trans y
	private static final float[] FISH_Z_2 = {};//trans z
	private static final float[] FISH_S_2 = {};//size (PLACEHOLDER)
	private static final Quaternion[] FISH_R_2 = null;//rot (PLACEHOLDER)
	private static final Color[] FISH_C_2 = null;//color (PLACEHOLDER)
	private static final float[] POT_X_2 = {};//trans x
	private static final float[] POT_Y_2 = {};//trans y
	private static final float[] POT_Z_2 = {};//trans z
	private static final float[] POT_S_2 = {};//size (PLACEHOLDER)
	private static final Quaternion[] POT_R_2 = null;//rot (PLACEHOLDER)
	private static final float[] PLANT_X_2 = {};//trans x
	private static final float[] PLANT_Y_2 = {};//trans y
	private static final float[] PLANT_Z_2 = {};//trans z
	private static final float[] PLANT_S_2 = {};//size (PLACEHOLDER)
	private static final Quaternion[] PLANT_R_2 = null;//rot (PLACEHOLDER)
	//DEFAULT SCENARIO 3
	private static final float[] FISH_X_3 = {};//trans x
	private static final float[] FISH_Y_3 = {};//trans y
	private static final float[] FISH_Z_3 = {};//trans z
	private static final float[] FISH_S_3 = {};//size (PLACEHOLDER)
	private static final Quaternion[] FISH_R_3 = null;//rot (PLACEHOLDER)
	private static final Color[] FISH_C_3 = null;//color (PLACEHOLDER)
	private static final float[] POT_X_3 = {};//trans x
	private static final float[] POT_Y_3 = {};//trans y
	private static final float[] POT_Z_3 = {};//trans z
	private static final float[] POT_S_3 = {};//size (PLACEHOLDER)
	private static final Quaternion[] POT_R_3 = null;//rot (PLACEHOLDER)
	private static final float[] PLANT_X_3 = {};//trans x
	private static final float[] PLANT_Y_3 = {};//trans y
	private static final float[] PLANT_Z_3 = {};//trans z
	private static final float[] PLANT_S_3 = {};//size (PLACEHOLDER)
	private static final Quaternion[] PLANT_R_3 = null;//rot (PLACEHOLDER)
	//DEFAULT SCENARIO 4
	private static final float[] FISH_X_4 = {};//trans x
	private static final float[] FISH_Y_4 = {};//trans y
	private static final float[] FISH_Z_4 = {};//trans z
	private static final float[] FISH_S_4 = {};//size (PLACEHOLDER)
	private static final Quaternion[] FISH_R_4 = null;//rot (PLACEHOLDER)
	private static final Color[] FISH_C_4 = null;//color (PLACEHOLDER)
	private static final float[] POT_X_4 = {};//trans x
	private static final float[] POT_Y_4 = {};//trans y
	private static final float[] POT_Z_4 = {};//trans z
	private static final float[] POT_S_4 = {};//size (PLACEHOLDER)
	private static final Quaternion[] POT_R_4 = null;//rot (PLACEHOLDER)
	private static final float[] PLANT_X_4 = {};//trans x
	private static final float[] PLANT_Y_4 = {};//trans y
	private static final float[] PLANT_Z_4 = {};//trans z
	private static final float[] PLANT_S_4 = {};//size (PLACEHOLDER)
	private static final Quaternion[] PLANT_R_4 = null;//rot (PLACEHOLDER)
	//DEFAULT SCENARIO 5
	private static final float[] FISH_X_5 = {};//trans x
	private static final float[] FISH_Y_5 = {};//trans y
	private static final float[] FISH_Z_5 = {};//trans z
	private static final float[] FISH_S_5 = {};//size (PLACEHOLDER)
	private static final Quaternion[] FISH_R_5 = null;//rot (PLACEHOLDER)
	private static final Color[] FISH_C_5 = null;//color (PLACEHOLDER)
	private static final float[] POT_X_5 = {};//trans x
	private static final float[] POT_Y_5 = {};//trans y
	private static final float[] POT_Z_5 = {};//trans z
	private static final float[] POT_S_5 = {};//size (PLACEHOLDER)
	private static final Quaternion[] POT_R_5 = null;//rot (PLACEHOLDER)
	private static final float[] PLANT_X_5 = {};//trans x
	private static final float[] PLANT_Y_5 = {};//trans y
	private static final float[] PLANT_Z_5 = {};//trans z
	private static final float[] PLANT_S_5 = {};//size (PLACEHOLDER)
	private static final Quaternion[] PLANT_R_5 = null;//rot (PLACEHOLDER)
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	/**
	 * 
	 */
	private final DEFAULT_SCENARIO def;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * 
	 * @param def
	 */
	private ScenarioDefinition(DEFAULT_SCENARIO def){
		this.def = def;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	/**
	 * 
	 * @param scenario
	 */
	private void generateEntities(Scenario scenario){
		switch(def){
		case EMPTY:
			genEnts1(scenario);
			break;
		case LONE_FISH:
			genEnts2(scenario);
			break;
		case PAIR_FISH:
			genEnts3(scenario);
			break;
		case MEDIUM_BORDER_PLANTS:
			genEnts4(scenario);
			break;
		case MEDIUM_CENTER_PLANTS:
			genEnts5(scenario);
			break;
		case BIG_AND_BUSY:
			genEnts6(scenario);
			break;
		}
	}//end of generateEntities method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts1(Scenario scenario){
		//Empty Scenario. No Entities.
	}//end of getEnts1 method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts2(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.0019916727f, 0.11999998f, -0.119983464f);
		fish.getObj().setLocalScale(0.0254f, 0.0254f, 0.0254f);
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(-2.73087E-4f, 0.0254f, -0.13999975f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 90, 0);
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.05833201f, 0.0254f, 0.15098202f);
		plant.getObj().setLocalScale(0.029526312f, 0.029542202f, 0.028008288f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts2 method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts3(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.051753357f, 0.08999999f, 0.14940417f);
		fish.getObj().setLocalScale(0.0254f, 0.0254f, 0.0254f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.041285567f, 0.12999998f, -0.10952401f);
		fish.getObj().setLocalScale(0.0254f, 0.0254f, 0.0254f);
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(0.044705704f, 0.0254f, 0.18138747f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 90, 0);
		//add pots
		scenario.addEnvironmentObject(pot);
		Plant plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.09556306f, 0.0254f, -0.152865f);
		plant.getObj().setLocalScale(0.026418276f, 0.026432494f, 0.025060045f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts3 method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts4(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.052004013f, 0.22000003f, -0.20884176f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.1912249f, 0.14999999f, -0.17919688f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.068836f, 0.17f, -0.012712434f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.16350275f, 0.21000002f, 0.13589273f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(0.012422485f, 0.0254f, 0.1294051f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.15475345f, 0.0254f, -0.06125257f);
		plant.getObj().setLocalScale(0.029526312f, 0.029542202f, 0.028008288f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.12913f, 0.0254f, -0.26037896f);
		plant.getObj().setLocalScale(0.03936841f, 0.0393896f, 0.03734438f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.049622167f, 0.0254f, -0.23982519f);
		plant.getObj().setLocalScale(0.01813019f, 0.018139949f, 0.01719807f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.0048237303f, 0.0254f, -0.35799065f);
		plant.getObj().setLocalScale(0.031080326f, 0.031097054f, 0.029482407f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.13064393f, 0.0254f, -0.3425547f);
		plant.getObj().setLocalScale(0.032116335f, 0.03213362f, 0.030465152f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.117809236f, 0.0254f, 0.077142775f);
		plant.getObj().setLocalScale(0.032116335f, 0.03213362f, 0.030465152f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.13215373f, 0.0254f, -0.081457675f);
		plant.getObj().setLocalScale(0.03367035f, 0.033688474f, 0.03193927f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.15309826f, 0.0254f, -0.22149652f);
		plant.getObj().setLocalScale(0.026936281f, 0.026950778f, 0.025551418f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.1495902f, 0.0254f, 0.21029106f);
		plant.getObj().setLocalScale(0.032116335f, 0.03213362f, 0.030465152f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.029375998f, 0.0254f, 0.32005772f);
		plant.getObj().setLocalScale(0.019684205f, 0.0196948f, 0.01867219f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.10064329f, 0.0254f, 0.32980508f);
		plant.getObj().setLocalScale(0.020720217f, 0.020731369f, 0.019654937f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.12152052f, 0.0254f, 0.21939106f);
		plant.getObj().setLocalScale(0.021238223f, 0.021249654f, 0.020146312f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts4 method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts5(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.05202833f, 0.29f, -0.17689845f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.02867765f, 0.14999999f, -0.19224523f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.045390487f, 0.17f, 0.1550474f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.06315039f, 0.07999999f, 0.13458093f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(-0.06697098f, 0.0254f, -0.15720972f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 160, 0);
		scenario.addEnvironmentObject(pot);
		pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(0.0070289057f, 0.0254f, 0.15984556f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 0, 0);
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.007075117f, 0.0254f, -0.08499807f);
		plant.getObj().setLocalScale(0.036778387f, 0.036798183f, 0.034887515f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.053520672f, 0.0254f, -0.009175407f);
		plant.getObj().setLocalScale(0.023310244f, 0.02332279f, 0.022111805f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.02953006f, 0.0254f, 0.06259964f);
		plant.getObj().setLocalScale(0.036778387f, 0.036798183f, 0.034887515f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts5 method
	
	/**
	 * 
	 * @param scenario
	 */
	private void genEnts6(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.05202833f, 0.29f, -0.17689845f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.02867765f, 0.14999999f, -0.19224523f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.045390487f, 0.17f, 0.1550474f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.06315039f, 0.07999999f, 0.13458093f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.023619981f, 0.109999985f, 0.086383976f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.08638396f, 0.08999999f, -0.023619983f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.024858873f, 0.27999999f, -0.047896907f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(0.047396127f, 0.04f, -0.16276915f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.16004182f, 0.31999996f, -0.29564947f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		fish.getObj().setLocalTranslation(-0.17998411f, 0.14999999f, 0.30095136f);
		fish.getObj().setLocalScale(0.0127f, 0.0127f, 0.0127f);
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(0.1081209f, 0.0254f, 0.24085239f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 0.5f, 0);
		scenario.addEnvironmentObject(pot);
		pot = EntityFactory.createPot();
		pot.getObj().setLocalTranslation(-0.10819905f, 0.0254f, -0.2308527f);
		pot.getObj().setLocalScale(0.0762f, 0.0762f, 0.0762f);
		pot.getObj().rotate(0, 10, 0);
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(0.09116956f, 0.0254f, -0.14929199f);
		plant.getObj().setLocalScale(0.036778387f, 0.036798183f, 0.034887515f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		plant.getObj().setLocalTranslation(-0.11015297f, 0.0254f, 0.019139629f);
		plant.getObj().setLocalScale(0.038332403f, 0.038353033f, 0.036361635f);
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts6 method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * 
	 * @param def
	 * @return
	 */
	public static Scenario genScenario(DEFAULT_SCENARIO def){
		Scenario returnValue = Scenario.createScenario(def);
		ScenarioDefinition definition = new ScenarioDefinition(def);
		definition.generateEntities(returnValue);
		return returnValue;
	}//end of genScenario(DEFAULT_SCENARIO) method
	
	/**
	 * 
	 * @param defName
	 * @return
	 */
	public static Scenario genScenario(String defName){
		Scenario returnValue = null;
		DEFAULT_SCENARIO scenDef = null;
		for(DEFAULT_SCENARIO def : DEFAULT_SCENARIO.values()){
			if(def.NAME.equals(defName)){
				scenDef = def;
				returnValue = Scenario.createScenario(def);
				break;
			}
		}
		if(returnValue != null){
			ScenarioDefinition definition = new ScenarioDefinition(scenDef);
			definition.generateEntities(returnValue);
		}
		return returnValue;
	}//end of genScenario(String) method
	
	/**
	 * 
	 * @param defName
	 * @return
	 */
	public static boolean isDefault(String defName){
		boolean returnValue = false;
		for(DEFAULT_SCENARIO def : DEFAULT_SCENARIO.values()){
			if(def.NAME.equals(defName)){
				returnValue = true;
				break;
			}
		}
		return returnValue;
	}//end of isDefault method
	
}//end of ScenarioDefinition class