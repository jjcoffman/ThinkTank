package gameAssests;

public class Player extends Cichlid
{
	static private Player player;  //singleton
	
	private Player()
	{
		super();
	}
	
	static public Player getPlayer()
	{
		if(player == null)
			player = new Player();
		return player;
	}
}
