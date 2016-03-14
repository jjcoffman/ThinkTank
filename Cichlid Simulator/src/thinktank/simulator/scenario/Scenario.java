package thinktank.simulator.scenario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Game.Main;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;
import thinktank.simulator.environment.Environment;

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
	private long id;
	private String name;
	private Environment environ;
	private ArrayList<EnvironmentObject> environObjs;
	private ArrayList<Fish> fish;
	
	//---------------------constructors--------------------------------
	public Scenario(){
		id = Main.RNG.nextLong();
		name = "Unnamed Scenario";
		environ = null;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
	}//end of default constructor
	
	public Scenario(String name){
		id = Main.RNG.nextLong();
		this.name = name;
		environ = null;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
	}//end of (String) constructor
	
	public Scenario(String name, Environment environment){
		id = Main.RNG.nextLong();
		this.name = name;
		this.environ = environment;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
	}//end of (String,Environment) constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public long getID(){
		return id;
	}//end of getID method
	
	public String getName(){
		return name;
	}//end of name method
	
	public Environment getEnvironment(){
		return environ;
	}//end of getEnvironment method
	
	public Iterator<EnvironmentObject> getEnvironmentObjects(){
		return environObjs.iterator();
	}//end of getEnvironmentObjects
	
	public Iterator<Fish> getFish(){
		return fish.iterator();
	}//end of getFish method
	
	//SETTERS
	public void setName(String name){
		this.name = name;
	}//end of setName method
	
	public void setEnvironment(Environment environment){
		this.environ = environment;
	}//end of setEnvironment method
	
	//OPERATIONS
	public void addEnvironmentObject(EnvironmentObject obj){
		if(obj != null){
			environObjs.add(obj);
		}
	}//end of addEnvironmentObject method
	
	public void removeEnvironmentObject(EnvironmentObject obj){
		if(obj != null){
			environObjs.remove(obj);
		}
	}//end of removeEnvironmentObject(EnvironmentObject) method
	
	public void removeEnvironmentObject(int index){
		if(index > -1 && index < environObjs.size()){
			environObjs.remove(index);
		}
	}//end of removeEnvironmentObject(int) method
	
	public void addFish(Fish fish){
		if(fish != null){
			this.fish.add(fish);
		}
	}//end of addFish method
	
	public void removeFish(Fish fish){
		if(fish != null){
			this.fish.remove(fish);
		}
	}//end of removeEnvironmentObject(EnvironmentObject) method
	
	public void removeFish(int index){
		if(index > -1 && index < fish.size()){
			fish.remove(index);
		}
	}//end of removeEnvironmentObject(int) method
	
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
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
		name = (String)(stream.readObject());
		environ = (Environment)(stream.readObject());
		int enObjsSize = stream.readInt();
		for(int i=0; i<enObjsSize; i++){
			environObjs.add((EnvironmentObject)(stream.readObject()));
		}
		int fishSize = stream.readInt();
		for(int j=0; j<fishSize; j++){
			fish.add((Fish)(stream.readObject()));
		}
	}//end of readObject method
	
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