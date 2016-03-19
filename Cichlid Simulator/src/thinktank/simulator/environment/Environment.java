package thinktank.simulator.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import Game.Main;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;

/*****************************************************************************************
 * Class: Environment
 * Purpose: Inititates the environment and its attributes
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Environment implements Serializable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -7243775336191321202L;
	
	//---------------------static variables----------------------------
	//Table should be converted to dimensions of 54"(width) x 20"(depth) x 30"(height)
	private static final float TABLE_WIDTH_INCHES = 54.0f;
	private static final float TABLE_HEIGHT_INCHES = 30.0f;
	private static final float TABLE_DEPTH_INCHES = 20.0f;
	public static final float TABLE_MODEL_WIDTH = 15.509306f;
	public static final float TABLE_MODEL_HEIGHT = 3.187446f;
	public static final float TABLE_MODEL_DEPTH = 6.528506f; 
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private long id;
	private float tempCelcius;
	private float tableHeight;
	private Tank tank;
	private Node environNode;
	
	//---------------------constructors--------------------------------
	public Environment(){
		id = Main.RNG.nextLong();
		tempCelcius = 0.0f;
		tank = Tank.createTank();
		setup();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public long getID(){
		return id;
	}//end of getID method
	
	public float getTempCelcius(){
		return tempCelcius;
	}//end of getTempCelcius method
	
	public Tank getTank(){
		return tank;
	}//end of getTank method
	
	public Node getEnvirionmentNode(){
		return environNode;
	}//end of getEnvirionmentNode method
	
	//SETTERS
	public void setTempCelcius(float temp){
		tempCelcius = temp;
	}//end of setTempCelcius method
	
	public void setTank(Tank tank){
		this.tank = tank;
	}//end of setTank method
	
	//OPERATIONS
	private void setup(){
		Spatial table = Main.am.loadModel("Table.obj");
		setTableDimensions(table);
		environNode = new Node();
		environNode.attachChild(table);
		environNode.attachChild(tank.getNode());
		table.setLocalTranslation(0, -tableHeight, 0);//sets table surface @ y=0
	}//end of setup method

	private void setTableDimensions(Spatial table){
		float worldUnitDepth = Environment.inchesToWorldUnits(TABLE_DEPTH_INCHES);
		float worldUnitHeight = Environment.inchesToWorldUnits(TABLE_HEIGHT_INCHES);
		float worldUnitWidth = Environment.inchesToWorldUnits(TABLE_WIDTH_INCHES);
		float depthFactor = worldUnitDepth / TABLE_MODEL_DEPTH;
		float heightFactor = worldUnitHeight / TABLE_MODEL_HEIGHT;
		float widthFactor = worldUnitWidth / TABLE_MODEL_WIDTH;
		tableHeight = TABLE_MODEL_HEIGHT*heightFactor;
		table.scale(depthFactor, heightFactor, widthFactor);
	}//end of setDimensions method
	
	@Override
	public boolean equals(Object obj){
		boolean returnValue = false;
		if(obj instanceof Environment){
			if(((Environment)obj).getID() == id){
				returnValue = true;
			}
		}
		return returnValue;
	}//end of equals method
	
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
		//TODO setup tank. 
		tempCelcius = stream.readFloat();
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
//		stream.writeObject(tank.getType()); //Disabled until Tank can be properly read in.
		stream.writeFloat(tempCelcius);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns the value, in world units, of the value specified in inches.
	 * 
	 * Uses the conversion value of 1.0 world units = 0.0254 inches.
	 * This corresponds to the value used by the jmonkey physics calculations, 
	 * which is 1.0 meters = 1.0 world units.
	 * 
	 * @param inches the value to be converted from inches to world units.
	 * @return the converted value, in world units.
	 */
	public static float inchesToWorldUnits(float inches){
		float returnValue = -1.0f;
		if(inches > 0){
			returnValue = inches * 0.0254f;
		}
		return returnValue;
	}//end of inchesToWorldUnits method
}//end of Environment class