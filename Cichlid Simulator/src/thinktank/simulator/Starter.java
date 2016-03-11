package thinktank.simulator;

import com.jme3.system.AppSettings;

import Game.Main;
import thinktank.simulator.util.ConfigLoader;

//itworks
/**
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
	private static Main client;

	// ---------------------instance constants--------------------------
	// ---------------------instance variables--------------------------
	// ---------------------constructors--------------------------------
	private Starter() {
	}// Starter is not to be instantiated

	// ---------------------instance methods----------------------------
	// ---------------------static main---------------------------------
	public static void main(String[] args) {
		client = new Main();
		client.setShowSettings(false);
		client.setSettings(ConfigLoader.getSettings());
		client.start();
	}// end of main method

	// ---------------------static methods------------------------------
}// end of Starter class