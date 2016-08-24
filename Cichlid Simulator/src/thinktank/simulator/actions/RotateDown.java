package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import gameAssets.Player;

/**
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class RotateDown extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 1693290894336651733L;
	public static final String NAME = "rotate-down";

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotateDown instance = null;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Player fish;
	private Node obj;

	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateDown</code> action 
	 * with the specified <code>Player</code>.
	 */
	private RotateDown(Player fish){
		this.fish = fish;
		this.obj = fish.getNode();
	}//end of (Player) constructor

	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		Vector3f side = new Vector3f(0,0,1);
		Vector3f up = new Vector3f(0,1,0);
		Quaternion q = new Quaternion();
		q.fromAngleNormalAxis(.1f, side);
		obj.rotate(q);
	}//end of actionPerformed method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static RotateDown getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new RotateDown(fish);
			return instance;
		}
	}//end of getInstance(Player) method
	
	public static RotateDown getInstance(){
		if (instance == null){
			System.out.println("Pass in a fish!");
			return null;
		}
		else return instance;
	}//end of getInstance method
	
}//end of RotateDown class