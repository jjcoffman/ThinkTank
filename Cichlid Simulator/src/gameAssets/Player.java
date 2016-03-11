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
	static private Player player;  //singleton
	
	private Player(float size, float speed, String sex, AssetManager am)
	{
		super(size, speed, sex);
		super.makeObj(am);
	}
	
	static public Player getPlayer(AssetManager am)
	{
		if(player == null)
			player = new Player(1, 1, "male", am);
			
		return player;
	}
}