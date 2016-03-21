package thinktank.simulator.entity;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
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

import Game.Main;
import thinktank.simulator.environment.Environment;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public abstract class Entity implements Serializable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 5841419875866449310L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private long id;
	private Spatial obj;
	protected float worldUnitDepth;//x-axis
	protected float worldUnitHeight;//y-axis
	protected float worldUnitWidth;//z-axis
	
	//---------------------constructors--------------------------------
	public Entity(){
		id = Main.RNG.nextLong();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public long getID(){
		return id;
	}//end of getID method
	
	public Spatial getObj(){
		return obj;
	}//end of getObj method

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
	public void setObj(Spatial obj){
		this.obj = obj;
	}//end of setObj method
	
	//OPERATIONS
	@Override
	public boolean equals(Object obj){
		boolean returnValue = false;
		if(obj instanceof Entity){
			if(((Entity)obj).getID() == id){
				returnValue = true;
			}
		}
		return returnValue;
	}//end of equals method
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Entity class