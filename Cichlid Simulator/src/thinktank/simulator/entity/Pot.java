package thinktank.simulator.entity;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/*****************************************************************************************
 * Class: Pot
 * Purpose: create Pot Objects
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
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import Game.Main;
import thinktank.simulator.environment.Environment;
/**
 * Concrete type of <code>Entity</code> representing a pot environment object.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class Pot extends EnvironmentObject{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 623844889531099635L;
	/**
	 * Constant value for the default pot depth (on the x-axis).
	 */
	private static final float MODEL_DEPTH = 1;
	/**
	 * Constant value for the default pot depth (on the y-axis).
	 */
	private static final float MODEL_HEIGHT = 1;
	/**
	 * Constant value for the default pot depth (on the z-axis).
	 */
	private static final float MODEL_WIDTH = 1;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default pot.
	 */
	public Pot(){
		init();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * Loads the model and initializes this pot to the appropriate values.
	 */
	private void init(){
		setObj(Main.am.loadModel("Pot/Pot.obj"));
		getObj().rotate(0, 2f, 0);
		setDimensions();
		getObj().setLocalTranslation(0, Environment.inchesToWorldUnits(1f), 0);
	}//end of init method

	/**
	 * Scales the model to the appropriate dimensions as defined in the 
	 * constants <code>MODEL_WIDTH</code>, <code>MODEL_HEIGHT</code>, 
	 * and <code>MODEL_DEPTH</code>.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(3f);
		worldUnitHeight = Environment.inchesToWorldUnits(3f);
		worldUnitWidth = Environment.inchesToWorldUnits(3f);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		getObj().scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method

	/**
	 * The readObject method is responsible for reading from the stream and restoring 
	 * the fields of the class.
	 * 
	 * @param stream the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		init();
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

	/**
	 * The writeObject method is responsible for writing the state of the object 
	 * so that the corresponding readObject method can restore it.
	 * 
	 * @param stream the output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
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
}//end of Pot class