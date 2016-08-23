package thinktank.simulator.entity;
/*****************************************************************************************
 * Class: Fish
 * Purpose: Outlines the details of a fish Entity and contains its accessors/mutators
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Concrete type of <code>Entity</code> meant to serve as a base for 
 * any more specific type of fish.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Fish extends Entity{
	//---------------------static constants----------------------------
	private static final long serialVersionUID = 8860191503415305251L;
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	/**
	 * Value representing the gender of this fish.
	 */
	private String sex;
	/**
	 * The name for this fish.
	 */
	private String name;
	/**
	 * Values for the general size and speed of this fish.
	 */
	private float size, speed;
	/**
	 * Value for the general color of this fish.
	 */
	private Color color;
	
	//---------------------constructors--------------------------------
	/**
	 * Constructs a basic, default fish.
	 */
	public Fish(){
		this.size = 1.0f;
		this.color = Color.BLACK;
		this.sex = "male";
		this.name = "unnamed";
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	/**
	 * Gets the value for the size of this fish.
	 * 
	 * @return the value for the size of this fish.
	 */
	public float getSize(){
		return this.size;
	}//end of getSize method

	/**
	 * Gets the value for the color of this fish.
	 * 
	 * @return the value for the color of this fish.
	 */
	public Color getColor(){
		return this.color;
	}//end of getColor method

	/**
	 * Gets the value for the speed of this fish.
	 * 
	 * @return the value for the speed of this fish.
	 */
	public float getSpeed(){
		return this.speed;
	}//end of getSpeed method
	
	/**
	 * Returns the sex of this fish.
	 * 
	 * @return the sex of this fish.
	 */
	public String getSex(){
		return sex;
	}//end of getSex method
	
	/**
	 * Gets the name of this fish.
	 * 
	 * @return the name of this fish.
	 */
	public String getName(){
		return name;
	}//end of getName method
	
	//SETTERS
	/**
	 * Sets the value for the speed of this fish to the specified value.
	 * 
	 * @param speed the value to which this fish's speed is to be set.
	 */
	public void setSpeed(float speed){
		this.speed = speed;
	}//end of setSpeed method

	/**
	 * Sets the value for the size of this fish to the specified value.
	 * 
	 * @param size the value to which this fish's size is to be set.
	 */
	public void setSize(float size){
		this.size = size;
	}//end of setSize method

	/**
	 * Sets the value for the color of this fish to the specified value.
	 * 
	 * @param color the value to which this fish's color is to be set.
	 */
	public void setColor(Color color){
		this.color = color;
	}//end of setColor method
	
	/**
	 * Sets the sex of this fish to the specified value.
	 * 
	 * @param sex the value to which the sex is to be set.
	 */
	public void setSex(String sex){
		this.sex = sex;
	}//end of setSex method
	
	/**
	 * Sets the name of this fish to the specified value.
	 * 
	 * @param name the value to which the name is to be set.
	 */
	public void setName(String name){
		this.name = name;
	}//end of setName method
	
	//OPERATIONS
	/**
	 * Method called in the update loop to move the fish based on the 
	 * specified elapsed time since the last update.
	 * 
	 * @param tpf the elapsed time since the last update.
	 */
	public void move(float tpf){
		System.out.println("umm...");
		//TODO implement or remove
	}//end of move method

	/**
	 * The readObject method is responsible for reading from the stream and restoring 
	 * the fields of the class.
	 * 
	 * @param stream the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		size = stream.readFloat();
		speed = stream.readFloat();
		color = (Color)(stream.readObject());
	}//end of readObject method

	/**
	 * The writeObject method is responsible for writing the state of the object 
	 * so that the corresponding readObject method can restore it.
	 * 
	 * @param stream the output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeFloat(size);
		stream.writeFloat(speed);
		stream.writeObject(color);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method

	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Fish class