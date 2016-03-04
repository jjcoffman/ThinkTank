package thinktank.simulator.util;

/**
 * Interface for classes that can observe another class 
 * that is observable.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public interface IObserver{
	
	/**
	 * Method invoked to update the observer class with respect 
	 * to the observable class it is observing based on the 
	 * specified argument.
	 * 
	 * @param o the observable class.
	 * @param arg the argument.
	 */
	public void update(IObservable o, Object arg);

}//end of IObserver interface