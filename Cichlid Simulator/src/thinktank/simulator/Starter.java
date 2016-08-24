package thinktank.simulator;
/*****************************************************************************************
 * Class: Starter
 * Purpose: Inititates the game
 * Author: Think Tank
 * Revisions:
 * 3/11/16 - JC - Added Class Header
 * 
 * 
 * 
 * 
 * 
 ****************************************************************************************/

import Game.Main;
import thinktank.simulator.util.ConfigLoader;

/**
 * Application initialization class containing the main method 
 * and initializing the game's <code>Main</code> object and loading 
 * the application settings.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 * 
 *          save, pull, commit&push
 * 
 */
public class Starter {
	// ---------------------static constants----------------------------
	// ---------------------static variables----------------------------
	/**
	 * The main client object for the game.
	 */
	private static Main client;

	// ---------------------instance constants--------------------------
	// ---------------------instance variables--------------------------
	// ---------------------constructors--------------------------------
	/**
	 * Provides only static methods.
	 * This class is not meant to be instantiated.
	 */
	private Starter(){}

	// ---------------------instance methods----------------------------
	// ---------------------static main---------------------------------
	/**
	 * Called when the application is first run.
	 * 
	 * @param args arguments passed to the application at the command line.
	 */
	public static void main(String[] args){
		client = new Main();
		client.setShowSettings(false);
		client.setSettings(ConfigLoader.getSettings());
		client.start();
	}// end of main method

	// ---------------------static methods------------------------------
	/**
	 * Gets a reference to the main client object for the game.
	 * 
	 * @return the main client object.
	 */
	public static Main getClient(){
		return client;
	}//end of getClient method
	
}// end of Starter class