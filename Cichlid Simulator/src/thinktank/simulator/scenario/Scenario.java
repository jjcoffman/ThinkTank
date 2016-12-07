package thinktank.simulator.scenario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.jme3.scene.Node;

import thinktank.simulator.entity.Cichlid;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.Tank;
import thinktank.simulator.main.Main;

/**
 * Manages the various objects and values which compose a given scenario. 
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class Scenario implements Serializable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -3983004414750924485L;
	/**
	 * Constant value for the name a new scenario before it has been saved.
	 */
	public static final String DEFAULT_NEW_SCENARIO_NAME = "Unnamed Scenario";
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * Unique number to identify a specific scenario.
	 */
	private long id;
	/**
	 * The name for the scenario that is visible to the user.
	 */
	private String name;
	/**
	 * The <code>Environment</code> object representing the tank environment 
	 * in which the scenario takes place.
	 */
	private Environment environ;
	/**
	 * The list of objects within the environment for the scenario.
	 */
	private ArrayList<EnvironmentObject> environObjs;
	/**
	 * The list of fish within the scenario.
	 */
	private ArrayList<Fish> fish;
	/**
	 * The base node for the scenario to which all entities are attached.
	 */
	private Node entityNode;
	/**
	 * A reference to the currently selected entity.
	 */
	private Entity selectedEntity;
	/**
	 * Flag for whether or not the objects in the scenario are able to be moved.
	 */
	private boolean movingMode;
	/**
	 * Flag for whether or not the scenario is currently able to be edited.
	 */
	private boolean editingMode;
	/**
	 * Flag for whether or not the scenario has a fish designated as a player fish.
	 */
	private boolean hasPlayer;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default scenario.
	 */
	private Scenario(){
		init();
	}//end of default constructor
	
	/**
	 * Constructs a default scenario, but with the specified name.
	 * 
	 * @param name the name for the scenario.
	 */
	private Scenario(String name){
		init();
		this.name = name;
	}//end of (String) constructor
	
	/**
	 * Constructs a scenario with the specified name, using the specified 
	 * <code>Environment</code> object.
	 * 
	 * @param name the name for the scenario.
	 * @param environment the environment for the scenario.
	 */
	private Scenario(String name, Environment environment){
		init();
		this.name = name;
		this.environ = environment;
	}//end of (String,Environment) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the id number of this scenario.
	 * 
	 * @return the id number in this scenario.
	 */
	public long getID(){
		return id;
	}//end of getID method
	
	/**
	 * Gets the name of this scenario.
	 * 
	 * @return the name of this scenario.
	 */
	public String getName(){
		return name;
	}//end of name method
	
	/**
	 * Gets the environment of this scenario.
	 * 
	 * @return the environment of this scenario.
	 */
	public Environment getEnvironment(){
		return environ;
	}//end of getEnvironment method
	
	/**
	 * Returns an iterator over the environment objects in this scenario.
	 * 
	 * @return an iterator over the environment objects in this scenario.
	 */
	public Iterator<EnvironmentObject> getEnvironmentObjects(){
		return environObjs.iterator();
	}//end of getEnvironmentObjects method

	/**
	 * Returns an iterator over the fish in this scenario.
	 * 
	 * @return an iterator over the fish in this scenario.
	 */
	public Iterator<Fish> getFish(){
		return fish.iterator();
	}//end of getFish method
	
	/**
	 * Gets the root node for entities in this scenario.
	 * 
	 * @return the root node for entities in this scenario.
	 */
	public Node getEntityNode(){
		return entityNode;
	}//end of getEntityNode method
	
	/**
	 * Get the entity with the specified name on its geometry.
	 * 
	 * @param geometryName the name of the geometry of the returned entity. 
	 * @return the entity with the specified name on its geometry.
	 */
	public Entity getEntity(String geometryName){
		Entity returnValue = null;
		for(EnvironmentObject entity : environObjs){
			if(geometryName.startsWith(entity.getName())){
				returnValue = entity;
				break;
			}
		}
		if(returnValue == null){
			for(Fish entity : fish){
				if(geometryName.startsWith(entity.getName())){
					returnValue = entity;
					break;
				}
			}
		}
		return returnValue;
	}//end of getEntity(String) method
	
	/**
	 * Returns a reference to the currently selected entity.
	 * 
	 * @return the currently selected entity, null if no entity 
	 * is selected.
	 */
	public Entity getSelectedEntity(){
		return selectedEntity;
	}//end of getSelectedEntity method
	
	/**
	 * Returns whether or not the scenario is currently in moving mode.
	 * 
	 * @return the value of the moving mode flag.
	 */
	public boolean isMovingMode(){
		return movingMode;
	}//end of isMovingMode method
	
	/**
	 * Returns whether or not the scenario is currently in editing mode.
	 * 
	 * @return the value of the editing mode flag.
	 */
	public boolean isEditingMode(){
		return editingMode;
	}//end of isEditingMode method
	
	/**
	 * Returns whether or not the scenario has a player fish.
	 * 
	 * @return the value of the player flag.
	 */
	public boolean hasPlayer(){
		return hasPlayer;
	}//end of hasPlayer method
	
	//SETTERS
	/**
	 * Sets the name of this scenario to the specified <code>String</code>.
	 * 
	 * @param name the name to which the scenario's name is to be set.
	 */

	public void setName(String name){
		this.name = name;
	}//end of setName method
	
	/**
	 * Sets the environment for this scenario to the specified 
	 * <code>Environment</code> object.
	 *  
	 * @param environment the <code>Environment</code> object to which this 
	 * scenario's environment is to be set.
	 */
	public void setEnvironment(Environment environment){
		this.environ = environment;
	}//end of setEnvironment method
	
	/**
	 * Sets the value of the moving mode flag.
	 * 
	 * @param movingMode the value to which the move mode flag is to be set.
	 */
	public void setMovingMode(boolean movingMode){
		this.movingMode = movingMode;
	}//end of setMovingMode method
	
	/**
	 * Sets the value of the editing mode flag.
	 * 
	 * @param editingMode the value to which the editing mode flag is to be set.
	 */
	public void setEditingMode(boolean editingMode){
		this.editingMode = editingMode;
	}//end of setEditingMode method
	
	//OPERATIONS
	/**
	 * Sets the specified entity as the currently selected entity.
	 * 
	 * @param entity the entity to set as selected.
	 */
	public void selectEntity(Entity entity){
		if(entity != null){
			if(selectedEntity != null){
				selectedEntity.setGlow(false);
			}
			selectedEntity = entity;
			selectedEntity.setGlow(true);
		}
	}//end of selectEntity method
	
	/**
	 * Sets the specified entity as not selected. If the specified entity 
	 * was not already selected, no action is taken.
	 * 
	 * @param entity the entity to deselect.
	 */
	public void deselectEntity(Entity entity){
		if(entity != null){
			if(selectedEntity != null && entity.equals(selectedEntity)){
				selectedEntity.setGlow(false);
				selectedEntity = null;
			}
		}
	}//end of deselectEntity method
	
	/**
	 * Adds the specified environment object to the scenario.
	 * 
	 * @param obj the <code>EnvironmentObject</code> object for the 
	 * environment object to be added.
	 */
	public void addEnvironmentObject(EnvironmentObject obj){
		if(obj != null){
			environObjs.add(obj);
			Grid.update(obj);
			entityNode.attachChild(obj.getObj());
		}
	}//end of addEnvironmentObject method

	/**
	 * Removes the specified environment object from the scene.
	 * 
	 * @param obj the <code>EnvironmentObject</code> object to be removed.
	 */
	public void removeEnvironmentObject(EnvironmentObject obj){
		if(obj != null){
			environObjs.remove(obj);
			entityNode.detachChild(obj.getObj());
		}
	}//end of removeEnvironmentObject(EnvironmentObject) method

	/**
	 * Removes the environment object with the specified index from the scene.
	 * 
	 * @param index the index of the environment object to be removed.
	 */
	public void removeEnvironmentObject(int index){
		if(index > -1 && index < environObjs.size()){
			entityNode.detachChild(environObjs.get(index).getObj());
			environObjs.remove(index);
		}
	}//end of removeEnvironmentObject(int) method
	
	/**
	 * Adds the specified fish to the scenario.
	 * 
	 * @param fish the <code>Fish</code> object for the fish to be added.
	 */
	public void addFish(Fish fish){
		if(fish != null){
			this.fish.add(fish);
			entityNode.attachChild(fish.getObj());
		}
	}//end of addFish method

	/**
	 * Removes the specified fish from the scene.
	 * 
	 * @param fish the <code>Fish</code> object for the fish to be removed.
	 */
	public void removeFish(Fish fish){
		if(fish != null){
			this.fish.remove(fish);
			entityNode.detachChild(fish.getObj());
		}
	}//end of removeFish(Fish) method
	
	/**
	 * Removes the fish with the specified index from the scene.
	 * 
	 * @param index the index of the fish to be removed.
	 */
	public void removeFish(int index){
		if(index > -1 && index < fish.size()){
			entityNode.detachChild(fish.get(index).getObj());
			fish.remove(index);
		}
	}//end of removeFish(int) method

	/**
	 * Removes the ghosts from all cichlids in the scenario.
	 */
	public void clearGhosts(){
		for(Fish fish : this.fish){
			if(fish instanceof Cichlid){
				Cichlid cichlid = (Cichlid)fish;
				cichlid.removeGhost();
			}
		}
	}//end of clearGhosts method
	
	/**
	 * Sets up the scenario.
	 */
	public void init(){
		id = Main.RNG.nextLong();
		name = DEFAULT_NEW_SCENARIO_NAME;
		environ = null;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
		selectedEntity = null;
		movingMode = false;
		editingMode = false;
		hasPlayer = false;
		setupEnvironment();
	}//end of init method

	/**
	 * Initializes the <code>Environment</code> object and the base entity <code>Node</code>.
	 */
	private void setupEnvironment(){
		environ = new Environment();
		entityNode = new Node();
	}//end of setupEnvironment method
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * Two scenarios are considered equal if and only if their id numbers are equal.
	 * 
	 * @param obj the <code>Object</code> to compare.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj){
		boolean returnValue = false;
		if(obj instanceof Scenario){
			if(((Scenario)obj).getID() == this.id){
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
		init();
		id = stream.readLong();
		name = (String)(stream.readObject());
		environ = (Environment)(stream.readObject());
		int enObjsSize = stream.readInt();
		for(int i=0; i<enObjsSize; i++){
			addEnvironmentObject((EnvironmentObject)(stream.readObject()));
		}
		int fishSize = stream.readInt();
		for(int j=0; j<fishSize; j++){
			addFish((Fish)(stream.readObject()));
		}
		hasPlayer = stream.readBoolean();
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
		stream.writeObject(name);
		stream.writeObject(environ);
		stream.writeInt(environObjs.size());
		for(EnvironmentObject obj : environObjs){
			stream.writeObject(obj);
		}
		stream.writeInt(fish.size());
		for(Fish fish : this.fish){
			stream.writeObject(fish);
		}
		stream.writeBoolean(hasPlayer);
	}//end of writeObject method
	
	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Creates a base scenario with default values.
	 * 
	 * @return the created scenario.
	 */
	public static Scenario createScenario(){
		return new Scenario();
	}//end of createScenario method
	
	/**
	 * Creates a scenario with the specified name.
	 * 
	 * @param name the name for the scenario.
	 * @return the created scenario.
	 */
	public static Scenario createScenario(String name){
		return new Scenario(name);
	}//end of createScenario(String) method
	
	/**
	 * Creates a scenario with the specified name and environment.
	 * 
	 * @param name the name for the scenario.
	 * @param eviron the environment for the scenario.
	 * @return the created scenario.
	 */
	public static Scenario createScenario(String name, Environment eviron){
		return new Scenario(name,eviron);
	}//end of createScenario(String,Environment) method
	
	/**
	 * Creates a scenario for the specified default scenario definition.
	 * 
	 * @param def the default scenario to create.
	 * @return the created scenario.
	 */
	public static Scenario createScenario(DEFAULT_SCENARIO def){
		Scenario returnValue = null;
		if(def != null){
			returnValue = new Scenario(def.NAME);
			returnValue.getEnvironment().setTempCelcius(def.TANK_TEMP);
			returnValue.getEnvironment().setTank(Tank.createTank(def.TYPE));
		}
		return returnValue;
	}//end of createScenario(DEFAULT_SCENARIO) method
	
}//end of Scenario class