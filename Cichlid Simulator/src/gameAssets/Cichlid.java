package gameAssets;
import java.awt.Color;

public class Cichlid 
{
	private float size;
	private float speed;
	private Color color;
	private String sex;
	//have to determine which we want to be our own types or existing
	//another useless comment
	public Cichlid()
	{
		
	}
	
	public Cichlid(float size, float speed, String sex){
		this.size = size;
		this.speed = speed;
		this.sex = sex;
	}
	public String getSex(){
		return this.sex;
	}
	public float getSize(){
		return this.size;
	}
	public float getSpeed(){
		return this.speed;
	}
	public Color getColor(){
		return this.color;
	}
	public void setSize(float size){
		this.size = size;
	}
	public void setSpeed(float speed){
		this.speed = speed;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
}
