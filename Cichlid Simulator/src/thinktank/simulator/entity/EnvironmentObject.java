package thinktank.simulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/*****************************************************************************************
 * Class: EnvironmentObject
 * Purpose: Assist in creating Environment Objects
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import com.jme3.scene.Spatial;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class EnvironmentObject extends Entity{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 3137180616296689370L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	public EnvironmentObject(){
		
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//OPERATIONS
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method
	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of EnvironmentObject class