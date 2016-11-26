package thinktank.simulator.util;

/**
 * Interface for classes that can be observed by other 
 * observer classes.
 * 
 * @author Robert Thompson
 * @version %I%, %G%
 */
public interface IObservable{
	
	/**
	 * Method invoked to register a class as an observer 
	 * of the observable class.
	 * 
	 * @param obs the observer to register.
	 */
	public void addObserver(IObserver obs);
	/**
	 * Method invoked to notify all registered observer 
	 * classes that the observable class has changed.
	 */
	public void notifyObservers();
	
}//end of IObservable interface