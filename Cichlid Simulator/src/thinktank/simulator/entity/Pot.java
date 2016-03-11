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
		getObj().rotate(0, 1.5f, 0);
		getObj().scale(.75f);
		getObj().move(0, 16.5f, 10);
	}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Pot class