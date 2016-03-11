package thinktank.simulator.util;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public enum DEFAULT_SETTINGS{
	RESOLUTION_WIDTH(640),
	RESOLUTION_HEIGHT(480),
	VSYNC(false),
	FULLSCREEN(false);
	
	public final Object VALUE;
	
	private DEFAULT_SETTINGS(Object value){
		this.VALUE = value;
	}//end of constructor
	
}//end of DEFAULT_SETTINGS enum