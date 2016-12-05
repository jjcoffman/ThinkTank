package thinktank.simulator.util;

/**
 * Maintains default values for application settings.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public enum DEFAULT_SETTINGS{
	
	RESOLUTION_WIDTH("resolution-width", 640),
	RESOLUTION_HEIGHT("resolution-height", 480),
	VSYNC("vsync", false),
	FULLSCREEN("fullscreen", false),
	BITS_PER_PIXEL("bits-per-pixel", 24),
	DEPTH_BITS("depth-bits", 24),
	FRAME_RATE("frame-rate", 180),
	REFRESH_RATE("refresh-rate", 60),
	ICONS("icons", null),
	MIN_WIDTH("min-width", 640),
	MIN_HEIGHT("min-height", 480),
	SAMPLES("samples", 0),
	STEREO_3D("stereo-3d", false),
	TITLE("title", "Cichlid Simulator");
	
	/**
	 * String for referencing the specific setting value.
	 */
	public final String NAME;
	/**
	 * The value for the specific setting.
	 */
	public final Object VALUE;
	
	private DEFAULT_SETTINGS(String name, Object value){
		this.NAME = name;
		this.VALUE = value;
	}//end of constructor
	
}//end of DEFAULT_SETTINGS enum