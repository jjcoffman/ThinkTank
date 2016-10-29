package thinktank.simulator.scenario;

import thinktank.simulator.environment.TANK_TYPE;

public enum DEFAULT_SCENARIO{
	
	EMPTY("Empty Scenario", 20.0f, TANK_TYPE.FIFTEEN_GAL);
	
	public final String NAME;
	public final float TANK_TEMP;
	public final TANK_TYPE TYPE;
	
	private DEFAULT_SCENARIO(String name, float temp, TANK_TYPE type){
		this.NAME = name;
		this.TANK_TEMP = temp;
		this.TYPE = type;
	}//end of constructor

}//end of DEFAULT_SCENARIO enum