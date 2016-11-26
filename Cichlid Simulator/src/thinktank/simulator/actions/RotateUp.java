package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Player;
import thinktank.simulator.main.Main;

/**
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class RotateUp extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8612618410300287322L;
	public static final String NAME = "rotate-up";

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotateUp instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Player fish;
	private Node obj;

	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateUp</code> action 
	 * with the specified <code>Player</code>.
	 */
	private RotateUp(Player fish){
		this.fish = fish;
		if(fish != null){
			this.obj = fish.getNode();
		}
		else{
			this.obj = null;
		}
	}//end of (Player) constructor

	//---------------------instance methods----------------------------
	//OPERATIONS
	@Override
	public void actionPerformed(ActionEvent evt){
		Main client = Starter.getClient();
		if(fish != null && obj != null && !client.isInMenus() && client.getWorkingScenario() != null && client.getWorkingScenario().isEditingMode()){
			Vector3f side = new Vector3f(0,0,1);
			Vector3f up = new Vector3f(0,1,0);
			Quaternion q = new Quaternion();
			q.fromAngleNormalAxis(-.1f, side);
			obj.rotate(q);
		}
	}//end of actionPerformed method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static RotateUp getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new RotateUp(fish);
			return instance;
		}
	}//end of getInstance(Player) method
	
	public static RotateUp getInstance(){
		if (instance == null){
			System.out.println("Pass in a fish!");
			return null;
		}
		else return instance;
	}//end of getInstance method
	
}//end of RotateUp class