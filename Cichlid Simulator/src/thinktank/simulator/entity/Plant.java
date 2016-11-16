package thinktank.simulator.entity;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Random;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import Game.Main;
import thinktank.simulator.environment.Environment;

/*****************************************************************************************
 * Class: Plant
 * Purpose: creates plant objects
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/

/**
 * Concrete type of <code>Entity</code> representing a plant environment object.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class Plant extends EnvironmentObject{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8500489926122948173L;
	/**
	 * Constant value for the default plant depth (on the x-axis).
	 */
	private static final float MODEL_DEPTH = 2.89302f;
	/**
	 * Constant value for the default plant depth (on the y-axis).
	 */
	private static final float MODEL_HEIGHT = 2.450393f;
	/**
	 * Constant value for the default plant depth (on the z-axis).
	 */
	private static final float MODEL_WIDTH = 1.033837f;

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private ArrayList<Material> mats;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default plant.
	 */
	public Plant(){
		//NOTE: avoid initializing variables here. Do so in init() method.
		init();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//SETTERS
	public void setGlow(boolean glow){
		if(glow){
			for(Material mat : mats){
				mat.setColor("GlowColor", ColorRGBA.Yellow);
			}
		}
		else{
			for(Material mat : mats){
			    mat.setColor("GlowColor", ColorRGBA.Black);
			}
		}
	}//end of setGlow method
	
	//OPERATIONS
	/**
	 * Loads the model and initializes this plant to the appropriate values.
	 */
	private void init(){
		mats = new ArrayList<Material>();
		
		setObj(Main.am.loadModel("Plants/Hygrophila.obj"));
		
		if(getObj() instanceof Node){//get and store materials
			Node node = (Node)getObj();
			for(int i=0; i<node.getChildren().size(); i++){
				if(node.getChild(i) instanceof Geometry){
					Geometry geom = (Geometry)node.getChild(i);
					
					mats.add(geom.getMaterial());
				}
			}
		}
		else if(getObj() instanceof Geometry){
			Geometry geom = (Geometry)getObj();
			mats.add(geom.getMaterial());
		}
		
		float y = (float) Math.toRadians(Main.RNG.nextInt(360));
		getObj().rotate(0, y, 0);
		getObj().setCullHint(CullHint.Never);
		getObj().setLocalTranslation(0, Environment.inchesToWorldUnits(1f), 0);
		setDimensions();
		int next = Main.RNG.nextInt(50);
		float scale = .3f + (float)next/100;
		getObj().scale(scale, scale, scale);
		//getObj().setName("Plant");
	}//end of init method

	/**
	 * Scales the model to the appropriate dimensions as defined in the 
	 * constants <code>MODEL_WIDTH</code>, <code>MODEL_HEIGHT</code>, 
	 * and <code>MODEL_DEPTH</code>.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(5.9f);
		worldUnitHeight = Environment.inchesToWorldUnits(5f);
		worldUnitWidth = Environment.inchesToWorldUnits(2f);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		getObj().setLocalScale(depthFactor, heightFactor, widthFactor);
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
}//end of Plant class