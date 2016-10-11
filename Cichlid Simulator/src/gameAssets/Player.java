package gameAssets;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;

/*****************************************************************************************
 * Class: Player
 * Purpose: Create a player instance of a Cichlid
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
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Line;

import Game.Main;
import Game.Main.CAM_MODE;
import thinktank.simulator.Starter;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.scenario.Scenario;

public class Player extends Cichlid
{
	private static final long serialVersionUID = 4038460719382327559L;
	static private Player player;  //singleton
	private static Node node = null;
	private CameraNode cam;
	private GhostControl ghost;

    private static Vector3f camDir = new Vector3f();
    private static Vector3f camLeft = new Vector3f();
    private boolean left = false, right = false, up = false, down = false,
    		forward = false, backward = false, ascend = false, descend = false;
    private boolean collision = false;
    private boolean isHiding = false;
    private boolean wantsToHide = false;
    private boolean canHide = false;
    private float deg = (float) (Math.PI/2);
    private float pitch;
    private Node collidables;
    private Tank tank;
    private Ray rayForward;
    private Ray rayBackward;
    private Ray rayLeft;
    private Ray rayRight;
    private Material red;
    private Material green;
    private Material blue;
    private Material yellow;
    private Line line1;
    private Geometry ray1;
    private Line line2;
    private Geometry ray2;
    private Line line3;
    private Geometry ray3;
    private Line line4;
    private Geometry ray4;
	private ArrayList<Ray> rayList = new ArrayList<Ray>();
	
	private Player(float size, float speed, String sex)
	{
		super(size, speed, sex);
		node.attachChild(super.getNode());
		node.rotate(0, (float) (Math.PI/2), 0);
		super.setName("Player");
		tank = Starter.getClient().getWorkingScenario().getEnvironment().getTank();
		
		debugStuff();
	}
	
	//temp visual aid for debugging
	private void debugStuff() {
		red = new Material(Main.am, "Common/MatDefs/Misc/Unshaded.j3md");
		red.setColor("Color", ColorRGBA.Red);
		green = new Material(Main.am, "Common/MatDefs/Misc/Unshaded.j3md");
		green.setColor("Color", ColorRGBA.Green);
		blue = new Material(Main.am, "Common/MatDefs/Misc/Unshaded.j3md");
		blue.setColor("Color", ColorRGBA.Blue);
		yellow = new Material(Main.am, "Common/MatDefs/Misc/Unshaded.j3md");
		yellow.setColor("Color", ColorRGBA.Yellow);
		makeRays();
	}

	private void makeRays() {
		rayList.clear();
		Vector3f forwardVec = getForwardVec();
		rayForward = new Ray(getObj().getWorldTranslation(), forwardVec);
		line1 = new Line(getObj().getWorldTranslation(), forwardVec);
		ray1 = new Geometry("ray1", line1);
		ray1.setMaterial(red);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray1);
		
		Vector3f backwardVec = getBackwardVec();
		rayBackward = new Ray(getObj().getWorldTranslation(), backwardVec);
		line2 = new Line(getObj().getWorldTranslation(), backwardVec);
		ray2 = new Geometry("ray2", line2);
		ray2.setMaterial(green);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray2);
		
		Vector3f leftVec = getLeftVec();
		rayLeft = new Ray(getObj().getWorldTranslation(), leftVec);
		line3 = new Line(getObj().getWorldTranslation(), leftVec);
		ray3 = new Geometry("ray3", line3);
		ray3.setMaterial(blue);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray3);
		
		Vector3f rightVec = getRightVec();
		rayRight = new Ray(getObj().getWorldTranslation(), rightVec);
		line4 = new Line(getObj().getWorldTranslation(), rightVec);
		ray4 = new Geometry("ray4", line4);
		ray4.setMaterial(yellow);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray4);
		
		rayList.add(rayForward);
		rayList.add(rayBackward);
		rayList.add(rayLeft);
		rayList.add(rayRight);
	}
	private Vector3f getLeftVec() {
		Vector3f vec = camLeft.normalize();
		//vec.setY(getNode().getWorldTranslation().getY());
		return vec;
	}
	private Vector3f getRightVec() {
		Vector3f vec = camLeft.normalize().negate();
		//vec.setY(getNode().getWorldTranslation().getY());
		return vec;
	}
	private Vector3f getForwardVec() {
		Vector3f vec = camDir.normalize();
		//vec.setY(getNode().getWorldTranslation().getY());
		return vec;
	}
	private Vector3f getBackwardVec() {
		Vector3f vec = camDir.normalize().negate();
		vec.setY(getNode().getWorldTranslation().getY());
		return vec;
	}

	static public Player getPlayer(CameraNode cam)
	{
		if(player == null){
			node = new Node();
			player = new Player(1, 1, "male");
			player.getObj().rotate(0, (float) (Math.PI/2), 0);
			player.setCam(cam);
			camDir = cam.getCamera().getDirection();
		    camLeft = cam.getCamera().getLeft();
		}
			
		return player;
	}
	
	/*
	 * attach camera from main to player to be used by cichlid controller
	 * offset the spatial in the z direction
	 * attaching the player node to camNode creates the broken rotation
	 */
	
	public void attachCam(CameraNode camera){
		
	}
	
	public Node getNode(){
		if (node == null){
			System.out.println("There is no player fish");
			return null;
		}
		else return node;
	}
	
	public void update(float tpf){
		//Debugging stuff, deletes rays so I can make them at new pos and rotation
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray1");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray2");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray3");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray4");

		Vector3f reset = new Vector3f(0, .25f, 0);
		Vector3f move = player.getNode().getWorldTranslation();
		Vector3f movement = new Vector3f();
		
		//update cam values
		if (cam.isEnabled()){
			camDir = cam.getCamera().getDirection();
			camLeft = cam.getCamera().getLeft();
		}
		//remake rays
		makeRays();
		rotateObj(tpf);
		collideWithWalls(tpf);
		
		collidables = new Node();
		Vector3f test = new Vector3f();
    	test = player.getNode().localToWorld(getNextLoc(tpf),getNextLoc(tpf));
    	collidables = getCollisions(test);
    	if (!collidables.getChildren().isEmpty()){
    		//TODO collision stuff
			for (Spatial s : collidables.getChildren()){
				if (s.getName().contains("ray") || s.getName().contains("wall") || 
						s.getName().contains("terrain")){
					canHide = false;
					isHiding = false;
					movement = getNextLoc(tpf);
					move = player.getNode().localToWorld(movement,movement);
				}
				if (s.getName().contains("plant")){
					System.out.println("Plant");
					movement = avoidCollision(tpf);
					move = player.getNode().localToWorld(movement,movement);
				}
				else if (s.getName().contains("pot")){
					System.out.println("Pot");
					canHide = true;
					if (wantsToHide()){
						movement = hide(s, tpf);
						move = player.getNode().localToWorld(movement,movement);
						isHiding = true;
					}
					else if(isHiding){
						movement = getNextLoc(tpf);
						move = player.getNode().localToWorld(movement,movement);
					}
					else {
						movement = collide(tpf);
						move = player.getNode().localToWorld(movement,movement);
					}
				}
				else if (s.getName().contains("cichlid")){
					System.out.println("Cichlid");
					
				}
			}
    	}
    	else {
			canHide = false;
			isHiding = false;
			movement = getNextLoc(tpf);
			move = player.getNode().localToWorld(movement,movement);
    	}
    	
		player.getNode().setLocalTranslation(move);
	    player.getGhost().setPhysicsRotation(player.getObj().getWorldRotation());
	    
		left = false;
        right = false;
        up = false;
        down = false;
		
	}
	/**
	 * Checks for any collisions with walls. Uses 4 raycasts in 4 general directions 
	 * to check for nearest wall. If nearest wall is within the distance threshold, 
	 * use the raycasted direction to bounce player back
	 * @param tpf
	 */
	private void collideWithWalls(float tpf) {
		//if raylist isnt broken, which it should never be empty.
		Vector3f move = player.getNode().getWorldTranslation();
		Vector3f movement = new Vector3f();
		if (!rayList.isEmpty()){
			CollisionResults results = new CollisionResults();
		    CollisionResult closest = null;
		    Vector3f dir = null;
		    float distance = 0;
			for (Ray r : rayList){
				tank.getNode().collideWith(r, results);
				if (results.size() > 0){
					distance = results.getClosestCollision().getDistance();
					if (closest == null){
						closest = results.getClosestCollision();
						dir = r.getDirection();
					}
					else if (distance < closest.getDistance()){
						closest = results.getClosestCollision();
						dir = r.getDirection();
					}
				}
			}
			if (results.size() > 0){
				float distanceToLoc = getObj().getWorldTranslation().distance(getNextLoc(tpf));
				if (distanceToLoc >= distance && distance < 0.025f){
					movement = dir.negate().mult(tpf/2);
					move = player.getObj().getWorldTranslation();
					move.setX(move.x + movement.x);
					//move.setY(move.y - movement.y);
					move.setZ(move.z + movement.z);
					move.mult(tpf/4);
					player.getNode().setLocalTranslation(move);
					System.out.println("Moved via RayTesting");
				}
			}
		}
	}

	private Vector3f hide(Spatial s, float tpf){
		Vector3f movement = new Vector3f();
		getNode().lookAt(s.getWorldBound().getCenter(), Vector3f.UNIT_Y);
		if (getObj().getWorldBound().contains(s.getWorldBound().getCenter())){
			System.out.println("Center");
			getNode().lookAt(camDir, Vector3f.UNIT_Y);
		}
		else {
	        if (forward) {
	        	//base forward movement, should use fish speed. 
	        	movement = new Vector3f(0,0,tpf*.25f);
	        }
	        else if (backward) {
	    		movement = new Vector3f(0,0,-tpf*.1f);
	        }
	        if(player.isSprinting()){
	    		//double movement speed
	    		movement.setZ(movement.getZ()*2);
	    	}
			
		}
		return movement;
	}

	private Vector3f avoidCollision(float tpf) {
		Vector3f movement = new Vector3f();
        if (forward) {
        	//base forward movement, should use fish speed. 
        	movement = new Vector3f(0,0,tpf*.25f);
        }
        else if (backward) {
    		movement = new Vector3f(0,0,-tpf*.1f);
        }
        if(player.isSprinting()){
    		//double movement speed
    		movement.setZ(movement.getZ()*2);
    	}
        movement.setZ(movement.getZ()/2);
        return movement;
	}
	private Vector3f collide(float tpf) {
		Vector3f movement = new Vector3f();
        if (forward) {
        	//base forward movement, should use fish speed. 
        	movement = new Vector3f(0,0,-tpf*.05f);
        }
        else if (backward) {
    		movement = new Vector3f(0,0,tpf*.05f);
        }
        if(player.isSprinting()){
    		//double movement speed
    		movement.setZ(movement.getZ()*2);
    	}
        movement.setZ(movement.getZ()/2);
        return movement;
	}

	/**
	 * Used to get collisions with player's next location
	 * @param loc, location of player
	 * @return boolean 
	 */
	private Node getCollisions(Vector3f loc){
		Node col = new Node();
		Spatial testObj = player.getNode().clone();
		testObj.setLocalTranslation(loc);
		Node test = Starter.getClient().getWorkingScenario().getEntityNode();
		
		for (Spatial s : test.getChildren()){
			if (s instanceof Node){
				Node t = (Node) s;
				for (Spatial p : t.getChildren()){
					if (testObj.getWorldBound().intersects(p.getWorldBound())){
						Spatial x = p.clone();
						col.attachChild(x);
					}
				}
			}
			else if (testObj.getWorldBound().intersects(s.getWorldBound())){
				Spatial x = s.clone();
				col.attachChild(x);
			}
		}
		
		for (Spatial s : tank.getNode().getChildren()){
			if (testObj.getWorldBound().intersects(s.getWorldBound())){
				Spatial x = s.clone();
				col.attachChild(x);
			}
		}
		
		if (!col.getChildren().isEmpty()){
			collision = true;
		}
		else collision = false;
		return col;
		
	}//end of testCollision method
	
	
	/**
	 * Used to get player's next location to test before moving
	 * @param tpf
	 * @return player's next location
	 */
	private Vector3f getNextLoc(float tpf){
		Vector3f movement = new Vector3f();
        if (forward) {
        	//base forward movement, should use fish speed. 
        	movement = new Vector3f(0,0,tpf*.25f);
        }
        else if (backward) {
    		movement = new Vector3f(0,0,-tpf*.1f);
        }
        if(player.isSprinting()){
    		//double movement speed
    		movement.setZ(movement.getZ()*2);
    	}
        return movement;
	}//end of getNextLoc method
	

	/**
	 * Rotates player, uses tpf to calculate rotation
	 * @param tpf
	 */
	private void rotateObj(float tpf){
		//TODO Need to add variance into turning, test if for value of left/right/up/down
		if (left) {
            deg -= 250f * tpf;
        }
        if (right) {
            deg += 250f * tpf;
        }
        if (up){
        	if (pitch < 45f){
        		pitch += 100f * tpf;
        	}
        }
        if (down){
        	if (pitch > -45f){
        		pitch -= 100f * tpf;
        	}
        }
        
        Vector3f point = getPoint(deg, pitch, .15f);
        player.getCam().setLocalTranslation(point);
        player.getCam().lookAt(player.getObj().getWorldTranslation(), Vector3f.UNIT_Y);
        
		if (cam.isEnabled()){
			player.getNode().setLocalRotation(player.getCam().getWorldRotation());
			//player.getObj().rotate(0, (float) (Math.PI/2), 0);
			//player.getPhysicsControl().setPhysicsRotation(player.getNode().getLocalRotation());
		}
	}//end of rotateObj method
	
	/**
	 * getPoint() returns position of camera based on a circle around
	 * player using float deg and float radius
	 * where deg = angle of camera from player and radius = distance from player
	 * @param degrees
	 * @param radius
	 * @return Vector3f position of camera
	 */
    private Vector3f getPoint(float degrees, float pitch, float radius){
    	Vector3f pos = new Vector3f();

        double rads = Math.toRadians(degrees - 90); // 0 becomes the top
        double r = Math.toRadians(pitch - 90); // 0 becomes the top
        
        float x = player.getObj().getWorldTranslation().getX();
        float y = player.getObj().getWorldTranslation().getY();
        float z = player.getObj().getWorldTranslation().getZ();
        
        pos.setX((float) (x + Math.cos(rads) * radius));
        pos.setY((float) (y + Math.cos(r) * radius));
        pos.setZ((float) (z + Math.sin(rads) * radius));

        return pos;
    }//end of getPoint method
	public void setCam(CameraNode fishCam) {
		cam = fishCam;
	}

	public CameraNode getCam() {
		return cam;
	}

	public void setCollision(boolean b) {
		collision = true;		
	}

	public void setLeft(boolean b) {
		left = b;
	}
	public void setRight(boolean b) {
		right = b;
	}
	public void setForward(boolean b) {
		forward = b;
	}
	public void setBackward(boolean b) {
		backward = b;
	}
	public void setUp(boolean b) {
		up = b;
	}
	public void setDown(boolean b) {
		down = b;
	}
	public void setAscend(boolean b) {
		ascend = b;
	}
	public void setDescend(boolean b) {
		descend = b;
	}
	public void toggleHiding(boolean b){
		wantsToHide = b;
	}
	public boolean wantsToHide() {
		return wantsToHide;
	}
	public boolean canHide() {
		return canHide;
	}
}