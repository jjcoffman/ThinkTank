package thinktank.simulator.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;

/**
 * Represents a ghost of another fish, helping manage their collisions.
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class FishGhost extends GhostControl{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * A reference to the fish that the ghost is attached to.
	 */
	private Fish owner;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a base <code>FishGhost</code> object.
	 */
	public FishGhost(){
		super();
		this.owner = null;
	}//end of default constructor
	
	/**
	 * Constructs a <code>FishGhost</code> object for the specified 
	 * collision shape and fish.
	 * 
	 * @param ghostShape the collision shape.
	 * @param owner the fish.
	 */
	public FishGhost(CollisionShape ghostShape, Fish owner){
		super(ghostShape);
		this.owner = owner;
	}//end of (CollisionShape,Fish) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Returns a reference to the owner fish this ghost is attached to.
	 * 
	 * @return the owner fish.
	 */
	public Fish getOwner(){
		return owner;
	}//end of getOwner method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of FishGhost class