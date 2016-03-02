package Game;

import com.jme3.app.SimpleApplication;
import gameAssets.*;


public class Main extends SimpleApplication {
	private static Player player;
	
	public Main(){
		
	}//end of default constructor
	
	@Override
	public void simpleInitApp() {
		player = Player.getPlayer();
		System.out.println(player.getSize());
		System.out.println(player.getSpeed());
		System.out.println(player.getSex());
	}

}
