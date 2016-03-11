/*****************************************************************************************
 * Class: IStrategy
 * Purpose: interface for strategies
 * Author: Jonathan Coffman via Think Tank
 * Revisions:
 * ??? - creation
 * Jonathan Coffman   -    changed to interface     -    3/10/16
 * 
 * 
 ****************************************************************************************/
package gameAssets.strategies;
import gameAssets.Cichlid;

public interface IStrategy 
{
	public void assign(Cichlid d);
}
