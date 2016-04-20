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
	private float rx = 0;
	private float ry = 0;
	private float rz = 0;
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
		//pitch = player.getCam().getLocalRotation().getY();
		pitch = player.getNode().getLocalRotation().getZ();
		if (keyPressed > 0){
			System.out.println("Pitch: " + pitch);
			Quaternion verticle = new Quaternion();
			Quaternion old = player.getNode().getLocalRotation();
			Quaternion r = new Quaternion();
			float y = 0;
			float x = 0;
			switch(name){
			case MoveForward.NAME:
				
				Vector3f movement = new Vector3f(0,0,tpf);
				playermove(movement);
				
				
				//MoveForward.getInstance(player).actionPerformed(null);
				
				break;
			case MoveBackward.NAME:
				MoveBackward.getInstance(player).actionPerformed(null);
				break;
			case RotateLeft.NAME:
				rx += keyPressed;
				value = keyPressed;
				vert = false;
				y = keyPressed;
				
				player.getCam().rotate(0, tpf, 0);
				//RotateLeft.getInstance(player).actionPerformed(null);
				break;
			case RotateRight.NAME:
				rx -= keyPressed;
				value = keyPressed;
				vert = false;
				y = -keyPressed;
				
				player.getCam().rotate(0, -tpf, 0);
				
				//RotateRight.getInstance(player).actionPerformed(null);
				break;
			case RotateUp.NAME:
				ry -= keyPressed;
				value = -keyPressed;
				vert = true;
				x = -keyPressed;

				/*verticle.fromAngleNormalAxis(-value, WORLD_Z_AXIS);
				r = old.multLocal(verticle);
				player.getNode().setLocalRotation(r);
				*/
				//player.getNode().rotate(0, 0, value);
				//Main.followCam.setDefaultVerticalRotation(Main.followCam.getVerticalRotation() + value);
				//RotateUp.getInstance(player).actionPerformed(null);
				break;
			case RotateDown.NAME:
				ry += keyPressed;
				value = keyPressed;
				vert = true;
				x = keyPressed;
				
				/*verticle.fromAngleNormalAxis(value, WORLD_Z_AXIS);
				r = old.multLocal(verticle);
				player.getNode().setLocalRotation(r);
				*/
				//player.getNode().rotate(0, 0, value);
				//Main.followCam.setDefaultVerticalRotation(Main.followCam.getVerticalRotation() + value);
				//RotateDown.getInstance(player).actionPerformed(null);
				break;
			}
			/*if (!vert){
				player.getNode().rotate(0, value, 0);
				Main.followCam.setDefaultHorizontalRotation(Main.followCam.getHorizontalRotation() - value);
				
			}
			else if (vert){
				player.getNode().rotate(0, 0, value);
				Main.followCam.setDefaultVerticalRotation(Main.followCam.getVerticalRotation() + value);
				
			}*/
			
			
			if (vert){
				Quaternion orig = new Quaternion();
				orig = player.getCam().getLocalRotation().normalizeLocal();
				System.out.println("Verticle movement");
				float rotate = pitch +=value;
				//pitch += value;
				//pitch = pitch * FastMath.RAD_TO_DEG;
				if ((rotate > .5f) || (rotate < -.5f)){
					player.getCam().getLocalRotation().set(orig);
					//player.getNode().rotate(0, 0, value);
				}
				else {
					player.getCam().rotate(value, 0, 0);
					Quaternion norm = player.getCam().getLocalRotation().normalizeLocal();
					player.getCam().getLocalRotation().set(norm);
					Vector3f loc = player.getObj().getWorldTranslation();
					player.getCam().lookAt(loc, WORLD_Y_AXIS);
					//Main.followCam.setDefaultVerticalRotation(Main.followCam.getVerticalRotation() + value);
					pitch = rotate;
				}
				System.out.println("New pitch: " + pitch);
			}
			
			/*
			Quaternion verticle = new Quaternion();
			verticle.fromAngleNormalAxis(ry, WORLD_Z_AXIS);
			Quaternion horizontal = new Quaternion();
			horizontal.fromAngleNormalAxis(rx, WORLD_Y_AXIS);
			Quaternion r = new Quaternion();
			r = verticle.mult(horizontal);
			player.getNode().setLocalRotation(r);*/
		}
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

	private void playermove(Vector3f movement){
		player.getCam().setLocalTranslation(player.getCam().localToWorld(movement,movement));
	}
	
}
