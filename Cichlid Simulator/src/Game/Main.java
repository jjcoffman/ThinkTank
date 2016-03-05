package Game;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import gameAssets.*;
import thinktank.simulator.environment.Tank;


public class Main extends SimpleApplication {
	private static Player player;
	private static Spatial fish;
	private static Tank tank;
	
	public Main(){
		
	}//end of default constructor
	
	@Override
	public void simpleInitApp() {
		//create player
		player = Player.getPlayer();
		//load model of cichlid
		makeSun();
		makeCichlid(fish);
		
		//printouts to test that player exists and has attributes
		System.out.println(player.getSize());
		System.out.println(player.getSpeed());
		System.out.println(player.getSex());
		//initialize terrain, objects, other cichlids camera, and display.
		makeTank(tank);
	}
	
	private void makeTank(Tank tanks) {
		tank = Tank.getTank();
		tank.setSpatial(assetManager.loadModel("Tank.obj"));
		tank.scale(3);
		rootNode.attachChild(tank.getSpatial());
		
	}

	private void makeSun() {
		DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-2f,-2f,-2f).normalizeLocal());
        rootNode.addLight(sun);
        
         
	}

	private void makeCichlid(Spatial fish) {
		fish = assetManager.loadModel("Cichlid_v5.obj");
		fish.scale(.25f, .25f, .25f);
		fish.rotate(0, 45f, 0);
		rootNode.attachChild(fish);
	}

	public void simpleUpdate(float tpf){
		//tpf = time per frame
		super.simpleUpdate(tpf);
	}

}
