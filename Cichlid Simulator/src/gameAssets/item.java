package gameAssets;

import java.awt.Color;
import java.util.Queue;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.material.Material;

/**
 * 
 * @deprecated
 *
 */
public class item
{
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
	
}
