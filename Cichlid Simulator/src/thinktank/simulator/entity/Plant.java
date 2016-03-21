package thinktank.simulator.entity;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
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
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Plant extends EnvironmentObject{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8500489926122948173L;
	private static final float MODEL_DEPTH = 2.89302f;//x-axis
	private static final float MODEL_HEIGHT = 2.450393f;//y-axis
	private static final float MODEL_WIDTH = 1.033837f;//z-axis

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	public Plant(){
		init();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	private void init(){
		setObj(Main.am.loadModel("AmazonSword.obj"));
		getObj().rotate(0, -1f, 0);
		getObj().setCullHint(CullHint.Never);
		getObj().setLocalTranslation(0, Environment.inchesToWorldUnits(1f), 0);
		setDimensions();
	}//end of init method
	
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(5.9f);
		worldUnitHeight = Environment.inchesToWorldUnits(5f);
		worldUnitWidth = Environment.inchesToWorldUnits(2f);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		getObj().scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
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