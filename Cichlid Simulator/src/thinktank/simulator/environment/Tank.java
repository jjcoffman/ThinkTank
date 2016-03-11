package thinktank.simulator.environment;
/*****************************************************************************************
 * Class: Tank
 * Purpose: Inititates the tank object
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Spatial;
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Tank{
	//---------------------static constants----------------------------
	private static final float MODEL_DEPTH = 3.666458f;//x-axis
	private static final float MODEL_HEIGHT = 3.791440f;//y-axis
	private static final float MODEL_WIDTH = 11.158852f;//z-axis
	
	//---------------------static variables----------------------------
	private static Tank theTank;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Spatial tank;
	private TANK_TYPE type;
	private float worldUnitDepth;//x-axis
	private float worldUnitHeight;//y-axis
	private float worldUnitWidth;//z-axis
	
	//---------------------constructors--------------------------------
	private Tank(AssetManager manager){
		super();
		tank = manager.loadModel("Tank.obj");
		scale(3);
		tank.setQueueBucket(Bucket.Transparent); 
		setType(TANK_TYPE.FIFTY_GAL);
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public Spatial getSpatial(){
		return tank;
	}//end of getSpatial method
	
	public TANK_TYPE getType(){
		return type;
	}//end of getType method
	
	public float getWorldUnitDepth(){
		return worldUnitDepth;
	}//end of getWorldUnitDepth method
	
	public float getWolrdUnitHeight(){
		return worldUnitHeight;
	}//end of getWorldUnitHeight method
	
	public float getWorldUnitWidth(){
		return worldUnitWidth;
	}//end of getWorldUnitWidth method
	
	//SETTERS
	public void setSpatial(Spatial spac){
		tank = spac;
	}//end of setSpactial method
	
	public void setType(TANK_TYPE type){
		this.type = type;
		//TODO this works, but doesn't play well with the sand, so it is disabled for now.
//		setDimensions();
	}//end of setTYpe method
	
	//OPERATIONS
	public void scale(float i){
		tank.scale(i);
	}//end of scale method
	
	/**
	 * Scales the model based on the TANK_TYPE and the base model dimensions 
	 * defined in the constants <code>MODEL_WIDTH</code>, <code>MODEL_HEIGHT</code>, 
	 * and <code>MODEL_DEPTH</code>.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(type.DEPTH);
		worldUnitHeight = Environment.inchesToWorldUnits(type.HEIGHT);
		worldUnitWidth = Environment.inchesToWorldUnits(type.WIDTH);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		tank.scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	//singleton
	public static Tank getTank(AssetManager manager){
		if(theTank == null)
			theTank = new Tank(manager);
		return theTank;
	}//end of getTank method
	
}//end of Tank class