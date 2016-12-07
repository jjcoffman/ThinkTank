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
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;

import thinktank.simulator.Starter;
import thinktank.simulator.environment.Tank;

/**
 * 
 * @author Vasher Lor
 * @version %I%, %G%
 * @deprecated
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
	 * <code>Node</code> holding the object of Player
	 */
	private static Node node = null;
	/**
	 * 
	 */
    private static Vector3f camDir = new Vector3f();
	/**
	 * 
	 */
    @SuppressWarnings("unused")
	private static Vector3f camLeft = new Vector3f();
	
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * Third-person chase camera for Player
	 */
	private CameraNode cam;
	/**
	 * Local reference to tank 
	 */
    private Tank tank;
	/**
	 * List of rays used to detect wall collisions
	 */
	private ArrayList<Ray> rayList;
	/**
	 * Ray pointing towards positive Z
	 */
    private Ray rayForward;
	/**
	 * Ray pointing towards negative Z
	 */
    private Ray rayBackward;
	/**
	 * Ray pointing towards negative X
	 */
    private Ray rayLeft;
	/**
	 * Ray pointing towards positive X
	 */
    private Ray rayRight;
	/**
	 * Ray pointing towards positive Y
	 */
    private Ray rayUp;
	/**
	 * Ray pointing towards negative Y
	 */
    private Ray rayDown;
	/**
	 * Boolean used to trigger left action
	 */
    private boolean left;
	/**
	 * Boolean used to trigger right action
	 */
    private boolean right;
	/**
	 * Boolean used to trigger up action
	 */
    private boolean up;
	/**
	 * Boolean used to trigger down action
	 */
    private boolean down;
	/**
	 * Boolean used to trigger forward action
	 */
    private boolean forward;
	/**
	 * Boolean used to trigger backward action
	 */
    private boolean backward;
	/**
	 * Boolean used to trigger ascend action
	 */
    @SuppressWarnings("unused")
	private boolean ascend;
	/**
	 * Boolean used to trigger descend action
	 */
    @SuppressWarnings("unused")
	private boolean descend;
	/**
	 * Boolean used to determine if Player is currently hiding in an object
	 */
    private boolean isHiding;
	/**
	 * Boolean used to determine if Player wants to hide
	 */
    private boolean wantsToHide;
	/**
	 * Boolean used to determine if conditions are met for Player to hide
	 */
    private boolean canHide;
	/**
	 * Set by wall collision to overwrite any other movement
	 */
	private boolean alreadyMoved;
	/**
	 * Used to store Player's rotation about the Y axis, left/right
	 */
    private float deg;
	/**
	 * Used to store Player's rotation about the X axis, up/down
	 */
    private float pitch;
    /**
     * Used to trigger sprint action
     */
    private boolean sprint = false;
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
		makeRays();
	}//end of (POSSIBLE_SIZES,float,String) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * getPoint() returns position of camera based on a circle around
	 * player using degrees, pitch, and radius where degrees = angle of 
	 * camera from player left/right, pitch = degree from player up/down
	 * and radius = distance from player
	 * @param degrees
	 * @param pitch
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
     * Returns the <code>Node</code> holding Player object
     */
	public Node getNode(){
		if (node == null){
			System.out.println("There is no player fish");
			return null;
		}
		else return node;
	}//end of getNode method

	/**
	 * Used to return any Spatial in contact with player's next location
	 * @param loc, location of player
	 * @return Spatial that collides with Player
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
	 * Returns sprint boolean
	 * @return boolean 
	 */
	public boolean isSprinting(){
		return sprint;
	}//end of isSprinting method
	/**
	 * Returns <code>CameraNode</code> attachted to Player
	 * @return
	 */
	public CameraNode getCam(){
		return cam;
	}//end of getCam method

	/**
	 * Returns wantsToHide boolean
	 * @return
	 */
	public boolean wantsToHide(){
		return wantsToHide;
	}//end of wantsToHide method
	
	/**
	 * Returns canHide boolean
	 * @return
	 */
	public boolean canHide(){
		return canHide;
	}//end of canHide method

	//SETTERS
	/**
	 * Attaches <code>CameraNode</code> to Player
	 * @param fishCam
	 */
	public void setCam(CameraNode fishCam){
		cam = fishCam;
		System.out.println("Player cam initiated");
	}//end of setCam method

	/**
	 * Setter for left boolean
	 * @param left
	 */
	public void setLeft(boolean left){
		this.left = left;
	}//end of setLeft method
	
	/**
	 * Setter for right boolean
	 * @param right
	 */
	public void setRight(boolean right){
		this.right = right;
	}//end of setRight method
	
	/**
	 * Setter for forward boolean
	 * @param forward
	 */
	public void setForward(boolean forward){
		this.forward = forward;
	}//end of setForward method
	
	/**
	 * Setter for backward boolean
	 * @param backward
	 */
	public void setBackward(boolean backward){
		this.backward = backward;
	}//end of setBackward
	
	/**
	 * Setter for up boolean
	 * @param up
	 */
	public void setUp(boolean up){
		this.up = up;
	}//end of setUp method
	
	/**
	 * Setter for down boolean
	 * @param down
	 */
	public void setDown(boolean down){
		this.down = down;
	}//end of setDown method
	
	/**
	 * Setter for ascend boolean
	 * @param ascend
	 */
	public void setAscend(boolean ascend){
		this.ascend = ascend;
	}//end of setAscend method
	
	/**
	 * Setter for descend boolean
	 * @param descend
	 */
	public void setDescend(boolean descend){
		this.descend = descend;
	}//end of setDescend method
	
	/**
	 * Setter for wantsToHide boolean
	 * @param wantsToHide
	 */
	public void setHiding(boolean wantsToHide){
		this.wantsToHide = wantsToHide;
	}//end of setHiding method
	
	//OPERATIONS
	/**
	 * Initializes variables and attaches object to Player Node
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
		node.attachChild(getObj());
		node.rotate(0, (float)(Math.PI / 2), 0);
		super.setName("Player");
	}//end of init method
	
	/**
	 * Sets up Player movement controls and maps them to keys
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
	 * Update Player's movement, rotation, and collision detection
	 * @param tpf
	 */
	public void update(float tpf){
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
	 * Used to bounce Player around an object. This method takes an object and compares
	 * the positions on each axis to move the Player accordingly
	 * @param spatial that player must move around
	 * @param tpf
	 * @return Vector3f of Player's next position
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
		if(forward){
        	movement = new Vector3f(0, 0, tpf * 0.075f);
        }
        else if(backward){
    		movement = new Vector3f(0, 0, -tpf * 0.075f);
        }
        move = player.getNode().localToWorld(movement,movement);
		if(!rayList.isEmpty()){
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
							dir = ray.getDirection();
						}
						else if(distance < closest.getDistance()){
							closest = results.getClosestCollision();
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
	 * This method allows a player to hide within an object. The method rotates the Player
	 * to look at the object, and moves the Player towards that object while ignoring collision
	 * @param spatial to move towards the center of
	 * @param tpf
	 * @return Vector3f of Player's next position
	 */
	private Vector3f hide(Spatial spatial, float tpf){
		Vector3f movement = new Vector3f();
		getNode().lookAt(spatial.getWorldBound().getCenter(), Vector3f.UNIT_Y);
		if(getObj().getWorldBound().contains(spatial.getWorldBound().getCenter())){
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
	 * This method implements the basic move method, but at half the speed. 
	 * @param tpf
	 * @return Vector3f of Player's next position
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
		}
		makeRays();
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
	
	private void setSprint(boolean b){
		sprint = b;
	}//end of setSpring method

	/**
	 * Make rays to detect wall collisions. A total of six rays are made, two for each axis.
	 * These rays are local to the Player object.
	 */
	private void makeRays(){
		rayList.clear();
		Vector3f posZ = getPosZVec();
		rayForward = new Ray(getObj().getWorldTranslation(), posZ);
		
		Vector3f negZ = getNegZVec();
		rayBackward = new Ray(getObj().getWorldTranslation(), negZ);
		
		Vector3f negX = getNegXVec();
		rayLeft = new Ray(getObj().getWorldTranslation(), negX);
		
		Vector3f posX = getPosXVec();
		rayRight = new Ray(getObj().getWorldTranslation(), posX);
		
		Vector3f downVec = getDownVec();
		rayDown = new Ray(getObj().getWorldTranslation(), downVec);

		Vector3f upVec = getUpVec();
		rayUp = new Ray(getObj().getWorldTranslation(), upVec);
		
		rayList.add(rayForward);
		rayList.add(rayBackward);
		rayList.add(rayLeft);
		rayList.add(rayRight);
		rayList.add(rayDown);
		rayList.add(rayUp);
	}//end of makeRays method

	/**
	 * Get player's location and return vector that has slightly larger X value
	 * @return
	 */
	private Vector3f getPosXVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(.1f, 0, 0);
		return vec;
	}//end of getPosXVec method

	/**
	 * Get player's location and return vector that has slightly smaller X value
	 * @return
	 */
	private Vector3f getNegXVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(-.1f, 0, 0);
		return vec;
	}//end of getNegXVec method

	/**
	 * Get player's location and return vector that has slightly larger Z value
	 * @return
	 */
	private Vector3f getPosZVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(0, 0, .1f);
		return vec;
	}//end of getPosZVec method

	/**
	 * Get player's location and return vector that has slightly smaller Z value
	 * @return
	 */
	private Vector3f getNegZVec(){
		Vector3f vec = getObj().getWorldTranslation().clone().add(0, 0, -.1f);
		return vec;
	}//end of getNegZVec method

	/**
	 * Get player's location and return vector that has slightly smaller Y value
	 * @return
	 */
	private Vector3f getDownVec(){
		Vector3f vec = getNode().getWorldTranslation();
		float y = vec.getY();
		vec.setY(y - 0.1f);
		return vec;
	}//end of getDownVec method

	/**
	 * Get player's location and return vector that has slightly larger Y value
	 * @return
	 */
	private Vector3f getUpVec(){
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
	 * @deprecated
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