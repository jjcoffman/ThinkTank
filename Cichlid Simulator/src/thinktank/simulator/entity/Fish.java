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
	private float size, speed;
	private Color color;
	
	//---------------------constructors--------------------------------
	public Fish(){
		this.size = 1.0f;
		this.color = Color.BLACK;
	}//end of default constructor
	
	//---------------------instance methods----------------------------
	//GETTERS
	public float getSize(){
		return this.size;
	}//end of getSize method
	
	public Color getColor(){
		return this.color;
	}//end of getColor method
	
	public float getSpeed() {
		return this.speed;
	}//end of getSpeed method
	//SETTERS
	public void setSpeed(float speed){
		this.speed = speed;
	}//end of setSpeed method
	public void setSize(float size){
		this.size = size;
	}//end of setSize method
	
	public void setColor(Color color){
		this.color = color;
	}//end of setColor method
	
	//OPERATIONS
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		size = stream.readFloat();
		color = (Color)(stream.readObject());
	}//end of readObject method
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeFloat(size);
		stream.writeObject(color);
	}//end of writeObject method
	
	private void readObjectNoData() throws ObjectStreamException{}//end of readObjectNoData method

	public void move(float tpf) {
		System.out.println("umm...");
	}

	
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Fish class