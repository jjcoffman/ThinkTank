package thinktank.simulator.scenario;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import javafx.geometry.Point3D;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;

public class Grid {
	
	private static List<EnvironmentObject> objs = new ArrayList();
	private static Vector3f[][][] gridXYZ = new Vector3f[10][10][10];

	private static float xIncr;
	private static float yIncr;
	private static float zIncr;
	
	public Grid(Scenario scenario) {
		initGrid(scenario.getEnvironment().getTank());
		
		while (scenario.getEnvironmentObjects().hasNext()){
			EnvironmentObject s = scenario.getEnvironmentObjects().next();
			objs.add(s);
		}
		System.out.println(objs);
	}


	private void initGrid(Tank tank){
		//implement tank variation
		float x = tank.getWorldUnitDepth() - .01f;
		float y = tank.getWolrdUnitHeight() - .01f;
		float z = tank.getWorldUnitWidth() - .01f;
		float negX = -x/2;
		float posX = x/2;
		float negZ = -z/2;
		float posZ = z/2;
		xIncr = x/10;
		yIncr = y/10;
		zIncr = z/10;

		
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				for (int k = 0; k < 10; k++){
					gridXYZ[i][j][k] = new Vector3f(negX + i*xIncr, j*yIncr, negZ + k*zIncr);
				}
			}
		}
		System.out.println(Arrays.deepToString(gridXYZ));
	}
	
	public static void update(EnvironmentObject obj) {
		objs.add(obj);
		System.out.println(obj.getLoc());

		boolean greaterX = false;
		boolean lesserX = false;
		boolean greaterZ = false;
		boolean lesserZ = false;
		int X = 0;
		int Y = 0;
		int Z = 0;
		float objX = obj.getLoc().getX();
		float objZ = obj.getLoc().getZ();
		BoundingBox y = (BoundingBox) obj.getObj().getWorldBound();
		float objY = y.getYExtent()*2;
		
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				for (int k = 0; k < 10; k++){
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

}
