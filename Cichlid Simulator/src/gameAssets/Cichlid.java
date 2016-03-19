package gameAssets;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/*****************************************************************************************
 * Class: Cichlid
 * Purpose: Create the Cichlid objects and handle movement
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added addControl() method with spinning for testing obj movement
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

import Game.Main;
import gameAssets.strategies.IStrategy;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.environment.Environment;

public class Cichlid extends Fish{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8763564513637299079L;
	private static final float MODEL_DEPTH = 24.217279f;//z-axis

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private float speed;
	private String sex;
	private float size;
	private IStrategy strategy;

	//---------------------constructors--------------------------------
	public Cichlid(){
		speed = 1.0f;
		sex = "male";
		size = 1.0f;
		strategy = null;
		setObj(Main.am.loadModel("Cichlid_v5.obj"));
		getObj().setLocalTranslation(Environment.inchesToWorldUnits(2f), Environment.inchesToWorldUnits(4f), Environment.inchesToWorldUnits(1f));
		setDimensions();
	}//end of default constructor
	
	public Cichlid(float size, float speed, String sex){
		this.speed = speed;
		this.sex = sex; 
		this.size = size;
		strategy = null;
		setObj(Main.am.loadModel("Cichlid_v5.obj"));
		getObj().setLocalTranslation(Environment.inchesToWorldUnits(2f), Environment.inchesToWorldUnits(4f), Environment.inchesToWorldUnits(1f));
		setDimensions();
	}//end of (float,float,String) constructor

	//---------------------instance methods----------------------------
	//GETTERS
	public String getSex(){
		return sex;
	}//end of getSex method
	
	public float getSpeed(){
		return speed;
	}//end of getSpeed method
	
	public IStrategy getStrategy(){
		return strategy;
	}//end of getStrategy method
	
	public float getSize(){
		return size;
	}//end of getSize method
	
	//SETTERS
	public void setSpeed(float speed){
		this.speed = speed;
	}//end of setSpeed method
	
	public void setSex(String sex){
		this.sex = sex;
	}//end of setSex method
	
	public void setStrategy(IStrategy strat){
		strategy = strat;
	}//end of setStrategy method
	
	public void setSize(float size){
		this.size = size;
	}//end of setSize method
	
	//OPERATIONS
	public void addControl(AbstractControl control){
		getObj().addControl(control);
	}//end of addControl method
	
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(2.51969f);
		float sizeFactor = worldUnitDepth / MODEL_DEPTH;
		getObj().scale(sizeFactor);
	}//end of setDimensions method

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Cichlid class