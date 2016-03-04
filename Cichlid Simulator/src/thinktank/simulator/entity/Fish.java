package thinktank.simulator.entity;

import java.awt.Color;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public class Fish extends Entity{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	private float size;
	private Color color;
	
	public float getSize(){
		return this.size;
	}
	public Color getColor(){
		return this.color;
	}
	public void setSize(float size){
		this.size = size;
	}
	public void setColor(Color color){
		this.color = color;
	}
	//---------------------constructors--------------------------------
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
}//end of Fish class