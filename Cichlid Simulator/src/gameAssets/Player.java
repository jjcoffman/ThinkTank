package gameAssets;

public class Player extends Cichlid
{
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
