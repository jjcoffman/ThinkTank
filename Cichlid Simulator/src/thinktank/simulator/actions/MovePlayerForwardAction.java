package thinktank.simulator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jme3.scene.Node;

import thinktank.simulator.Starter;
import thinktank.simulator.entity.Player;
import thinktank.simulator.main.Main;

/**
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class MovePlayerForwardAction extends AbstractAction{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 1177802847050601841L;
	/**
	 * 
	 */
	public static final String NAME = "move-forward";

	//---------------------static variables----------------------------
	/**
	 * Singleton instance for the action.
	 */
	private static MovePlayerForwardAction instance = null;

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
	 * Constructs a basic, default <code>MoveForward</code> action 
	 * with the specified <code>Player</code>.
	 */
	public MovePlayerForwardAction(Player fish){
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
	/**
	 * 
	 * @param fish
	 * @return
	 */
	public static MovePlayerForwardAction getInstance(Player fish){
		if(instance == null || !fish.equals(instance.fish)){
			instance = new MovePlayerForwardAction(fish);
		}
		return instance;
	}//end of getInstance(Player) method
	
	/**
	 * 
	 * @return
	 */
	public static MovePlayerForwardAction getInstance(){
		if(instance == null){
			instance = new MovePlayerForwardAction(null);
		}
		return instance;
	}//end of getInstance method

}//end of MoveForward class