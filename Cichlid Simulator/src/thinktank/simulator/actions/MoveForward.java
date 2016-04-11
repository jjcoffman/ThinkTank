package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import thinktank.simulator.entity.Fish;

public class MoveForward extends AbstractAction{
	public static final String NAME = "move-forward";
	
	private Fish fish;
	private Spatial obj;
	
	public MoveForward(Fish fish){
		this.fish = fish;
		this.obj = fish.getObj();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Vector3f newLoc = new Vector3f();
		Quaternion rot = obj.getLocalRotation();
		Vector3f dir = new Vector3f(0,0,1);
		Vector3f curLoc = new Vector3f(obj.getLocalTranslation());
		
		newLoc = curLoc.addLocal(obj.getLocalRotation().getRotationColumn(0).mult(-fish.getSpeed()/100));
		obj.setLocalTranslation(newLoc);
	}



}
