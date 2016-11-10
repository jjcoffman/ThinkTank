
/*****************************************************************************************
 * Interface: Iterator
 * Purpose: inteface for an iterator to a collection
 * Author: Jonathan Coffman via Think Tank
 * Revisions:
 * Jonathan Coffman   -    Creation     -    3/10/16
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/
package thinktank.simulator.entity.collection;

/**
 * @deprecated
 * @author DoubleJ
 *
 */
public interface Iterator 
{
	public boolean hasNext();
	public Object getNext();
	public void decrement();
}
