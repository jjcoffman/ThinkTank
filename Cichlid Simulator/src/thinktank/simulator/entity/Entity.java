package thinktank.simulator.entity;
/*****************************************************************************************
 * Class: Entity
 * Purpose: Base Object class used by all environmental objects
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

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