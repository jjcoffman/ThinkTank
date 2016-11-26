package thinktank.simulator.main;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import thinktank.simulator.Starter;

public class CichlidController extends RigidBodyControl {
	
	private Vector3f moveDir;
	private Quaternion viewDir;
	private float moveSpeed, rotSpeed;
	private boolean atLoc = false;

	public CichlidController(CollisionShape shape, float mass, float moveSpeed, float rotSpeed) {
		super(shape, mass);
		this.moveSpeed = moveSpeed;
		this.rotSpeed = rotSpeed;
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().addTickListener(physics);
	}
	
	public void moveDirection(Vector3f direction){
		moveDir = direction;
	}
	public void lookAt(Quaternion loc){
		viewDir = loc;
	}
	
	public void isAtLoc(boolean isAtLoc){
		atLoc = isAtLoc;
	}
	PhysicsTickListener physics = new PhysicsTickListener(){

		@Override
		public void physicsTick(PhysicsSpace arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		    if (moveDir != null) {
		    	setLinearVelocity(moveDir); 
		    	moveDir = null;
		    }
		    if (atLoc){
		    	clearForces();
		    	setLinearVelocity(Vector3f.ZERO);
		    }
		    
			
		}
	};

}
