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
public class RotatePlayerUpAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -8612618410300287322L;
	/**
	 * 
	 */
	public static final String NAME = "rotate-up";

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static RotatePlayerUpAction instance = null;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * 
	 */
	private Player fish;
	/**
	 * 
	 */
	private Node obj;

	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>RotateUp</code> action 
	 * with the specified <code>Player</code>.
	 */
	private RotatePlayerUpAction(Player fish){
		this.fish = fish;
		if(fish != null){
			this.obj = fish.getNode();
		}
		else{
			this.obj = null;
		}
	}//end of (Player) constructor

	//---------------------instance methods----------------------------
	//SETTERS
	/**
	 * 
	 * @param fish
	 */
	public void setFish(Player fish){
		this.fish = fish;
		if(fish != null){
			this.obj = fish.getNode();
		}
		else{
			this.obj = null;
		}
	}//end of setFish method
	
	//OPERATIONS
	/**
	 * 
	 */
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
	/**
	 * 
	 * @param fish
	 * @return
	 */
	public static RotatePlayerUpAction getInstance(Player fish){
		if(instance == null || !fish.equals(instance.fish)){
			instance = new RotatePlayerUpAction(fish);
		}
		return instance;
	}//end of getInstance(Player) method
	
	/**
	 * 
	 * @return
	 */
	public static RotatePlayerUpAction getInstance(){
		if(instance == null){
			instance = new RotatePlayerUpAction(null);
		}
		return instance;
	}//end of getInstance method
	
}//end of RotateUp class