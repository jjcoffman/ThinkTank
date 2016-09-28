package gameAssets;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.Random;
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
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
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
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.IMoving;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.scenario.Grid;

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
	private GhostControl ghost;
	private Node fish = null;
	private boolean sprint = false;
	CollisionShape fishShape;
	
	private Point3D destination = null;
	private boolean atLoc = false, rest = false;
	Random rng = new Random();
	private Grid grid;
	private Vector3f[][][] gridXYZ;
	
	private float time = 0;

	private int i, j, k;
	
	private Material mat;//temp
	
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
	
	public GhostControl getGhost(){
		return ghost;
	}//end of getGhost method
	
	public Node getNode(){
		return fish;
	}//end of getNode method
	
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
        
		//animation stuff
		control = getObj().getControl(AnimControl.class);
	    control.addListener(this);
	    channel = control.createChannel();
	    channel.setAnim("Float", 2f);
        channel.setLoopMode(LoopMode.Loop);
        
        
		i = rng.nextInt(10);
		j = rng.nextInt(10);
		k = rng.nextInt(10);
		destination = new Point3D(i, j, k);
		grid = Main.getGrid();
		gridXYZ = grid.getGrid();
        
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
		ghost = new GhostControl(ghostShape);
		setCollisionGroups(ghost);
		//getObj().rotate(0, (float) (Math.PI/2), 0);
		getObj().addControl(ghost);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(ghost);
	}
	
	/**
	 * This Sets the Collision Group parameters for the Cichlid Fish object and assigns it the proper collision 
	 * parameters to collide with other Fish, the Tank, and Plants.
	 * @param GhostControl
	 * @author Jonathan Coffman
	 */
	private void  setCollisionGroups(GhostControl ghost2) 
	{
		ghost2.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01); //collision group 1 is cichlid 
		ghost2.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_01); 
		ghost2.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02); //collision group 2 is Tank
		ghost2.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_03); //collsion group 3 are plants
	}
	
	/**
	 * 
	 * @param PhyisicsCollisionObject
	 * @param PhysicsCollisionObject 
	 * @return
	 */
	public boolean collide(PhysicsCollisionObject collider1, PhysicsCollisionObject collider2) {
		// TODO Auto-generated method stub
		
		
		//Cichlid Collides with Cichlid
		if((collider1.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_01) && 
				(collider2.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_01))
		{

		}

		//Cichlid Collides with Tank
		if((collider1.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_01) && 
				(collider2.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_02))
		{

		}

		//Cichlid Collides with Plants
		if((collider1.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_01) && 
				(collider2.getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_03))
		{
			
		}


		
		return false;
	}

	@Override
	public void move(float tpf){
		if (atLoc){
			if (time > 0){
				time -= tpf;
			}
			else if (time <= 0){
				time = rng.nextFloat();
				i = rng.nextInt(10);
				j = rng.nextInt(10);
				k = rng.nextInt(10);
				destination = new Point3D(i, j, k);
				atLoc = false;
			}
		}
		else {
			moveToLoc(tpf);
		}
	}//end of move method
	
	private void rotate(){
		
	}//end of rotate method
	
	private void moveToLoc(float tpf){
		Vector3f loc = gridXYZ[i][j][k];
		Quaternion rot = new Quaternion();
		rot.lookAt(loc, Vector3f.UNIT_Y);
		//fish.getWorldRotation().set(rot);
		getObj().lookAt(loc, Vector3f.UNIT_Y);
		ghost.setPhysicsLocation(getObj().getWorldTranslation());
		//fishControl.setPhysicsRotation(getObj().getLocalRotation());
		
		Vector3f movement = new Vector3f();
		movement = new Vector3f(0,0,tpf*getSpeed());
		Vector3f move = getObj().localToWorld(movement,movement);
		getObj().setLocalTranslation(move);
		
		///fishControl.setPhysicsLocation(getObj().getLocalTranslation());

		float testX = getObj().getWorldTranslation().getX();
		float testY = getObj().getWorldTranslation().getY();
		float testZ = getObj().getWorldTranslation().getZ();
		float deltX = Math.abs(testX - loc.x);
		float deltY = Math.abs(testY - loc.y);
		float deltZ = Math.abs(testZ - loc.z);
		
		if (deltX < .01 && deltY < 0.1 && deltZ < 0.1){
			atLoc = true;
		}
		getObj().rotate(0, (float) (Math.PI/2), 0);
		ghost.setPhysicsRotation(getObj().getWorldRotation());
		//fishControl.setPhysicsRotation(getObj().getLocalRotation());
	}//end of moveToLoc method

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

	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Cichlid class