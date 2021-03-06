package thinktank.simulator.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;

import de.lessvoid.nifty.Nifty;
import thinktank.simulator.actions.AddFishAction;
import thinktank.simulator.actions.AddPlantAction;
import thinktank.simulator.actions.AddPotAction;
import thinktank.simulator.actions.CTRLMaskAction;
import thinktank.simulator.actions.MoveEntityBackwardAction;
import thinktank.simulator.actions.MoveEntityDownAction;
import thinktank.simulator.actions.MoveEntityForwardAction;
import thinktank.simulator.actions.MoveEntityLeftAction;
import thinktank.simulator.actions.MoveEntityRightAction;
import thinktank.simulator.actions.MoveEntityUpAction;
import thinktank.simulator.actions.RotateEntityLeftAction;
import thinktank.simulator.actions.RotateEntityRightAction;
import thinktank.simulator.actions.SelectEntityAction;
import thinktank.simulator.actions.ToggleCamModeAction;
import thinktank.simulator.actions.ToggleMouselookAction;
import thinktank.simulator.entity.Player;
import thinktank.simulator.scenario.DEFAULT_SCENARIO;
import thinktank.simulator.scenario.Grid;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.scenario.ScenarioDefinition;
import thinktank.simulator.scenario.ScenarioIO;

/**
 * The main client for the application, extending the JMonkeyEngine class
 * <code>SimpleApplication</code> and providing functionality for starting and
 * running the game.
 * 
 * @author Bob Thompson, Vasher Lor, Jonathan Coffman
 * @version %I%, %G%
 */

public class Main extends SimpleApplication implements ActionListener{
	/**
	 * @deprecated
	 */
	public enum CAM_MODE{
		FLY, FOLLOW
	};//end of CAM_MODE enum
	
	// ---------------------static constants----------------------------
	/**
	 * The up axis for the game world.
	 */
	public static final Vector3f WORLD_UP_AXIS = new Vector3f(0, 1, 0);
	/**
	 * The random number generator for generating random values throughout 
	 * the simulator.
	 */
	public static final Random RNG = new Random();

	// ---------------------static variables----------------------------
	/**
	 * A reference to the asset manager for the application.
	 */
	public static AssetManager asset_manager = null;
	/**
	 * A reference to the current grid for the simulation.
	 */
	private static Grid grid = null;
	/**
	 * Flag for whether or not the application is in the process of loading.
	 */
	private static boolean loading = true;
	/**
	 * Value for tracking elapsed time during execution.
	 */
	private static long timer = 0;

	// ---------------------instance constants--------------------------
	// ---------------------instance variables--------------------------
	/**
	 * @deprecated
	 */
	private Player player;//TODO move to scenario
	/**
	 * The nifty object that controls the interface.
	 */
	private Nifty nifty;
	/**
	 * List of names of the available scenarios.
	 */
	private ArrayList<String> scenarioNames;
	/**
	 * The current scenario that can be edited or run.
	 */
	private Scenario workingScenario;
	/**
	 * 
	 */
	private BulletAppState bulletAppState;
	/**
	 * @deprecated
	 */
	private CameraNode fishCam; //Player camera
	/**
	 * @deprecated
	 */
	private CAM_MODE activeCam; 
	/**
	 * Flag for whether or not the mouse is currently active (visible).
	 */
	private boolean mouselookActive;
	/**
	 * Flag for whether or not the user is in a menu.
	 */
	private boolean inMenus;
	/**
	 * Flag for whether or not the control [ctrl] key is pressed.
	 */
	private boolean ctrlDown;
	/**
	 * Flag for whether or not the simulation is paused.
	 */
	private boolean pause;
	/**
	 * Value for calculating and tracking elapsed time.
	 */
	private long defTime;
	/**
	 * @deprecated
	 */
	private int mult = 1; //TODO delete and change fast forward implementation
	/**
	 * App state for pausing and playing the simulation.
	 */
	private RootNodeController simulator;

	// ---------------------constructors--------------------------------
	/**
	 * Constructor for Starter
	 */
	public Main(){
		player = null;
		nifty = null;
		scenarioNames = new ArrayList<String>();
		workingScenario = null;
		bulletAppState = null;
		fishCam = null;
		activeCam = null;
		mouselookActive = true;
		inMenus = true;
		ctrlDown = false;
		pause = true;
		defTime = 0;
	}//end of default constructor

	// ---------------------instance methods----------------------------
	// GETTERS
	/**
	 * Returns a reference to the current working scenario.
	 * 
	 * @return the working scenario.
	 */
	public Scenario getWorkingScenario(){
		return workingScenario;
	}//end of getWorkingScenario method

	/**
	 * Determines whether user is in menu.
	 * 
	 * @return true if the user is in a menu, false otherwise.
	 */
	public boolean isInMenus(){
		return inMenus;
	}//end of isInMenus method

	/**
	 * Returns whether or not mouselook is currently active.
	 * 
	 * @return true if mouselook is active, false otherwise.
	 */
	public boolean isMouselookActive(){
		return mouselookActive;
	}//end of isMouselookActive method

	/**
	 * Returns whether or not the control [ctrl] key is pressed.
	 * 
	 * @return true if [ctrl] is pressed, false otherwise.
	 */
	public boolean isCTRLDown(){
		return ctrlDown;
	}//end of isCTRLDown method

	/**
	 * Getter for activeCam.
	 * 
	 * @return activeCam
	 * @deprecated
	 */
	public CAM_MODE getActiveCam(){
		return activeCam;
	}//end of getActiveCam method

	/**
	 * Returns the list of scenario names.
	 * 
	 * @return List of scenario names.
	 */
	public ArrayList<String> getScenarioNames(){
		ArrayList<String> returnValue = new ArrayList<String>();
		returnValue.addAll(scenarioNames);
		return returnValue;
	}//end of getScenarioNames method

	/**
	 * Checks whether or not the specified scenario name is the name 
	 * of the current working scenario.
	 * 
	 * @param scenarioName the name to be checked.
	 * @return true if the specified name is the same as the current 
	 * working scenario, false otherwise.
	 */
	public boolean isWorkingScenario(String scenarioName){
		boolean returnValue = false;
		if(workingScenario.getName().equals(scenarioName)){
			returnValue = true;
		}
		return returnValue;
	}//end of isWorkingScenario method
	
	/**
	 * Returns whether or not the simulation is currently paused.
	 * 
	 * @return true if the simulation is paused, false otherwise.
	 */
	public boolean isPaused(){
		return pause;
	}//end of isPaused method
	
	// SETTERS
	/**
	 * Changes camera mode to FLY or FOLLOW
	 * 
	 * @param mode
	 * @deprecated
	 */
	public void setCamMode(CAM_MODE mode){
		activeCam = mode;
		if(player != null){
			switch(mode){
			case FLY:
				player.getCam().setEnabled(false);
				flyCam.setEnabled(true);
				inputManager.setCursorVisible(false);
				this.cam.setLocation(new Vector3f(-2, 0.1f, 0));
				// TODO save previous fly cam position and reset to that
				this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
				ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
				break;
			case FOLLOW:
				if(player != null){
					flyCam.setEnabled(false);
					player.getCam().setEnabled(true);
					inputManager.setCursorVisible(false);
					ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FOLLOW);
				}
				break;
			}
		}
	}//end of setCamMode method
	
	/**
	 * Sets up the physics for ghosts
	 */
	private void setupPhys(){
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        // turn off gravity, sort of.
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -.00001f, 0));
        // bulletAppState.setDebugEnabled(true);//DEBUG, obviously...
    }//end of setupPhys
	
	/**
	 * Sets the value of the in menus flag to the specified value.
	 * 
	 * @param the value to which the in menus flag is to be set.
	 */
	public void setInMenus(boolean inMenus){
		this.inMenus = inMenus;
	}//end of setInMenus method

	/**
	 * Sets the value of the control down flag to the specified value.
	 * 
	 * @param the value to which the control down flag is to be set.
	 */
	public void setCTRLDown(boolean ctrlDown){
		this.ctrlDown = ctrlDown;
	}//end of setCTRLDown method

	/**
	 * Sets the specified scenario as the current working scenario.
	 * 
	 * @param scenario the new working scenario.
	 */
	public void setWorkingScenario(Scenario scenario){
		if(scenario != null){
			clearScenario();
			workingScenario = scenario;
			setGrid();
			displayScenario();
		}
	}//end of setWorkingScenario method
	
	/**
	 * Sets the working scenario to the default scenario. This is defined 
	 * as the "Empty" scenario.
	 */
	public void setWorkingScenarioToDefault(){
		if(workingScenario != null){
			clearScenario();
		}
		setWorkingScenario(ScenarioDefinition.genScenario(DEFAULT_SCENARIO.EMPTY));
	}//end of setWorkingScenarioToDefault method

	/**
	 * Sets the grid based on the current working scenario.
	 */
	public void setGrid(){
		grid = new Grid(getWorkingScenario());
	}//end of setGrid method

	// OPERATIONS
	/**
	 * Adds the specified scenario name to the list of scenario names.
	 * 
	 * @param scenarioName the new scenario name.
	 */
	public void addScenario(String scenarioName){
		if(scenarioName != null && scenarioName.length() > 0){
			getScenarioNames().add(scenarioName);
		}
	}//end of addScenario method
	
	/**
	 * Removes the specified scenario name from the list of scenario names.
	 * 
	 * @param scenarioName the scenario name to remove.
	 */
	public void removeScenario(String scenarioName){
		if(scenarioName != null && scenarioName.length() > 0){
			getScenarioNames().remove(scenarioName);
		}
	}//end of removeScenario method

	/**
	 * Adds a <code>Spatial</code> object to the root node 
	 * of the scene graph.
	 * 
	 * @param obj the spatial to add.
	 */
	public void attachToRootNode(Spatial obj){
		if(obj != null){
			rootNode.attachChild(obj);
		}
	}//end of attachToRootNode method

	/**
	 * Removes a <code>Spatial</code> object from the root 
	 * node of the scene graph.
	 * 
	 * @param obj the spatial to remove. 
	 */
	public void removeFromRootNode(Spatial obj){
		if(obj != null){
			rootNode.detachChild(obj);
		}
	}//end of removeFromRootNode method

	/**
	 * Removes the current working scenario and resets all values.
	 */
	private void clearScenario(){
		if (workingScenario != null){
			rootNode.detachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			workingScenario.clearGhosts();
			rootNode.detachChild(workingScenario.getEntityNode());
			workingScenario = null;
		}
	}// ends of clearScenario method

	/**
	 * Displays the current working scenario.
	 */
	private void displayScenario(){
		if (workingScenario != null){
			rootNode.attachChild(workingScenario.getEnvironment().getEnvirionmentNode());
			rootNode.attachChild(workingScenario.getEntityNode());
		}
	}//end of displayScenario method

	/**
	 * Refills the values of the list of scenario names.
	 */
	public void refreshScenarioList(){
		scenarioNames.clear();
		populateScenarioNames();
	}//end of refreshScenarioList method
	
	/**
	 * Toggles the mouse mode.
	 */
	public void toggleMouseMode(){
		if(mouselookActive){
			inputManager.setCursorVisible(true);
			if(activeCam == CAM_MODE.FLY){
				flyCam.setEnabled(false);
			}
			else if(activeCam == CAM_MODE.FOLLOW){
				if (player != null){
					player.getCam().setEnabled(false);
				}
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
				if (player != null){
					player.getCam().setEnabled(true);
				}
			}
			nifty.setIgnoreKeyboardEvents(true);
			mouselookActive = true;
		}
	}//end of toggleMouseMode method

	/**
	 * Main update method called as part of the game loop.
	 */
	@Override
	public void simpleUpdate(float tpf){
		// tpf stands for "time per frame"
		tpf = tpf * mult;
		long oldTime = timer;
		timer = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - defTime);

		if(oldTime != timer){
			System.out.println("Time Elapsed: " + timer);
		}

		super.simpleUpdate(tpf);
	}//end of simpleUpdate method

	/**
	 * Method invoked by the event listener.
	 */
	@Override
	public void onAction(String binding, boolean value, float tpf){
		if(binding.equals("Speed")){
			if(value){
				if(mult == 1){
					mult = 5;
				}
				else{
					mult = 1;
				}
			}
		}
		if(binding.equals("Pause")){
			if(value){
				pause();
			}
		}
	}// end of onAction method

	/**
	 * Pauses and unpauses the simulation
	 */
	private void pause(){
		pause = !pause;
		if(pause){
			stateManager.detach(simulator);
		}
		else{
			stateManager.attach(simulator);
		}
	}//end of pause method

	/**
	 * Shows the X, Y, and Z axes for debug purposes.
	 */
	@SuppressWarnings("unused")
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
	}//end of showAxes method

	/**
	 * Hides FPS and Stat View.
	 */
	public void hideStatsInfo(){
		setDisplayFps(false);
		setDisplayStatView(false);
	}//end of hideStatsInfo method
	
	//INITIALIZATION
	/**
	 * Initializes the values and objects necessary for the application to run.
	 */
	@Override
	public void simpleInitApp(){
		setupPhys();
		
		hideStatsInfo(); //turn off stats display

		asset_manager = this.assetManager;
		setWorkingScenarioToDefault();
		populateScenarioNames();
		displayScenario();
		//showAxes(); //available for debugging & development

		//world elements
		setupSun();
		rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
		//setupWaterEffect();
		
		// set initial cameras & positions
		setupCam();

		simulator = new RootNodeController(this, player);
		simulator.setEnabled(true);
		
		//TODO move all inputs to instance class
		// setup inputs 
		initInputs();
		
		//set up interface
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
		
		setCamMode(CAM_MODE.FLY); //default camera set to FLY cam
		toggleMouseMode(); //toggle to set mouse active
		defTime = System.nanoTime(); //log time at loading completion
		
		loading = false; //loading complete
	}//end of simpleInitApp method

	/**
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void makePlayer(){
		//TODO move, attach to button to be called and clean up
		// make player and set camera to player
		fishCam = new CameraNode("Player camera", cam);
		player = Player.getPlayer(fishCam);
		player.getNode().setLocalTranslation(0, 0.25f, 0);
		// player.setCam(fishCam);

		rootNode.attachChild(player.getNode());
		rootNode.attachChild(player.getCam());
	}//end of makePlayer method

	/**
	 * Sets up camera. Move speed is set to 1.5f, set to look at tank, fly cam
	 * is emabled, and active cam is set to FLY as default.
	 */
	private void setupCam(){
		// set toggle action to switch to follow on first invocation
		ToggleCamModeAction.getInstance().setTargetMode(CAM_MODE.FLY);
		this.cam.setLocation(new Vector3f(-2, 0.1f, 0));// temp: for easier testing
		this.cam.lookAt(workingScenario.getEnvironment().getTank().getSpatial().getWorldBound().getCenter(), WORLD_UP_AXIS);
		// set (fovY, ratio, near, far)
		this.cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100f);
		flyCam.setMoveSpeed(1.5f);
		flyCam.setEnabled(true);
		activeCam = CAM_MODE.FLY;
		inputManager.setCursorVisible(true);
	}//end of setupCam method

	/**
	 * Initial inputs, and sets up all the keyboard hotkeys.
	 */
	private void initInputs(){
		// initiate listeners
		InputListener.getInstance();

		inputManager.addMapping(AddPotAction.NAME, new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping(AddPlantAction.NAME, new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping(AddFishAction.NAME, new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping(ToggleMouselookAction.NAME, new KeyTrigger(KeyInput.KEY_APOSTROPHE));
		inputManager.addMapping(CTRLMaskAction.NAME, new KeyTrigger(KeyInput.KEY_LCONTROL),	new KeyTrigger(KeyInput.KEY_RCONTROL));
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

		inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_0));
		inputManager.addListener(this, "Pause");
	}//end of initInputs method

	/**
	 * Instantiates where main light source will be (sun), it's direction and
	 * color. Currently set to have two light sources to avoid dark shadows.
	 */
	private void setupSun(){
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(1f, -1f, 1f).normalizeLocal());
		sun.setColor(new ColorRGBA(2, 2, 2, 0));
		rootNode.addLight(sun);
	}//end of setupSun method
	
	/**
	 * This adds the water effect with a surface to the tank
	 */
	@SuppressWarnings("unused")
	private void setupWaterEffect(){
		Vector3f lightDir = new Vector3f(-2.9f, -1.2f, -5.8f);
		TerrainQuad terrain;
		float time = 0.0f;
		float waterHeight = 0.0f;
		float initialWaterHeight = 0.8f;
		  
		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
	    viewPort.addProcessor(fpp);
	    // add water
	    WaterFilter water = new WaterFilter(rootNode, lightDir);
	    water.setLightColor(ColorRGBA.White);
	    water.setWindDirection(Vector2f.UNIT_XY);
	    water.setLightDirection(lightDir);
	    water.setSunScale(3);
	    water.setWaveScale(0.005f);
	    water.setMaxAmplitude(5);
	    water.setWaterTransparency(0.1f);
	    water.setWaterColor(new ColorRGBA(0.1f, 0.3f, 0.5f, 1.0f));
	    water.setDeepWaterColor(new ColorRGBA(0.0f, 0.0f, 0.1f, 1.0f));
	    water.setWaterHeight(initialWaterHeight);
	    fpp.addFilter(water);
	}//end of setupWaterEffect method

	/**
	 * Fills the scenario name list with the names of the default 
	 * and saved scenarios.
	 */
	private void populateScenarioNames(){
		for(DEFAULT_SCENARIO def : DEFAULT_SCENARIO.values()){
			scenarioNames.add(def.NAME);
		}
		for(String scenario : ScenarioIO.getSavedScenarioList()){
			scenarioNames.add(scenario);
		}
	}//end of populateScenarioNames method

	// ---------------------static main---------------------------------
	// ---------------------static methods------------------------------
	/**
	 * Returns a reference to the grid for the current working scenario.
	 * 
	 * @return reference to the grid.
	 */
	public static Grid getGrid(){
		return grid;
	}//end of getGrid method

	/**
	 * Returns whether or not the application is in the process of loading.
	 * 
	 * @return true if the application is currently loading, false otherwise.
	 */
	public static boolean isLoading(){
		return loading;
	}//end of isLoading method
	
	/**
	 * Returns the time of the simulation running
	 * 
	 * @return timer
	 */
	public static long getTime(){
		return timer;
	}//end of getTime method
	
}// end of Main class