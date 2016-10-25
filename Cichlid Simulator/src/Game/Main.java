package Game;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/*****************************************************************************************
 * Class: Main
 * Purpose: Initiates the game entities and environment, also contains the update method
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
import com.jme3.bullet.control.GhostControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.jme3.util.SkyFactory;

import de.lessvoid.nifty.Nifty;
import gameAssets.*;
import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.CTRLMaskAction;
import thinktank.simulator.actions.LoadScenarioAction;
import thinktank.simulator.actions.MoveEntityBackwardAction;
import thinktank.simulator.actions.MoveEntityDownAction;
import thinktank.simulator.actions.MoveEntityForwardAction;
import thinktank.simulator.actions.MoveEntityLeftAction;
import thinktank.simulator.actions.MoveEntityRightAction;
import thinktank.simulator.actions.MoveEntityUpAction;
import thinktank.simulator.actions.RotateEntityLeftAction;
import thinktank.simulator.actions.RotateEntityRightAction;
import thinktank.simulator.actions.SaveScenarioAction;
import thinktank.simulator.actions.SelectEntityAction;
import thinktank.simulator.actions.TestVisibility;
import thinktank.simulator.actions.ToggleCamModeAction;
import thinktank.simulator.actions.ToggleMouselookAction;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.Grid;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.ui.RootNodeState;

/**
 * The main client for the application, extending the JMonkeyEngine class 
 * <code>SimpleApplication</code> and providing functionality for starting 
 * and running the game.
 * 
 * @author Bob Thompson
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class Main extends SimpleApplication implements ActionListener{
	//---------------------static constants----------------------------
	public enum CAM_MODE{
		FLY,
		FOLLOW
	};
	public static final Vector3f WORLD_UP_AXIS = new Vector3f(0, 1, 0);
	public static final SecureRandom RNG = new SecureRandom();

	//---------------------static variables----------------------------
	private static SimulatorCollection simCollection;
	private static Node environ_node; 
	private static ArrayList<Scenario> scenarios;
	private static Scenario workingScenario;
	private static Grid grid;
	public static AssetManager am;

	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Player player;
	private Nifty nifty;
	private BulletAppState bulletAppState;
	private CameraNode fishCam;
	private GhostControl test;
	private CAM_MODE activeCam;
	private int activeScenarioIndex;
	private boolean mouselookActive;
	private boolean inMenus;
	private boolean ctrlDown;

    private float deg = (float) (Math.PI/2);
    private float pitch;
    private long timer;
    private long defTime;
    private int mult = 1;
    private boolean pause = true;
    public RootNodeState simulator;
	

	//---------------------constructors--------------------------------
	public Main(){
		scenarios = new ArrayList<Scenario>();
		activeScenarioIndex = -1;
		mouselookActive = true;
		inMenus = true;
		ctrlDown = false;
		test = null;
	}//end of default constructor

	//---------------------instance methods----------------------------
	//GETTERS
	public Scenario getWorkingScenario(){
		return workingScenario;
	}//end of getWorkingScenario method
	
	public boolean isInMenus(){
		return inMenus;
	}//end of isInMenus method

	public boolean isMouselookActive(){
		return mouselookActive;
	}//end of isMouselookActive method
	
	public boolean isCTRLDown(){
		return ctrlDown;
	}//end of isCTRLDown method
	
	public CAM_MODE getActiveCam(){
		return activeCam;
	}//end of getActiveCam method
	
    
	//SETTERS
	/**
	 * Changes camera mode to FLY or FOLLOW
	 * @param mode
	 */
	public void setCamMode(CAM_MODE mode){
//		System.out.println(mode);
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
	
	public void setInMenus(boolean inMenus){
		this.inMenus = inMenus;
	}//end of setInMenus method
	
	public void setCTRLDown(boolean ctrlDown){
		System.out.println("ctrlDown="+ctrlDown);
		this.ctrlDown = ctrlDown;
	}//end of setCTRLDown method
	
	//OPERATIONS
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

	/**
	 * Used to remove inputs when switching to FLY cam
	 */
	public void removeFishInput(){
		inputManager.removeListener(this);
        inputManager.addListener(this, "Speed");
        inputManager.addListener(this, "Pause");
	}//end of removeFishInput mode
	
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
        inputManager.addListener(this, "Speed");
        inputManager.addListener(this, "Hide");
	}//end of repairFishInput method

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

	public void toggleMouseMode(){
//		System.out.println(mouselookActive+", "+activeCam);
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

	@Override
	public void simpleUpdate(float tpf){
		//tpf stands for time per frame
		/*
		if (pause){
			return;
		}
		*/
		//if (simulator.isEnabled()){
			//simulator.update(tpf);
		//}
		
		/*
		moveFish(tpf);
		player.update(tpf);
		*/
		tpf = tpf * mult;
		long oldTime = timer;
		timer = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-defTime);

		
		if (oldTime != timer){
			System.out.println("Time Elapsed: " + timer);
		}
		
		super.simpleUpdate(tpf);
	}//end of simpleUpdate method

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
	}//end of moveFish method

	@Override
    public void onAction(String binding, boolean value, float tpf){
		//player.getPhysicsControl().clearForces();
		if (binding.equals("Speed")){
        	if (value){
        		if (mult == 1){
        			mult = 5;
        		}
        		else mult = 1;
        	}
        }
        if (binding.equals("Left")) {
            if (value) {
                player.setLeft(true);
            }
        }
        else if (binding.equals("Right")) {
            if (value) {
                player.setRight(true);
            }
        }
        else if (binding.equals("Up")) {
            if (value) {
                player.setUp(true);
            }
            else {
                player.setUp(false);
            }
        }
        else if (binding.equals("Down")) {
            if (value) {
                player.setDown(true);
            }
            else {
                player.setDown(false);
            }
        }
        else if (binding.equals("Forward")) {
            if (value) {
                player.setForward(true);
            }
            else {
                player.setForward(false);
            }
        }
        else if (binding.equals("Backward")) {
            if (value) {
                player.setBackward(true);
            }
            else {
                player.setBackward(false);
            }
        }
        else if (binding.equals("Ascend")){
        	if (value){
                player.setAscend(true);
        	}
        	else {
                player.setAscend(false);
        	}
        }
        else if (binding.equals("Descend")){
        	if (value){
                player.setDescend(true);
        	}
        	else {
                player.setDescend(false);
        	}
        }
        else if (binding.equals("Sprint")){
        	if (value){
                player.setSprint(true);
        	}
        	else player.setSprint(false);
        }
        else if (binding.equals("Hide")){
        	if (value){
        		if (player.canHide()){
                    player.toggleHiding(!player.wantsToHide());
        		}
        	}
        }
        else if (binding.equals("Pause")){
        	if (value){
        		pause = !pause;
        		if(pause){
        			//simulator.setEnabled(false);
        			stateManager.detach(simulator);
        		}
        		else {
        			//simulator.setEnabled(true);
        			stateManager.attach(simulator);
        		}
        	}
        }
        
	}//end of onAction method

	/**
	 * Shows the X, Y, and Z axes for debug purposes.
	 */
	public void showAxes(){
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
	
	//INITIALIZATION
	@Override
	public void simpleInitApp(){
		//setup physics
		setupPhys();
		
		simulator = new RootNodeState();
		simulator.initialize(stateManager, this);
		simulator.setEnabled(true);
		
		//turn off stats display
		hideStatsInfo();

		am = this.assetManager;
		simCollection = new SimulatorCollection();
		//TODO load saved scenarios
		workingScenario = new Scenario();
		grid = new Grid(getWorkingScenario());
		
		//showAxes();//DEBUG
		displayScenario();
		
		//world elements
		setupSun();
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));

		//set initial cameras & positions
		setupCam();
		
		//make player and set camera to player
		fishCam = new CameraNode("Player camera", cam);
		player = Player.getPlayer(fishCam);
		player.getNode().setLocalTranslation(0, 0.25f, 0);
		//player.setCam(fishCam);
		
		rootNode.attachChild(player.getNode());
		rootNode.attachChild(player.getCam());
		
		//setup inputs
		initInputs();

		//set up interface
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		nifty.fromXml("Interface/screen.xml", "start");
		inMenus = true;
		guiViewPort.addProcessor(niftyDisplay);
		
		//debug
	    FilterPostProcessor fpp = new FilterPostProcessor(this.assetManager);
	    BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
	    bloom.setDownSamplingFactor(2.0f);
	    bloom.setBlurScale(3.0f);
	    bloom.setBloomIntensity(0.4f);
	    fpp.addFilter(bloom);
	    viewPort.addProcessor(fpp);
		//end debug
		
		setCamMode(CAM_MODE.FLY);
		toggleMouseMode();
		defTime = System.nanoTime();
	}//end of simpleInitApp method
	
	/**
	 * Setup all physics base configurations 
	 */
	private void setupPhys(){
		bulletAppState = new BulletAppState();
	    stateManager.attach(bulletAppState);
	    //turn off gravity, sort of. 
	    bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0,-.00001f,0));
	    //bulletAppState.setDebugEnabled(true);//DEBUG, obviously...
	}//end of setupPhys method

	private void setupCam(){
		ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);//set toggle action to switch to follow on first invocation
		this.cam.setLocation(new Vector3f(-2, 0.1f, 0));//temp: for easier testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
		//set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100f);
		flyCam.setMoveSpeed(1.5f);
		flyCam.setEnabled(true);
		activeCam = CAM_MODE.FLY;
		inputManager.setCursorVisible(true);
	}//end of setupCam method

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
		inputManager.addMapping(CTRLMaskAction.NAME, new KeyTrigger(KeyInput.KEY_LCONTROL), new KeyTrigger(KeyInput.KEY_RCONTROL));
		
		inputManager.addMapping(SelectEntityAction.NAME, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		
		inputManager.addMapping(MoveEntityLeftAction.NAME, new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping(MoveEntityRightAction.NAME, new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping(MoveEntityForwardAction.NAME, new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping(MoveEntityBackwardAction.NAME, new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping(MoveEntityUpAction.NAME, new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping(MoveEntityDownAction.NAME, new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping(RotateEntityLeftAction.NAME, new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping(RotateEntityRightAction.NAME, new KeyTrigger(KeyInput.KEY_RIGHT));
		
		//DEBUG
		inputManager.addMapping(TestVisibility.NAME, new KeyTrigger(KeyInput.KEY_Y));
		
		// Add the names to the action listener.
	    inputManager.addListener(InputListener.getInstance(), AddPotAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddPlantAction.NAME);
	    inputManager.addListener(InputListener.getInstance(), AddFishAction.NAME);
		inputManager.addListener(InputListener.getInstance(), SaveScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), LoadScenarioAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleCamModeAction.NAME);
		inputManager.addListener(InputListener.getInstance(), ToggleMouselookAction.NAME);
		inputManager.addListener(InputListener.getInstance(), SelectEntityAction.NAME);
		inputManager.addListener(InputListener.getInstance(), CTRLMaskAction.NAME);
		
		inputManager.addListener(InputListener.getInstance(), MoveEntityLeftAction.NAME);
		inputManager.addListener(InputListener.getInstance(), MoveEntityRightAction.NAME);
		inputManager.addListener(InputListener.getInstance(), MoveEntityForwardAction.NAME);
		inputManager.addListener(InputListener.getInstance(), MoveEntityBackwardAction.NAME);
		inputManager.addListener(InputListener.getInstance(), MoveEntityUpAction.NAME);
		inputManager.addListener(InputListener.getInstance(), MoveEntityDownAction.NAME);
		inputManager.addListener(InputListener.getInstance(), RotateEntityLeftAction.NAME);
		inputManager.addListener(InputListener.getInstance(), RotateEntityRightAction.NAME);
		
		//DEBUG
		inputManager.addListener(InputListener.getInstance(), TestVisibility.NAME);
		

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addListener(this, "Pause");
		
	    setupFishInput();
		
	}//end of initInputs method
	
	/**
	 * Setup all inputs regarding the player fish
	 */
	public void setupFishInput(){
        inputManager.addMapping("Speed", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
      	inputManager.addMapping("Right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("Up", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
      	inputManager.addMapping("Down", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping("Ascend", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Descend", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("Sprint", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Hide", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(this, "Speed");
        inputManager.addListener(this, "Forward");
        inputManager.addListener(this, "Backward");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Ascend");
        inputManager.addListener(this, "Descend");
        inputManager.addListener(this, "Sprint");
        inputManager.addListener(this, "Hide");
	}//end of setupFishInput method
	
	private void setupSun(){
		DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1f, -1f, 1f).normalizeLocal());
        sun.setColor(new ColorRGBA(2,2,2,0));
        rootNode.addLight(sun);
        
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection(new Vector3f(2f,2f,2f).normalizeLocal());
        sun2.setColor(new ColorRGBA(2,2,2,0));
        //rootNode.addLight(sun2);
	}//end of setupSun method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Grid getGrid(){
		return grid;
	}//end of getGrid method

}//end of Main class