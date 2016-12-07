package thinktank.simulator.scenario;

import java.util.ArrayList;
import java.util.List;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;

/**
 * A grid system used by the game. Calculations are done using tank size.
 * Grid size is 10x10x10
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 */
public class Grid{
	//---------------------static constants----------------------------
	/**
	 * Constant value for the size of the grid
	 */
	private static final int SIZE = 20;
	
	//---------------------static variables----------------------------
	/**
	 * List of environment objects in the grid.
	 */
	private static List<EnvironmentObject> objs = new ArrayList<EnvironmentObject>();
	/**
	 * Multi-dimensional array of vectors representing the grid.
	 */
	private static Vector3f[][][] gridXYZ = new Vector3f[SIZE][SIZE][SIZE];
	/**
	 * 
	 */
	private static float xIncr = 0;
	/**
	 * 
	 */
	private static float yIncr = 0;
	/**
	 * 
	 */
	private static float zIncr = 0;
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Constructs a new <code>Grid</code> object for the specified scenario.
	 * 
	 * @param scenario the scenario to build the grid for.
	 */
	public Grid(Scenario scenario){
		init(scenario.getEnvironment().getTank());
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the vector representation of the grid.
	 * 
	 * @return the vectors of the grid.
	 */
	public Vector3f[][][] getGrid(){
		return gridXYZ;
	}//end of getGrid method
	
	/**
	 * 
	 * @return
	 */
	public float getXIncr(){
		return xIncr;
	}//end of getXIncr method
	
	/**
	 * 
	 * @return
	 */
	public float getYIncr(){
		return yIncr;
	}//end of getYIncr method
	
	/**
	 * 
	 * @return
	 */
	public float getZIncr(){
		return zIncr;
	}//end of getZIncr method
	
	/**
	 * Returns the value for the size of the grid.
	 * 
	 * @return the size of the grid.
	 */
	public int getSize(){
		return SIZE;
	}//end of getSize method
	
	//SETTERS
	/**
	 * Sets the values of the grid based on the specified tank.
	 * 
	 * @param tank the tank to use for seting the grid's values.
	 */
	public void setGrid(Tank tank){
		init(tank);
	}//end of setGrid method
	
	//OPERATIONS
	/**
	 * Takes tank and creates a 10x10x10 grid based on depth, height, width.
	 * 
	 * @param tank the tank to base the grid values on.
	 */
	private void init(Tank tank){
		//implement tank variation
		float x = tank.getWorldUnitDepth() - 0.01f;
		float y = tank.getWolrdUnitHeight() - 0.01f;
		float z = tank.getWorldUnitWidth() - 0.01f;
		float negX = -x / 2;
		float negZ = -z / 2;
		float zeroY = tank.getTerrain().getHeight(Vector2f.ZERO);
		System.out.println(zeroY);
		xIncr = x / SIZE;
		yIncr = y / SIZE;
		zIncr = z / SIZE;
		
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				for (int k=0; k<SIZE; k++){
					gridXYZ[i][j][k] = new Vector3f(negX + i * xIncr, zeroY + j * yIncr, negZ + k * zIncr);
				}
			}
		}
	}//end of init method
	
	/**
	 * Adds object to a list and stores it's location.
	 * 
	 * @param obj the object to store.
	 */
	public static void update(EnvironmentObject obj) {
		objs.add(obj);
		int X = 0;
		int Y = 0;
		int Z = 0;
		float objX = obj.getLoc().getX();
		float objY = obj.getLoc().getY();
		float objZ = obj.getLoc().getZ();
		if(obj.getName().contains("plant")){
			if(obj.getObj().getWorldBound() instanceof BoundingBox){
				BoundingBox bound = (BoundingBox)obj.getObj().getWorldBound();
				objY = bound.getYExtent() * 2;
			}
		}
		
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				for(int k=0; k<SIZE; k++){
					Vector3f testVector = gridXYZ[i][j][k];
					if(objZ < testVector.getZ() + zIncr && objZ > testVector.getZ() - zIncr){
						Z = k;
					}
					if(objY < testVector.getY() + yIncr && objY > testVector.getY() - yIncr){
						Y = j;
					}
					if(objX < testVector.getX() + xIncr && objX > testVector.getX() - xIncr){
						X = i;
					}
				}
			}
		}
	}//end of update method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Grid class