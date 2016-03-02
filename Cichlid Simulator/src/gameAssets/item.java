package gameAssets;

import java.awt.Color;

public class item
{
	private float size;
	private Color color;
	private gameAssets.Strategy Strategy;
	
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
	
	
}
