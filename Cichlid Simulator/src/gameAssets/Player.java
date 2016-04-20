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

public class Player extends Cichlid
{
	private static final long serialVersionUID = 4038460719382327559L;
	static private Player player;  //singleton
	private static Node node = null;
	private BetterCharacterControl cc;
	
	private Player(float size, float speed, String sex)
	{
		super(size, speed, sex);
		cc = new BetterCharacterControl(1,1,1);
		//node.addControl(cc);
		node.attachChild(this.getObj());
	}
	
	static public Player getPlayer()
	{
		if(player == null){
			node = new Node();
			player = new Player(1, 1, "male");
		}
			
		return player;
	}
	
	public void attachCam(CameraNode cam){
		node.attachChild(cam);
		cam.setLocalTranslation(.2f, .1f, 0);
		cam.lookAt(player.getObj().getWorldTranslation(), new Vector3f(0,1,0));
	}
	public Node getNode(){
		if (node == null){
			System.out.println("There is no player fish");
			return null;
		}
		else return node;
	}
	
}