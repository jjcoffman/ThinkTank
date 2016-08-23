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
 * Abstract base type for all animate and inanimate objects that make up a scenario.
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
	/**
	 * Unique number to identify a specific entity.
	 */
	private long id;
	/**
	 * The <code>Spatial</code> object representing the visible model 
	 * for this entity.
	 */
	private Spatial obj;
	/**
	 * Value for the entity depth (on the x-axis) in game world units.
	 */
	protected float worldUnitDepth;
	/**
	 * Value for the entity depth (on the y-axis) in game world units.
	 */
	protected float worldUnitHeight;
	/**
	 * Value for the entity depth (on the z-axis) in game world units.
	 */
	protected float worldUnitWidth;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default entity.
	 */
	public Entity(){
		id = Main.RNG.nextLong();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the id number of this entity.
	 * 
	 * @return the id number in this entity.
	 */
	public long getID(){
		return id;
	}//end of getID method

	/**
	 * Gets the model for this entity.
	 * 
	 * @return the model for this entity.
	 */
	public Spatial getObj(){
		return obj;
	}//end of getObj method

	/**
	 * Gets the depth of this entity in world units.
	 * 
	 * @return the depth of this entity in world units.
	 */
	public float getWorldUnitDepth(){
		return worldUnitDepth;
	}//end of getWorldUnitDepth method

	/**
	 * Gets the height of this entity in world units.
	 * 
	 * @return the height of this entity in world units.
	 */
	public float getWolrdUnitHeight(){
		return worldUnitHeight;
	}//end of getWorldUnitHeight method

	/**
	 * Gets the width of this entity in world units.
	 * 
	 * @return the width of this entity in world units.
	 */
	public float getWorldUnitWidth(){
		return worldUnitWidth;
	}//end of getWorldUnitWidth method
	
	//SETTERS
	/**
	 * Sets the model for this entity to the specified <code>Spatial</code> object.
	 * 
	 * @param obj the <code>Spatial</code> object to which this entity's model 
	 * is to be set.
	 */
	public void setObj(Spatial obj){
		this.obj = obj;
	}//end of setObj method
	
	//OPERATIONS
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * Two entities are considered equal if and only if their id numbers are equal.
	 * 
	 * @param obj the <code>Object</code> to compare.
	 * @return true if the objects are equal, false otherwise.
	 */
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

	/**
	 * The readObject method is responsible for reading from the stream and restoring 
	 * the fields of the class.
	 * 
	 * @param stream the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
	}//end of readObject method

	/**
	 * The writeObject method is responsible for writing the state of the object 
	 * so that the corresponding readObject method can restore it.
	 * 
	 * @param stream the output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Entity class