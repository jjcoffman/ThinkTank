package gameAssets;
/*****************************************************************************************
 * Class: Player
 * Purpose: Create a player instance of a Cichlid
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;

import Game.Main;

public class Player extends Cichlid
{
	private static final long serialVersionUID = 4038460719382327559L;
	static private Player player;  //singleton
	private static Node node = null;
	private CameraNode cam;
	private BetterCharacterControl cc;

    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    private Vector3f walkDirection = new Vector3f(0,0,0);
    private Vector3f viewDirection = new Vector3f(0,0,0);
	
	private Player(float size, float speed, String sex)
	{
		super(size, speed, sex);
		node.attachChild(super.getNode());
		//rotate object 180 degrees to correct orientation
	}
	
	static public Player getPlayer()
	{
		if(player == null){
			node = new Node();
			player = new Player(1, 1, "male");
		}
			
		return player;
	}
	
	/*
	 * attach camera from main to player to be used by cichlid controller
	 * offset the spatial in the z direction
	 * attaching the player node to camNode creates the broken rotation
	 */
	
	public void attachCam(CameraNode camera){
		
	}
	
	public Node getNode(){
		if (node == null){
			System.out.println("There is no player fish");
			return null;
		}
		else return node;
	}
	
	public void update(){
		
	}
	public CameraNode getCam(){
		return cam;
	}
}