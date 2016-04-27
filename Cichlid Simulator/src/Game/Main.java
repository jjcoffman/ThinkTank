package Game;
import java.awt.event.ActionEvent;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.AbstractAction;

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
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Line;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.sun.xml.internal.stream.Entity;

import de.lessvoid.nifty.Nifty;
import gameAssets.*;
import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.LoadScenarioAction;
import thinktank.simulator.actions.MoveBackward;
import thinktank.simulator.actions.MoveForward;
import thinktank.simulator.actions.RotateDown;
import thinktank.simulator.actions.RotateLeft;
import thinktank.simulator.actions.RotateRight;
import thinktank.simulator.actions.RotateUp;
import thinktank.simulator.actions.SaveScenarioAction;
import thinktank.simulator.actions.ToggleCamModeAction;
import thinktank.simulator.actions.ToggleMouselookAction;
import thinktank.simulator.actions.SpinControlTEST;
import thinktank.simulator.controllers.CichlidController;
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
	public enum CAM_MODE{FLY,FOLLOW};
	public static AssetManager am;
	private static SimulatorCollection simCollection;
	private static Node environ_node; ArrayList<Scenario> scenarios;
	private int activeScenarioIndex;
	private Scenario workingScenario;
	private Player player;
//	private Node player;
	private CameraNode camNode;
	private boolean mouselookActive;
	private CAM_MODE activeCam;
	
	public Main(){
		scenarios = new ArrayList<Scenario>();
		activeScenarioIndex = -1;
		mouselookActive = true;
	}//end of default constructor
	
	public void addScenario(Scenario scenario){
		if(scenario != null){
			scenarios.add(scenario);
			//TODO temp: for testing only.
			clearScenario();
			activeScenarioIndex = scenarios.indexOf(scenario);
			workingScenario = scenario;
			displayScenario();
		}
	}//end of addScenario method
	
	public void attachToRootNode(Spatial obj){
		if(obj != null){
			rootNode.attachChild(obj);
		}
	}//end of attachToRootNode method
	
	public void removeFromRootNode(Spatial obj){
		if(obj != null){
			rootNode.detachChild(obj);
		}
	}//end of removeFromRootNode method
	
	public Scenario getWorkingScenario(){
		return workingScenario;
	}//end of getWorkingScenario method
	
	@Override
	public void simpleInitApp(){
		//turn off stats display
		hideStatsInfo();
		
		am = this.assetManager;
		simCollection = new SimulatorCollection();
		//TODO load saved scenarios
		workingScenario = new Scenario();
		
		//DEBUG
		showAxes();
		//END DEBUG
		
		//Add nodes to root
		displayScenario();
		
		//world elements
		makeSun();
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));

		//set initial cameras & positions
		activeCam = CAM_MODE.FLY;
		ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);//set toggle action to switch to follow on first invocation
		this.cam.setLocation(new Vector3f(-2, 0.1f, 0));//temp: for easier testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
		//set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100f);
		flyCam.setMoveSpeed(1.5f);
		player = Player.getPlayer();
		setCam();
		rootNode.attachChild(player.getCam());

		flyCam.setEnabled(true);
		activeCam = CAM_MODE.FLY;
		inputManager.setCursorVisible(true);
		
		//setup inputs
		initInputs();
		

		//set up interface
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		Nifty nifty = niftyDisplay.getNifty();
		nifty.fromXml("Interface/screen.xml", "start");
		guiViewPort.addProcessor(niftyDisplay);
		toggleMouseMode();
	}//end of simpleInitApp method
	
	private void setCam() {
		camNode = new CameraNode("camNode", cam);
		player.attachCam(camNode);
	}

	private void initInputs(){
		InputListener.getInstance();
		CichlidController.getInstance(player);
	    inputManager.addMapping(AddPotAction.NAME, new KeyTrigger(KeyInput.KEY_P));
	    inputManager.addMapping(AddPlantAction.NAME, new KeyTrigger(KeyInput.KEY_L));
	    inputManager.addMapping(AddFishAction.NAME, new KeyTrigger(KeyInput.KEY_K));
	    inputManager.addMapping(SaveScenarioAction.NAME, new KeyTrigger(KeyInput.KEY_M));
	    inputManager.addMapping(LoadScenarioAction.NAME, new KeyTrigger(KeyInput.KEY_N));
	    
		inputManager.addMapping(MoveForward.NAME, new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping(MoveBackward.NAME, new KeyTrigger(KeyInput.KEY_S));
		
		inputManager.addMapping(ToggleCamModeAction.NAME, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(ToggleMouselookAction.NAME, new KeyTrigger(KeyInput.KEY_APOSTROPHE));
		
		inputManager.addMapping(RotateLeft.NAME, new MouseAxisTrigger(MouseInput.AXIS_X, true));
		inputManager.addMapping(RotateRight.NAME, new MouseAxisTrigger(MouseInput.AXIS_X, false));
		inputManager.addMapping(RotateUp.NAME, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
		inputManager.addMapping(RotateDown.NAME, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
		
		// Add the names to the action listener.
	    inputManager.addListener(InputListener.getInstance(), AddPotAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddPlantAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddFishAction.NAME);
		inputManager.addListener(InputListener.getInstance(), SaveScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), LoadScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleCamModeAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleMouselookAction.NAME);
		
		
		inputManager.addListener(CichlidController.getInstance(), MoveForward.NAME);
		inputManager.addListener(CichlidController.getInstance(), MoveBackward.NAME);
		
		inputManager.addListener(CichlidController.getInstance(), RotateLeft.NAME);
		inputManager.addListener(CichlidController.getInstance(), RotateRight.NAME);
		inputManager.addListener(CichlidController.getInstance(), RotateUp.NAME);
		inputManager.addListener(CichlidController.getInstance(), RotateDown.NAME);
		
	}//end of initInputs method
	
	private InputManager getIM()
	{
		return this.inputManager;
	}

	private void clearScenario(){
		if(workingScenario != null){
			System.out.println("Scene: "+workingScenario.getName());
			rootNode.detachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			rootNode.detachChild(workingScenario.getEnvironment().getTank().getNode());
			rootNode.detachChild(workingScenario.getEntityNode());
			workingScenario = null;
		}
	}//ends of clearScenario method
	
	private void displayScenario(){
		if(workingScenario != null){	
			System.out.println("Scene: "+workingScenario.getName());	
			System.out.println("Tank node: "+workingScenario.getEntityNode().getNumControls());
			rootNode.attachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			rootNode.attachChild(workingScenario.getEnvironment().getTank().getNode());
			rootNode.attachChild(workingScenario.getEntityNode());
		}
	}//end of displayScenario method
	

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

	/**
	 * Shows the X, Y, and Z axes for debug purposes.
	 */
	private void showAxes(){
		Line x = new Line(new Vector3f(0, 0, 0), new Vector3f(100, 0, 0));
		Line y = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 100, 0));
		Line z = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 0, 100));
		x.setLineWidth(1);
		y.setLineWidth(1);
		z.setLineWidth(1);
		Geometry geometryX = new Geometry("x", x);
		Geometry geometryY = new Geometry("y", y);
		Geometry geometryZ = new Geometry("z", z);
		Material green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Material red = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Material blue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		green.setColor("Color", ColorRGBA.Green);
		red.setColor("Color", ColorRGBA.Red);
		blue.setColor("Color", ColorRGBA.Blue);
		geometryX.setMaterial(green);            
		geometryY.setMaterial(red);            
		geometryZ.setMaterial(blue);                  
		rootNode.attachChild(geometryX);
		rootNode.attachChild(geometryY);
		rootNode.attachChild(geometryZ);
		//DEBUG 2
//		Line z1 = new Line(new Vector3f(0, 0, 1), new Vector3f(0, 100, 1));
//		Line z5 = new Line(new Vector3f(0, 0, 5), new Vector3f(0, 100, 5));
//		Line z10 = new Line(new Vector3f(0, 0, 10), new Vector3f(0, 100, 10));
//		z1.setLineWidth(1);
//		z5.setLineWidth(1);
//		z10.setLineWidth(1);
//		Geometry geometryZ1 = new Geometry("z1", z1);
//		Geometry geometryZ5 = new Geometry("z5", z5);
//		Geometry geometryZ10 = new Geometry("z10", z10);
//		geometryZ1.setMaterial(red);            
//		geometryZ5.setMaterial(red);            
//		geometryZ10.setMaterial(red);                  
//		rootNode.attachChild(geometryZ1);
//		rootNode.attachChild(geometryZ5);
//		rootNode.attachChild(geometryZ10);
		//END DEBUG 2
	}//end of showAxes method
	
	public void hideStatsInfo(){
		setDisplayFps(false);
		setDisplayStatView(false);
	}//end of hideStatsInfo method
	
	public void toggleMouseMode(){
		System.out.println(mouselookActive+", "+activeCam);
		if(mouselookActive){
			inputManager.setCursorVisible(true);
			if(activeCam == CAM_MODE.FLY){
				flyCam.setEnabled(false);
			}
			else if(activeCam == CAM_MODE.FOLLOW){
				player.getCam().setEnabled(false);
			}
			mouselookActive = false;
		}
		else{
			inputManager.setCursorVisible(false);
			if(activeCam == CAM_MODE.FLY){
				flyCam.setEnabled(true);
			}
			else if(activeCam == CAM_MODE.FOLLOW){
				player.getCam().setEnabled(true);
			}
			mouselookActive = true;
		}
	}//end of toggleMouseMode method
	
	public void setCamMode(CAM_MODE mode){
		activeCam = mode;
		switch(mode){
		case FLY:
			player.getCam().setEnabled(false);
			flyCam.setEnabled(true);
			inputManager.setCursorVisible(false);
			this.cam.setLocation(new Vector3f(-2, 0.1f, 0));//TODO save previous fly cam position and reset to that
			this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
			ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FOLLOW);
			break;
		case FOLLOW:
			if(player != null){
				flyCam.setEnabled(false);
				player.getCam().setEnabled(true);
				inputManager.setCursorVisible(false);
				ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
			}
			break;
		}
	}//end of setCamMode method
	
	/**
	 * @deprecated
	 * @param mode
	 * @return
	 */
	public Object getCam(CAM_MODE mode){
		Object returnValue = null;
		switch(mode){
		case FLY:
			returnValue = flyCam;
			break;
		case FOLLOW:
			break;
		}
		return returnValue;
	}//end of getCam method
	
	@Override
	public void simpleUpdate(float tpf){
		//tpf = time per frame
		
		moveFish();
		player.update();
		super.simpleUpdate(tpf);
	}//end of simpleUpdate method
	
	private void moveFish(){
		java.util.Iterator<Fish> itr = workingScenario.getFish();
		while (itr.hasNext()){
			Fish f = (Fish) itr.next();
			f.move();
		}
	}

}//end of Main class
