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
	//---------------------constructors--------------------------------

	public Tank(){
		
	}
	public Tank(float s){
		size = s;	
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
	
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Tank class