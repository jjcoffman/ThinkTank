package Game;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
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
import gameAssets.Cichlid;
import gameAssets.Player;
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
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.DEFAULT_SCENARIO;
import thinktank.simulator.scenario.Grid;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.ui.RootNodeController;

/**
 * The main client for the application, extending the JMonkeyEngine class
 * <code>SimpleApplication</code> and providing functionality for starting and
 * running the game.
 * 
 * @author Bob Thompson
 * @author Vasher Lor
 * @version %I%, %G%
 */

public class Main extends SimpleApplication implements ActionListener {
	// ---------------------static constants----------------------------
	public enum CAM_MODE {
		FLY, FOLLOW
	};

	public static final Vector3f WORLD_UP_AXIS = new Vector3f(0, 1, 0);
	public static final SecureRandom RNG = new SecureRandom();

	// ---------------------static variables----------------------------
	private static SimulatorCollection simCollection;
	private static Node environ_node;
	/**
	 * @deprecated
	 */
	private static ArrayList<Scenario> scenarios;
	private static ArrayList<String> scenario_names;
	private static Scenario workingScenario;
	private static Grid grid;
	public static AssetManager am;

	// ---------------------instance constants--------------------------
	// ---------------------instance variables--------------------------
	private Player player;
	private Nifty nifty;
	private BulletAppState bulletAppState;
	private CameraNode fishCam; //Player camera
	private GhostControl test; //TODO for testing, remove later
	private CAM_MODE activeCam; 
	private int activeScenarioIndex;
	private boolean mouselookActive;
	private boolean inMenus;
	private boolean ctrlDown;
	private boolean pause = true;

	private float deg = (float) (Math.PI / 2); //TODO convert to this
	private long timer; //TODO Check out the date class
	private long defTime;
	private int mult = 1; //TODO delete and change fast forward implementation
	private RootNodeController simulator;

	// ---------------------constructors--------------------------------
	/**
	 * Constructor for Starter
	 */
	public Main() {
		scenario_names = new ArrayList<String>();
		activeScenarioIndex = -1;
		mouselookActive = true;
		inMenus = true;
		ctrlDown = false;
		test = null;
	}// end of default constructor

	// ---------------------instance methods----------------------------
	// GETTERS
	/**
	 * 
	 * @return workingScenario.
	 */
	public Scenario getWorkingScenario() {
		return workingScenario;
	}// end of getWorkingScenario method

	/**
	 * Determines whether user is in menu.
	 * 
	 * @return Boolean inMenu
	 */
	public boolean isInMenus() {
		return inMenus;
	}// end of isInMenus method

	public boolean isMouselookActive() {
		return mouselookActive;
	}// end of isMouselookActive method

	public boolean isCTRLDown() {
		return ctrlDown;
	}// end of isCTRLDown method

	/**
	 * Getter for activeCam.
	 * 
	 * @return activeCam
	 */
	public CAM_MODE getActiveCam() {
		return activeCam;
	}// end of getActiveCam method

	/**
	 * Iterates through scenarios, grabs their names and
	 * 
	 * @return List of scenario names.
	 */
	public ArrayList<String> getScenarioNames() {
		return scenario_names;
	}// end of getScenarioNames method

	// SETTERS
	/**
	 * Changes camera mode to FLY or FOLLOW
	 * 
	 * @param mode
	 */
	public void setCamMode(CAM_MODE mode) {

		activeCam = mode;
		switch (mode) {
		case FLY:
			player.getCam().setEnabled(false);
			removeFishInput();
			flyCam.setEnabled(true);
			inputManager.setCursorVisible(false);
			this.cam.setLocation(new Vector3f(-2, 0.1f, 0));
			// TODO save previous fly cam position and reset to that
			this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(),
					WORLD_UP_AXIS);
			ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
			break;
		case FOLLOW:
			if (player != null) {
				flyCam.setEnabled(false);
				player.getCam().setEnabled(true);
				repairFishInput();
				inputManager.setCursorVisible(false);
				ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FOLLOW);
			}
			break;
		}
	}// end of setCamMode method
	
	/**
	 * Sets up the physics for ghosts
	 */
	private void setupPhys() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        // turn off gravity, sort of.
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -.00001f, 0));
        // bulletAppState.setDebugEnabled(true);//DEBUG, obviously...
    }
	
	/**
	 * Setter for inMenus
	 * 
	 * @param inMenus
	 */
	public void setInMenus(boolean inMenus) {
		this.inMenus = inMenus;
	}// end of setInMenus method

	/**
	 * Setter for ctrlDown.
	 * 
	 * @param ctrlDown
	 */
	public void setCTRLDown(boolean ctrlDown) {
		System.out.println("ctrlDown=" + ctrlDown);
		this.ctrlDown = ctrlDown;
	}// end of setCTRLDown method

	public void setWorkingScenario(String scnearioName) {
		int i = 0;
		for (Scenario scenario : scenarios) {
			if (scenario.getName().equals(scnearioName)) {
				workingScenario = scenario;
			}
			i++;
		}
	}// end of setWorkingScenario method

	// OPERATIONS

	/**
	 * Pass in a scenario-type object to be added the array-list of scenarios.
	 * 
	 * @deprecated
	 */
	public void addScenario(Scenario scenario) {

		if (scenario != null) {

			scenarios.add(scenario);
			// TODO temp: for testing only.
//			clearScenario();
//			activeScenarioIndex = scenarios.indexOf(scenario);
//			workingScenario = scenario;
//			displayScenario();
		}
	}// end of addScenario method

	/**
	 * This adds a spatial-type object to rootNode
	 * 
	 * @param Spatial
	 *            obj
	 */
	public void attachToRootNode(Spatial obj) {
		if (obj != null) {
			rootNode.attachChild(obj);
		}
	}// end of attachToRootNode method

	/**
	 * Used to remove inputs when switching to FLY cam
	 */
	public void removeFromRootNode(Spatial obj) {

		if (obj != null) {
			rootNode.detachChild(obj);
		}
	}// end of removeFromRootNode method

	public void removeFishInput() {
		inputManager.removeListener(this);
		inputManager.addListener(this, "Speed");
		inputManager.addListener(this, "Pause");
	}// end of removeFishInput mode

	/**
	 * Used to restore inputs when switching to FOLLOW cam
	 */
	public void repairFishInput() {
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
	}// end of repairFishInput method

	/**
	 * Prints current scenario, then wipes workingScenario.
	 */
	private void clearScenario() {

		if (workingScenario != null) {
			System.out.println("Scene: " + workingScenario.getName());
			rootNode.detachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			rootNode.detachChild(workingScenario.getEnvironment().getTank().getNode());
			rootNode.detachChild(workingScenario.getEntityNode());
			workingScenario = null;
		}
	}// ends of clearScenario method

	private void displayScenario() {
		if (workingScenario != null) {
			System.out.println("Scene: " + workingScenario.getName());
			System.out.println("Tank node: " + workingScenario.getEntityNode().getNumControls());
			rootNode.attachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			rootNode.attachChild(workingScenario.getEnvironment().getTank().getNode());
			rootNode.attachChild(workingScenario.getEntityNode());
		}
	}// end of displayScenario method

	/**
	 * This toggles
	 */
	public void toggleMouseMode() {

		// System.out.println(mouselookActive+", "+activeCam);
		if (mouselookActive) {
			inputManager.setCursorVisible(true);
			if (activeCam == CAM_MODE.FLY) {
				flyCam.setEnabled(false);
			} else if (activeCam == CAM_MODE.FOLLOW) {
				player.getCam().setEnabled(false);
			}
			nifty.setIgnoreKeyboardEvents(false);
			mouselookActive = false;
		} else {
			inputManager.setCursorVisible(false);
			if (activeCam == CAM_MODE.FLY) {
				flyCam.setEnabled(true);
			} else if (activeCam == CAM_MODE.FOLLOW) {
				player.getCam().setEnabled(true);
			}
			nifty.setIgnoreKeyboardEvents(true);
			mouselookActive = true;
		}
	}// end of toggleMouseMode method

	@Override
	public void simpleUpdate(float tpf) {
		// tpf stands for time per frame
		tpf = tpf * mult; //TODO modify so TPF doesnt get modified
		long oldTime = timer;
		timer = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - defTime);

		if (oldTime != timer) {
			System.out.println("Time Elapsed: " + timer);
		}
		
		if(workingScenario != null){
			Iterator<Fish> fishIt = workingScenario.getFish();
			while(fishIt.hasNext()){
				Fish fish = fishIt.next();
				if(fish instanceof Cichlid){
					((Cichlid)fish).clearRelationships();
				}
			}
		}

		super.simpleUpdate(tpf);
	}// end of simpleUpdate method

	/**
	 * Go through every fish in workingScenario and move them
	 * 
	 * @param tpf
	 */
	private void moveFish(float tpf) {
		java.util.Iterator<Fish> itr = workingScenario.getFish();
		while (itr.hasNext()) {
			Fish f = (Fish) itr.next();
			if (f instanceof Cichlid) {
				Cichlid c = (Cichlid) f;
				c.move(tpf);
			}
		}
	}// end of moveFish method

	@Override
	public void onAction(String binding, boolean value, float tpf) { //TODO move to player or something, also remove hide if needed
		if (binding.equals("Speed")) {
			if (value) {
				if (mult == 1) {
					mult = 5;
				} else
					mult = 1;
			}
		}
		if (binding.equals("Left")) {
			if (value) {
				player.setLeft(true);
			}
		} else if (binding.equals("Right")) {
			if (value) {
				player.setRight(true);
			}
		} else if (binding.equals("Up")) {
			if (value) {
				player.setUp(true);
			} else {
				player.setUp(false);
			}
		} else if (binding.equals("Down")) {
			if (value) {
				player.setDown(true);
			} else {
				player.setDown(false);
			}
		} else if (binding.equals("Forward")) {
			if (value) {
				player.setForward(true);
			} else {
				player.setForward(false);
			}
		} else if (binding.equals("Backward")) {
			if (value) {
				player.setBackward(true);
			} else {
				player.setBackward(false);
			}
		} else if (binding.equals("Ascend")) {
			if (value) {
				player.setAscend(true);
			} else {
				player.setAscend(false);
			}
		} else if (binding.equals("Descend")) {
			if (value) {
				player.setDescend(true);
			} else {
				player.setDescend(false);
			}
		} else if (binding.equals("Sprint")) {
			if (value) {
				player.setSprint(true);
			} else
				player.setSprint(false);
		} else if (binding.equals("Hide")) {
			if (value) {
				if (player.canHide()) {
					player.toggleHiding(!player.wantsToHide());
				}
			}
		} else if (binding.equals("Pause")) {
			if (value) {
				pause();
			}
		}

	}// end of onAction method

	private void pause() {
		pause = !pause;
		if (pause) {
			stateManager.detach(simulator);
		} else
			stateManager.attach(simulator);
	}

	/**
	 * Shows the X, Y, and Z axes for debug purposes.
	 */
	public void showAxes() {
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
	}// end of showAxes method

	/**
	 * Hides FPS and Stat View.
	 */
	public void hideStatsInfo() {
		setDisplayFps(false);
		setDisplayStatView(false);
	}// end of hideStatsInfo method

	// INITIALIZATION
	@Override
	public void simpleInitApp() {
		
		setupPhys();
		
		// turn off stats display
		hideStatsInfo();

		am = this.assetManager;
		simCollection = new SimulatorCollection();
		// TODO load saved scenarios
		workingScenario = Scenario.createScenario(); //TODO change to default
		//TODO make sure changing grid doesnt break stuff
		grid = new Grid(getWorkingScenario());
												
		populateScenarioNames();
		// showAxes();//DEBUG
		displayScenario(); //TODO dependent on design, what happens when user clicks "Enter simulation" on start

		// world elements
		setupSun();
		rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
		//TODO add water effect?
		
		// set initial cameras & positions
		setupCam();
		
		//TODO move, attach to buttun to be called and clean up
		// make player and set camera to player
		fishCam = new CameraNode("Player camera", cam);
		player = Player.getPlayer(fishCam);
		player.getNode().setLocalTranslation(0, 0.25f, 0);
		// player.setCam(fishCam);

		rootNode.attachChild(player.getNode());
		rootNode.attachChild(player.getCam());

		simulator = new RootNodeController(this, player);
		simulator.setEnabled(true);
		
		//TODO move all inputs to instance class
		// setup inputs 
		initInputs();
		
		// set up interface, dont mess with
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		nifty.fromXml("Interface/screen.xml", "start");
		inMenus = true;
		guiViewPort.addProcessor(niftyDisplay);

		// settings for cichlid glow
		FilterPostProcessor fpp = new FilterPostProcessor(this.assetManager);
		BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
		bloom.setDownSamplingFactor(2.0f);
		bloom.setBlurScale(3.0f);
		bloom.setBloomIntensity(0.4f);
		fpp.addFilter(bloom);
		viewPort.addProcessor(fpp);
		
		//default camera set to FLY cam
		setCamMode(CAM_MODE.FLY);
		//toggle to set mouse active
		toggleMouseMode();
		defTime = System.nanoTime();
	}// end of simpleInitApp method


	/**
	 * Sets up camera. Move speed is set to 1.5f, set to look at tank, fly cam
	 * is emabled, and active cam is set to FLY as default.
	 */
	private void setupCam() {
		// set toggle action to switch to follow on first invocation
		ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
		this.cam.setLocation(new Vector3f(-2, 0.1f, 0));// temp: for easier
														// testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(),
				WORLD_UP_AXIS);
		// set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100f);
		flyCam.setMoveSpeed(1.5f);
		flyCam.setEnabled(true);
		activeCam = CAM_MODE.FLY;
		inputManager.setCursorVisible(true);
	}// end of setupCam method

	//TODO Move to input listener
	/**
	 * Initial inputs, and sets up all the keyboard hotkeys.
	 */
	private void initInputs() {
		// initiate listeners
		InputListener.getInstance();

		inputManager.addMapping(AddPotAction.NAME, new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping(AddPlantAction.NAME, new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping(AddFishAction.NAME, new KeyTrigger(KeyInput.KEY_K));

		inputManager.addMapping(ToggleCamModeAction.NAME, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(ToggleMouselookAction.NAME, new KeyTrigger(KeyInput.KEY_APOSTROPHE));
		inputManager.addMapping(CTRLMaskAction.NAME, new KeyTrigger(KeyInput.KEY_LCONTROL),
				new KeyTrigger(KeyInput.KEY_RCONTROL));

		inputManager.addMapping(SelectEntityAction.NAME, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

		inputManager.addMapping(MoveEntityLeftAction.NAME, new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping(MoveEntityRightAction.NAME, new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping(MoveEntityForwardAction.NAME, new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping(MoveEntityBackwardAction.NAME, new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping(MoveEntityUpAction.NAME, new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping(MoveEntityDownAction.NAME, new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping(RotateEntityLeftAction.NAME, new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping(RotateEntityRightAction.NAME, new KeyTrigger(KeyInput.KEY_RIGHT));

		// Add the names to the action listener.
		inputManager.addListener(InputListener.getInstance(), AddPotAction.NAME);
		inputManager.addListener(InputListener.getInstance(), AddPlantAction.NAME);
		inputManager.addListener(InputListener.getInstance(), AddFishAction.NAME);
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

		// DEBUG
		inputManager.addMapping("Speed", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addListener(this, "Speed");
		inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_0));
		inputManager.addListener(this, "Pause");
		setupFishInput();

	}// end of initInputs method

	//TODO move to listener
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
		inputManager.addMapping("Hide", new KeyTrigger(KeyInput.KEY_SPACE));

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
	}// end of setupFishInput method

	/**
	 * Instantiates where main light source will be (sun), it's direction and
	 * color. Currently set to have two light sources to avoid dark shadows.
	 */
	private void setupSun() {
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(1f, -1f, 1f).normalizeLocal());
		sun.setColor(new ColorRGBA(2, 2, 2, 0));
		rootNode.addLight(sun);
	}// end of setupSun method

	private void populateScenarioNames() {
		for (DEFAULT_SCENARIO def : DEFAULT_SCENARIO.values()) {
			scenario_names.add(def.NAME);
		}
	}// end of populateScenarioNames method

	// ---------------------static main---------------------------------
	// ---------------------static methods------------------------------

	/**
	 * Getter for object type Grid. (Grid(getWorkingScenario()))
	 * 
	 * @return Returns local variable grid
	 */
	public static Grid getGrid() {
		return grid;
	}// end of getGrid method

}// end of Main class