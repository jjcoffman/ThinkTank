package thinktank.simulator.environment;
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
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

import Game.Main;
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Tank{
	//---------------------static constants----------------------------
	private static final float MODEL_DEPTH = 3.666458f;//x-axis
	private static final float MODEL_HEIGHT = 3.791440f;//y-axis
	private static final float MODEL_WIDTH = 11.158852f;//z-axis
	
	//---------------------static variables----------------------------
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Spatial tank;
	private TerrainQuad terrain;
	private Node node;
	private TANK_TYPE type;
	private float worldUnitDepth;//x-axis
	private float worldUnitHeight;//y-axis
	private float worldUnitWidth;//z-axis
	
	//---------------------constructors--------------------------------
	private Tank(){
//		super();
		node = new Node();
		tank = Main.am.loadModel("Tank.obj");
//		tank.setLocalScale(3);
		makeMap();
		tank.setQueueBucket(Bucket.Transparent);
		setType(TANK_TYPE.FIVE_GAL);
		node.attachChild(tank);
		node.attachChild(terrain);
//		node.setLocalScale(.5f); 
	}//end of default constructor
	
	private Tank(TANK_TYPE type){
//		super();
		node = new Node();
		tank = Main.am.loadModel("Tank.obj");
		tank.setLocalScale(3);
		makeMap();
		tank.setQueueBucket(Bucket.Transparent); 
		setType(type);
		node.attachChild(tank);
		node.attachChild(terrain);
		node.setLocalScale(.5f);
	}//end of (TANK_TYPE) constructor
	
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
	}
	
	public Node getNode(){
		return node;
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
	
	//SETTERS
	public void setSpatial(Spatial spac){
		tank = spac;
	}//end of setSpactial method
	
	public void setType(TANK_TYPE type){
		this.type = type;
//		setDimensions();
	}//end of setTYpe method
	
	public void setTerrain(TerrainQuad terrain){
		this.terrain = terrain;
	}
	
	//OPERATIONS
	private void makeMap(){
		Material terrainMat = new Material(Main.am, "Common/MatDefs/Terrain/Terrain.j3md");
		terrainMat.setTexture("Alpha", Main.am.loadTexture("Sand.jpg"));
		AbstractHeightMap heightmap = null;
		Texture heightmapImage = Main.am.loadTexture("terrain3.bmp");
		heightmap = new ImageBasedHeightMap(heightmapImage.getImage());
		heightmap.load();
		terrain = new TerrainQuad("tankBase", 65, 513, heightmap.getHeightMap());
		terrain.setMaterial(terrainMat);
		terrain.rotate(0, 3.14159f, 0);
		terrain.setLocalScale(0.00733333f, 0.00166667f, 0.0219166667f);//old .022, .005, .06575
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
		
		float depthFactor = worldUnitDepth / MODEL_DEPTH;
		float heightFactor = worldUnitHeight / MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / MODEL_WIDTH;
		node.setLocalScale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static Tank createTank(){
		return new Tank();
	}//end of createTank method
	
	public static Tank createTank(TANK_TYPE type){
		return new Tank(type);
	}//end of createTank method
}//end of Tank class