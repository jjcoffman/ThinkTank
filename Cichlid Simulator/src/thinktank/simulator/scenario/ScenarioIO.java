package thinktank.simulator.scenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Provides functions to load and save scenarios as individual, external files. 
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class ScenarioIO{
	//---------------------static constants----------------------------
	/**
	 * Constant value for the file extension associated with scenario files.
	 */
	public static final String SCENARIO_FILE_EXTENSION = ".cichlid";
	public static final String SCENARIO_FOLDER = "scenarios";
	
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Saves the specified <code>Scenario</code> to the specified file.
	 * 
	 * @param scenario the <code>Scenario</code> to be saved.
	 * @param file the <code>File</code> object representing the location where 
	 * the scenario will be saved.
	 * @return true if the scenario was saved successfully, false otherwise.
	 */
	public static boolean saveScenario(Scenario scenario, File file){
		boolean returnValue = false;
		checkScenariosFolder();
		if(file == null){
			return false;
		}
		else{
			String filePath = file.getPath();
			if(!filePath.toLowerCase().endsWith(SCENARIO_FILE_EXTENSION)){
			    file = new File(SCENARIO_FOLDER + File.separator + filePath + SCENARIO_FILE_EXTENSION);
			}
		}
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			if(!saveScenario(scenario, oos)){
				//error
			}
			else{//success
				returnValue = true;
			}
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			if(oos != null){
				try{
					oos.close();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
			if(fos != null){
				try{
					fos.close();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		
		return returnValue;
	}//end of saveScenario method
	
	/**
	 * Private helper class for saving a scenario.
	 * 
	 * @param scenario the scenario.
	 * @param out the <code>ObjectOutputStream</code> object.
	 * @return true if the scenario was saved successfully, false otherwise.
	 */
	private static boolean saveScenario(Scenario scenario, ObjectOutputStream out){
		boolean returnValue = false;
		try{
			out.writeObject(scenario);
			returnValue = true;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		return returnValue;
	}//end of saveScenario method
	
	/**
	 * Loads the scenario stored in the specified file.
	 * 
	 * @param file the <code>File</code> object representing the file where 
	 * the scenario is to be loaded from.
	 * @return the <code>Scenario</code> object loaded from the file.
	 */
	public static Scenario loadScenario(File file){
		Scenario returnValue = null;

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			returnValue = loadScenario(ois);
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			if(ois != null){
				try{
					ois.close();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
			if(fis != null){
				try{
					fis.close();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return returnValue;
	}//end of loadScenario method
	
	/**
	 * Private helper class for loading a scenario.
	 * 
	 * @param in the <code>ObjectInputStream</code> object.
	 * @return the scenario.
	 */
	private static Scenario loadScenario(ObjectInputStream in){
		Scenario returnValue = null;
		try{
			returnValue = (Scenario)(in.readObject());
		}
		catch(IOException ex){
			returnValue = null;
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex){
			returnValue = null;
			ex.printStackTrace();
		}
		return returnValue;
	}//end of loadScenario method
	
	public static boolean deleteSavedScenario(File file){
		boolean returnValue = false;
		checkScenariosFolder();
		String filePath = file.getPath();
	    file = new File(SCENARIO_FOLDER + File.separator + filePath + SCENARIO_FILE_EXTENSION);
		if(file.exists() && file.isFile()){
			try{
				file.delete();
				returnValue = true;
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return returnValue;
	}//end of deleteSavedScenario method
	
	public static ArrayList<String> getSavedScenarioList(){
		ArrayList<String> returnValue = new ArrayList<String>();
		checkScenariosFolder();
		File folder = new File(SCENARIO_FOLDER);
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles){
			if(file.isFile() && file.getName().endsWith(SCENARIO_FILE_EXTENSION)){
				returnValue.add(file.getName().substring(0, file.getName().length()-8));
			}
		}
		return returnValue;
	}//end of getSavedScenarioList method
	
	private static void checkScenariosFolder(){
		File folder = new File(SCENARIO_FOLDER);
		if(!folder.exists() || !folder.isDirectory()){
			folder.mkdirs();
		}
	}//end of checkScenariosFolder method
	
}//end of ScenarioIO class