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
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Pot extends EnvironmentObject{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 623844889531099635L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	public Pot(){
		setObj(Main.am.loadModel("Pot.obj"));
		getObj().setLocalScale(.5f);
		getObj().rotate(0, 2f, 0);
	}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Pot class