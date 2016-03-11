package thinktank.simulator.util;

import java.awt.Dimension;

import com.jme3.system.AppSettings;
import com.sun.javafx.tk.Toolkit;

/**
 * 
 * @author Bob Thompson
 * @version %I%, %G%
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
		AppSettings returnValue = null;
		
		returnValue = loadConfigFile();//attempt to load from file
		
		if(returnValue == null){//if load fails, use defaults
			returnValue = loadDefaults();
		}
		
		return returnValue;
	}//end of getSettings method
	
	private static AppSettings loadConfigFile(){
		AppSettings returnValue = null;
		//TODO implement loading config file
		return returnValue;
	}//end of loadConfigFile method
	
	private static AppSettings loadDefaults(){
		AppSettings returnValue = new AppSettings(true);
		returnValue.setFullscreen((boolean)DEFAULT_SETTINGS.FULLSCREEN.VALUE);
		returnValue.setVSync((boolean)DEFAULT_SETTINGS.VSYNC.VALUE);
		returnValue.setResolution((int)DEFAULT_SETTINGS.RESOLUTION_WIDTH.VALUE, (int)DEFAULT_SETTINGS.RESOLUTION_HEIGHT.VALUE);
		return returnValue;
	}//end of loadDefaults method
	
}//end of ConfigLoader class