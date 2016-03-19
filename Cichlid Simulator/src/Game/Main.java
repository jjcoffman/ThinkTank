package Game;
import java.security.SecureRandom;
import java.util.ArrayList;

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
import com.jme3.asset.AssetManager;
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
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Plant;
import thinktank.simulator.entity.Pot;
import thinktank.simulator.entity.collection.Iterator;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.Scenario;


public class Main extends SimpleApplication {
	public static final Vector3f WORLD_UP_AXIS = new Vector3f(0, 1, 0);
	public static final SecureRandom RNG = new SecureRandom();
	
	public static AssetManager am;
	private static Player player;
	private static Spatial table;
	private static Tank tank;
	private static SimulatorCollection simCollection;
	private static Node environ_node;
	private static Environment environment;
	
	private Pot pot;
	private Plant plant;
	
	private ArrayList<Scenario> scenarios;
	private int activeScenarioIndex;
	private Scenario workingScenario;
	
	public Main(){
		scenarios = new ArrayList<Scenario>();
		activeScenarioIndex = -1;
	}//end of default constructor
	
	@Override
	public void simpleInitApp() {
		am = this.assetManager;
		simCollection = new SimulatorCollection();
		//TODO load saved scenarios
		workingScenario = new Scenario();
		
		//Add nodes to root
		rootNode.attachChild(workingScenario.getEnvironment().getEnvirionmentNode());
		rootNode.attachChild(workingScenario.getEnvironment().getTank().getNode());
		rootNode.attachChild(workingScenario.getEntityNode());
		
//		makeEnvironment(TANK_TYPE.FIFTEEN_GAL_TALL);
//		makePlayer();
//		makePot();
//		makePlant();

		//world elements
		makeSun();
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
		
		//set initial camera position
		this.cam.setLocation(new Vector3f(-30,15,0));//temp: for easier testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
		//set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.05f, 100f);
		flyCam.setMoveSpeed(10);
	}//end of simpleInitApp method
	

	private void makePlayer(){
		player = Player.getPlayer();
		SpinControlTEST cont = new SpinControlTEST();
		player.addControl(cont);
		rootNode.attachChild(player.getObj());
		player.getObj().setLocalTranslation(0, 6f, 0);
	}

	private void makeEnvironment(TANK_TYPE type){
		makeTable();
		environment = new Environment();
		tank = Tank.createTank(type);
		environment.setTank(tank);
		//move on top of table
		tank.getNode().setLocalTranslation(0, 4.675f, 0);
		environ_node = new Node();
		environ_node.attachChild(table);
		environ_node.attachChild(tank.getNode());
		rootNode.attachChild(environ_node);
	}
	private void makeTable() {
		table = assetManager.loadModel("Table.obj");
		table.scale(1.5f);
	}
	private void makePot() {
		pot = EntityFactory.createPot();
		simCollection.add(pot);
		rootNode.attachChild(pot.getObj());
	}
	private void makePlant() {
		plant = EntityFactory.createPlant();
		simCollection.add(plant);
		rootNode.attachChild(plant.getObj());
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

	@Override
	public void simpleUpdate(float tpf){
		//tpf = time per frame
		
		super.simpleUpdate(tpf);
	}//end of simpleUpdate method

}//end of Main class
