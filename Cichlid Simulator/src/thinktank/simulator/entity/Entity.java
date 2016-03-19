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
		//values for Spatial local rotation
		float rotX = stream.readFloat();
		float rotY = stream.readFloat();
		float rotZ = stream.readFloat();
		float rotW = stream.readFloat();
		Quaternion rot = new Quaternion(rotX, rotY, rotZ, rotW);
		//values for Spatial local scale
		float scaleX = stream.readFloat();
		float scaleY = stream.readFloat();
		float scaleZ = stream.readFloat();
		Vector3f scale = new Vector3f(scaleX, scaleY, scaleZ);
		//values for Spatial local translate
		float transX = stream.readFloat();
		float transY = stream.readFloat();
		float transZ = stream.readFloat();
		Vector3f trans = new Vector3f(transX, transY, transZ);
		//set Spatial transform
		Transform xform = new Transform(trans, rot, scale);
		getObj().setLocalTransform(xform);
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
		//values for Spatial local rotation
		stream.writeFloat(getObj().getLocalRotation().getX());
		stream.writeFloat(getObj().getLocalRotation().getY());
		stream.writeFloat(getObj().getLocalRotation().getZ());
		stream.writeFloat(getObj().getLocalRotation().getW());
		//values for Spatial local scale
		stream.writeFloat(getObj().getLocalScale().getX());
		stream.writeFloat(getObj().getLocalScale().getY());
		stream.writeFloat(getObj().getLocalScale().getZ());
		//values for Spatial local translate
		stream.writeFloat(getObj().getLocalTranslation().getX());
		stream.writeFloat(getObj().getLocalTranslation().getY());
		stream.writeFloat(getObj().getLocalTranslation().getZ());
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Entity class