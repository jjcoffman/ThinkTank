package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import gameAssets.Player;
import thinktank.simulator.entity.Fish;

public class RotateRight extends AbstractAction{
	public static final String NAME = "rotate-right";
	private static RotateRight instance = null;
	private Player fish;
	private Node obj;

	private RotateRight(Player fish){
		this.fish = fish;
		this.obj = fish.getNode();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Vector3f up = new Vector3f(0,1,0);
		Quaternion q = new Quaternion();
		q.fromAngleNormalAxis(-.1f, up);
		obj.rotate(q);
	}
	
	public static RotateRight getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new RotateRight(fish);
			return instance;
		}
	}
	public static RotateRight getInstance(){
		if (instance == null){
			System.out.println("Pass in a fish!");
			return null;
		}
		else return instance;
	}
}
