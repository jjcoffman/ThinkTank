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
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------

	private Spatial tank;
	private static Tank theTank;
	//---------------------constructors--------------------------------


	private Tank(AssetManager manager){
		super();
		tank = manager.loadModel("Tank.obj");
		scale(3);
		tank.setQueueBucket(Bucket.Transparent); 
		
	}
	public Spatial getSpatial(){
		return tank;
	}
	public void setSpatial(Spatial spac){
		tank = spac;
	}
	public void scale(float i){
		tank.scale(i);
	}
	//singleton
	public static Tank getTank(AssetManager manager){
		if(theTank == null)
			theTank = new Tank(manager);
		return theTank;
	}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Tank class