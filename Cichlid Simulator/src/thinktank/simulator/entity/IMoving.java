package thinktank.simulator.entity;

/**
 * The Interface for processing movement of objects in the environment.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 *
 */
public interface IMoving {
	
	/**
	 * The method that is invoked to move the object within 
	 * the environment. This movement is controlled in a way
	 * specified by the method itself and not controlled by
	 * the user.
	 * 
	 * @param delta the time interval over which the move is 
	 * to take place.
	 */
	void move(double delta);

}//end of IMoving interface