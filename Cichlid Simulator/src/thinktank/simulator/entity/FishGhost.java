package thinktank.simulator.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;

public class FishGhost extends GhostControl {
	private Fish owner;
	
	public FishGhost(CollisionShape ghostShape, Fish owner) {
		super(ghostShape);
		this.owner = owner;
	}

	public FishGhost() {
		super();
	}

	public Fish getOwner(){
		return owner;
	}
}
