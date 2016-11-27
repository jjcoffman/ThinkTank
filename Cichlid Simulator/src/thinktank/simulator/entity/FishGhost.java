package thinktank.simulator.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;

/**
 * 
 * @author Bob
 *
 */
public class FishGhost extends GhostControl{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private Fish owner;
	
	//---------------------constructors--------------------------------
	/**
	 * 
	 */
	public FishGhost(){
		super();
		this.owner = null;
	}//end of default constructor
	
	/**
	 * 
	 * @param ghostShape
	 * @param owner
	 */
	public FishGhost(CollisionShape ghostShape, Fish owner){
		super(ghostShape);
		this.owner = owner;
	}//end of (CollisionShape,Fish) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * 
	 * @return
	 */
	public Fish getOwner(){
		return owner;
	}//end of getOwner method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of FishGhost class