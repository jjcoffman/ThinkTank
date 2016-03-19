package thinktank.simulator.entity;
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
import com.jme3.scene.Spatial;

import Game.Main;
import thinktank.simulator.environment.Environment;
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Pot extends EnvironmentObject{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 623844889531099635L;
	private static final float MODEL_DEPTH = 4.37242f;//x-axis
	private static final float MODEL_HEIGHT = 4.11213f;//y-axis
	private static final float MODEL_WIDTH = 4.16959f;//z-axis
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	public Pot(){
		setObj(Main.am.loadModel("Pot.obj"));
		getObj().rotate(0, 2f, 0);
		setDimensions();
		getObj().setLocalTranslation(0, Environment.inchesToWorldUnits(1f), 0);
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(2.75f);
		worldUnitHeight = Environment.inchesToWorldUnits(2.5f);
		worldUnitWidth = Environment.inchesToWorldUnits(2.5f);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		getObj().scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Pot class