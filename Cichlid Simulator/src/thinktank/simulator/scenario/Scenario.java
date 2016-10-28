package thinktank.simulator.scenario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import Game.Main;
import thinktank.simulator.entity.Entity;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.entity.collection.SimulatorCollection;
import thinktank.simulator.environment.Environment;
import thinktank.simulator.environment.TANK_TYPE;
import thinktank.simulator.environment.Tank;

/*****************************************************************************************
 * Class: Scenario
 * Purpose: assists in creating scenarios
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
 * Manages the various objects and values which compose a given scenario. 
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class Scenario implements Serializable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -3983004414750924485L;
	
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
	private Entity selectedEntity;
	private boolean movingMode;
	private boolean editingMode;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default scenario.
	 */
	public Scenario(){
		init();
	}//end of default constructor
	
	/**
	 * Constructs a default scenario, but with the specified name.
	 * 
	 * @param name the name for the scenario.
	 */
	public Scenario(String name){
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
	public Scenario(String name, Environment environment){
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
	
	public Entity getSelectedEntity(){
		return selectedEntity;
	}//end of getSelectedEntity method
	
	public boolean isMovingMode(){
		return movingMode;
	}//end of isMovingMode method
	
	public boolean isEditingMode(){
		return editingMode;
	}//end of isEditingMode method
	
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
	
	public void setMovingMode(boolean movingMode){
		this.movingMode = movingMode;
	}//end of setMovingMode method
	
	public void setEditingMode(boolean editingMode){
		this.editingMode = editingMode;
	}//end of setEditingMode method
	
	//OPERATIONS
	public void selectEntity(Entity entity){
		if(entity != null){
			if(selectedEntity != null){
				selectedEntity.setGlow(false);
			}
			selectedEntity = entity;
			selectedEntity.setGlow(true);
		}
	}//end of selectEntity method
	
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
	 * Sets up the scenario.
	 */
	public void init(){
		id = Main.RNG.nextLong();
		name = "Unnamed Scenario";
		environ = null;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
		selectedEntity = null;
		movingMode = false;
		editingMode = false;
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
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Scenario class