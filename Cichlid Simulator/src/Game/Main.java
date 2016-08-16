package Game;
import java.awt.event.ActionEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsSweepTestResult;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
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
import com.jme3.math.Transform;
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
import thinktank.simulator.entity.EntityFactory;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.Plant;
import thinktank.simulator.entity.Pot;
import thinktank.simulator.entity.collection.Iterator;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.Grid;
import thinktank.simulator.scenario.Scenario;


public class Main extends SimpleApplication implements ActionListener{
	public static final Vector3f WORLD_UP_AXIS = new Vector3f(0, 1, 0);
	public static final SecureRandom RNG = new SecureRandom();
	public enum CAM_MODE{FLY,FOLLOW};
	public static AssetManager am;
	private static SimulatorCollection simCollection;
	private static Node environ_node; ArrayList<Scenario> scenarios;
	private int activeScenarioIndex;
	private static Scenario workingScenario;
	private Player player;
	private static Grid grid;
	private Nifty nifty;
	private BulletAppState bulletAppState;
	private CameraNode fishCam;
	private boolean mouselookActive;
	private CAM_MODE activeCam;

    private boolean left = false, right = false, up = false, down = false,
    		ascend = false, descend = false;
    private boolean forward = false, backward = false;
    private float deg = 0;
    private float pitch = 0;
    private long timer = 0;
    private long defTime = System.nanoTime();
	private GhostControl test = null;
	
	private boolean upLock = false, downLock = false, leftLock = false, rightLock = false,
			forwardLock = false, backLock = false;
    
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
	
	public static Grid getGrid(){
		return grid;
	}
	
	@Override
	public void simpleInitApp(){
		//setup physics
		setPhys();

		//turn off stats display
		hideStatsInfo();

		am = this.assetManager;
		simCollection = new SimulatorCollection();
		//TODO load saved scenarios
		workingScenario = new Scenario();
		grid = new Grid(getWorkingScenario());
		
		//DEBUG
		//showAxes();
		//END DEBUG
		displayScenario();
		
		//world elements
		makeSun();
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));

		//set initial cameras & positions
		setCam();
		
		//make player and set camera to player
		fishCam = new CameraNode("Player camera", cam);
		player = Player.getPlayer();
		player.getNode().setLocalTranslation(0, 0.25f, 0);
		player.setCam(fishCam);
		
		rootNode.attachChild(player.getNode());
		rootNode.attachChild(player.getCam());
		
		//setup inputs
		initInputs();

		//set up interface
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		nifty.fromXml("Interface/screen.xml", "start");
		guiViewPort.addProcessor(niftyDisplay);
		toggleMouseMode();
		setCamMode(CAM_MODE.FLY);
	}//end of simpleInitApp method
	
	/**
	 * Setup all physics base configurations 
	 */
	private void setPhys() {
		bulletAppState = new BulletAppState();
	    stateManager.attach(bulletAppState);
	    //turn off gravity, sort of. 
	    bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0,-.00001f,0));
	    bulletAppState.setDebugEnabled(true);//DEBUG, obviously...
	}

	private void setCam() {
		ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);//set toggle action to switch to follow on first invocation
		this.cam.setLocation(new Vector3f(-2, 0.1f, 0));//temp: for easier testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
		//set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100f);
		flyCam.setMoveSpeed(1.5f);
		flyCam.setEnabled(true);
		activeCam = CAM_MODE.FLY;
		inputManager.setCursorVisible(true);
	}

	private void initInputs(){
		//initiate listeners
		InputListener.getInstance();
		
	    inputManager.addMapping(AddPotAction.NAME, new KeyTrigger(KeyInput.KEY_P));
	    inputManager.addMapping(AddPlantAction.NAME, new KeyTrigger(KeyInput.KEY_L));
	    inputManager.addMapping(AddFishAction.NAME, new KeyTrigger(KeyInput.KEY_K));
	    inputManager.addMapping(SaveScenarioAction.NAME, new KeyTrigger(KeyInput.KEY_M));
	    inputManager.addMapping(LoadScenarioAction.NAME, new KeyTrigger(KeyInput.KEY_N));

		inputManager.addMapping(ToggleCamModeAction.NAME, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(ToggleMouselookAction.NAME, new KeyTrigger(KeyInput.KEY_APOSTROPHE));
		
		// Add the names to the action listener.
	    inputManager.addListener(InputListener.getInstance(), AddPotAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddPlantAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddFishAction.NAME);
		inputManager.addListener(InputListener.getInstance(), SaveScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), LoadScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleCamModeAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleMouselookAction.NAME);
		
	    setupFishInput();
		
	}//end of initInputs method
	
	/**
	 * Setup all inputs regarding the player fish
	 */
	public void setupFishInput() {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
      	inputManager.addMapping("Right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("Up", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
      	inputManager.addMapping("Down", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping("Ascend", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Descend", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("Sprint", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(this, "Forward");
        inputManager.addListener(this, "Backward");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Ascend");
        inputManager.addListener(this, "Descend");
        inputManager.addListener(this, "Sprint");
	}
	/**
	 * Used to remove inputs when switching to FLY cam
	 */
	public void removeFishInput(){
		inputManager.removeListener(this);
	}
	/**
	 * Used to restore inputs when switching to FOLOW cam
	 */
	public void repairFishInput(){
		inputManager.addListener(this, "Forward");
        inputManager.addListener(this, "Backward");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Ascend");
        inputManager.addListener(this, "Descend");
        inputManager.addListener(this, "Sprint");
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
        sun.setDirection(new Vector3f(1f, -1f, 1f).normalizeLocal());
        sun.setColor(new ColorRGBA(2,2,2,0));
        rootNode.addLight(sun);
        
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection(new Vector3f(2f,2f,2f).normalizeLocal());
        sun2.setColor(new ColorRGBA(2,2,2,0));
        //rootNode.addLight(sun2);
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
			nifty.setIgnoreKeyboardEvents(false);
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
			nifty.setIgnoreKeyboardEvents(true);
			mouselookActive = true;
		}
	}//end of toggleMouseMode method
	
	/**
	 * Changes camera mode to FLY or FOLLOW
	 * @param mode
	 */
	public void setCamMode(CAM_MODE mode){
		activeCam = mode;
		switch(mode){
		case FLY:
			player.getCam().setEnabled(false);
			removeFishInput();
			flyCam.setEnabled(true);
			inputManager.setCursorVisible(false);
			this.cam.setLocation(new Vector3f(-2, 0.1f, 0));
			//TODO save previous fly cam position and reset to that
			this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
			ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
			break;
		case FOLLOW:
			if(player != null){
				flyCam.setEnabled(false);
				player.getCam().setEnabled(true);
				repairFishInput();
				inputManager.setCursorVisible(false);
				ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FOLLOW);
			}
			break;
		}
	}//end of setCamMode method
	
	@Override
	public void simpleUpdate(float tpf){
		//tpf stands for time per frame
		long oldTime = timer;
		timer = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-defTime);

		moveFish(tpf);
		rotateObj(tpf);
		
		limitFishMovement();
		moveObj(tpf);
		
		if (oldTime != timer){
			System.out.println("Time Elapsed: " + timer);
		}
		
		left = false;
        right = false;
        up = false;
        down = false;
		upLock = false;
		downLock = false;
		leftLock = false;
		rightLock = false;
		forwardLock = false;
		backLock = false;
		
		super.simpleUpdate(tpf);
	}//end of simpleUpdate method
	
	/**
	 * Test to see if player fish will move past tank boundaries
	 */
	private void limitFishMovement() {
		float x = player.getObj().getWorldTranslation().getX();
		float y = player.getObj().getWorldTranslation().getY();
		float z = player.getObj().getWorldTranslation().getZ();
		Tank tank = workingScenario.getEnvironment().getTank();

		if (y >= tank.getY()){
			upLock = true;
		}
		else if (y <= 0.02f){
			downLock = true;
		}
		if (x >= tank.getX()){
			rightLock = true;
		}
		else if (x <= -tank.getX()){
			leftLock = true;
		}
		if (z >= tank.getZ()){
			backLock = true;
		}
		else if (z <= -tank.getZ()){
			forwardLock = true;
		}
	}

	private void moveObj(float tpf) {
		Vector3f old = player.getObj().getWorldTranslation();
		Vector3f movement = new Vector3f();
		
        if (forward) {
        	//printout for fish location
    		//System.out.println(player.getObj().getWorldTranslation().getY());
        	
        	if(player.isSprinting()){
        		movement = new Vector3f(0,0,tpf*.5f);
        	}
        	else movement = new Vector3f(0,0,tpf*.25f);
        }
        else if (backward) {
    		movement = new Vector3f(0,0,-tpf*.1f);
        }
        
		Vector3f move = player.getNode().localToWorld(movement,movement);
		
		if (upLock) { move.setY(old.y - 0.00015f); }
		if (downLock) { move.setY(old.y + 0.00015f); }
		if (leftLock) { move.setX(old.x + 0.00015f); }
		if (rightLock) { move.setX(old.x - 0.00015f); }
		if (forwardLock) { move.setZ(old.z + 0.00015f); }
		if (backLock) { move.setZ(old.z - 0.00015f); }
		
		player.getNode().setLocalTranslation(move);
        player.getPhysicsControl().setPhysicsLocation(player.getObj().getWorldTranslation());
	}
	
	/**
	 * Used to get player's next location to test before moving
	 * @param tpf
	 * @return player's next location
	 */
	private Vector3f getNextLoc(float tpf) {
		//TODO change to call method in Cichlid class for each cichlid to check it's own collision
		Vector3f movement = new Vector3f(0,0,tpf);
		if (forward) {
        	//printout for fish location
    		//System.out.println(player.getObj().getWorldTranslation().getY());
        	
        	if(player.isSprinting()){
        		movement = new Vector3f(0,0,tpf*.5f);
        	}
        	else movement = new Vector3f(0,0,tpf*.25f);
        }
        else if (backward) {
    		movement = new Vector3f(0,0,-tpf*.1f);
        }
		Vector3f move = player.getNode().localToWorld(movement,movement);
        return move;
	}
	
	/**
	 * Used to test collisions with player's ghost
	 * @param loc, location of player
	 * @return boolean 
	 */
	private boolean testCollision(Vector3f loc) {
		//TODO change to call method in Cichlid class for each cichlid to check it's own collision
		boolean col;
		test = player.getGhost();
		test.setPhysicsLocation(loc);
		if (test.getOverlappingCount() > 1){
			System.out.println("COLLISION");
			col = true;
		}
		else {
			col = false;
		}		
		return col;
	}
	
	
	/**
	 * Rotates player, uses tpf to calculate rotation
	 * @param tpf
	 */
	private void rotateObj(float tpf) {
		//TODO Need to add variance into turning, test if for value of left/right/up/down
		if (left) {
            deg -= 250f * tpf;
        }
        if (right) {
            deg += 250f * tpf;
        }
        if (up){
        	if (pitch < 45f){
        		pitch += 100f * tpf;
        	}
        }
        if (down){
        	if (pitch > -45f){
        		pitch -= 100f * tpf;
        	}
        }
        Vector3f point = getPoint(deg, pitch, .15f);
        player.getCam().setLocalTranslation(point);
        player.getCam().lookAt(player.getObj().getWorldTranslation(), WORLD_UP_AXIS);
        
		if (activeCam == CAM_MODE.FOLLOW){
			player.getNode().setLocalRotation(player.getCam().getWorldRotation());
			player.getPhysicsControl().setPhysicsRotation(player.getNode().getLocalRotation());
		}
	}

	/**
	 * getPoint() returns position of camera based on a circle around
	 * player using float deg and float radius
	 * where deg = angle of camera from player and radius = distance from player
	 * @param degrees
	 * @param radius
	 * @return Vector3f position of camera
	 */
    private Vector3f getPoint(float degrees, float pitch, float radius) {
    	Vector3f pos = new Vector3f();

        double rads = Math.toRadians(degrees - 90); // 0 becomes the top
        double r = Math.toRadians(pitch - 90); // 0 becomes the top
        
        float x = player.getObj().getWorldTranslation().getX();
        float y = player.getObj().getWorldTranslation().getY();
        float z = player.getObj().getWorldTranslation().getZ();
        
        pos.setX((float) (x + Math.cos(rads) * radius));
        pos.setY((float) (y + Math.cos(r) * radius));
        pos.setZ((float) (z + Math.sin(rads) * radius));

        return pos;
    }
    
	/**
	 * Go through every fish in workingScenario and move them
	 * @param tpf
	 */
	private void moveFish(float tpf){
		java.util.Iterator<Fish> itr = workingScenario.getFish();
		while (itr.hasNext()){
			Fish f = (Fish) itr.next();
			//f.move();
			if(f instanceof Cichlid){
				Cichlid c = (Cichlid)f;
				c.move(tpf);
			}
		}
	}

	@Override
    public void onAction(String binding, boolean value, float tpf) {
		player.getPhysicsControl().clearForces();
        if (binding.equals("Left")) {
            if (value) {
                left = true;
            }
        }
        else if (binding.equals("Right")) {
            if (value) {
                right = true;
            }
        }
        else if (binding.equals("Up")) {
            if (value) {
                up = true;
            }
            else {
            	up = false;
            }
        }
        else if (binding.equals("Down")) {
            if (value) {
                down = true;
            }
            else {
            	down = false;
            }
        }
        else if (binding.equals("Forward")) {
            if (value) {
                forward = true;
            }
            else {
            	forward = false;
            }
        }
        else if (binding.equals("Backward")) {
            if (value) {
                backward = true;
            }
            else {
            	backward = false;
            }
        }
        else if (binding.equals("Ascend")){
        	if (value){
        		ascend = true;
        	}
        	else {
                ascend = false;
        	}
        }
        else if (binding.equals("Descend")){
        	if (value){
        		descend = true;
        	}
        	else {
                descend = false;
        	}
        }
        else if (binding.equals("Sprint")){
        	if (value){
        		player.setSprint(true);
        	}
        	else player.setSprint(false);
        }
	}


}//end of Main class
