
/*****************************************************************************************
 * Class: Collection
 * Purpose: inteface for a generic collection
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
public interface Collection 
{
	public void add(Object newObject);
	public Iterator getIterator();
}
