package thinktank.simulator.main;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import thinktank.simulator.Starter;

/**
 * 
 * @author Bob
 *
 */
public class CichlidController extends RigidBodyControl{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private PhysicsTickListener physics = new PhysicsTickListener(){
		/**
		 * 
		 */
		@Override
		public void physicsTick(PhysicsSpace space, float tpf){
		}//end of physicsTick method

		/**
		 * 
		 */
		@Override
		public void prePhysicsTick(PhysicsSpace space, float tpf){
		    if(moveDir != null){
		    	setLinearVelocity(moveDir); 
		    	moveDir = null;
		    }
		    if(atLoc){
		    	clearForces();
		    	setLinearVelocity(Vector3f.ZERO);
		    }
		}//end of prePhysicsTick method
	};//end of physics declaration
	/**
	 * 
	 */
	private Vector3f moveDir;
	/**
	 * 
	 */
	private Quaternion viewDir;
	/**
	 * 
	 */
	private float moveSpeed;
	/**
	 * 
	 */
	private float rotSpeed;
	/**
	 * 
	 */
	private boolean atLoc;
	
	//---------------------constructors--------------------------------
	/**
	 * 
	 * @param shape
	 * @param mass
	 * @param moveSpeed
	 * @param rotSpeed
	 */
	public CichlidController(CollisionShape shape, float mass, float moveSpeed, float rotSpeed){
		super(shape, mass);
		moveDir = null;
		viewDir = null;
		atLoc = false;
		this.moveSpeed = moveSpeed;
		this.rotSpeed = rotSpeed;
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().addTickListener(physics);
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	/**
	 * 
	 * @param direction
	 */
	public void moveDirection(Vector3f direction){
		moveDir = direction;
	}//end of moveDirection method
	
	/**
	 * 
	 * @param loc
	 */
	public void lookAt(Quaternion loc){
		viewDir = loc;
	}//end of lookAt method
	
	/**
	 * 
	 * @param isAtLoc
	 */
	public void isAtLoc(boolean isAtLoc){
		atLoc = isAtLoc;
	}//end of isAtLoc method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of CichlidController class