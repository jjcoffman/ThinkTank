package thinktank.simulator.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.main.Main;

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
	/**
	 * Constant value for the table width (on the z-axis) to be resized to by default.
	 */
	private static final float TABLE_WIDTH_INCHES = 54.0f;
	/**
	 * Constant value for the table height (on the y-axis) to be resized to by default.
	 */
	private static final float TABLE_HEIGHT_INCHES = 30.0f;
	/**
	 * Constant value for the table depth (on the x-axis) to be resized to by default.
	 */
	private static final float TABLE_DEPTH_INCHES = 20.0f;
	/**
	 * Constant value for the default table depth (on the z-axis).
	 */
	public static final float TABLE_MODEL_WIDTH = 1f;
	/**
	 * Constant value for the default table depth (on the y-axis).
	 */
	public static final float TABLE_MODEL_HEIGHT = 1f;
	/**
	 * Constant value for the default table depth (on the x-axis).
	 */
	public static final float TABLE_MODEL_DEPTH = 1f; 
	public static final float[] POSSIBLE_TEMPS = {
				20.0f,
				20.5f,
				21.0f,
				21.5f,
				22.0f,
				22.5f,
				23.0f,
				23.5f,
				24.0f,
				24.5f,
				25.0f,
				25.5f,
				26.0f,
				26.5f,
				27.0f
			};
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * Unique number to identify a specific environment.
	 */
	private long id;
	/**
	 * Value for the temperature of the water in the tank in degrees Celcius.
	 */
	private float tempCelcius;
	/**
	 * Value for the current height of the table.
	 */
	private float tableHeight;
	/**
	 * The <code>Tank</code> object for the tank in this environment.
	 */
	private Tank tank;
	/**
	 * The root node for this environment. 
	 */
	private Node environNode;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default environment.
	 */
	public Environment(){
		id = Main.RNG.nextLong();
		tempCelcius = 0.0f;
		tank = null;
		tank = Tank.createTank(TANK_TYPE.FORTY_GAL_LONG);
		setup();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the id number of this environment.
	 * 
	 * @return the id number in this environment.
	 */
	public long getID(){
		return id;
	}//end of getID method

	/**
	 * Gets the temperature of this environment.
	 * 
	 * @return the temperature in this environment.
	 */
	public float getTempCelcius(){
		return tempCelcius;
	}//end of getTempCelcius method

	/**
	 * Gets the <code>Tank</code> object in this environment.
	 * 
	 * @return the tank in this environment.
	 */
	public Tank getTank(){
		return tank;
	}//end of getTank method

	/**
	 * Gets the root <code>Node</code> object for this environment.
	 * 
	 * @return the root node for this environment.
	 */
	public Node getEnvirionmentNode(){
		return environNode;
	}//end of getEnvirionmentNode method
	
	//SETTERS
	/**
	 * Sets the temperature of this environment to the specified value.
	 * 
	 * @param temp the value to which the environment's temperature is to be set.
	 */
	public void setTempCelcius(float temp){
		tempCelcius = temp;
	}//end of setTempCelcius method

	/**
	 * Sets the tank in this environment to the specified <code>Tank</code> object.
	 * 
	 * @param tank the tank to be used in this environment.
	 */
	public void setTank(Tank tank){
		if(this.tank != null){
			environNode.detachChild(this.tank.getNode());
		}
		this.tank = tank;
		if(this.tank != null){
			environNode.attachChild(this.tank.getNode());
		}
	}//end of setTank method
	
	//OPERATIONS
	/**
	 * Initializes the table model and the base entity <code>Node</code>, establishing 
	 * the appropriate attributes for each.
	 */
	private void setup(){
		Spatial table = Main.am.loadModel("Table/Table.obj");
		setTableDimensions(table);
		environNode = new Node();
		environNode.attachChild(table);
		environNode.attachChild(tank.getNode());
		table.setLocalTranslation(0, -tableHeight, 0);//sets table surface @ y=0
	}//end of setup method

	/**
	 * Scales the table model to the appropriate size.
	 * 
	 * @param table the table model to be scaled.
	 */
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

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * Two environments are considered equal if and only if their id numbers are equal.
	 * 
	 * @param obj the <code>Object</code> to compare.
	 * @return true if the objects are equal, false otherwise.
	 */
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

	/**
	 * The readObject method is responsible for reading from the stream and restoring 
	 * the fields of the class.
	 * 
	 * @param stream the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
		tank = Tank.createTank((TANK_TYPE)(stream.readObject()));
		tempCelcius = stream.readFloat();
		setup();
	}//end of readObject method

	/**
	 * The writeObject method is responsible for writing the state of the object 
	 * so that the corresponding readObject method can restore it.
	 * 
	 * @param stream the output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
		stream.writeObject(tank.getType());
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