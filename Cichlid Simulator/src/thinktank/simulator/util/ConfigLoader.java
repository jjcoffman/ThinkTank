package thinktank.simulator.util;

import java.awt.Dimension;

import com.jme3.system.AppSettings;
import com.sun.javafx.tk.Toolkit;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 * 
 *          save, pull, commit&push
 * 
 */
public class ConfigLoader{
	//---------------------static constants----------------------------
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	private ConfigLoader(){}//not to be instantiated
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	public static AppSettings getSettings(){
		AppSettings returnValue = new AppSettings(true);
		returnValue.setFullscreen(false);
		returnValue.setVSync(false);
		returnValue.setResolution(640, 480);
		return returnValue;
	}//end of getSettings method
	
}//end of ConfigLoader class