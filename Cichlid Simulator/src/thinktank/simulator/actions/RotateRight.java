package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
		obj.rotate(0, -.1f, 0);
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
