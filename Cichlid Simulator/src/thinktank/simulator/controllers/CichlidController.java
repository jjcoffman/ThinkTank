package thinktank.simulator.controllers;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import gameAssets.Cichlid;
import gameAssets.Player;
import thinktank.simulator.actions.MoveBackward;
import thinktank.simulator.actions.MoveForward;
import thinktank.simulator.entity.Fish;

public class CichlidController implements AnalogListener, ActionListener{
	private Player player;
	private static CichlidController cc = null;
	
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
		if (keyPressed > 0){
			switch(name){
			case MoveForward.NAME:
				MoveForward.getInstance(player).actionPerformed(null); break;
			case MoveBackward.NAME:
				MoveBackward.getInstance(player).actionPerformed(null);
				break;
			}
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
	
	
}
