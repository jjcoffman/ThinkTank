package Game;
/*****************************************************************************************
 * Class: Main
 * Purpose: creates the simulation world and 
 * Author: Jonathan Coffman via Think Tank
 * Revisions:
 * Jonathan Coffman   -    added collection     -    3/10/16
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
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import gameAssets.*;
import thinktank.simulator.entity.Pot;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Tank;


public class Main extends SimpleApplication {
	private static Player player;
	private Pot pot;
	private static Spatial fish, table;
	private static Tank tank;
	private static SimulatorCollection simCollection;
	
	public Main(){
		
	}//end of default constructor
	
	@Override
	public void simpleInitApp() {
		simCollection = new SimulatorCollection();
		//create player
		makePlayer();
		//TODO tank and table should be in a separate node together since
		//they are constants
		makeTank(tank);
		makeSun();
		flyCam.setMoveSpeed(10);
		
		//printouts to test that player exists and has attributes
		System.out.println(player.getSize());
		System.out.println(player.getSpeed());
		System.out.println(player.getSex());
		
		//initialize terrain, objects, other cichlids camera, and display.
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));

		//rootNode.attachChild(SkyFactory.createSky(
	    //        assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
		
		
		
		//garbage code, just testing scene
		makeTable(table);
		makePot();
		makeMap();
		//end garbage code
	}
	

	private void makePlayer() {
		player = Player.getPlayer(assetManager);
		rootNode.attachChild(player.getObj());
	}

	private void makeTank(Tank tanks) {
		tank = Tank.getTank(this.assetManager);
		tank.getSpatial().move(0, 16, 0);
		tank.getSpatial().setQueueBucket(Bucket.Transparent);
		rootNode.attachChild(tank.getSpatial());
	}
	private void makeTable(Spatial table) {
		//code is messy, just testing scene
		
		table = assetManager.loadModel("Table.obj");
		rootNode.attachChild(table);
		table.scale(5);
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
		TerrainQuad terrain = new TerrainQuad("tankBase", 65, 1025, heightmap.getHeightMap());
		terrain.setMaterial(terrainMat);
		terrain.rotate(0, 3.14159f, 0);
		terrain.setLocalScale(.01075f, 0.0025f, 0.033f);
		terrain.setLocalTranslation(-.25f, 15.95f, 0);
		rootNode.attachChild(terrain);
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
