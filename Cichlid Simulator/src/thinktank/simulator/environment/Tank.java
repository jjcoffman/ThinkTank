package thinktank.simulator.environment;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/*****************************************************************************************
 * Class: Tank
 * Purpose: Inititates the tank object
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

import Game.Main;
import thinktank.simulator.Starter;
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */

public class Tank{
	//---------------------static constants----------------------------
	private static final float MODEL_DEPTH = 1; //x-axis
	private static final float MODEL_HEIGHT = 1; //y-axis
	private static final float MODEL_WIDTH = 1; //z-axis
	
	//tank is 1m x 1m x 1m by default
	
	//---------------------static variables----------------------------
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Spatial tank;
	private TerrainQuad terrain;
	private Node tankNode, terrainNode;
	private RigidBodyControl tankControl;
	private TANK_TYPE type;
	private float worldUnitDepth;//x-axis
	private float worldUnitHeight;//y-axis
	private float worldUnitWidth;//z-axis
	private float depthFactor;
	private float heightFactor;
	private float widthFactor;
	private float x, y, z;
	
	//---------------------constructors--------------------------------
	private Tank(){
		tankNode = new Node();
		tank = Main.am.loadModel("Tank/Tank_clear.obj");
		makeMap();
		tankNode.attachChild(tank);
		tankNode.attachChild(terrainNode);
		setType(TANK_TYPE.FIFTY_GAL);
		makePhys();
		Vector3f loc = tank.getWorldTranslation();
		x = loc.x + depthFactor/2;
		y = loc.y + heightFactor;
		z = loc.z + widthFactor/2;
		System.out.println(loc);
	}//end of default constructor

	private Tank(TANK_TYPE type){
		
		tankNode = new Node();
		tank = Main.am.loadModel("Tank/Tank_clear.obj");
		makeMap();
		tankNode.attachChild(tank);
		tankNode.attachChild(terrainNode);
		setType(type);
		//makePhys();
		Vector3f loc = tank.getWorldTranslation();
		x = loc.x + depthFactor/2;
		y = loc.y + heightFactor;
		z = loc.z + widthFactor/2;
		System.out.println(loc);
	}//end of (TANK_TYPE) constructor
	
	
	private void makePhys() {
		CollisionShape tankShape = CollisionShapeFactory.createMeshShape(tankNode);
		RigidBodyControl tankControl = new RigidBodyControl(tankShape, 0);
		tank.addControl(tankControl);
	    Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(tankControl);
	    
	    //CollisionShape terrainShape = CollisionShapeFactory.createMeshShape(terrainNode);
	    //RigidBodyControl terrainControl = new RigidBodyControl(terrainShape, 0);
	    //terrain.addControl(terrainControl);
	    //Starter.getClient().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(terrainControl);
	}
	//---------------------instance methods----------------------------
	//GETTERS
	public Spatial getSpatial(){
		return tank;
	}//end of getSpatial method
	
	public TANK_TYPE getType(){
		return type;
	}//end of getType method
	
	public TerrainQuad getTerrain(){
		return terrain;
	}//end of getTerrain method
	
	public Node getNode(){
		return tankNode;
	}//end of getNode method
	
	public float getWorldUnitDepth(){
		return worldUnitDepth;
	}//end of getWorldUnitDepth method
	
	public float getWolrdUnitHeight(){
		return worldUnitHeight;
	}//end of getWorldUnitHeight method
	
	public float getWorldUnitWidth(){
		return worldUnitWidth;
	}//end of getWorldUnitWidth method
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getZ(){
		return z;
	}
	
	//SETTERS
	public void setSpatial(Spatial spac){
		tank = spac;
	}//end of setSpactial method
	
	public void setType(TANK_TYPE type){
		this.type = type;
		setDimensions();
	}//end of setTYpe method
	
	public void setTerrain(TerrainQuad terrain){
		this.terrain = terrain;
	}//end of setTerrain method
	
	//OPERATIONS
	private void makeMap(){
		terrainNode = new Node();
		Material terrainMat = new Material(Main.am, "Common/MatDefs/Terrain/Terrain.j3md");
		terrainMat.setTexture("Alpha", Main.am.loadTexture("Terrain/Sand.jpg"));
		AbstractHeightMap heightmap = null;
		Texture heightmapImage = Main.am.loadTexture("Terrain/terrain3.bmp");
		heightmap = new ImageBasedHeightMap(heightmapImage.getImage());
		heightmap.load();
		terrain = new TerrainQuad("tankBase", 65, 513, heightmap.getHeightMap());
		terrain.setMaterial(terrainMat);
		terrain.rotate(0, 3.14159f, 0);
		terrain.setLocalScale(0.0019f, 0.00025f, 0.001925f);
		terrainNode.attachChild(terrain);
		
	}//end of makeMap method

	/**
	 * Scales the model based on the TANK_TYPE and the base model dimensions 
	 * defined in the constants <code>MODEL_WIDTH</code>, <code>MODEL_HEIGHT</code>, 
	 * and <code>MODEL_DEPTH</code>.
	 */
	private void setDimensions(){
		worldUnitDepth = Environment.inchesToWorldUnits(type.DEPTH);
		worldUnitHeight = Environment.inchesToWorldUnits(type.HEIGHT);
		worldUnitWidth = Environment.inchesToWorldUnits(type.WIDTH);
		
		depthFactor = worldUnitDepth / MODEL_DEPTH;
		heightFactor = worldUnitHeight / MODEL_HEIGHT;
		widthFactor = worldUnitWidth / MODEL_WIDTH;
		tankNode.setLocalScale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		setType((TANK_TYPE)(stream.readObject()));
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeObject(type);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Tank createTank(){

		return new Tank();
	}//end of createTank method
	
	public static Tank createTank(TANK_TYPE type){
		return new Tank(type);
	}//end of createTank method
	
}//end of Tank class