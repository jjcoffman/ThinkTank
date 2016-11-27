package thinktank.simulator.scenario;

import java.util.ArrayList;
import java.util.List;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;

/**
 * @author Vasher
 *
 * A grid system used by the game. Calculations are done using tank size.
 * Grid size is 10x10x10
 */
public class Grid{
	//---------------------static constants----------------------------
	/**
	 * 
	 */
	private static final int SIZE = 20;
	
	//---------------------static variables----------------------------
	/**
	 * 
	 */
	private static List<EnvironmentObject> objs = new ArrayList<EnvironmentObject>();
	/**
	 * 
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
	 * 
	 * @param scenario
	 */
	public Grid(Scenario scenario){
		init(scenario.getEnvironment().getTank());
	}//end of constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public int getSize(){
		return SIZE;
	}//end of getSize method
	
	//SETTERS
	/**
	 * 
	 * @param tank
	 */
	public void setGrid(Tank tank){
		init(tank);
	}//end of setGrid method
	
	//OPERATIONS
	/**
	 * Takes tank and create a 10x10x10 grid based on depth, height, width
	 * @param tank
	 */
	private void init(Tank tank){
		//implement tank variation
		float x = tank.getWorldUnitDepth() - 0.01f;
		float y = tank.getWolrdUnitHeight() - 0.01f;
		float z = tank.getWorldUnitWidth() - 0.01f;
		float negX = -x / 2;
		float posX = x / 2;
		float negZ = -z / 2;
		float posZ = z / 2;
		xIncr = x / SIZE;
		yIncr = y / SIZE;
		zIncr = z / SIZE;
		
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				for (int k=0; k<SIZE; k++){
					gridXYZ[i][j][k] = new Vector3f(negX + i * xIncr, j * yIncr, negZ + k * zIncr);
				}
			}
		}
		//System.out.println(Arrays.deepToString(gridXYZ));
	}
	/**
	 * Adds object to a list and stores it's location
	 * @param obj
	 * 
	 */
	public static void update(EnvironmentObject obj) {
		//TODO store location of object
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
		System.out.print(obj.getName() + " is located at Grid: ");
		System.out.println(X + " " + Y + " " + Z);
	}//end of update method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Grid class