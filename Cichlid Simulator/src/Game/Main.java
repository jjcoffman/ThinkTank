package Game;
/*****************************************************************************************
 * Class: Main
 * Purpose: Inititates the game entities and environment, also contains the update method
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.sun.xml.internal.stream.Entity;

import gameAssets.*;
import thinktank.simulator.actions.SpinControlTEST;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Pot;
import thinktank.simulator.entity.collection.Iterator;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Tank;


public class Main extends SimpleApplication {
	private static Player player;
	private Pot pot;
	private static Spatial fish, table;
	private static Tank tank;
	private static SimulatorCollection simCollection;
	private static Node environment;
	private static TerrainQuad terrain;
	
	public Main(){
	}//end of default constructor
	
	@Override
	public void simpleInitApp() {
		limitFrames(60);
		simCollection = new SimulatorCollection();
		//create player
		makePlayer();
		makeEnvironment();
		//TODO tank and table should be in a separate node together since
		//they are constants
		makeSun();
		flyCam.setMoveSpeed(10);
		
		//printouts to test that player exists and has attributes
		System.out.println(player.getSize());
		System.out.println(player.getSpeed());
		System.out.println(player.getSex());
		
		//initialize terrain, objects, other cichlids camera, and display.
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
			
		//garbage code, just testing scene
		makePot();
		makeMap();
		//end garbage code
	}
	

	public void limitFrames(int frames) {
		AppSettings newSetting = new AppSettings(true);
		newSetting.setFrameRate(frames);
		setSettings(newSetting);
		restart();
	}

	private void makePlayer() {
		player = Player.getPlayer(assetManager);
		SpinControlTEST cont = new SpinControlTEST();
		player.addControl(cont);
		rootNode.attachChild(player.getObj());
	}

	private void makeEnvironment(){
		makeTable();
		makeTank();
		makeMap();
		tank.scale(.25f);
		tank.getSpatial().move(0, 3.155f, 0);

		terrain.rotate(0, 3.14159f, 0);
		terrain.setLocalScale(.00275f, 0.001f, 0.00825f);
		terrain.setLocalTranslation(0, 3.15f, 0);
		
		environment = new Node();
		environment.attachChild(table);
		environment.attachChild(tank.getSpatial());
		environment.attachChild(terrain);
		rootNode.attachChild(environment);
	}
	private void makeTank() {
		tank = Tank.getTank(this.assetManager);
		//tank.getSpatial().move(0, 16, 0);
		tank.getSpatial().setQueueBucket(Bucket.Transparent);
	}
	private void makeTable() {
		//code is messy, just testing scene
		
		table = assetManager.loadModel("Table.obj");
		//table.scale(5);
	}
	private void makePot() {
		pot = new Pot(assetManager);
		simCollection.add(pot);
		rootNode.attachChild(pot.getObj());
	}
	
	private void makeMap(){
		Material terrainMat = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
		terrainMat.setTexture("Alpha", assetManager.loadTexture("Sand.jpg"));
		AbstractHeightMap heightmap = null;
		Texture heightmapImage = assetManager.loadTexture("terrain.bmp");
		heightmap = new ImageBasedHeightMap(heightmapImage.getImage());
		heightmap.load();
		terrain = new TerrainQuad("tankBase", 65, 1025, heightmap.getHeightMap());
		terrain.setMaterial(terrainMat);
	}
	private void makeSun() {
		DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-2f,-2f,-2f).normalizeLocal());
        sun.setColor(new ColorRGBA(2,2,2,0));
        rootNode.addLight(sun);
        
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection(new Vector3f(2f,2f,2f).normalizeLocal());
        sun2.setColor(new ColorRGBA(2,2,2,0));
        rootNode.addLight(sun2);
	}

	private void makeCichlid(Spatial fish) {
		fish = assetManager.loadModel("Cichlid_v5.obj");
		fish.scale(.1f);
		fish.rotate(0, 45f, 0);
		fish.move(0, 18, 0);
		rootNode.attachChild(fish);
		simCollection.add(fish);
	}

	public void simpleUpdate(float tpf){
		//tpf = time per frame
		
		super.simpleUpdate(tpf);
	}

}
