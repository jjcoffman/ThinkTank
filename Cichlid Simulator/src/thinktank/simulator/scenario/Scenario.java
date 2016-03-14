package thinktank.simulator.scenario;

import java.util.ArrayList;
import java.util.Iterator;

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
public class Scenario{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private Environment environ;
	private ArrayList<EnvironmentObject> environObjs;
	private ArrayList<Fish> fish;
	
	//---------------------constructors--------------------------------
	public Scenario(){
		environ = null;
		environObjs = new ArrayList<EnvironmentObject>();
		fish = new ArrayList<Fish>();
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
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
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Scenario class