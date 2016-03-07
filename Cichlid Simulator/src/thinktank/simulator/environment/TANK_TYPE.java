package thinktank.simulator.environment;

/**
 * Enumerated type for built-in tank dimension options.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public enum TANK_TYPE{
	
	FIVE_GAL(5, 16.1875f, 8.375f, 10.5f, "5 Gal. (16 3/16 x 8 3/8 x 10 1/2"),
	TEN_GAL(10, 20.25f, 10.5f, 12.5625f, "10 Gal. (20 1/4 x 10 1/2 x 12 9/16)"),
	FIFTEEN_GAL(15, 24.25f, 12.5f, 12.75f, "15 Gal. (24 1/4 x 12 1/2 x 12 3/4)"),
	FIFTEEN_GAL_TALL(15, 20.25f, 10.5f, 18.75f, "15 Gal. - tall (20 1/4 x 10 1/2 x 18 3/4)"),
	TWENTY_GAL_TALL(20, 24.25f, 12.5f, 16.75f, "20 Gal. - tall (24 1/4 x 12 1/2 x 16 3/4)"),
	TWENTY_GAL_EXTRA_TALL(20, 20.0f, 10.0f, 24.0f, "20 Gal. - extra-tall (20 x 10 x 24)"),
	TWENTY_GAL_LONG(20, 30.25f, 12.5f, 12.75f, "20 Gal. - long (30 1/4 x 12 1/2 x 12 3/4)"),
	TWENTY_FIVE_GAL(25, 24.25f, 12.5f, 20.75f, "25 Gal. (24 1/4 x 12 1/2 x 20 3/4)"),
	THIRTY_GAL(30, 36.25f, 12.625f, 16.75f, "30 Gal. (36 1/4 x 12 5/8 x 16 3/4)"),
	THIRTY_GAL_TALL(30, 24.25f, 12.5f, 34.75f, "30 Gal. - tall (24 1/4 x 12 1/2 x 24 3/4)"),
	THIRTY_GAL_SHORT(30, 36.1875f, 18.25f, 12.9375f, "30 Gal. - short (36 3/16 x 18 1/4 x 12 15/16)"),
	THIRTY_SEVEN_GAL(37, 30.25f, 12.5f, 22.75f, "37 Gal. (30 1/4 x 12 1/2 x 22 3/4)"),
	FORTY_GAL_SHORT(40, 36.1875f, 18.25f, 16.9375f, "40 Gal. - short (36 3/16 x 18 1/4 x 16 15/16)"),
	FORTY_GAL_LONG(40, 48.25f, 12.75f, 16.875f, "40 Gal. - long (48 1/4 x 12 3/4 x 16 7/8)"),
	FORTY_GAL_TALL(40, 36.0f, 13.0f, 20.0f, "40 Gal. - tall (36 x 13 x 20)"),
	FIFTY_GAL(50, 36.375f, 18.375f, 19.0f, "50 Gal. (36 3/8 x 18 3/8 x 19)");
	
	/**
	 * Volume of water that the tank holds, in gallons.
	 */
	public final int VOLUME;
	/**
	 * Length of the tank, in inches.
	 */
	public final float LENGTH;
	/**
	 * Width of the tank, in inches.
	 */
	public final float WIDTH;
	/**
	 * Height of the tank, in inches.
	 */
	public final float HEIGHT;
	/**
	 * Display name for describing the tank type info in a readable format.
	 */
	public final String DISPLAY_NAME;
	
	private TANK_TYPE(int volume, float length, float width, float height, String displayName){
		this.VOLUME = volume;
		this.LENGTH = length;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.DISPLAY_NAME = displayName;
	}

}//end of TANK_TYPE enum