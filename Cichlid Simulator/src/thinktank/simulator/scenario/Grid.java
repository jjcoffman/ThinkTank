package thinktank.simulator.scenario;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import javafx.geometry.Point3D;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;

/**
 * @author Vasher
 *
 * A grid system used by the game. Calculations are done using tank size.
 * Grid size is 10x10x10
 */
public class Grid {
	private static final int size = 20;
	private static List<EnvironmentObject> objs = new ArrayList();
	private static Vector3f[][][] gridXYZ = new Vector3f[size][size][size];

	private static float xIncr;
	private static float yIncr;
	private static float zIncr;
	
	public Grid(Scenario scenario) {
		initGrid(scenario.getEnvironment().getTank());
		
		while (scenario.getEnvironmentObjects().hasNext()){
			EnvironmentObject s = scenario.getEnvironmentObjects().next();
			objs.add(s);
		}
	}
	
	/**
	 * Takes tank and create a 10x10x10 grid based on depth, height, width
	 * @param tank
	 */
	private void initGrid(Tank tank){
		//implement tank variation
		float x = tank.getWorldUnitDepth() - .01f;
		float y = tank.getWolrdUnitHeight() - .01f;
		float z = tank.getWorldUnitWidth() - .01f;
		float negX = -x/2;
		float posX = x/2;
		float negZ = -z/2;
		float posZ = z/2;
		xIncr = x/size;
		yIncr = y/size;
		zIncr = z/size;
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				for (int k = 0; k < size; k++){
					gridXYZ[i][j][k] = new Vector3f(negX + i*xIncr, j*yIncr, negZ + k*zIncr);
				}
			}
		}
		//System.out.println(Arrays.deepToString(gridXYZ));
	}
	
	/**
	 * Adds object to a list and stores it's location
	 * @param obj
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
		if (obj.getName().contains("plant")){
			if (obj.getObj().getWorldBound() instanceof BoundingBox){
				BoundingBox y = (BoundingBox) obj.getObj().getWorldBound();
				objY = y.getYExtent()*2;
			}
		}
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				for (int k = 0; k < size; k++){
					Vector3f test = gridXYZ[i][j][k];
					if (objZ < test.getZ() + zIncr && objZ > test.getZ() - zIncr){
						Z = k;
					}
					if (objY < test.getY() + yIncr && objY > test.getY() - yIncr){
						Y = j;
					}
					if (objX < test.getX() + xIncr && objX > test.getX() - xIncr){
						X = i;
					}
				}
			}
		}
		System.out.print(obj.getName() + " is located at Grid: ");
		System.out.println(X + " " + Y + " " + Z);
	}
	
	public Vector3f[][][] getGrid(){
		return gridXYZ;
	}
	public float getXIncr(){
		return xIncr;
	}
	public float getYIncr(){
		return yIncr;
	}
	public float getZIncr(){
		return zIncr;
	}
	public int getSize(){
		return size;
	}
	public void setGrid (Tank tank){
		initGrid(tank);
	}

}
