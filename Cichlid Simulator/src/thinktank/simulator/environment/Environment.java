package thinktank.simulator.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import Game.Main;
import thinktank.simulator.entity.EnvironmentObject;
import thinktank.simulator.entity.Fish;

/*****************************************************************************************
 * Class: Environment
 * Purpose: Inititates the environment and its attributes
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
 *
 */
public class Environment implements Serializable{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = -7243775336191321202L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private long id;
	private float tempCelcius;
	private Tank tank;
	
	//---------------------constructors--------------------------------
	public Environment(){
		id = Main.RNG.nextLong();
		tempCelcius = 0.0f;
		tank = null;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public long getID(){
		return id;
	}//end of getID method
	
	public float getTempCelcius(){
		return tempCelcius;
	}//end of getTempCelcius method
	
	public Tank getTank(){
		return tank;
	}//end of getTank method
	
	//SETTERS
	public void setTempCelcius(float temp){
		tempCelcius = temp;
	}//end of setTempCelcius method
	
	public void setTank(Tank tank){
		this.tank = tank;
	}//end of setTank method
	
	@Override
	public boolean equals(Object obj){
		boolean returnValue = false;
		if(obj instanceof Environment){
			if(((Environment)obj).getID() == id){
				returnValue = true;
			}
		}
		return returnValue;
	}//end of equals method
	
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		id = stream.readLong();
		//TODO setup tank. Tank needs to be changed from singleton. Best method would be to make Tank 
		//(as well as other classes with models) be able to get the AssetManager on their own without 
		//needing it to be passed in. As it works right now they can't really be accessed from anywhere 
		//but Main, which is not ideal.
		tempCelcius = stream.readFloat();
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeLong(id);
//		stream.writeObject(tank.getType()); //Disabled until Tank can be properly read in.
		stream.writeFloat(tempCelcius);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns the value, in world units, of the value specified in inches.
	 * 
	 * Uses the conversion value of 1.0 world units = 1.1811 inches.
	 * 
	 * @param inches the value to be converted from inches to world units.
	 * @return the converted value, in world units.
	 */
	public static float inchesToWorldUnits(float inches){
		float returnValue = -1.0f;
		if(inches > 0){
			returnValue = inches * 1.1811f;
		}
		return returnValue;
	}//end of inchesToWorldUnits method
}//end of Environment class