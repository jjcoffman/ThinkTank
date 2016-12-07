package thinktank.simulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

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
		this.name = "unnamed";
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the name of this environment object.
	 * 
	 * @return the name of this environment object.
	 */
	public String getName(){
		return name;
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
	
	//SETTERS
	/**
	 * Sets the name of this fish to the specified value.
	 * 
	 * @param name the value to which the name is to be set.
	 */
	public void setName(String name){
		this.name = name;
		if(getObj() instanceof Geometry){
			Geometry geom = (Geometry)getObj();
			geom.setName(name);
		}
		if(getObj() instanceof Node){						// sets the name of the geometry
			Node objNode = (Node)getObj();						// which is returned by the ray collision
			for(int i=0; i<objNode.getChildren().size(); i++){	// when selecting with mouse clicks
				objNode.getChild(i).setName(name+"-"+i);	
			}
		}
	}//end of setName method
	
	/**
	 * Not implemented in this type.
	 */
	@Override
	public void setGlow(boolean glow){
		//not implemented in this class
	}//end of setGlow method
	
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
		String name = (String)(stream.readObject());
		this.setName(name);
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
	
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of EnvironmentObject class