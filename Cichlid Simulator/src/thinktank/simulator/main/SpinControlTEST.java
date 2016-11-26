package thinktank.simulator.main;
/*****************************************************************************************
 * Class: SpinControlTEST
 * Purpose: creates an AbstractControl to spin a fish for DEMO ONLY
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added class and class header via JME2 tutorials
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
import java.io.IOException;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * 
 * @deprecated
 */
public class SpinControlTEST  extends AbstractControl implements Savable, Cloneable 
{
	  private int index; // can have custom fields -- example 
	  
	  public SpinControlTEST(){} // empty serialization constructor
	 
	  /** Optional custom constructor with arguments that can init custom fields.
	    * Note: you cannot modify the spatial here yet! */
	  public SpinControlTEST(int i){ 
	    // index=i; // example 
	  } 
	 
	  /** This method is called when the control is added to the spatial,
	    * and when the control is removed from the spatial (setting a null value).
	    * It can be used for both initialization and cleanup. */    
	  @Override
	  public void setSpatial(Spatial spatial) {
	    super.setSpatial(spatial);
	    /* Example:
	    if (spatial != null){
	        // initialize
	    }else{
	        // cleanup
	    }
	    */
	  }
	 
	 
	  /** Implement your spatial's behaviour here.
	    * From here you can modify the scene graph and the spatial
	    * (transform them, get and set userdata, etc).
	    * This loop controls the spatial while the Control is enabled. */
	  @Override
	  protected void controlUpdate(float tpf){
	    if(spatial != null) {
	    	//spatial.move(0, 1*tpf, 0);
	    	spatial.rotate(0,1*tpf,0);
	    }
	  }
	 
	  @Override
	  public Control cloneForSpatial(Spatial spatial){
	    final SpinControlTEST control = new SpinControlTEST();
	    /* Optional: use setters to copy userdata into the cloned control */
	    // control.setIndex(i); // example
	    control.setSpatial(spatial);
	    return control;
	  }
	 
	  @Override
	  protected void controlRender(RenderManager rm, ViewPort vp){
	     /* Optional: rendering manipulation (for advanced users) */
	  }
	 
	  @Override
	  public void read(JmeImporter im) throws IOException {
	      super.read(im);
	      // im.getCapsule(this).read(...);
	  }
	 
	}

