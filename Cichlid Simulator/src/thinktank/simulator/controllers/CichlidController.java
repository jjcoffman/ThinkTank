package thinktank.simulator.controllers;

import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import Game.Main;
import gameAssets.Cichlid;
import gameAssets.Player;
import thinktank.simulator.actions.MoveBackward;
import thinktank.simulator.actions.MoveForward;
import thinktank.simulator.actions.RotateDown;
import thinktank.simulator.actions.RotateLeft;
import thinktank.simulator.actions.RotateRight;
import thinktank.simulator.actions.RotateUp;
import thinktank.simulator.entity.Fish;

public class CichlidController implements AnalogListener, ActionListener{
	private Player player;
	private static CichlidController cc = null;
	private float value = 0;
	private boolean vert;
	private Vector3f WORLD_X_AXIS = new Vector3f(1,0,0);
	private Vector3f WORLD_Y_AXIS = new Vector3f(0,1,0);
	private Vector3f WORLD_Z_AXIS = new Vector3f(0,0,1);
	private float pitch = 0;
	
	public CichlidController(Player player){
		this.player = player;
	}

	@Override
	public void onAction(String name, boolean keyPressed, float tpf) {
		if(keyPressed){
			
		}
	}
	
	@Override
	public void onAnalog(String name, float keyPressed, float tpf) {
		
		//grab rotation along Z axis and store as float pitch
		pitch = player.getCam().getLocalRotation().getZ();
		
		if (keyPressed > 0){
			System.out.println("Pitch: " + pitch);
			switch(name){

			/*
			 * Move camNode forward at speed tpf/5, should be tpf*speed
			 * Using tpf makes it so that the movement is the same
			 * on fast and slow computers
			 * Consider changing to the movement example in "advance terrain collision"
			 * which requires moving code to simpleUpdate();
			 */
			
			case MoveForward.NAME:
				Vector3f movementf = new Vector3f(0,0,tpf/5);
				player.getPhysicsControl().setPhysicsLocation(movementf);
				playermove(movementf);
				//MoveForward.getInstance(player).actionPerformed(null);
				break;
			case MoveBackward.NAME:
				Vector3f movementb = new Vector3f(0,0,-tpf/10);

				player.getPhysicsControl().setPhysicsLocation(movementb);
				//MoveBackward.getInstance(player).actionPerformed(null);
				playermove(movementb);
				break;

				/*
				 * Store vert boolean for rotations to correct pitch rotation later
				 * keyPressed is also stored in float value to be used to calculate pitch
				 */
				
			case RotateLeft.NAME:
				vert = false;
				player.getCam().rotate(0, tpf*5, 0);
				//RotateLeft.getInstance(player).actionPerformed(null);
				break;
			case RotateRight.NAME:
				vert = false;
				player.getCam().rotate(0, -tpf*5, 0);
				//RotateRight.getInstance(player).actionPerformed(null);
				break;
			case RotateUp.NAME:
				value = -keyPressed;
				vert = true;
				//RotateUp.getInstance(player).actionPerformed(null);
				break;
			case RotateDown.NAME:
				value = keyPressed;
				vert = true;
				//RotateDown.getInstance(player).actionPerformed(null);
				break;
			}
			
			if (vert){
				//store original rotation to revert, works as a limiter, does not work yet
				//TODO limit camera movement so fish can't look directly up/down
				Quaternion orig = new Quaternion();
				orig = player.getCam().getLocalRotation().normalizeLocal();
				System.out.println("Verticle movement");
				
				pitch += value;
				
				//test if rotation exceeds limit, doesnt work yet???
				if ((pitch >.5f) || (pitch < -.5f)){
					player.getCam().getLocalRotation().set(orig);
					System.out.println("STOP IN THE NAME OF LOVE");
				}
				else{
					float i = tpf;
					if(value < 0){
						i = -tpf;
					}
					player.getCam().rotate(i*2.25f, 0, 0);
				}
				System.out.println("New pitch: " + pitch);
				vert=false;
			}
		}
		//correct orientation
		Vector3f loc = player.getObj().getWorldTranslation();
		player.getCam().lookAt(loc, WORLD_Y_AXIS);
	}

	private void playermove(Vector3f movement){
		player.getCam().setLocalTranslation(player.getCam().localToWorld(movement,movement));
	}
	
	public static CichlidController getInstance(Player player){
		cc = new CichlidController(player);
		return cc;
	}
	public static CichlidController getInstance(){
		if (cc == null){
			System.out.println("Pass in player cichlid");
			return null;
		}
		else return cc;
	}
	
}
