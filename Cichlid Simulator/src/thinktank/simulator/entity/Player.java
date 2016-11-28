package thinktank.simulator.entity;

import java.util.ArrayList;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.jme3.terrain.geomipmap.TerrainQuad;

import thinktank.simulator.Starter;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.main.Main;

/**
 * 
 * @author Bob
 *
 */
public class Player extends Cichlid implements ActionListener{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 4038460719382327559L;
	
	//---------------------static variables----------------------------
	/**
	 * 
	 */
	private static Player player = null;  //singleton
	/**
	 * 
	 */
	private static Node node = null;
	/**
	 * 
	 */
    private static Vector3f camDir = new Vector3f();
	/**
	 * 
	 */
    private static Vector3f camLeft = new Vector3f();
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private CameraNode cam;
	/**
	 * 
	 */
    private Tank tank;
	/**
	 * 
	 */
	private ArrayList<Ray> rayList;
	/**
	 * 
	 */
    private Ray rayForward;
	/**
	 * 
	 */
    private Ray rayBackward;
	/**
	 * 
	 */
    private Ray rayLeft;
	/**
	 * 
	 */
    private Ray rayRight;
	/**
	 * 
	 */
    private Ray rayUp;
	/**
	 * 
	 */
    private Ray rayDown;
	/**
	 * 
	 */
    private boolean left;
	/**
	 * 
	 */
    private boolean right;
	/**
	 * 
	 */
    private boolean up;
	/**
	 * 
	 */
    private boolean down;
	/**
	 * 
	 */
    private boolean forward;
	/**
	 * 
	 */
    private boolean backward;
	/**
	 * 
	 */
    private boolean ascend;
	/**
	 * 
	 */
    private boolean descend;
	/**
	 * 
	 */
    private boolean isHiding;
	/**
	 * 
	 */
    private boolean wantsToHide;
	/**
	 * 
	 */
    private boolean canHide;
	/**
	 * 
	 */
	private boolean alreadyMoved;
	/**
	 * 
	 */
    private float deg;
	/**
	 * 
	 */
    private float pitch;
    
    //TODO debuging stuff
    private Material red;
    private Material green;
    private Material blue;
    private Material yellow;
    private Material orange;
    private Material magenta;
    private Line line1;
    private Geometry ray1;
    private Line line2;
    private Geometry ray2;
    private Line line3;
    private Geometry ray3;
    private Line line4;
    private Geometry ray4;
    private Line line5;
    private Geometry ray5;
    private Line line6;
    private Geometry ray6;
    //end debugging stuff
	
	//---------------------constructors--------------------------------
    /**
     * 
     * @param size
     * @param speed
     * @param sex
     */
	private Player(POSSIBLE_SIZES size, float speed, String sex){
		super(size, speed, sex);
		init();
	    setupInputs();
		debugStuff();
	}//end of (POSSIBLE_SIZES,float,String) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
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
        
        pos.setX((float)(x + Math.cos(rads) * radius));
        pos.setY((float)(y + Math.cos(r) * radius));
        pos.setZ((float)(z + Math.sin(rads) * radius));

        return pos;
    }//end of getPoint method
	
    /**
     * 
     */
    @Override
	public Node getNode(){
		if (node == null){
			System.out.println("There is no player fish");
			return null;
		}
		else return node;
	}//end of getNode method

	/**
	 * Used to get collisions with player's next location
	 * @param loc, location of player
	 * @return boolean 
	 */
	private Spatial getCollisions(Vector3f loc){
		Spatial returnValue = null;
		Spatial testObj = player.getNode().clone();
		testObj.setLocalTranslation(loc);
		Node entities = Starter.getClient().getWorkingScenario().getEntityNode();
		for(Spatial spatial : entities.getChildren()){
			if(testObj.getWorldBound().intersects(spatial.getWorldBound())){
				returnValue = spatial;
			}
		}
		return returnValue;
	}//end of testCollision method

	/**
	 * Used to get player's next location to test before moving
	 * @param tpf
	 * @return player's next location
	 */
	private Vector3f getNextLoc(float tpf){
		Vector3f returnValue = new Vector3f();
        if(forward){
        	//base forward movement, should use fish speed. 
        	returnValue = new Vector3f(0, 0, tpf * 0.25f);
        }
        else if(backward){
        	returnValue = new Vector3f(0, 0, -tpf * 0.1f);
        }
        if(player.isSprinting()){
    		//double movement speed
        	returnValue.setZ(returnValue.getZ() * 2);
    	}
        return returnValue;
	}//end of getNextLoc method

	/**
	 * 
	 * @return
	 */
	public CameraNode getCam(){
		return cam;
	}//end of getCam method

	/**
	 * 
	 * @return
	 */
	public boolean wantsToHide(){
		return wantsToHide;
	}//end of wantsToHide method
	
	/**
	 * 
	 * @return
	 */
	public boolean canHide(){
		return canHide;
	}//end of canHide method

	//SETTERS
	/**
	 * 
	 * @param fishCam
	 */
	public void setCam(CameraNode fishCam){
		cam = fishCam;
		System.out.println("Player cam initiated");
	}//end of setCam method

	/**
	 * 
	 * @param left
	 */
	public void setLeft(boolean left){
		this.left = left;
	}//end of setLeft method
	
	/**
	 * 
	 * @param right
	 */
	public void setRight(boolean right){
		this.right = right;
	}//end of setRight method
	
	/**
	 * 
	 * @param forward
	 */
	public void setForward(boolean forward){
		this.forward = forward;
	}//end of setForward method
	
	/**
	 * 
	 * @param backward
	 */
	public void setBackward(boolean backward){
		this.backward = backward;
	}//end of setBackward
	
	/**
	 * 
	 * @param up
	 */
	public void setUp(boolean up){
		this.up = up;
	}//end of setUp method
	
	/**
	 * 
	 * @param down
	 */
	public void setDown(boolean down){
		this.down = down;
	}//end of setDown method
	
	/**
	 * 
	 * @param ascend
	 */
	public void setAscend(boolean ascend){
		this.ascend = ascend;
	}//end of setAscend method
	
	/**
	 * 
	 * @param descend
	 */
	public void setDescend(boolean descend){
		this.descend = descend;
	}//end of setDescend method
	
	/**
	 * 
	 * @param wantsToHide
	 */
	public void setHiding(boolean wantsToHide){
		this.wantsToHide = wantsToHide;
	}//end of setHiding method
	
	//OPERATIONS
	/**
	 * 
	 */
	private void init(){
	    left = false;
	    right = false;
	    up = false;
	    down = false;
	    forward = false;
	    backward = false;
	    ascend = false;
	    descend = false;
	    isHiding = false;
	    wantsToHide = false;
	    canHide = false;
	    alreadyMoved = false;
	    deg = (float)(Math.PI / 2);
	    rayList = new ArrayList<Ray>(6);
		tank = Starter.getClient().getWorkingScenario().getEnvironment().getTank();
		node.attachChild(super.getNode());
		node.rotate(0, (float)(Math.PI / 2), 0);
		super.setName("Player");
	}//end of init method
	
	/**
	 * attach camera from main to player to be used by cichlid controller
	 * offset the spatial in the z direction
	 * attaching the player node to camNode creates the broken rotation
	 */
	private void setupInputs(){
		InputManager im = Starter.getClient().getInputManager();
		im.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
		im.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
		im.addMapping("Left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
		im.addMapping("Right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
		im.addMapping("Up", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
		im.addMapping("Down", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
		im.addMapping("Ascend", new KeyTrigger(KeyInput.KEY_Q));
		im.addMapping("Descend", new KeyTrigger(KeyInput.KEY_Z));
		im.addMapping("Sprint", new KeyTrigger(KeyInput.KEY_SPACE));
		im.addMapping("Hide", new KeyTrigger(KeyInput.KEY_SPACE));

		im.addListener(this, "Forward");
		im.addListener(this, "Backward");
		im.addListener(this, "Left");
		im.addListener(this, "Right");
		im.addListener(this, "Up");
		im.addListener(this, "Down");
		im.addListener(this, "Ascend");
		im.addListener(this, "Descend");
		im.addListener(this, "Sprint");
		im.addListener(this, "Hide");
	}//end of setupInputs method

	/**
	 * 
	 * @param camera
	 */
	public void attachCam(CameraNode camera){
		
	}//end of attachCam method
	
	/**
	 * 
	 * @param tpf
	 */
	public void update(float tpf){
		//TODO Debugging stuff, deletes rays so I can make them at new pos and rotation
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray1");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray2");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray3");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray4");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray5");
		Starter.getClient().getWorkingScenario().getEntityNode().detachChildNamed("ray6");

		Vector3f move = player.getNode().getWorldTranslation();
		Vector3f movement = new Vector3f();

		rotateObj(tpf);

		collideWithWalls(tpf);
		
		Spatial spatial;
		Vector3f testVec = new Vector3f();
    	testVec = player.getNode().localToWorld(getNextLoc(tpf),getNextLoc(tpf));
    	spatial = getCollisions(testVec);
    	if(spatial != null){
    		//TODO collision stuff
				if(spatial.getName().contains("ray")){
					//move normally for rays
					canHide = false;
					isHiding = false;
					movement = getNextLoc(tpf);
					move = player.getNode().localToWorld(movement,movement);
				}
				if(spatial.getName().contains("wall")){
					//nothing, handled by collideWithWalls(tpf)
				}
				if(spatial.getName().contains("terrain")){
					//handled by collisionWithTerrain()
				}
				if(spatial.getName().contains("plant") || spatial.getName().contains("Hygro")){
					movement = slowMove(tpf);
					move = player.getNode().localToWorld(movement,movement);
				}
				else if(spatial.getName().contains("pot")){
					System.out.println("Pot");
					canHide = true;
					if(wantsToHide()){
						movement = hide(spatial, tpf);
						move = player.getNode().localToWorld(movement,movement);
						isHiding = true;
					}
					else if(isHiding){
						move = moveAroundObj(spatial, tpf);
					}
					else{
						move = moveAroundObj(spatial, tpf);
					}
				}
				else if(spatial.getName().contains("cichlid")){
					System.out.println("Cichlid");
					
				}
    	}
    	else{
			canHide = false;
			isHiding = false;
			movement = getNextLoc(tpf);
			move = player.getNode().localToWorld(movement,movement);
    	}
    	
    	if(!alreadyMoved){
    		player.getNode().setLocalTranslation(move);
    		player.getGhost().setPhysicsRotation(player.getObj().getWorldRotation());
    	}

		collideWithTerrain();
		left = false;
        right = false;
        up = false;
        down = false;
        alreadyMoved = false;
	}//end of update method
	
	/**
	 * Checks for player collision with sand and surface level. 
	 * @param tpf
	 */
	private void collideWithTerrain(){
		TerrainQuad terrain = tank.getTerrain();
		if (getObj().getWorldBound().intersects(terrain.getWorldBound())){
			Vector3f yTranslation = getNode().getLocalTranslation();
			yTranslation.setY(yTranslation.getY() + 0.00575f);
			getNode().setLocalTranslation(yTranslation);
		}
		float yPosition = getObj().getWorldTranslation().getY();
		if (yPosition > (tank.getWolrdUnitHeight() - 0.01f)){
			Vector3f yTranslation = getNode().getLocalTranslation();
			yTranslation.setY(yTranslation.getY() - 0.00575f);
			getNode().setLocalTranslation(yTranslation);
		}
	}//end of collideWithTerrain method

	/**
	 * 
	 * @param spatial
	 * @param tpf
	 * @return
	 */
	private Vector3f moveAroundObj(Spatial spatial, float tpf){
		float myX = getObj().getWorldTranslation().getX();
		float myY = getObj().getWorldTranslation().getY();
		float myZ = getObj().getWorldTranslation().getZ();
		float x = spatial.getWorldTranslation().getX();
		float y = spatial.getWorldTranslation().getY();
		float z = spatial.getWorldTranslation().getZ();
		
		Vector3f movement = new Vector3f();
		Vector3f move = new Vector3f();
        if(forward){
        	//base forward movement, should use fish speed. 
        	movement = new Vector3f(0,0,tpf*.1f);
        }
        else if(backward){
    		movement = new Vector3f(0,0,-tpf*.1f);
        }
        if(player.isSprinting()){
    		//double movement speed
    		movement.setZ(movement.getZ()*2);
    	}
        move = player.getNode().localToWorld(movement,movement);
        float dx = Math.abs(myX - x);
        float dy = Math.abs(myY - y);
        float dz = Math.abs(myZ - z);
        if(x > myX){
			move.setX(move.getX() - dx / 50);
		}
		else if(x < myX){
			move.setX(move.getX() + dx / 50);
		}
        if(y > myY){
			move.setY(move.getY() - dy / 50);
		}
		else if(y < myY){
			move.setY(move.getY() + dy / 50);
		}
        if(z > myZ){
			move.setZ(move.getZ() - dz / 50);
		}
		else if(z < myZ){
			move.setZ(move.getZ() + dz / 50);
		}
		return move;
	}//end of moveAroundObj method

	/**
	 * Checks for any collisions with walls. Uses 4 raycasts in 4 general directions 
	 * to check for nearest wall. If nearest wall is within the distance threshold, 
	 * use the raycasted direction to bounce player back
	 * @param tpf
	 */
	private void collideWithWalls(float tpf){
		//if raylist isnt broken, which it should never be empty.
		tank = Starter.getClient().getWorkingScenario().getEnvironment().getTank();
		Vector3f move = player.getNode().getWorldTranslation();
		Vector3f movement = new Vector3f();
		String rayName = null;
		if(forward){
        	movement = new Vector3f(0, 0, tpf * 0.075f);
        }
        else if(backward){
    		movement = new Vector3f(0, 0, -tpf * 0.075f);
        }
		Vector3f old = player.getObj().getWorldTranslation();
        move = player.getNode().localToWorld(movement,movement);
		if(!rayList.isEmpty()){
			CollisionResults saveResults = new CollisionResults();
		    CollisionResult closest = null;
		    Vector3f dir = null;
		    float distance = 0;
			for(Ray ray : rayList){
				CollisionResults results = new CollisionResults();
				for(Spatial spatial : tank.getNode().getChildren()){
					spatial.collideWith(ray, results);
					if(results.size() > 0){
						distance = results.getClosestCollision().getDistance();
						if (closest == null){
							closest = results.getClosestCollision();
							saveResults = results;
							dir = ray.getDirection();
						}
						else if(distance < closest.getDistance()){
							closest = results.getClosestCollision();
							saveResults = results;
							dir = ray.getDirection();
						}
					}
				}
				if(closest != null){
					float closestDis = closest.getDistance();
					float distanceToLoc = getObj().getWorldTranslation().distance(getNextLoc(tpf));
					if(distanceToLoc >= closestDis && closestDis < 0.05f){
						Vector3f direction = dir.subtract(getObj().getWorldTranslation()).mult(tpf);
						System.out.println(direction);
						move.setX(move.x - direction.x);
						move.setZ(move.z - direction.z);
						player.getNode().setLocalTranslation(move);
						System.out.println("Moved via RayTesting");
						alreadyMoved = true;
					}
				}
			}
		}
	}//end of collideWithWalls method

	/**
	 * 
	 * @param spatial
	 * @param tpf
	 * @return
	 */
	private Vector3f hide(Spatial spatial, float tpf){
		Vector3f movement = new Vector3f();
		getNode().lookAt(spatial.getWorldBound().getCenter(), Vector3f.UNIT_Y);
		if(getObj().getWorldBound().contains(spatial.getWorldBound().getCenter())){
			System.out.println("Center");
			getNode().lookAt(camDir, Vector3f.UNIT_Y);
		}
		else{
	        if(forward){
	        	//base forward movement, should use fish speed. 
	        	movement = new Vector3f(0, 0, tpf * 0.25f);
	        }
	        else if(backward){
	    		movement = new Vector3f(0, 0, -tpf * 0.1f);
	        }
	        if(player.isSprinting()){
	    		//double movement speed
	    		movement.setZ(movement.getZ() * 2);
	    	}
			
		}
		return movement;
	}//end of hide method

	/**
	 * 
	 * @param tpf
	 * @return
	 */
	private Vector3f slowMove(float tpf){
		Vector3f movement = new Vector3f();
        if(forward){
        	//base forward movement, should use fish speed. 
        	movement = new Vector3f(0, 0, tpf * 0.25f);
        }
        else if(backward){
    		movement = new Vector3f(0, 0, -tpf * 0.1f);
        }
        if(player.isSprinting()){
    		//double movement speed
    		movement.setZ(movement.getZ() * 2);
    	}
        movement.setZ(movement.getZ() / 2);
        return movement;
	}//end of slowMove method


	/**
	 * Rotates player, uses tpf to calculate rotation
	 * @param tpf
	 */
	private void rotateObj(float tpf){
		//TODO Need to add variance into turning, test if for value of left/right/up/down
		if(left){
            deg -= 250f * tpf;
        }
        if(right){
            deg += 250f * tpf;
        }
        if(up){
        	if(pitch < 45f){
        		pitch += 100f * tpf;
        	}
        }
        if(down){
        	if(pitch > -45f){
        		pitch -= 100f * tpf;
        	}
        }
        
        Vector3f point = getPoint(deg, pitch, 0.15f);
        player.getCam().setLocalTranslation(point);
        player.getCam().lookAt(player.getObj().getWorldTranslation(), Vector3f.UNIT_Y);
        
		if (cam.isEnabled()){
			player.getNode().setLocalRotation(player.getCam().getWorldRotation());
			//remake rays
		}
		makeRays();//TODO debug?
	}//end of rotateObj method
    
	/**
	 * 
	 */
	@Override
	public void onAction(String binding, boolean value, float tpf){
		if(player.getCam().isEnabled()){
			if(binding.equals("Left")){
				if(value){
					player.setLeft(true);
				}
			}
			else if(binding.equals("Right")){
				if(value){
					player.setRight(true);
				}
			}
			else if(binding.equals("Up")){
				if(value){
					player.setUp(true);
				}
				else{
					player.setUp(false);
				}
			}
			else if(binding.equals("Down")){
				if (value){
					player.setDown(true);
				}
				else{
					player.setDown(false);
				}
			}
			else if(binding.equals("Forward")){
				if(value){
					player.setForward(true);
				}
				else{
					player.setForward(false);
				}
			}
			else if(binding.equals("Backward")){
				if(value){
					player.setBackward(true);
				}
				else{
					player.setBackward(false);
				}
			}
			else if(binding.equals("Ascend")){
				if(value){
					player.setAscend(true);
				}
				else{
					player.setAscend(false);
				}
			}
			else if(binding.equals("Descend")){
				if(value){
					player.setDescend(true);
				}
				else{
					player.setDescend(false);
				}
			}
			else if(binding.equals("Sprint")){
				if(value){
					player.setSprint(true);
				}
				else
					player.setSprint(false);
			}
			else if(binding.equals("Hide")){
				if(value){
					if(player.canHide()){
						player.setHiding(!player.wantsToHide());
					}
				}
			}
		}
	}//end of onAction method

	//TODO temp visual aid for debugging
	private void debugStuff(){
		red = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		red.setColor("Color", ColorRGBA.Red);
		green = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		green.setColor("Color", ColorRGBA.Green);
		blue = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		blue.setColor("Color", ColorRGBA.Blue);
		yellow = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		yellow.setColor("Color", ColorRGBA.Yellow);
		orange = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		orange.setColor("Color", ColorRGBA.Orange);
		magenta = new Material(Main.asset_manager, "Common/MatDefs/Misc/Unshaded.j3md");
		magenta.setColor("Color", ColorRGBA.Magenta);
		makeRays();
	}//end of debugStuff method
	
	//TODO temp visual aid for debugging
	private void makeRays(){
		rayList.clear();
		Vector3f posZ = getPosZVec();
		rayForward = new Ray(getObj().getWorldTranslation(), posZ);
		line1 = new Line(getObj().getWorldTranslation(), posZ);
		ray1 = new Geometry("ray1", line1);
		ray1.setMaterial(red);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray1);
		
		Vector3f negZ = getNegZVec();
		rayBackward = new Ray(getObj().getWorldTranslation(), negZ);
		line2 = new Line(getObj().getWorldTranslation(), negZ);
		ray2 = new Geometry("ray2", line2);
		ray2.setMaterial(green);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray2);
		
		Vector3f negX = getNegXVec();
		rayLeft = new Ray(getObj().getWorldTranslation(), negX);
		line3 = new Line(getObj().getWorldTranslation(), negX);
		ray3 = new Geometry("ray3", line3);
		ray3.setMaterial(blue);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray3);
		
		Vector3f posX = getPosXVec();
		rayRight = new Ray(getObj().getWorldTranslation(), posX);
		line4 = new Line(getObj().getWorldTranslation(), posX);
		ray4 = new Geometry("ray4", line4);
		ray4.setMaterial(yellow);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray4);
		
		Vector3f downVec = getDownVec();
		rayDown = new Ray(getObj().getWorldTranslation(), downVec);
		line6 = new Line(getObj().getWorldTranslation(), downVec);
		ray6 = new Geometry("ray6", line6);
		ray6.setMaterial(magenta);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray6);

		Vector3f upVec = getUpVec();
		rayUp = new Ray(getObj().getWorldTranslation(), upVec);
		line5 = new Line(getObj().getWorldTranslation(), upVec);
		ray5 = new Geometry("ray5", line5);
		ray5.setMaterial(orange);
		Starter.getClient().getWorkingScenario().getEntityNode().attachChild(ray5);
		
		rayList.add(rayForward);
		rayList.add(rayBackward);
		rayList.add(rayLeft);
		rayList.add(rayRight);
		rayList.add(rayDown);
		//rayList.add(rayUp);
	}//end of makeRays method

	//TODO temp visual aid for debugging
	private Vector3f getPosXVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(.1f, 0, 0);
		return vec;
	}//end of getPosXVec method

	//TODO temp visual aid for debugging
	private Vector3f getNegXVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(-.1f, 0, 0);
		return vec;
	}//end of getNegXVec method

	//TODO temp visual aid for debugging
	private Vector3f getPosZVec() {
		Vector3f vec = getObj().getWorldTranslation().clone().add(0, 0, .1f);
		return vec;
	}//end of getPosZVec method

	//TODO temp visual aid for debugging
	private Vector3f getNegZVec() {
		Vector3f vec = getObj().getWorldTranslation().clone().add(0, 0, -.1f);
		return vec;
	}//end of getNegZVec method

	//TODO temp visual aid for debugging
	private Vector3f getDownVec() {
		Vector3f vec = getNode().getWorldTranslation();
		float y = vec.getY();
		vec.setY(y - 0.1f);
		return vec;
	}//end of getDownVec method

	//TODO temp visual aid for debugging
	private Vector3f getUpVec() {
		Vector3f vec = getNode().getWorldTranslation();
		vec.setY(vec.getY() + 0.1f);
		return vec;
	}//end of getUpVec method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * 
	 * @param cam
	 * @return
	 */
	static public Player getPlayer(CameraNode cam){
		if(player == null){
			node = new Node();
			player = new Player(POSSIBLE_SIZES.MEDIUM, 1, "male");
			player.getObj().rotate(0, (float)(Math.PI/2), 0);
			player.setCam(cam);
			camDir = cam.getCamera().getDirection();
		    camLeft = cam.getCamera().getLeft();
		    System.out.println("Player created");
		}
		return player;
	}//end of getPlayer method
}//end of Player class