package gameAssets;

import java.awt.Color;
import java.util.Queue;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

public class item extends Spatial
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
	@Override
	public int collideWith(Collidable arg0, CollisionResults arg1) throws UnsupportedCollisionException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void breadthFirstTraversal(SceneGraphVisitor arg0, Queue<Spatial> arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Spatial deepClone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void depthFirstTraversal(SceneGraphVisitor arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getTriangleCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getVertexCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setModelBound(BoundingVolume arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateModelBound() {
		// TODO Auto-generated method stub
		
	}
	
}
