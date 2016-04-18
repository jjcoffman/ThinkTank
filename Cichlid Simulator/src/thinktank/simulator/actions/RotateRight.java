package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.scene.Spatial;

import thinktank.simulator.entity.Fish;

public class RotateRight extends AbstractAction{
	public static final String NAME = "rotate-right";
	private static RotateRight instance = null;
	private Fish fish;
	private Spatial obj;

	private RotateRight(Fish fish){
		this.fish = fish;
		this.obj = fish.getObj();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		obj.rotate(0, -.1f, 0);
	}
	
	public static RotateRight getInstance(Fish fish){
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
