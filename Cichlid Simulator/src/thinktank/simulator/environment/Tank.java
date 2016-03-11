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
	//---------------------static variables----------------------------
	private static Tank theTank;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Spatial tank;
	private TANK_TYPE type;
	
	//---------------------constructors--------------------------------
	private Tank(AssetManager manager){
		super();
		tank = manager.loadModel("Tank.obj");
		scale(3);
		tank.setQueueBucket(Bucket.Transparent); 
		type = TANK_TYPE.FIVE_GAL;
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public Spatial getSpatial(){
		return tank;
	}//end of getSpatial method
	
	public TANK_TYPE getType(){
		return type;
	}//end of getType method
	
	//SETTERS
	public void setSpatial(Spatial spac){
		tank = spac;
	}//end of setSpactial method
	
	public void setType(TANK_TYPE type){
		this.type = type;
	}//end of setTYpe method
	
	//OPERATIONS
	public void scale(float i){
		tank.scale(i);
	}//end of scale method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	//singleton
	public static Tank getTank(AssetManager manager){
		if(theTank == null)
			theTank = new Tank(manager);
		return theTank;
	}//end of getTank method
	
}//end of Tank class