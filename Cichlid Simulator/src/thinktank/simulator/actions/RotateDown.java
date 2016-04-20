package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import gameAssets.Player;

public class RotateDown extends AbstractAction{
	public static final String NAME = "rotate-down";
	private static RotateDown instance = null;
	private Player fish;
	private Node obj;

	private RotateDown(Player fish){
		this.fish = fish;
		this.obj = fish.getNode();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Vector3f side = new Vector3f(0,0,1);
		Vector3f up = new Vector3f(0,1,0);
		Quaternion q = new Quaternion();
		q.fromAngleNormalAxis(.1f, side);
		obj.rotate(q);
	}
	
	public static RotateDown getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new RotateDown(fish);
			return instance;
		}
	}
	public static RotateDown getInstance(){
		if (instance == null){
			System.out.println("Pass in a fish!");
			return null;
		}
		else return instance;
	}
}
