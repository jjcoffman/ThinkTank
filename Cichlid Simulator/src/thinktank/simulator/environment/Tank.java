package thinktank.simulator.environment;
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
	private float size;
	private Spatial tank;
	private static Tank theTank;
	//---------------------constructors--------------------------------

	private Tank(){
		super();
		size =1;
	}

	public float getSize(){
		return size;
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
	public static Tank getTank(){
		if(theTank == null)
			theTank = new Tank();
		return theTank;
	}
	
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Tank class