package thinktank.simulator.util;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public enum DEFAULT_SETTINGS{
	RESOLUTION_WIDTH("resolution-width", 640),
	RESOLUTION_HEIGHT("resolution-height", 480),
	VSYNC("vsync", false),
	FULLSCREEN("fullscreen", false);
	
	public final String NAME;
	public final Object VALUE;
	
	private DEFAULT_SETTINGS(String name, Object value){
		this.NAME = name;
		this.VALUE = value;
	}//end of constructor
	
}//end of DEFAULT_SETTINGS enum