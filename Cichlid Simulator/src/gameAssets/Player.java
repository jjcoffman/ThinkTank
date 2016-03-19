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

public class Player extends Cichlid
{
	private static final long serialVersionUID = 4038460719382327559L;
	static private Player player;  //singleton
	
	private Player(float size, float speed, String sex)
	{
		super(size, speed, sex);
	}
	
	static public Player getPlayer()
	{
		if(player == null)
			player = new Player(1, 1, "male");
			
		return player;
	}
}