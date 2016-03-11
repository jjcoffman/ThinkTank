package gameAssets;
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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

import gameAssets.strategies.IStrategy;
import thinktank.simulator.entity.Fish;

public class Cichlid extends Fish
{
	private float speed;
	private String sex;
	private float size;
	private IStrategy strategy;
	private Spatial cichlid;
	
	//have to determine which we want to be our own types or existing
	public Cichlid()
	{
		
	}
	
	public Cichlid(float siz, float spee, String se){
		speed = spee;
		sex = se; 
		size = siz;
	}
	public void makeObj(AssetManager am) {
		cichlid = am.loadModel("Cichlid_v5.obj");
		cichlid.scale(.1f);
		cichlid.rotate(0, 45f, 0);
		cichlid.move(0, 18, 0);
	}
	
	//Added for controlling fish 
	public void addControl(AbstractControl control){
		cichlid.addControl(control);
	}

	public String getSex(){
		return sex;
	}
	public float getSpeed(){
		return speed;
	}
	public IStrategy getStrategy(){
		return strategy;
	}
	public float getSize(){
		return size;
	}
	public Spatial getObj(){
		return cichlid;
	}
	public void setSpeed(float spee){
		speed = spee;
	}
	public void setSex(String se){
		sex = se;
	}
	public void setStrategy(IStrategy strat){
		strategy = strat;
	}
	public void setSize(float siz){
		size = siz;
	}
}