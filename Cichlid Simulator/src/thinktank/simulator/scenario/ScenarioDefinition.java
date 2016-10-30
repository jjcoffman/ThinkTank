package thinktank.simulator.scenario;

public final class ScenarioDefinition{
	//---------------------static constants----------------------------
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
		
	}//end of getEnts2 method
	
	private void genEnts3(Scenario scenario){
		
	}//end of getEnts3 method
	
	private void genEnts4(Scenario scenario){
		
	}//end of getEnts4 method
	
	private void genEnts5(Scenario scenario){
		
	}//end of getEnts5 method
	
	private void genEnts6(Scenario scenario){
		
	}//end of getEnts6 method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Scenario genScenario(DEFAULT_SCENARIO def){
		Scenario returnValue = Scenario.createScenario(def);
		ScenarioDefinition definition = new ScenarioDefinition(def);
		definition.generateEntities(returnValue);
		return returnValue;
	}//end of genScenario method
	
}//end of ScenarioDefinition class