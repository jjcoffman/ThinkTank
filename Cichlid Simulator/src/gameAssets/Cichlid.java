package gameAssets;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.jme3.bullet.collision.PhysicsCollisionGroupListener;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
/*****************************************************************************************
 * Class: Cichlid
 * Purpose: Create the Cichlid objects and handle movement
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added addControl() method with spinning for testing obj movement
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Ring;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture;
import com.jme3.material.Material;

import Game.Main;
import gameAssets.strategies.IStrategy;
import javafx.geometry.Point3D;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.FishGhost;
import thinktank.simulator.entity.IMoving;
import thinktank.simulator.entity.Plant;
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
public class Cichlid extends Fish implements IMoving, PhysicsCollisionGroupListener{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8763564513637299079L;
	private static final float MODEL_DEPTH = 2f;//z-axis

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The behavior strategy used by this cichlid.
	 */
	private IStrategy strategy;
	/**
	 * 
	 */
	private AnimChannel channel;
	/**
	 * 
	 */
	private AnimControl control;
	/**
	 * 
	 */
	private RigidBodyControl fishControl;
	private FishGhost ghost;
	private Node fish = null;
	private boolean sprint = false;
	CollisionShape fishShape;
	private boolean atLoc = false, rest = false;
	Random rng = new Random();
	private Grid grid;
	private Vector3f[][][] gridXYZ;
	private Vector3f destination = new Vector3f();
	private Vector3f loc = new Vector3f();
	private Vector3f tempLoc = new Vector3f();
	private Vector3f viewDirection = new Vector3f();
	private boolean col = false;
	
	private float time = 0;

	private int i, j, k;
	
	private Material mat;//temp

	private Scenario scenario;
	
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
	public Cichlid(float size, float speed, String sex){
		init();
		setSpeed(speed);
		setSize(size);
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
	public Cichlid(float size, float speed, String sex, String name){
		init();
		setSpeed(speed);
		setSize(size);
		setSex(sex);
		setName(name);
	}//end of (float,float,String,String) constructor

	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Returns the <code>IStrategy</code> used by the cichlid to determine 
	 * its behavior.
	 * 
	 * @return the <code>IStrategy</code> used by the cichlid.
	 */
	public IStrategy getStrategy(){
		return strategy;
	}//end of getStrategy method
	
	public RigidBodyControl getPhysicsControl(){
		return fishControl;
	}//end of getPhysicsControl method
	
	public CollisionShape getShape(){
		return fishShape;
	}//end of getShape method
	
	public FishGhost getGhost(){
		return ghost;
	}//end of getGhost method
	
	public Node getNode(){
		return fish;
	}//end of getNode method
	
	public Vector3f getViewDirection(){
		return viewDirection;
	}//end of getViewDirection method
	
	public boolean isSprinting(){
		return sprint;
	}//end of isSprinting method
	
	//SETTERS
	/**
	 * Sets the <code>IStrategy</code> to be used by this cichlid
	 * to the specified object.
	 * 
	 * @param strat the object to use for this cichlid's behavior.
	 */
	public void setStrategy(IStrategy strat){
		strategy = strat;
	}//end of setStrategy method
	
	public void setSprint(boolean x){
		sprint = x;
	}//end of setSprint method
	
	public void setGlow(boolean glow){
		if(glow){
		    mat.setColor("GlowColor", ColorRGBA.Yellow);
		}
		else{
		    mat.setColor("GlowColor", ColorRGBA.Black);
		}
	}//end of setGlow method
	
	//OPERATIONS
	/**
	 * Initializes the values of this <code>Cichilid</code> object and 
	 * prepares it to be displayed in the environment.
	 */
	private void init(){
		fish = new Node();
		setSpeed(1.5f + 2*rng.nextFloat());
		setSize(1f);
		viewDirection = new Vector3f(0,0,50);
		strategy = null;
		time = rng.nextFloat();
		setObj(Main.am.loadModel("Cichlid/Cube.mesh.xml"));
		Material cichlidMat = new Material(Main.am, 
		        "Common/MatDefs/Misc/Unshaded.j3md");
		cichlidMat.setTexture("ColorMap",
				Main.am.loadTexture(new TextureKey("Cichlid/CichlidText.jpg", false)));
		getObj().setMaterial(cichlidMat);
		mat = cichlidMat;
		setDimensions();

        fish.attachChild(getObj());
		
        //physics
        //attachPhys();
		attachGhost();
        
		this.setScenario(Starter.getClient().getWorkingScenario());
		
		//animation stuff
		control = getObj().getControl(AnimControl.class);
	    control.addListener(this);
	    channel = control.createChannel();
	    channel.setAnim("Float", 2f);
        channel.setLoopMode(LoopMode.Loop);
        
        
		i = rng.nextInt(10);
		j = rng.nextInt(10);
		k = rng.nextInt(10);
		grid = Main.getGrid();
		gridXYZ = grid.getGrid();
		destination = gridXYZ[i][j][k];
		loc = destination;
        
	}//end of init method
	
	/**
	 * Adds the specified <code>AbstractControl</code> object to the 
	 * <code>Spatial</code> object representing this cichlid in the environment.
	 * 
	 * @param control the <code>AbstractControl</code> object to be added.
	 */
	public void addControl(AbstractControl control){
		getObj().addControl(control);
	}//end of addControl method
	
	/**
	 * Calculates and sets the values for the dimensions of this 
	 * cichlid in the environment.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(2.51969f);
		float sizeFactor = worldUnitDepth / MODEL_DEPTH;
		getObj().scale(sizeFactor);
	}//end of setDimensions method
	
	/**
	 * Create physic object for fish and attach it to Obj
	 */
	private void attachPhys(){
		fishShape = CollisionShapeFactory.createDynamicMeshShape(this.getObj());
		fishControl = new RigidBodyControl(fishShape, 1f);
		fishControl.setKinematic(false);
		fishControl.setAngularDamping(.9f);
		fishControl.setDamping(.9f, .9f);
		fishControl.setRestitution(0.0f);
		fishControl.setGravity(new Vector3f (0,-0.0001f,0));
		fishControl.setPhysicsRotation(fish.getLocalRotation());
		fishControl.setSleepingThresholds(0, 0);
		fishControl.setAngularFactor(0);
		getObj().addControl(fishControl);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(fishControl);
		fishControl.setPhysicsLocation(getObj().getWorldTranslation());
		//CollisionShape ghostShape = new CapsuleCollisionShape(1.2f, 3f);
		//ghost = new GhostControl(fishShape);
		//fish.addControl(ghost);
		fish.setLocalTranslation(0, 0, 0);
		//Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(ghost);

	}//end of attachPhys method
	
	private void attachGhost(){
		CollisionShape ghostShape = CollisionShapeFactory.createDynamicMeshShape(getObj());
		ghost = new FishGhost(ghostShape, this);
		setCollisionGroups(ghost);
		//getObj().rotate(0, (float) (Math.PI/2), 0);
		getObj().addControl(ghost);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(ghost);
	}

	@Override
	public void move(float tpf){
		if (atLoc){
			if (time > 0){
				time -= tpf;
			}
			else if (time <= 0){
				time = rng.nextFloat();
				i = getNextPoint(i);
				j = getNextPoint(j);
				k = getNextPoint(k);
				loc = gridXYZ[i][j][k];
				
				atLoc = false;
			}
		}
		else {
			if (getGhost().getOverlappingCount() > 0){
				//TODO collision and decision stuff here
		       avoid(tpf);
		      this.behavioralMovement(tpf);
			}
			else moveToLoc(tpf, loc);
		}
		
	}//end of move method
	
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
		for (int i = 1; i <= getGhost().getOverlappingCount(); i++){
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
		
		//reset the targetAggression level and the fish it may be targetting
		setTargetAggression(0);
		setTargetFish(this);
		Iterator<Fish> itrF = scenario.getFish();
		//Here determine which fish is a target fish. If none targetAggression will remain 0
		while(itrF.hasNext()){
			
			Fish nextFish =itrF.next();
			if(this.getID() != nextFish.getID()){
				double nextAggression = fishInteract(nextFish);
				if(nextAggression > this.getTargetAggression() && nextAggression > AGGRESSION_THRESHOLD) {
					this.setTargetAggression(nextAggression);
					setTargetFish(nextFish);
		}}}
		double shelterWeight = 0;
		EnvironmentObject shelterObject = null;
		
		Iterator<EnvironmentObject> itrO = scenario.getEnvironmentObjects();
		//This has to be here so that it fish interaction occurs first and takes into account 
		while(itrO.hasNext()){
			EnvironmentObject nextObject = itrO.next();
			shelterWeight = objectInteract(nextObject);
			if(shelterWeight > 0)
				shelterObject = nextObject;
		}
		
		/*
		 * Here we handle the interactions between the Fish. We comapare the two that are 
		 * the aggressors and invoke attack or run methods to hide
		 */
		float oldSpeed = this.getSpeed();
		if(this.getTargetAggression() > AGGRESSION_THRESHOLD){
			if(this.getTargetAggression() > getTargetFish().getTargetAggression()){
				//This fish object is more aggressive than his opponent
				this.attack(tpf);
			}
			else{
				//His opponent is more aggressive
				this.run(tpf);
			}
		}
		else if(this.getTargetAggression() < AGGRESSION_THRESHOLD){
			if(shelterWeight > 0){
				
				this.hide(shelterObject, tpf);
			}	
		}
		this.setSpeed(oldSpeed);
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
		
		float keepDistance = 0;
		int newPositionX, newPositionY, newPositionZ;
		
		if(shelterObject instanceof Plant)
			keepDistance = 30;
		else
			keepDistance = 10;
		
		newPositionX = getHidePosition(xShelter, xAvoid, keepDistance);
		newPositionY = getHidePosition(yShelter, yAvoid, keepDistance);
		newPositionZ = getHidePosition(zShelter, zAvoid, keepDistance);
		
		/*TODO
		 * 
		 * map a path to get the the location
		 * also account for when the fish arrives to sit still or move around the object
		 * 
		 */

		//here we increase the speed a little bit to encourage a more realistic scenario.
		this.setSpeed((float) (this.getSpeed() + ((this.getTargetAggression()*Math.random()))));
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[newPositionX][newPositionY][newPositionZ];
		moveToLoc(tpf, loc); //TEMPORARY
		
	}

	private int getHidePosition(float shelter, float avoid, float distance) 
	{
		float newPosition = shelter - avoid;
		if(newPosition > shelter)
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
		i = getDesiredPoint(xPos, xAvoid, i);
		j = getDesiredPoint(yPos, yAvoid, j);
		k = getDesiredPoint(zPos, zAvoid, k);
		//here we increase the speed a little bit to encourage a more realistic scenario.
		this.setSpeed((float) (this.getSpeed() + ((this.getTargetAggression()*Math.random()))));
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[i][j][k];
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
		i = getAvoidingPoint(xPos, xAvoid, i);
		j = getAvoidingPoint(yPos, yAvoid, j);
		k = getAvoidingPoint(zPos, zAvoid, k);
		//here we increase the speed a little bit to encourage a more realistic scenario.
		this.setSpeed((float) (this.getSpeed() + ((this.getTargetAggression()*Math.random()))));
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[i][j][k];
		moveToLoc(tpf, loc);
		
	}

	/**
	 * DO NOT CALL DIRECTLY: Use behavioralMovement() 
	 * Handles the interaction with the cichlid object and the fish.  
	 * @param EmvironmentObject next
	 * @return Double shelterWeight
	 */
	private double objectInteract(EnvironmentObject next) {
		double shelterWeight = 0;
		// TODO Needs to attract the fish without causing harm and the fish will remain close
		return shelterWeight;
	}
/**
 * DO NOT CALL DIRECTLY: Use behavioralMovement() Handles the interations with other 
 * fish via range with a weight, size with a weight, and speed with a weight
 * @param Fish opponent
 */
	private double fishInteract(Fish opponent) {
		double aggression = 0;
		aggression = (1/calculateRelationships(opponent).getRange() * DISTANCE_WEIGHT);
		aggression = aggression + (this.getSize() / opponent.getSize() * SIZE_WEIGHT);	
		aggression = aggression + (this.getSpeed() / opponent.getSpeed() * SPEED_WEIGHT);
		if(!this.getSex().matches(opponent.getSex()))
			aggression = aggression*2; //This will accoint for different sex's with an attemot to mate
		aggression = aggression * calculateRelationships(opponent).getVisibility(); //here we account for visibility 0 is blocked, 100 is visible
		aggression = 2 - (1/aggression) ;
		System.out.println("Aggression: " + aggression);
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
		i = getAvoidingPoint(xPos, p.x, i);
		j = getAvoidingPoint(yPos, p.y, j);
		k = getAvoidingPoint(zPos, p.z, k);
		/**
		 * Using loc overwrites the old destination
		 */
		loc = gridXYZ[i][j][k];
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
	 * Used by Desited algorithm to determine relative positions of colliding fishes
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
		boolean add = rng.nextBoolean();
		int size = grid.getSize();
		int limit = 5;
		if (add) {
			if (x >= size - limit){
				x -= (rng.nextInt(limit) + 1);
			}
			else x += (rng.nextInt(limit) + 1);
		}
		else {
			if (x <= limit){
				 x =+ (rng.nextInt(limit) + 1);
			}
			else  x = x - (rng.nextInt(limit) + 1);
		}
		return x;
	}

	private void rotate(){
		
	}//end of rotate method
	
	private void moveToLoc(float tpf, Vector3f location){
		Quaternion rot = new Quaternion();
		rot.lookAt(location, Vector3f.UNIT_Y);
		//fish.getWorldRotation().set(rot);
		getObj().lookAt(location, Vector3f.UNIT_Y);
		getObj().setLocalTranslation(getNextLoc(tpf));
		ghost.setPhysicsLocation(getObj().getWorldTranslation());
		//fishControl.setPhysicsRotation(getObj().getLocalRotation());
		/*
		Vector3f movement = new Vector3f();
		movement = new Vector3f(0,0,tpf*getSpeed());
		Vector3f move = getObj().localToWorld(movement,movement);
		*/
		
		///fishControl.setPhysicsLocation(getObj().getLocalTranslation());

		float testX = getObj().getWorldTranslation().getX();
		float testY = getObj().getWorldTranslation().getY();
		float testZ = getObj().getWorldTranslation().getZ();
		float deltX = Math.abs(testX - location.x);
		float deltY = Math.abs(testY - location.y);
		float deltZ = Math.abs(testZ - location.z);
		
		if (deltX < .01 && deltY < 0.01 && deltZ < 0.01){
			atLoc = true;
		}
		getObj().rotate(0, (float) (Math.PI/2), 0);
		ghost.setPhysicsRotation(getObj().getWorldRotation());
		//fishControl.setPhysicsRotation(getObj().getLocalRotation());
		viewDirection = new Vector3f(deltX, deltY, deltZ);
	}//end of moveToLoc method
	
	public CichlidRelationships calculateRelationships(Entity entity){
		CichlidRelationships returnValue = new CichlidRelationships(this,entity);
		returnValue.setVisibility(visibilityFactor(entity));
		returnValue.setRange(range(entity));
		return returnValue;
	}//end of calculateRelationships method
	
	//TODO make private after testing
	public double range(Entity entity){
		double returnValue = 0;
		Vector3f loc = getObj().getLocalTranslation();
		Vector3f tar = entity.getObj().getLocalTranslation();
		returnValue = loc.distance(tar);
		return returnValue;
	}//end of range method
	
	//TODO make private after testing
	public int visibilityFactor(Entity entity){
//		System.out.println("target = "+((Fish)entity).getName());
//		System.out.println("this = "+this.getName());
		int returnValue = 0;
		Vector3f loc = getObj().getLocalTranslation();
		Vector3f tar = entity.getObj().getLocalTranslation();
		Vector3f viewVec = tar.subtract(loc);
		Ring ring = new Ring(loc,viewVec,0,0.001f);
		ArrayList<Ray> rayList = new ArrayList<Ray>();
		rayList.add(new Ray(loc,viewVec));
		for(int i=0; i<100; i++){
			Vector3f origin = ring.random();
			rayList.add(new Ray(origin,viewVec));
		}
		for(Ray ray : rayList){
			Scenario scenario = Starter.getClient().getWorkingScenario();
			CollisionResults results = new CollisionResults();
			Node entityNode = scenario.getEntityNode();
			entityNode.collideWith(ray, results);
//			System.out.print(results.size()+"[");
    		if(results.size() > 0){
        		CollisionResult closest = results.getClosestCollision();
        		String closestName = closest.getGeometry().getName();
        		System.out.print(closestName);
        		Entity closestEntity = scenario.getEntity(closestName);
        		if(closestEntity.equals(entity)){
        			returnValue++;
        		}
        		else if(closestEntity.equals(this)){
        			Iterator<CollisionResult> it = results.iterator();
        			Entity nextClosest = null;
        			float nextClosestDist = Float.POSITIVE_INFINITY;
        			while(it.hasNext()){
        				//TODO find closest that isn't this
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
//    		System.out.print("], ");
		}
//		System.out.println();
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



	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Cichlid class