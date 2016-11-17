package gameAssets;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Ring;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.material.Material;
import Game.Main;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.FishGhost;
import thinktank.simulator.entity.IMoving;
import thinktank.simulator.entity.Pot;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.scenario.Grid;
import thinktank.simulator.scenario.Scenario;
import thinktank.simulator.util.CichlidRelationships;

/**
 * Class representing a specific type of <code>Fish</code> object, which is a
 * type of entity.
 * 
 * @author 
 * @version %I%, %G%
 *
 */
public class Cichlid extends Fish implements IMoving{
	//---------------------static constants----------------------------
	public enum POSSIBLE_COLORS{
		BLACK("Black",Color.BLACK,"Cichlid/CichlidTextDark.jpg"),
		BLUE("Blue",Color.BLUE, "Cichlid/CichlidTextContrast.jpg"),
		DEFAULT("Default",Color.WHITE, "Cichlid/CichlidText.jpg");
		
		public final String NAME;
		public final Color COLOR;
		public final String TEXTURE;
		
		private POSSIBLE_COLORS(String name, Color color, String texture){
			this.NAME = name;
			this.COLOR = color;
			this.TEXTURE = texture;
		}//end of enum constructor
	}//end of POSSIBLE_COLORS enum
	public enum POSSIBLE_SIZES{
		SMALL("Small (2in)",2.0f),
		MEDIUM("Medium (2.51969in)",2.51969f),
		LARGE("Large (3in)",3.0f);
		
		public final String NAME;
		public final float LENGTH_INCHES;
		
		private POSSIBLE_SIZES(String name, float lengthInches){
			this.NAME = name;
			this.LENGTH_INCHES = lengthInches;
		}//end of enum constructor
	}//end of POSSIBLE_SIZES enum

	
	
	private static final long serialVersionUID = 8763564513637299079L;
	private static final float MODEL_DEPTH = 2f;//z-axis
	private static final float OBJECT_DISTANCE = 30;

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private AnimChannel channel;
	/**
	 * 
	 */
	private AnimControl control;
	/**
	 * @deprecated
	 */
	private RigidBodyControl fishControl;
	private FishGhost ghost;
	private Node fish = null;
	private POSSIBLE_SIZES pSize;
	private POSSIBLE_COLORS pColor;
	//TODO not currently being used for AI
	/**
	 * @deprecated
	 */
	private boolean sprint = false;
	private boolean atLoc = false, rest = false;
	//TODO refer to Main.grid, also create one
	private Grid grid;
	private Vector3f[][][] gridXYZ;
	private int gridX, gridY, gridZ;
	private Vector3f destination = new Vector3f();
	private Vector3f loc = new Vector3f();
	//Used for glowing cichlid
	private boolean col = false;
	
	//TODO rename to more useful name
	private float time = 0;
	private boolean hasDestination = false;

	private Material mat;//temp
	private ColorRGBA glowColor;

	private Scenario scenario;
	private HashMap<Long,CichlidRelationships> currentRelationships;
	private float elapsed;
	private int randomTimeControl;
	private double shelterWeight;
	private EnvironmentObject shelterObject;

	/*
	 * Determines the aggression threshold requirement
	 */
	private static  double AGGRESSION_THRESHOLD = 1.998;

	/*
	 * These variables provide weights based on these attributes and what
	 * impact they have on interacting with other fish
	 */
	private static double DISTANCE_WEIGHT = 1.005;
	private static double SIZE_WEIGHT = 1.002;
	private static float SPEED_WEIGHT = 1;

	//fish is 10cm long, 4.5cm tall, 2.5cm wide in blender
	//the orge file seems to have scaled the model to 2 world units.

	//---------------------constructors--------------------------------
	/**
	 * Constructor for creating a cichlid object with default values.
	 */
	public Cichlid(){
		pSize = POSSIBLE_SIZES.SMALL;
		pColor = POSSIBLE_COLORS.BLACK;
		setSize(pSize.LENGTH_INCHES);
		init();
	}//end of default constructor

	/**
	 * Constructor for creating a cichlid object with the specified values 
	 * for size, speed, and sex.
	 * 
	 * @param size the size value for this cichlid.
	 * @param speed the speed value for this cichlid.
	 * @param sex the set value for this cichlid.
	 */
	public Cichlid(POSSIBLE_SIZES size, float speed, String sex){
		pSize = size;
		pColor = POSSIBLE_COLORS.BLACK;
		setSize(pSize.LENGTH_INCHES);
		init();
		setSpeed(speed);
		setSex(sex);
	}//end of (float,float,String) constructor

	/**
	 * Constructor for creating a cichlid object with the specified values 
	 * for size, speed, sex, and name.
	 * 
	 * @param size the size value for this cichlid.
	 * @param speed the speed value for this cichlid.
	 * @param sex the sex value for this cichlid.
	 * @param name the name for this cichlid
	 */
	public Cichlid(POSSIBLE_SIZES size, float speed, String sex, String name){
		pSize = size;
		pColor = POSSIBLE_COLORS.BLACK;
		setSize(pSize.LENGTH_INCHES);
		init();
		setSpeed(speed);
		setSex(sex);
		setName(name);
	}//end of (float,float,String,String) constructor

	//---------------------instance methods----------------------------
	//GETTERS

	public FishGhost getGhost(){
		return ghost;
	}//end of getGhost method

	public Node getNode(){
		return fish;
	}//end of getNode method
	public boolean isSprinting(){
		return sprint;
	}//end of isSprinting method
	
	public POSSIBLE_SIZES getPSize(){
		return pSize;
	}//end of getPSize method
	
	public POSSIBLE_COLORS getPColor(){
		return pColor;
	}//end of getPColor method

	//SETTERS
	public void setSize(POSSIBLE_SIZES size){
		if(size != null){
			pSize = size;
			setSize(pSize.LENGTH_INCHES);
			setDimensions();
		}
	}//end of setSize(POSSIBLE_SIZES) method
	
	public void setColor(POSSIBLE_COLORS color){
		if(color != null){
			pColor = color;
			setColor(pColor.COLOR);
			//TODO change texture
		}
	}//end of setColor(POSSIBLE_COLORS) method

	public void setSprint(boolean x){
		sprint = x;
	}//end of setSprint method

	public void setGlow(boolean glow){
		if(glow){
			mat.setColor("GlowColor", glowColor);
		}
		else{
			mat.setColor("GlowColor", ColorRGBA.Black);
		}
	}//end of setGlow method

	public void setGlowColor(ColorRGBA color){
		glowColor = color;
	}//end of setGlowColor method

	//OPERATIONS
	/**
	 * Initializes the values of this <code>Cichilid</code> object and 
	 * prepares it to be displayed in the environment.
	 */
	private void init(){
		//TODO ensure all variables are initialized here, even if their values are supposed to have been initialized in the constructor
		fish = new Node();
		currentRelationships = new HashMap<Long,CichlidRelationships>();
		
		setSpeed(1.5f + 2*Main.RNG.nextFloat());
//		setSize(1f);
		time = Main.RNG.nextFloat();
		setObj(Main.am.loadModel("Cichlid/Cube.mesh.xml"));
		Material cichlidMat = new Material(Main.am, 
				"Common/MatDefs/Misc/Unshaded.j3md");
		cichlidMat.setTexture("ColorMap",
				Main.am.loadTexture(new TextureKey("Cichlid/CichlidTextDark.jpg", false)));
		getObj().setMaterial(cichlidMat);
		mat = cichlidMat;
		glowColor = ColorRGBA.Yellow;
		setDimensions();

		fish.attachChild(getObj());

		//collision radius
		attachGhost();

		this.setScenario(Starter.getClient().getWorkingScenario());
		
		//this sets the starting random time interval for behavior decision
		randomTimeControl = Main.RNG.nextInt(10);
		
		//animation stuff
		control = getObj().getControl(AnimControl.class);
		control.addListener(this);
		channel = control.createChannel();
		channel.setAnim("Float", 2f);
		channel.setLoopMode(LoopMode.Loop);

		gridX = Main.RNG.nextInt(10);
		gridY = Main.RNG.nextInt(10);
		gridZ = Main.RNG.nextInt(10);
		
		grid = Main.getGrid();
		gridXYZ = grid.getGrid();
		destination = gridXYZ[gridX][gridY][gridZ];
		loc = destination;

	}//end of init method

	/**
	 * Adds the specified <code>AbstractControl</code> object to the 
	 * <code>Spatial</code> object representing this cichlid in the environment.
	 * 
	 * @param control the <code>AbstractControl</code> object to be added.
	 * @deprecated
	 */
	public void addControl(AbstractControl control){
		getObj().addControl(control);
	}//end of addControl method

	/**
	 * Calculates and sets the values for the dimensions of this 
	 * cichlid in the environment.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(this.getSize()); //TODO adapt this to account for the fish size
		float sizeFactor = worldUnitDepth / MODEL_DEPTH;
		getObj().setLocalScale(1.0f);
		getObj().scale(sizeFactor);
	}//end of setDimensions method
	
	/**
	 * This creates a mesh for the object and places it on top of the model.
	 * TODO Potenially remove - VASH
	 */
	private void attachGhost(){
		CollisionShape ghostShape = CollisionShapeFactory.createDynamicMeshShape(getObj());
		ghost = new FishGhost(ghostShape, this);
		//getObj().rotate(0, (float) (Math.PI/2), 0);
		getObj().addControl(ghost);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(ghost); //TODO convert to 
	}

	/**
	 * Moves the fish based on time per frame
	 */
	@Override
	public void move(float tpf){
		if (atLoc){
			getDestination();
			if (time > 0){
				time -= tpf;
				//slerpIt(tpf);
			}
			else if (time <= 0){
				atLoc = false;
			}
		}
		else {
			hasDestination = false;
			if (getGhost().getOverlappingCount() > 0){
				//TODO collision and decision stuff here
				avoid(tpf);
				this.behavioralMovement(tpf);
			}
			else moveToLoc(tpf, loc);
		}

	}//end of move method
	private void getDestination(){
		if (!hasDestination){
			time = Main.RNG.nextFloat();
			gridX = getNextPoint(gridX);
			gridY = getNextPoint(gridY);
			gridZ = getNextPoint(gridZ);
			loc = gridXYZ[gridX][gridY][gridZ];
			hasDestination = true;
		}
	}
	private void slerpIt(float tpf) {

		Quaternion result = getObj().getLocalRotation();
		Quaternion look = new Quaternion().IDENTITY;
		look.lookAt(loc, Vector3f.UNIT_Y);
		Quaternion test = new Quaternion().slerp(result, look, tpf*5);
		
		getObj().setLocalRotation(test);
		//getObj().rotate(0, (float) (Math.PI / 2), 0);
		System.out.println("Slerp called!");

	}
	/**
	 * Avoidance algorithm. Uses ghost to gather potential collision objects.
	 * Raycasts towards destination to detect possible collisions to avoid.
	 * @param tpf
	 */
	private void avoid(float tpf){
		Vector3f collisionPos = new Vector3f();
		CollisionResults results = new CollisionResults();
		Ray ray = new Ray(getNextLoc(tpf), loc);
		CollisionResult closest = new CollisionResult();
		Node collision = new Node();
		for (int i = 1; i <= getGhost().getOverlappingCount(); i++){ //Determines if anything is near by
			PhysicsCollisionObject p = getGhost().getOverlappingObjects().get(i-1);
			if (p instanceof FishGhost){
				FishGhost f = (FishGhost) getGhost().getOverlappingObjects().get(i-1);

				//if colliding with player
				if (f.getOwner() instanceof Player){
					moveAround(tpf, f.getOwner().getObj().getWorldTranslation());
				}
				//if colliding with other fish
				else{
					Spatial s = f.getOwner().getObj();
					collision.attachChild(s);
				}
			}
		}
		collision.collideWith(ray, results);
		if (results.size() > 0) {
			// The closest collision point is what was truly hit:
			closest = results.getClosestCollision();
			collisionPos = closest.getGeometry().getWorldTranslation();
			col = true;
		}
		else col = false;

		/**
		 * Need to add all spatials back to entity node to be rendered
		 */
		for (Spatial s : collision.getChildren()){
			Starter.getClient().getWorkingScenario().getEntityNode().attachChild(s);
		}
		if (col){
			this.setGlow(true);
			moveAround(tpf, collisionPos);
		}
		else if (!col){
			this.setGlow(false);
			moveToLoc(tpf, loc);
		}
	}
	/**
	 * This is the handler for behavioral movement. It receives the scenario object and handles iteration through all the objects
	 * in the tank and calls independent interaction methods for each item.
	 * @param Scenario scenario
	 */
	private void behavioralMovement(float tpf){
		
		//this decides what action to take for the next random amount of time.
		decision(tpf);
		
		if(this.getBehavior() == BEHAVIOR.ATTACK) {

			if(this.getTargetAggression() > AGGRESSION_THRESHOLD) {
				if(this.getTargetAggression() > getTargetFish().getTargetAggression()){
					//This fish object is more aggressive than his opponent
					getTargetFish().setBehavior(BEHAVIOR.RUN);
					getTargetFish().setTargetFish(this);
					this.attack(tpf);
				}
			}
		}
		
		else if(this.getBehavior() == BEHAVIOR.HIDE) {
			if(shelterWeight > 0){
				this.hide(shelterObject, tpf);
			}	
		}
		
		else if(this.getBehavior() == BEHAVIOR.RUN) {
			this.run(tpf);
		}
		
		else if(this.getBehavior() == BEHAVIOR.LOITER) {
			this.loiter(tpf);
		}
		
		else if(this.getBehavior() == BEHAVIOR.DART) {
			this.dart(tpf);
			
		}
		
	}


	/**
	 * This is where the Cichlid determines what his course of action will be. also this will be overridden if
	 * another fish attempts to attack him. it uses a random time interval between 0 and 10 seconds to 
	 * decide between Fish.BEHAVIOR options. if it is before the time limit is up, then the fish does not make a new 
	 * decision.
	 * @param tpf
	 */
	private void decision(float tpf) 
	{
		/*
		 * here we enter the loop based on a random amount of time and the fish decides what to do.
		 */
		if(elapsed >= tpf*randomTimeControl)
		{
			//reset the variables used for movement as well as the aggression level.
			elapsed = 0;
			setTargetAggression(0);
			setTargetFish(this);
			this.setSpeed(this.getBaseSpeed());
			
			int decision = Main.RNG.nextInt(2); //TODO change after these other actions are implemented
			if(decision == 0)
				this.setBehavior(BEHAVIOR.ATTACK);
			else if(decision == 1)
				this.setBehavior(BEHAVIOR.HIDE);
			else if(decision == 2)
				this.setBehavior(BEHAVIOR.DART);
			else if(decision == 3)
				this.setBehavior(BEHAVIOR.LOITER);
			
			//Iterate through the fish and determine the aggression level for each fish
			Iterator<Fish> itrF = scenario.getFish();
			while(itrF.hasNext()){
				Fish nextFish =itrF.next();
				if(this.getID() != nextFish.getID()){
					double nextAggression = fishInteract(nextFish);
					if(nextAggression > this.getTargetAggression() && nextAggression > AGGRESSION_THRESHOLD) {
						this.setTargetAggression(nextAggression);
						setTargetFish(nextFish);
					}
				}
			}
			this.setSpeed((float) (this.getSpeed() + ((this.getTargetAggression()*Main.RNG.nextFloat()))));
			
			//This has to be here so that it fish interaction occurs first and takes into account 
			Iterator<EnvironmentObject> itrO = scenario.getEnvironmentObjects();
			while(itrO.hasNext()){
				EnvironmentObject nextObject = itrO.next();
				double tempSheltWeight = calculateRelationships(nextObject).getRange();
				if(shelterWeight < tempSheltWeight)
				{
					shelterWeight = tempSheltWeight;
					shelterObject = nextObject;
				}
			}
			
			
			randomTimeControl = Main.RNG.nextInt(10);
		}
		else {
			elapsed++;
		}
		
	}

	/**
	 * This controls the fish action of darting
	 * @param tpf
	 */
	private void dart(float tpf) {
		// TODO Auto-generated method stub
		
		
		
		
		
	}

	private void loiter(float tpf) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * This Cichlid will hide near EnvironmentObjects
	 * @param shelterObject 
	 * @param tpf 
	 */
	private void hide(EnvironmentObject shelterObject, float tpf) {
		float xPos = this.getObj().getWorldTranslation().getX();
		float yPos = this.getObj().getWorldTranslation().getY();
		float zPos = this.getObj().getWorldTranslation().getZ();
		float xAvoid = this.getTargetFish().getObj().getWorldTranslation().getX();
		float yAvoid = this.getTargetFish().getObj().getWorldTranslation().getY();
		float zAvoid = this.getTargetFish().getObj().getWorldTranslation().getZ();
		float xShelter = shelterObject.getObj().getWorldTranslation().getX();
		float yShelter = shelterObject.getObj().getWorldTranslation().getY();
		float zShelter = shelterObject.getObj().getWorldTranslation().getZ();

		//here we are setting this fish as the origin and comparing two vectors to the other objects for manuever info
		float diffXAvoid = xAvoid - xPos;
		float diffYAvoid = yAvoid - yPos;
		float diffZAvoid = zAvoid - zPos;
		float diffXShelter = xShelter - xPos;
		float diffYShelter = yShelter - yPos;
		float diffZShelter = zShelter - zPos;
		Vector3f toAvoid = new Vector3f(diffXAvoid, diffYAvoid, diffZAvoid);
		Vector3f toShelter = new Vector3f(diffXShelter, diffYShelter, diffZShelter);
		float angle = toAvoid.angleBetween(toShelter);


		/*
		 * Here the fish will look to see if the shelter object and the opponent fish are 
		 * within a close proximity from its line of sight. It then checks to see if the pot is 
		 * closer than the opponent fish and if it is it attempts to hide behind it. If it is not, 
		 * it fails to attempt to hide behind the object.
		 */
		if(angle < Math.PI/4)
		{
			if(toShelter.length() < toAvoid.length())
			{


				if(shelterObject instanceof Pot)
				{
					loc = gridXYZ[(int)xShelter][(int)yShelter][(int)zShelter];
					moveToLoc(tpf, loc); 
				}
				else
				{
					int newPositionX = getHidePosition(xShelter, xAvoid, OBJECT_DISTANCE);
					int newPositionY = getHidePosition(yShelter, yAvoid, OBJECT_DISTANCE);
					int newPositionZ = getHidePosition(zShelter, zAvoid, OBJECT_DISTANCE);
					//here we increase the speed a little bit to encourage a more realistic scenario.
					loc = gridXYZ[newPositionX][newPositionY][newPositionZ];
					moveToLoc(tpf, loc); 
				}


			}
		}


	}

	/**
	 * Determines the new position for destination based on the two points passed to it
	 * with the distance from the object added to the total.
	 * NOTE: this is only in D1, must call 3 times for each XYZ coordinate
	 * @param shelter float
	 * @param avoid float
	 * @param distance float
	 * @return the point in D1 desired
	 */
	private int getHidePosition(float shelter, float avoid, float distance) 
	{
		float newPosition = shelter - avoid;
		if(newPosition < shelter)
			return (int)(shelter + distance);
		else
			return (int)(shelter - distance);
	}

	/**
	 * This Cichlid will attack the other Fish in the tank
	 * @param tpf 
	 */
	private void attack(float tpf) {
		float xPos = this.getObj().getWorldTranslation().getX();
		float yPos = this.getObj().getWorldTranslation().getY();
		float zPos = this.getObj().getWorldTranslation().getZ();
		float xAvoid = this.getTargetFish().getObj().getWorldTranslation().getX();
		float yAvoid = this.getTargetFish().getObj().getWorldTranslation().getY();
		float zAvoid = this.getTargetFish().getObj().getWorldTranslation().getZ();
		gridX = getDesiredPoint(xPos, xAvoid, gridX);
		gridY = getDesiredPoint(yPos, yAvoid, gridY);
		gridZ = getDesiredPoint(zPos, zAvoid, gridZ);
		//here we increase the speed a little bit to encourage a more realistic scenario.
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[gridX][gridY][gridZ];
		moveToLoc(tpf, loc);

	}

	/**
	 * This Cichlid will Run from the attacking fish in the tank. Pulled the avoiding point details from
	 * the target fish who is more aggressive that the running fish
	 * @param tpf 
	 */
	private void run(float tpf) {
		float xPos = this.getObj().getWorldTranslation().getX();
		float yPos = this.getObj().getWorldTranslation().getY();
		float zPos = this.getObj().getWorldTranslation().getZ();
		float xAvoid = this.getTargetFish().getObj().getWorldTranslation().getX();
		float yAvoid = this.getTargetFish().getObj().getWorldTranslation().getY();
		float zAvoid = this.getTargetFish().getObj().getWorldTranslation().getZ();
		gridX = getAvoidingPoint(xPos, xAvoid, gridX);
		gridY = getAvoidingPoint(yPos, yAvoid, gridY);
		gridZ = getAvoidingPoint(zPos, zAvoid, gridZ);
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[gridX][gridY][gridZ];
		moveToLoc(tpf, loc);

	}
	/**
	 * DO NOT CALL DIRECTLY: Use behavioralMovement() Handles the interations with other 
	 * fish via range with a weight, size with a weight, and speed with a weight
	 * @param Fish opponent
	 * TODO add tank temp to this calculation
	 */
	private double fishInteract(Fish opponent) {
		double aggression = 0;
		aggression = (1/calculateRelationships(opponent).getRange() * DISTANCE_WEIGHT);
		aggression = aggression + (this.getSize() / opponent.getSize() * SIZE_WEIGHT);	//TODO review weighting with changes to "size" system
		aggression = aggression + (this.getSpeed() / opponent.getSpeed() * SPEED_WEIGHT);
		if(!this.getSex().matches(opponent.getSex()))
			aggression = aggression*2; //This will accoint for different sex's with an attemot to mate
		aggression = aggression * calculateRelationships(opponent).getVisibility(); //here we account for visibility 0 is blocked, 100 is visible
		aggression = 2 - (1/aggression) ;
		//System.out.println("Aggression: " + aggression);
		return aggression;
	}


	/**
	 * Used by avoidance algorithm to find new destination and move
	 * @param tpf
	 * @param p point to avoid
	 */
	private void moveAround(float tpf, Vector3f p) {
		float xPos = this.getObj().getWorldTranslation().getX();
		float yPos = this.getObj().getWorldTranslation().getY();
		float zPos = this.getObj().getWorldTranslation().getZ();
		gridX = getAvoidingPoint(xPos, p.x, gridX);
		gridY = getAvoidingPoint(yPos, p.y, gridY);
		gridZ = getAvoidingPoint(zPos, p.z, gridZ);
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[gridX][gridY][gridZ];
		moveToLoc(tpf, loc);
	}
	/**
	 * Used by avoidance algorithm to determine relative positions of colliding fishes
	 * @param pos this objects own coordinate position
	 * @param avoid colliding fish's coordinate position
	 * @param g this objects position on the grid
	 * @return new position to move to, on the grid
	 */
	private int getAvoidingPoint(float pos, float avoid, int g){
		int size = grid.getSize();
		if (pos > avoid){
			g++;
			if (g >= size){
				g--;
			}
		}
		else {
			g--;
			if (g < 0){
				g++;
			}
		}
		return g;
	}
	/**
	 *	TODO Adjust name to more general one and better description
	 * Used by Desired algorithm to determine relative positions of colliding fishes
	 * @param pos this objects own coordinate position
	 * @param Desired Targets coordinate position
	 * @param g this objects position on the grid
	 * @return new position to move to, on the grid
	 */
	private int getDesiredPoint(float pos, float target, int g){
		int size = grid.getSize();
		if (target > pos){
			g++;
			if (g >= size){
				g--;
			}
		}
		else {
			g--;
			if (g < 0){
				g++;
			}
		}
		return g;
	}


	/**
	 * Used to find next destination on 3d grid
	 * @param x 
	 * @return 
	 */
	private int getNextPoint(int x) {
		boolean add = Main.RNG.nextBoolean();
		int size = grid.getSize();
		int limit = 5;
		if (add) {
			if (x >= size - limit){
				x -= (Main.RNG.nextInt(limit) + 1);
			}
			else x += (Main.RNG.nextInt(limit) + 1);
		}
		else {
			if (x <= limit){
				x =+ (Main.RNG.nextInt(limit) + 1);
			}
			else  x = x - (Main.RNG.nextInt(limit) + 1);
		}
		return x;
	}

	/**
	 * 
	 * @param tpf
	 * @param location
	 */
	private void moveToLoc(float tpf, Vector3f location){
		Quaternion rot = new Quaternion();
		rot.lookAt(location, Vector3f.UNIT_Y);
		getObj().lookAt(location, Vector3f.UNIT_Y);
		getObj().setLocalTranslation(getNextLoc(tpf));
		ghost.setPhysicsLocation(getObj().getWorldTranslation());

		float testX = getObj().getWorldTranslation().getX();
		float testY = getObj().getWorldTranslation().getY();
		float testZ = getObj().getWorldTranslation().getZ();
		float deltX = Math.abs(testX - location.x);
		float deltY = Math.abs(testY - location.y);
		float deltZ = Math.abs(testZ - location.z);

		if (deltX < .01 && deltY < 0.01 && deltZ < 0.01){
			atLoc = true;
		}
		//getObj().rotate(0, (float) (Math.PI/2), 0);
		ghost.setPhysicsRotation(getObj().getWorldRotation());
	}//end of moveToLoc method

	/**
	 * returns the visibility of a cichlid from this instance of a fish
	 * @param entity
	 * @return
	 */
	public CichlidRelationships calculateRelationships(Entity entity){
		CichlidRelationships returnValue = null;
		if(currentRelationships.containsKey(entity.getID())){
			returnValue = currentRelationships.get(entity.getID());
		}
		else{
			returnValue = new CichlidRelationships(this,entity);
			returnValue.setVisibility(visibilityFactor(entity));
			returnValue.setRange(range(entity));
		}
		return returnValue;
	}//end of calculateRelationships method

	/**
	 * Returns the range between this instance of a cichlid and the entity passed. Used by calculateRelationships
	 * @param entity
	 * @return
	 */
	private double range(Entity entity){
		double returnValue = 0;
		Vector3f loc = getObj().getLocalTranslation();
		Vector3f tar = entity.getObj().getLocalTranslation();
		returnValue = loc.distance(tar);
		return returnValue;
	}//end of range method

	/**
	 * This is used by calculateRelationships to determine if there is an object inbetween the 
	 * two entities and returns a vaisibility factor variable
	 * @param entity
	 * @return
	 */
	private int visibilityFactor(Entity entity){
		int returnValue = 0;
		Vector3f loc = getObj().getLocalTranslation();
		Vector3f tar = entity.getObj().getLocalTranslation();
		Vector3f viewVec = tar.subtract(loc);
		Ring ring = new Ring(loc,viewVec,0,0.001f);
		ArrayList<Ray> rayList = new ArrayList<Ray>();
		rayList.add(new Ray(loc,viewVec));
		for(int i=0; i<50; i++){
			Vector3f origin = ring.random();
			rayList.add(new Ray(origin,viewVec));
		}
		for(Ray ray : rayList){
			Scenario scenario = Starter.getClient().getWorkingScenario();
			CollisionResults results = new CollisionResults();
			Node entityNode = scenario.getEntityNode();
			entityNode.collideWith(ray, results);
			if(results.size() > 0){
				CollisionResult closest = results.getClosestCollision();
				String closestName = closest.getGeometry().getName();
				Entity closestEntity = scenario.getEntity(closestName);
				if(closestEntity.equals(entity)){
					returnValue++;
				}
				else if(closestEntity.equals(this)){
					Iterator<CollisionResult> it = results.iterator();
					Entity nextClosest = null;
					float nextClosestDist = Float.POSITIVE_INFINITY;
					while(it.hasNext()){
						CollisionResult collision = it.next();
						Entity colEntity = scenario.getEntity(collision.getGeometry().getName());
						if(!colEntity.equals(this)){
							if(nextClosestDist > collision.getDistance()){
								nextClosestDist = collision.getDistance();
								nextClosest = colEntity;
							}
						}
					}
					if(nextClosest == null || nextClosest.equals(entity)){
						returnValue++;
					}
				}
			}
			else{
				returnValue++;
			}
		}
		returnValue *= 2;
		return returnValue;
	}//end of visibilityFactor method

	/**
	 * Used to get next location to test before moving
	 * @param tpf
	 * @return next location
	 */

	private Vector3f getNextLoc(float tpf){
		Vector3f movement = new Vector3f();
		movement = new Vector3f(0,0,tpf*getSpeed()); //TODO add multiplier for fast forward
		Vector3f move = getObj().localToWorld(movement,movement);
		return move;
	}//end of getNextLoc method
	/**
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public void onAnimChange(AnimControl arg0, AnimChannel arg1, String arg2) {

	}//end of onAnimChange method

	/**
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public void onAnimCycleDone(AnimControl control, AnimChannel channel, String anim) {

	}//end of onAnimCycleDone method

	/**
	 * Reads in the data for this serializable object.
	 * 
	 * @param stream the input data to be read
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		init();
		//values for Spatial local rotation
		float rotX = stream.readFloat();
		float rotY = stream.readFloat();
		float rotZ = stream.readFloat();
		float rotW = stream.readFloat();
		Quaternion rot = new Quaternion(rotX, rotY, rotZ, rotW);
		//values for Spatial local scale
		float scaleX = stream.readFloat();
		float scaleY = stream.readFloat();
		float scaleZ = stream.readFloat();
		Vector3f scale = new Vector3f(scaleX, scaleY, scaleZ);
		//values for Spatial local translate
		float transX = stream.readFloat();
		float transY = stream.readFloat();
		float transZ = stream.readFloat();
		Vector3f trans = new Vector3f(transX, transY, transZ);
		//set Spatial transform
		Transform xform = new Transform(trans, rot, scale);
		getObj().setLocalTransform(xform);
		POSSIBLE_SIZES size = (POSSIBLE_SIZES)stream.readObject();
		this.setSize(size);
		POSSIBLE_COLORS color = (POSSIBLE_COLORS)stream.readObject();
		this.setColor(color);
	}//end of readObject method

	/**
	 * Writes the data from this object to an output stream.
	 * 
	 * @param stream the output to be written to
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		//values for Spatial local rotation
		stream.writeFloat(getObj().getLocalRotation().getX());
		stream.writeFloat(getObj().getLocalRotation().getY());
		stream.writeFloat(getObj().getLocalRotation().getZ());
		stream.writeFloat(getObj().getLocalRotation().getW());
		//values for Spatial local scale
		stream.writeFloat(getObj().getLocalScale().getX());
		stream.writeFloat(getObj().getLocalScale().getY());
		stream.writeFloat(getObj().getLocalScale().getZ());
		//values for Spatial local translate
		stream.writeFloat(getObj().getLocalTranslation().getX());
		stream.writeFloat(getObj().getLocalTranslation().getY());
		stream.writeFloat(getObj().getLocalTranslation().getZ());
		stream.writeObject(pSize);
		stream.writeObject(pColor);
	}//end of writeObject method

	/**
	 * Unused placeholder for object serialization.
	 * @throws ObjectStreamException
	 */
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method

	public void removeGhost() {
		getObj().removeControl(ghost);		
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	//Should only be called in the final phase of update in Main
	public void clearRelationships(){
		currentRelationships.clear();
	}//end of clearRelationships method


	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Cichlid class