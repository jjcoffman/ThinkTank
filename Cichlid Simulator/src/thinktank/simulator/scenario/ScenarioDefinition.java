package thinktank.simulator.scenario;

import java.awt.Color;

import com.jme3.math.Quaternion;

import gameAssets.Cichlid;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Plant;
import thinktank.simulator.entity.Pot;

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
	private final DEFAULT_SCENARIO def;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private ScenarioDefinition(DEFAULT_SCENARIO def){
		this.def = def;
	}//end of constructor
	
	//---------------------instance methods----------------------------
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
	
	private void genEnts1(Scenario scenario){
		//Empty Scenario. No Entities.
	}//end of getEnts1 method
	
	private void genEnts2(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts2 method
	
	private void genEnts3(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts3 method
	
	private void genEnts4(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts4 method
	
	private void genEnts5(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts5 method
	
	private void genEnts6(Scenario scenario){
		//add fish
		Cichlid fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		fish = EntityFactory.createCichlid();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addFish(fish);
		//add pots
		Pot pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		pot = EntityFactory.createPot();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(pot);
		//add plants
		Plant plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
		plant = EntityFactory.createPlant();
		//TODO set translate
		//TODO set scale
		//TODO set rotation
		scenario.addEnvironmentObject(plant);
	}//end of getEnts6 method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Scenario genScenario(DEFAULT_SCENARIO def){
		Scenario returnValue = Scenario.createScenario(def);
		ScenarioDefinition definition = new ScenarioDefinition(def);
		definition.generateEntities(returnValue);
		return returnValue;
	}//end of genScenario(DEFAULT_SCENARIO) method
	
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