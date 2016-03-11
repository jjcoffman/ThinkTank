/*****************************************************************************************
 * Class: Simulator Collection
 * Purpose: To hold the collection of objects placed into the simulator world
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

import java.util.Vector;

public class SimulatorCollection implements Collection
{
	//create our data structure
	private Vector<Object> theCollection;
	public SimulatorCollection()
	{
		theCollection = new Vector<Object>();
	}
	
	//add an object to the collection
	public void add(Object newObject) 
	{
		theCollection.addElement(newObject);
		
	}
	
	//remove an object from the collection
	public void remove(Object remove, Iterator i)
	{
		theCollection.remove(remove);
		if(!(i==null))
			i.decrement();
		
	}
	
	
	//return an iterator for the collection
	public Iterator getIterator() 
	{
		
		return new CollectionIterator();
	}
	
	//our private class that creates an iterator that can be used to step through the collection.
	private class CollectionIterator implements Iterator
	{
		private int currElementIndex;
		public CollectionIterator()
		{
			currElementIndex = -1;
		}
		
		//checks if there are more values.
		public boolean hasNext() 
		{
			if(theCollection.size() <= 0 || currElementIndex == theCollection.size() -1)
				return false;
			return true;
		}

		//returns the object thats next in the collection.
		public Object getNext() 
		{
			return (theCollection.get(++currElementIndex));
		}

		
		public void decrement() 
		{
			--currElementIndex;
			
		}
		
	}
}
