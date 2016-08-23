package thinktank.simulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import com.jme3.math.Vector3f;
/*****************************************************************************************
 * Class: EnvironmentObject
 * Purpose: Assist in creating Environment Objects
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

/**
 * Concrete type of <code>Entity</code> meant to serve as a base for 
 * objects that make up the environment (i.e. inanimate non-actors).
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class EnvironmentObject extends Entity{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 3137180616296689370L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The name for this environment object.
	 */
	private String name;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default environment object.
	 */
	public EnvironmentObject(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the name of this environment object.
	 * 
	 * @return the name of this environment object.
	 */
	public String getName(){
		return getObj().getName();
	}//end of getName method

	/**
	 * Gets the <code>Vector3f</code> representation of this environment 
	 * object's location in the game world.
	 * 
	 * @return this environment object's location in the game world.
	 */
	public Vector3f getLoc(){
		return getObj().getWorldTranslation();
	}//end of getLoc method
	
	//OPERATIONS
	/**
	 * The readObject method is responsible for reading from the stream and restoring 
	 * the fields of the class.
	 * 
	 * @param stream the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		name = (String)(stream.readObject());
	}//end of readObject method

	/**
	 * The writeObject method is responsible for writing the state of the object 
	 * so that the corresponding readObject method can restore it.
	 * 
	 * @param stream the output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(name);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of EnvironmentObject class