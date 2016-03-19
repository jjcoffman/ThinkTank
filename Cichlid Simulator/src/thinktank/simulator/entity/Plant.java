package thinktank.simulator.entity;

import com.jme3.asset.AssetManager;
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
		setObj(Main.am.loadModel("AmazonSword.obj"));
		getObj().rotate(0, -1f, 0);
		getObj().setCullHint(CullHint.Never);
		getObj().setLocalTranslation(0, Environment.inchesToWorldUnits(1f), 0);
		setDimensions();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(5.9f);
		worldUnitHeight = Environment.inchesToWorldUnits(5f);
		worldUnitWidth = Environment.inchesToWorldUnits(2f);
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		getObj().scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Plant class