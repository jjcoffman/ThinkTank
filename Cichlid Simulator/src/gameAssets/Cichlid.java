package gameAssets;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

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
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture;
import com.jme3.material.Material;

import Game.Main;
import gameAssets.strategies.IStrategy;
import thinktank.simulator.Starter;
import thinktank.simulator.controllers.CichlidController;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.IMoving;
import thinktank.simulator.environment.Environment;

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
	private static final long serialVersionUID = 8763564513637299079L;
	private static final float MODEL_DEPTH = 2f;//z-axis
	
	//fish is 10cm long, 4.5cm tall, 2.5cm wide in blender
	//the orge file seems to have scaled the model to 2 world units.

	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * The value representing the gender of the cichlid.
	 */
	private String sex;
	/**
	 * The value representing the name of the cichlid.
	 */
	private String name;
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
	private CichlidController cc;
	private RigidBodyControl fishControl;
	private BetterCharacterControl bcc;
	private GhostControl ghost;
	private Node fish = null;

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
	 * @param size the size value for the cichlid.
	 * @param speed the speed value for the cichlid.
	 * @param sex the set value for the cichlid.
	 */
	public Cichlid(float size, float speed, String sex){
		init();
		setSpeed(speed);
		setSize(size);
		this.sex = sex; 
	}//end of (float,float,String) constructor

	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Returns the sex of the cichlid.
	 * 
	 * @return the sex of the cichlid.
	 */
	public String getSex(){
		return sex;
	}//end of getSex method

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
	
	public BetterCharacterControl getbcc(){
		return bcc;
	}//end of getPhysicsControl method
	
	public GhostControl getGhost(){
		return ghost;
	}
	public Node getNode(){
		return fish;
	}
	//SETTERS
	/**
	 * Sets the sex of this cichlid to the specified value.
	 * 
	 * @param sex the value to which the sex is to be set.
	 */
	public void setSex(String sex){
		this.sex = sex;
	}//end of setSex method
	
	/**
	 * Sets the <code>IStrategy</code> to be used by this cichlid
	 * to the specified object.
	 * 
	 * @param strat the object to use for this cichlid's behavior.
	 */
	public void setStrategy(IStrategy strat){
		strategy = strat;
	}//end of setStrategy method
	
	/**
	 * Sets the name of this cichlid to the specified value.
	 * 
	 * @param name the value to which the name is to be set.
	 */
	public void setName(String name){
		this.name = name;
	}//end of setName method
	
	//OPERATIONS
	/**
	 * Initializes the values of this <code>Cichilid</code> object and 
	 * prepares it to be displayed in the environment.
	 */
	private void init(){
		fish = new Node();
		setSpeed(1f);
		sex = "male";
		setSize(1f);
		strategy = null;
		setObj(Main.am.loadModel("Cichlid/Cube.mesh.xml"));
		Material cichlidMat = new Material(Main.am, 
		        "Common/MatDefs/Misc/Unshaded.j3md");
		cichlidMat.setTexture("ColorMap",
				Main.am.loadTexture(new TextureKey("Cichlid/CichlidText.jpg", false)));
		getObj().setMaterial(cichlidMat);
		//getObj().setLocalTranslation(Environment.inchesToWorldUnits(2f), Environment.inchesToWorldUnits(4f), Environment.inchesToWorldUnits(1f));
		setDimensions();

        fish.attachChild(getObj());
		
		//physics
		CollisionShape fishShape = CollisionShapeFactory.createMeshShape(this.getObj());
		fishControl = new RigidBodyControl(fishShape, 0);
		fishControl.setKinematic(true);
		fishControl.setDamping(1, 1);
		fishControl.setGravity(new Vector3f (0,-.0000000001f,0));
		fishControl.setPhysicsRotation(fish.getWorldRotation());
		fish.addControl(fishControl);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(fishControl);
		
		ghost = new GhostControl(CollisionShapeFactory.createMeshShape(this.getObj()));
		fish.addControl(ghost);
		Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(ghost);

		//animation stuff
		control = getObj().getControl(AnimControl.class);
	    control.addListener(this);
	    channel = control.createChannel();
	    channel.setAnim("Float", 2f);
        channel.setLoopMode(LoopMode.Loop);
        
        fish.rotate(0, (float) (3.14/2), 0);
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

	/**
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public void onAnimChange(AnimControl arg0, AnimChannel arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public void onAnimCycleDone(AnimControl control, AnimChannel channel, String anim) {
	}

	/**
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public void move(double delta) {
		
	}
	

	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Cichlid class