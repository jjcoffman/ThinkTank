package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import gameAssets.Player;
import thinktank.simulator.entity.Fish;

public class MoveForward extends AbstractAction{
	public static final String NAME = "move-forward";
	private static MoveForward instance = null;
	private Player fish;
	private Node obj;
	
	public MoveForward(Player fish){
		this.fish = fish;
		this.obj = fish.getNode();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Vector3f newLoc = new Vector3f();
		Vector3f curLoc = new Vector3f(obj.getLocalTranslation());
		
		newLoc = curLoc.addLocal(obj.getLocalRotation().getRotationColumn(0).mult(-fish.getSpeed()/100));
		obj.setLocalTranslation(newLoc);
	}

	public static MoveForward getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new MoveForward(fish);
			return instance;
		}
	}
	public static MoveForward getInstance(){
		if (instance == null){
			System.out.println("Pass fish");
			return null;
		}
		else return instance;
	}

}
