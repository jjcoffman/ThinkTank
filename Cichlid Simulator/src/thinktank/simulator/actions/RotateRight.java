package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Player;
import thinktank.simulator.main.Main;

/**
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class RotateRight extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 1532622902473342568L;
	public static final String NAME = "rotate-right";

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotateRight instance = null;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Player fish;
	private Node obj;

	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateRight</code> action 
	 * with the specified <code>Player</code>.
	 */
	private RotateRight(Player fish){
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
			Vector3f up = new Vector3f(0,1,0);
			Quaternion q = new Quaternion();
			q.fromAngleNormalAxis(-.1f, up);
			obj.rotate(q);
		}
	}//end of actionPerformed method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static RotateRight getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new RotateRight(fish);
			return instance;
		}
	}//end of getInstance(Player) method
	
	public static RotateRight getInstance(){
		if (instance == null){
			System.out.println("Pass in a fish!");
			return null;
		}
		else return instance;
	}//end of getInstance method
	
}//end of RotateRight class