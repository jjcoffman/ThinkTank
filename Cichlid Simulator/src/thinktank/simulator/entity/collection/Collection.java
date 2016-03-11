
/*****************************************************************************************
 * Class: Collection
 * Purpose: Interface to allow adding objects to a collection and an iterator
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Created Class
 * 3/11/16 - JC - Added CLass Header
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
