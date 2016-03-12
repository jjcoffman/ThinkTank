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
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Pot extends EnvironmentObject{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	public Pot(AssetManager am){
		setObj(am.loadModel("Pot.obj"));
		getObj().rotate(0, 2f, 0);
		getObj().scale(.5f);
	}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Pot class