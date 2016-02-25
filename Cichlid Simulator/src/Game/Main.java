package Game;

import com.jme3.app.SimpleApplication;
import gameAssets.*;


public class Main extends SimpleApplication {
	private static Player player;
	
	@Override
	public void simpleInitApp() {
		player = Player.getPlayer();
		// TODO Auto-generated method stub
		System.out.println(player.getSize());
		System.out.println(player.getSpeed());
		System.out.println(player.getSex());
	}

	public static void main(String[] args) {
		Main app = new Main();
		app.start();
	}

}
