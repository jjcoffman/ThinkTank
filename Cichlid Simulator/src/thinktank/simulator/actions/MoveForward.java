package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.math.Matrix3f;
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
public class MoveForward extends AbstractAction{
	//---------------------static constants----------------------------
	public static final String NAME = "move-forward";
	private static final long serialVersionUID = 1177802847050601841L;

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MoveForward instance = null;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Player fish;
	private Node obj;

	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default <code>MoveForward</code> action 
	 * with the specified <code>Player</code>.
	 */
	public MoveForward(Player fish){
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
		/*
		Vector3f newLoc = new Vector3f();
		Vector3f curLoc = new Vector3f(obj.getLocalTranslation());
		curLoc.addLocal(obj.getLocalRotation().getRotationColumn(0).mult(-fish.getSpeed()/250));
		newLoc = curLoc;
		obj.setLocalTranslation(newLoc);
		*/
		}
	}//end of actionPerformed method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static MoveForward getInstance(Player fish){
		if (instance != null){
			return instance;
		}
		else {
			instance = new MoveForward(fish);
			return instance;
		}
	}//end of getInstance(Player) method
	
	public static MoveForward getInstance(){
		if (instance == null){
			System.out.println("Pass fish");
			return null;
		}
		else return instance;
	}//end of getInstance method

}//end of MoveForward class