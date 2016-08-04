package thinktank.simulator.scenario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;

public class Grid {
	
	private static List<EnvironmentObject> objs = new ArrayList();
	
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
		float x = tank.getNode().getLocalScale().getX() - .001f;
		float y = tank.getNode().getLocalScale().getY() - .001f;
		float z = tank.getNode().getLocalScale().getZ() - .001f;
		System.out.println("Tank Scales");
		System.out.println(x + " " + y + " " + z);
		float negX = -x/2;
		float posX = x/2;
		float negZ = -z/2;
		float posZ = z/2;
		float xIncr = x/10;
		float yIncr = y/10;
		float zIncr = z/10;
		
		int grid[][][] = new int[10][10][10];
		for (int i = 0; i < 10; i++){
			
		}
	}

	public static void update(EnvironmentObject obj) {
		objs.add(obj);
		System.out.println(obj.getLoc());
	}

}
