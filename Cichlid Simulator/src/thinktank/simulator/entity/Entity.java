package thinktank.simulator.entity;

import com.jme3.scene.Spatial;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Entity {
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Spatial obj;
	//---------------------constructors--------------------------------
	//---------------------instance methods----------------------------
	
	public Spatial getObj(){
		return obj;
	}
	public void setObj(Spatial obj){
		this.obj = obj;
	}
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Entity class