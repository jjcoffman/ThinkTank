package thinktank.simulator.scenario;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Plant;
import thinktank.simulator.entity.Pot;

/**
 * Contains values and operations for generating the built-in, 
 * default scenarios.
 * 
 * @author Bob Thompson, Vasher Lor
 * @version %I%, %G%
 */
public final class ScenarioDefinition{
	//---------------------static constants----------------------------
	//DEFAULT SCENARIO 2
	/**
	 * The X-coordinates for the fish in default scenario 2.
	 */
	private static final float[] FISH_X_1 = {
			-0.0019916727f
	};
	/**
	 * The Y-coordinates for the fish in default scenario 2.
	 */
	private static final float[] FISH_Y_1 = {
			0.11999998f
	};
	/**
	 * The Z-coordinates for the fish in default scenario 2.
	 */
	private static final float[] FISH_Z_1 = {
			-0.119983464f
	};
	/**
	 * The sizes for the fish in default scenario 2.
	 */
	private static final Cichlid.POSSIBLE_SIZES[] FISH_S_1 = {
			Cichlid.POSSIBLE_SIZES.SMALL
	};
	/**
	 * The colors for the fish in default scenario 2.
	 */
	private static final Cichlid.POSSIBLE_COLORS[] FISH_C_1 = {
			Cichlid.POSSIBLE_COLORS.DEFAULT
	};
	/**
	 * The X-coordinates for the pots in default scenario 2.
	 */
	private static final float[] POT_X_1 = {
			-2.73087E-4f
	};
	/**
	 * The Y-coordinates for the pots in default scenario 2.
	 */
	private static final float[] POT_Y_1 = {
			0.0f
	};
	/**
	 * The Z-coordinates for the pots in default scenario 2.
	 */
	private static final float[] POT_Z_1 = {
			-0.13999975f
	};
	/**
	 * The sizes for the pots in default scenario 2.
	 */
	private static final float[] POT_S_1 = {
			0.08f
	};
	/**
	 * The X-coordinates for the plants in default scenario 2.
	 */
	private static final float[] PLANT_X_1 = {
			-0.05833201f
	};
	/**
	 * The Y-coordinates for the plants in default scenario 2.
	 */
	private static final float[] PLANT_Y_1 = {
			0.0f
	};
	/**
	 * The Z-coordinates for the plants in default scenario 2.
	 */
	private static final float[] PLANT_Z_1 = {
			0.15098202f
	};
	/**
	 * The sizes for the plants in default scenario 2.
	 */
	private static final float[] PLANT_S_1 = {
			0.029526312f
	}; 
	//DEFAULT SCENARIO 3
	/**
	 * The X-coordinates for the fish in default scenario 3.
	 */
	private static final float[] FISH_X_2 = {
			0.051753357f,
			-0.041285567f
	};
	/**
	 * The Y-coordinates for the fish in default scenario 3.
	 */
	private static final float[] FISH_Y_2 = {
			0.08999999f,
			0.12999998f
	};
	/**
	 * The Z-coordinates for the fish in default scenario 3.
	 */
	private static final float[] FISH_Z_2 = {
			0.14940417f,
			-0.10952401f
	};
	/**
	 * The sizes for the fish in default scenario 3.
	 */
	private static final Cichlid.POSSIBLE_SIZES[] FISH_S_2 = {
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.MEDIUM
	};
	/**
	 * The colors for the fish in default scenario 3.
	 */
	private static final Cichlid.POSSIBLE_COLORS[] FISH_C_2 = {
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLACK
	};
	/**
	 * The X-coordinates for the pots in default scenario 3.
	 */
	private static final float[] POT_X_2 = {
			0.044705704f
	};
	/**
	 * The Y-coordinates for the pots in default scenario 3.
	 */
	private static final float[] POT_Y_2 = {
			0.0f
	};
	/**
	 * The Z-coordinates for the pots in default scenario 3.
	 */
	private static final float[] POT_Z_2 = {
			0.18138747f
	};
	/**
	 * The sizes for the pots in default scenario 3.
	 */
	private static final float[] POT_S_2 = {
			0.079f
	}; 
	/**
	 * The X-coordinates for the plants in default scenario 3.
	 */
	private static final float[] PLANT_X_2 = {
			-0.09556306f
	};
	/**
	 * The Y-coordinates for the plants in default scenario 3.
	 */
	private static final float[] PLANT_Y_2 = {
			0.0f
	};
	/**
	 * The Z-coordinates for the plants in default scenario 3.
	 */
	private static final float[] PLANT_Z_2 = {
			-0.152865f
	};
	/**
	 * The sizes for the plants in default scenario 3.
	 */
	private static final float[] PLANT_S_2 = {
			0.025060045f
	}; 
	//DEFAULT SCENARIO 4
	/**
	 * The X-coordinates for the fish in default scenario 4.
	 */
	private static final float[] FISH_X_3 = {
			0.052004013f,
			-0.1912249f,
			0.068836f,
			-0.16350275f
	};
	/**
	 * The Y-coordinates for the fish in default scenario 4.
	 */
	private static final float[] FISH_Y_3 = {
			0.22000003f,
			0.14999999f,
			0.17f,
			0.21000002f
	};
	/**
	 * The Z-coordinates for the fish in default scenario 4.
	 */
	private static final float[] FISH_Z_3 = {
			-0.20884176f,
			-0.17919688f,
			-0.012712434f,
			0.13589273f
	};
	/**
	 * The sizes for the fish in default scenario 4.
	 */
	private static final Cichlid.POSSIBLE_SIZES[] FISH_S_3 = {
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.MEDIUM
	};
	/**
	 * The colors for the fish in default scenario 4.
	 */
	private static final Cichlid.POSSIBLE_COLORS[] FISH_C_3 = {
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLACK,
			Cichlid.POSSIBLE_COLORS.BLUE,
			Cichlid.POSSIBLE_COLORS.BLUE
	};
	/**
	 * The X-coordinates for the pots in default scenario 4.
	 */
	private static final float[] POT_X_3 = {
			0.012422485f
	};
	/**
	 * The Y-coordinates for the pots in default scenario 4.
	 */
	private static final float[] POT_Y_3 = {
			0.0f
	};
	/**
	 * The Z-coordinates for the pots in default scenario 4.
	 */
	private static final float[] POT_Z_3 = {
			0.1294051f
	};
	/**
	 * The sizes for the pots in default scenario 4.
	 */
	private static final float[] POT_S_3 = {
			0.078f
	};
	/**
	 * The X-coordinates for the plants in default scenario 4.
	 */
	private static final float[] PLANT_X_3 = {
			-0.15475345f,
			-0.12913f,
			-0.049622167f,
			0.0048237303f,
			0.13064393f,
			0.117809236f,
			0.13215373f,
			0.15309826f,
			-0.1495902f,
			-0.029375998f,
			0.10064329f,
			0.12152052f
	};
	/**
	 * The Y-coordinates for the plants in default scenario 4.
	 */
	private static final float[] PLANT_Y_3 = {
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f,
			0.0f
	};
	/**
	 * The Z-coordinates for the plants in default scenario 4.
	 */
	private static final float[] PLANT_Z_3 = {
			-0.06125257f,
			-0.26037896f,
			-0.23982519f,
			-0.35799065f,
			-0.3425547f,
			0.077142775f,
			-0.081457675f,
			-0.22149652f,
			0.21029106f,
			0.32005772f,
			0.32980508f,
			0.21939106f
	};
	/**
	 * The sizes for the plants in default scenario 4.
	 */
	private static final float[] PLANT_S_3 = {
			0.029526312f,
			0.03936841f,
			0.01813019f,
			0.031080326f,
			0.032116335f,
			0.032116335f,
			0.03367035f,
			0.026936281f,
			0.032116335f,
			0.019684205f,
			0.020720217f,
			0.021238223f
	}; 
	//DEFAULT SCENARIO 5
	/**
	 * The X-coordinates for the fish in default scenario 5.
	 */
	private static final float[] FISH_X_4 = {
			-0.05202833f,
			0.02867765f,
			0.045390487f,
			-0.06315039f
	};
	/**
	 * The Y-coordinates for the fish in default scenario 5.
	 */
	private static final float[] FISH_Y_4 = {
			0.29f,
			0.14999999f,
			0.17f,
			0.07999999f
	};
	/**
	 * The Z-coordinates for the fish in default scenario 5.
	 */
	private static final float[] FISH_Z_4 = {
			-0.17689845f,
			-0.19224523f,
			0.1550474f,
			0.13458093f
	};
	/**
	 * The sizes for the fish in default scenario 5.
	 */
	private static final Cichlid.POSSIBLE_SIZES[] FISH_S_4 = {
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.LARGE
	};
	/**
	 * The colors for the fish in default scenario 5.
	 */
	private static final Cichlid.POSSIBLE_COLORS[] FISH_C_4 = {
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLACK,
			Cichlid.POSSIBLE_COLORS.BLUE,
			Cichlid.POSSIBLE_COLORS.BLUEFIN
	};
	/**
	 * The X-coordinates for the pots in default scenario 5.
	 */
	private static final float[] POT_X_4 = {
			-0.06697098f,
			0.0070289057f
	};
	/**
	 * The Y-coordinates for the pots in default scenario 5.
	 */
	private static final float[] POT_Y_4 = {
			0.0f,
			0.0f
	};
	/**
	 * The Z-coordinates for the pots in default scenario 5.
	 */
	private static final float[] POT_Z_4 = {
			-0.15720972f,
			0.15984556f
	};
	/**
	 * The sizes for the pots in default scenario 5.
	 */
	private static final float[] POT_S_4 = {
			0.09f,
			0.072f
	};
	/**
	 * The X-coordinates for the plants in default scenario 5.
	 */
	private static final float[] PLANT_X_4 = {
			-0.007075117f,
			-0.053520672f,
			0.02953006f
	};
	/**
	 * The Y-coordinates for the plants in default scenario 5.
	 */
	private static final float[] PLANT_Y_4 = {
			0.0f,
			0.0f,
			0.0f
	};
	/**
	 * The Z-coordinates for the plants in default scenario 5.
	 */
	private static final float[] PLANT_Z_4 = {
			-0.08499807f,
			-0.009175407f,
			0.06259964f
	};
	/**
	 * The sizes for the plants in default scenario 5.
	 */
	private static final float[] PLANT_S_4 = {
			0.036778387f,
			0.023310244f,
			0.036778387f
	}; 
	//DEFAULT SCENARIO 6
	/**
	 * The X-coordinates for the fish in default scenario 6.
	 */
	private static final float[] FISH_X_5 = {
			-0.05202833f,
			0.02867765f,
			0.045390487f
			,-0.06315039f,
			-0.023619981f,
			-0.08638396f,
			0.024858873f,
			0.047396127f,
			-0.16004182f,
			-0.17998411f
	};
	/**
	 * The Y-coordinates for the fish in default scenario 6.
	 */
	private static final float[] FISH_Y_5 = {
			0.29f,
			0.14999999f,
			0.17f,
			0.07999999f,
			0.109999985f,
			0.08999999f,
			0.27999999f,
			0.04f,
			0.31999996f,
			0.14999999f
	};
	/**
	 * The Z-coordinates for the fish in default scenario 6.
	 */
	private static final float[] FISH_Z_5 = {
			-0.17689845f,
			-0.19224523f,
			0.1550474f,
			0.13458093f,
			0.086383976f,
			-0.023619983f,
			-0.047896907f,
			-0.16276915f,
			-0.29564947f,
			0.30095136f
	};
	/**
	 * The sizes for the fish in default scenario 6.
	 */
	private static final Cichlid.POSSIBLE_SIZES[] FISH_S_5 = {
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.SMALL,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.MEDIUM,
			Cichlid.POSSIBLE_SIZES.LARGE,
			Cichlid.POSSIBLE_SIZES.LARGE
	};
	/**
	 * The colors for the fish in default scenario 6.
	 */
	private static final Cichlid.POSSIBLE_COLORS[] FISH_C_5 = {
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLACK,
			Cichlid.POSSIBLE_COLORS.BRIGHTWHITES,
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLUE,
			Cichlid.POSSIBLE_COLORS.DEFAULT,
			Cichlid.POSSIBLE_COLORS.BLUEFIN,
			Cichlid.POSSIBLE_COLORS.BRIGHTWHITES,
			Cichlid.POSSIBLE_COLORS.VIBRANTBLUE,
	};
	/**
	 * The X-coordinates for the pots in default scenario 6.
	 */
	private static final float[] POT_X_5 = {
			0.1081209f,
			-0.10819905f
	};
	/**
	 * The Y-coordinates for the pots in default scenario 6.
	 */
	private static final float[] POT_Y_5 = {
			0.0f,
			0.0f
	};
	/**
	 * The Z-coordinates for the pots in default scenario 6.
	 */
	private static final float[] POT_Z_5 = {
			0.24085239f,
			-0.2308527f
	};
	/**
	 * The sizes for the pots in default scenario 6.
	 */
	private static final float[] POT_S_5 = {
			0.079f,
			0.079f
	};
	/**
	 * The X-coordinates for the plants in default scenario 6.
	 */
	private static final float[] PLANT_X_5 = {
			0.09116956f,
			-0.11015297f
	};
	/**
	 * The Y-coordinates for the plants in default scenario 6.
	 */
	private static final float[] PLANT_Y_5 = {
			0.0f,
			0.0f
	};
	/**
	 * The Z-coordinates for the plants in default scenario 6.
	 */
	private static final float[] PLANT_Z_5 = {
			-0.14929199f,
			0.019139629f
	};
	/**
	 * The sizes for the plants in default scenario 6.
	 */
	private static final float[] PLANT_S_5 = {
			0.036798183f,
			0.038353033f
	}; 
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	/**
	 * The default scenario to which this definition object corresponds.
	 */
	private final DEFAULT_SCENARIO def;
	
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a new <code>ScenarioDefinition</code> object for the 
	 * specified <code>DEFAULT_SCENARIO</code> definition.
	 * 
	 * @param def the default scenario to define.
	 */
	private ScenarioDefinition(DEFAULT_SCENARIO def){
		this.def = def;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	/**
	 * Generates the entities appropriate to the default scenario with 
	 * which this definition is associated. The generated entities are 
	 * then added to the specified scenario.
	 * 
	 * @param scenario the scenario to add the generated entities to.
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
	 * Generates the entities for default scenario 1.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts1(Scenario scenario){
		//Empty Scenario. No Entities.
	}//end of getEnts1 method
	
	/**
	 * Generates the entities for default scenario 2.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts2(Scenario scenario){
		//add fish
		for (int i = 0; i < FISH_X_1.length; i++){
			Cichlid fish = EntityFactory.createCichlid();
			fish.getObj().setLocalTranslation(FISH_X_1[i], FISH_Y_1[i], FISH_Z_1[i]);
			fish.setSize(FISH_S_1[i]);
			fish.setColor(FISH_C_1[i]);
			scenario.addFish(fish);
		}
		//add pots
		for (int i = 0; i < POT_X_1.length; i++){
			Pot pot = EntityFactory.createPot();
			pot.getObj().setLocalTranslation(POT_X_1[i], POT_Y_1[i], POT_Z_1[i]);
			pot.getObj().setLocalScale(POT_S_1[i], POT_S_1[i], POT_S_1[i]);
			//pot.getObj().rotate(0, 90, 0);
			scenario.addEnvironmentObject(pot);
		}
		//add plants
		for (int i = 0; i < PLANT_X_1.length; i++){
			Plant plant = EntityFactory.createPlant();
			plant.getObj().setLocalTranslation(PLANT_X_1[i], PLANT_Y_1[i], PLANT_Z_1[i]);
			plant.getObj().setLocalScale(PLANT_S_1[i], PLANT_S_1[i], PLANT_S_1[i]);
			scenario.addEnvironmentObject(plant);
		}
	}//end of getEnts2 method
	
	/**
	 * Generates the entities for default scenario 3.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts3(Scenario scenario){
		//add fish
		for(int i = 0; i < FISH_X_2.length; i++){
			Cichlid fish = EntityFactory.createCichlid();
			fish.getObj().setLocalTranslation(FISH_X_2[i], FISH_Y_2[i], FISH_Z_2[i]);
			fish.setSize(FISH_S_2[i]);
			fish.setColor(FISH_C_2[i]);
			scenario.addFish(fish);
		}
		//add pots
		for (int i = 0; i < POT_X_2.length; i++){
			Pot pot = EntityFactory.createPot();
			pot.getObj().setLocalTranslation(POT_X_2[i], POT_Y_2[i], POT_Z_2[i]);
			pot.getObj().setLocalScale(POT_S_2[i], POT_S_2[i], POT_S_2[i]);
			//pot.getObj().rotate(0, 90, 0);
			scenario.addEnvironmentObject(pot);
		}
		//add plants
		for (int i = 0; i < PLANT_X_2.length; i++){
			Plant plant = EntityFactory.createPlant();
			plant.getObj().setLocalTranslation(PLANT_X_2[i], PLANT_Y_2[i], PLANT_Z_2[i]);
			plant.getObj().setLocalScale(PLANT_S_2[i], PLANT_S_2[i], PLANT_S_2[i]);
			scenario.addEnvironmentObject(plant);
		}
	}//end of getEnts3 method
	
	/**
	 * Generates the entities for default scenario 4.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts4(Scenario scenario){
		//add fish
		for(int i = 0; i < FISH_X_3.length; i++){
			Cichlid fish = EntityFactory.createCichlid();
			fish.getObj().setLocalTranslation(FISH_X_3[i], FISH_Y_3[i], FISH_Z_3[i]);
			fish.setSize(FISH_S_3[i]);
			fish.setColor(FISH_C_3[i]);
			scenario.addFish(fish);
		}
		//add pots
		for (int i = 0; i < POT_X_3.length; i++){
			Pot pot = EntityFactory.createPot();
			pot.getObj().setLocalTranslation(POT_X_3[i], POT_Y_3[i], POT_Z_3[i]);
			pot.getObj().setLocalScale(POT_S_3[i], POT_S_3[i], POT_S_3[i]);
			//pot.getObj().rotate(0, 90, 0);
			scenario.addEnvironmentObject(pot);
		}
		//add plants
		for (int i = 0; i < PLANT_X_3.length; i++){
			Plant plant = EntityFactory.createPlant();
			plant.getObj().setLocalTranslation(PLANT_X_3[i], PLANT_Y_3[i], PLANT_Z_3[i]);
			plant.getObj().setLocalScale(PLANT_S_3[i], PLANT_S_3[i], PLANT_S_3[i]);
			scenario.addEnvironmentObject(plant);
		}
	}//end of getEnts4 method
	
	/**
	 * Generates the entities for default scenario 5.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts5(Scenario scenario){
		//add fish
		for(int i = 0; i < FISH_X_4.length; i++){
			Cichlid fish = EntityFactory.createCichlid();
			fish.getObj().setLocalTranslation(FISH_X_4[i], FISH_Y_4[i], FISH_Z_4[i]);
			fish.setSize(FISH_S_4[i]);
			fish.setColor(FISH_C_4[i]);
			scenario.addFish(fish);
		}
		//add pots
		for (int i = 0; i < POT_X_4.length; i++){
			Pot pot = EntityFactory.createPot();
			pot.getObj().setLocalTranslation(POT_X_4[i], POT_Y_4[i], POT_Z_4[i]);
			pot.getObj().setLocalScale(POT_S_4[i], POT_S_4[i], POT_S_4[i]);
			//pot.getObj().rotate(0, 90, 0);
			scenario.addEnvironmentObject(pot);
		}
		//add plants
		for (int i = 0; i < PLANT_X_4.length; i++){
			Plant plant = EntityFactory.createPlant();
			plant.getObj().setLocalTranslation(PLANT_X_4[i], PLANT_Y_4[i], PLANT_Z_4[i]);
			plant.getObj().setLocalScale(PLANT_S_4[i], PLANT_S_4[i], PLANT_S_4[i]);
			scenario.addEnvironmentObject(plant);
		}
	}//end of getEnts5 method
	
	/**
	 * Generates the entities for default scenario 6.
	 * 
	 * @param scenario the scenario to add the generated entities to.
	 */
	private void genEnts6(Scenario scenario){
		//add fish
		for(int i = 0; i < FISH_X_5.length; i++){
			Cichlid fish = EntityFactory.createCichlid();
			fish.getObj().setLocalTranslation(FISH_X_5[i], FISH_Y_5[i], FISH_Z_5[i]);
			fish.setSize(FISH_S_5[i]);
			fish.setColor(FISH_C_5[i]);
			scenario.addFish(fish);
		}
		//add pots
		for (int i = 0; i < POT_X_5.length; i++){
			Pot pot = EntityFactory.createPot();
			pot.getObj().setLocalTranslation(POT_X_5[i], POT_Y_5[i], POT_Z_5[i]);
			pot.getObj().setLocalScale(POT_S_5[i], POT_S_5[i], POT_S_5[i]);
			//pot.getObj().rotate(0, 90, 0);
			scenario.addEnvironmentObject(pot);
		}
		//add plants
		for (int i = 0; i < PLANT_X_5.length; i++){
			Plant plant = EntityFactory.createPlant();
			plant.getObj().setLocalTranslation(PLANT_X_5[i], PLANT_Y_5[i], PLANT_Z_5[i]);
			plant.getObj().setLocalScale(PLANT_S_5[i], PLANT_S_5[i], PLANT_S_5[i]);
			scenario.addEnvironmentObject(plant);
		}
	}//end of getEnts6 method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Generates and returns a <code>Scenario</code> object for the specified 
	 * default scenario.
	 * 
	 * @param def the default scenario for which the scenario should be generated.
	 * @return the generated scenario.
	 */
	public static Scenario genScenario(DEFAULT_SCENARIO def){
		Scenario returnValue = Scenario.createScenario(def);
		ScenarioDefinition definition = new ScenarioDefinition(def);
		definition.generateEntities(returnValue);
		return returnValue;
	}//end of genScenario(DEFAULT_SCENARIO) method
	
	/**
	 * Generates and returns a <code>Scenario</code> object for the specified 
	 * definition name.
	 * 
	 * @param defName the name of the definition for the scenario.
	 * @return the generated scenario.
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
	 * Returns whether or not the specified definition name is the name of 
	 * a default scenario.
	 * 
	 * @param defName the definition name to check
	 * @return true if the specified definition name is a default scenario, 
	 * false otherwise.
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