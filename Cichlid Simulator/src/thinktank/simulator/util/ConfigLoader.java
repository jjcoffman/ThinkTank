package thinktank.simulator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.jme3.system.AppSettings;

/**
 * Class providing a concise means of getting the settings for the game, 
 * first checking for a file at "/settings/config.txt" and reading the 
 * settings from that file, if it exists. Any settings not contained in 
 * the file are set to the default values, as specified in 
 * <code>thinktank.simulator.util.DEFAULT_SETTINGS</code>.
 * 
 * The file "config.txt" will primarily be produced by the <code>
 * ConfigSaver</code>, but can also be manually edited. The structure 
 * of the entries should be:
 * 
 * 		setting-name=value
 * 
 * with one setting specified per line. The order in which the entries 
 * are listed has no effect.
 * 
 * @author Bob Thompson
 * @version %I%, %G%
 */
public class ConfigLoader{
	//---------------------static constants----------------------------
	public static final String SETTINGS_FOLDER = "settings";
	//---------------------static variables----------------------------
	//---------------------instance constants--------------------------
	//---------------------instance variables--------------------------
	//---------------------constructors--------------------------------
	/**
	 * Provides only static methods.
	 * This class is not meant to be instantiated.
	 */
	private ConfigLoader(){}
	
	//---------------------instance methods----------------------------
	//---------------------static main---------------------------------
	//---------------------static methods------------------------------
	/**
	 * Returns the <code>AppSettings</code> object containing the configuration 
	 * values for the game.
	 * 
	 * @return the settings for the game.
	 */
	public static AppSettings getSettings(){
		AppSettings returnValue = null;
		
		returnValue = loadConfigFile();//attempt to load from file
		
		if(returnValue == null){//if load fails, use defaults
			returnValue = loadDefaults();
		}
		
		return returnValue;
	}//end of getSettings method
	
	/**
	 * Private helper method. Loads the "config.txt" file and reads the values 
	 * from it, setting the values in the AppSettings object accordingly.
	 * 
	 * @return the settings for the game.
	 */
	private static AppSettings loadConfigFile(){
		System.out.println("attempt load config file...");
		AppSettings returnValue = null;
		Charset charset = Charset.forName("US-ASCII");
		checkSettingsFolder();
		Path path = FileSystems.getDefault().getPath(SETTINGS_FOLDER, "config.txt");
		checkConfigFile(path);
		try(BufferedReader reader = Files.newBufferedReader(path, charset)){
	        ArrayList<String[]> tokenList = new ArrayList<String[]>();
		    String line = null;
		    while((line = reader.readLine()) != null){
		    	System.out.println(line);
		        if(line.length() > 0){
		        	tokenList.add(tokenizeLine(line));
		        }
		    }
		    returnValue = applyTokens(tokenList);
		}
		catch(IOException x){
		    System.err.format("IOException: %s%n", x);
		}
		return returnValue;
	}//end of loadConfigFile method
	
	/**
	 * Private helper method. Loads the default values for the settings as 
	 * specified in the <code>DEFAULT_SETTINGS</code> enumerated type.
	 * 
	 * @return the default settings for the game.
	 */
	private static AppSettings loadDefaults(){
		AppSettings returnValue = new AppSettings(true);
		returnValue.setFullscreen((boolean)DEFAULT_SETTINGS.FULLSCREEN.VALUE);
		returnValue.setVSync((boolean)DEFAULT_SETTINGS.VSYNC.VALUE);
		returnValue.setResolution((int)DEFAULT_SETTINGS.RESOLUTION_WIDTH.VALUE, (int)DEFAULT_SETTINGS.RESOLUTION_HEIGHT.VALUE);
		returnValue.setMinResolution((int)DEFAULT_SETTINGS.MIN_WIDTH.VALUE, (int)DEFAULT_SETTINGS.MIN_HEIGHT.VALUE);
		returnValue.setBitsPerPixel((int)DEFAULT_SETTINGS.BITS_PER_PIXEL.VALUE);
		returnValue.setDepthBits((int)DEFAULT_SETTINGS.DEPTH_BITS.VALUE);
		returnValue.setFrameRate((int)DEFAULT_SETTINGS.FRAME_RATE.VALUE);
		returnValue.setFrequency((int)DEFAULT_SETTINGS.REFRESH_RATE.VALUE);
		returnValue.setSamples((int)DEFAULT_SETTINGS.SAMPLES.VALUE);
		returnValue.setStereo3D((boolean)DEFAULT_SETTINGS.STEREO_3D.VALUE);
		returnValue.setTitle((String)DEFAULT_SETTINGS.TITLE.VALUE);
//		returnValue.setIcons();//TODO make & import icons
		return returnValue;
	}//end of loadDefaults method
	
	/**
	 * Private helper method. Parses the specified line, turning it into a 
	 * two-token pair, with the first entry representing the setting name, 
	 * and the second entry representing the corresponding value. The entries 
	 * are read based on a separating '=' character.
	 * 
	 * @param line the string to be parsed.
	 * @return the name/value string pair derived from the line. A line that 
	 * is not correctly formatted (e.g. if it does not contain and '=' character) 
	 * will return a pair of "invalid" and "invalid"
	 */
	private static String[] tokenizeLine(String line){
		String[] returnValue = new String[2];
		int delimiterIndex = 0;
		for(int i=0; i<line.length(); i++){
			if(line.charAt(i) == '='){
				delimiterIndex = i;
				break;
			}
		}
		if(delimiterIndex != 0){
			returnValue[0] = line.substring(0, delimiterIndex);
			returnValue[1] = line.substring(delimiterIndex+1);
		}
		else{
			returnValue[0] = "invalid";
			returnValue[1] = "invalid";
		}
		return returnValue;
	}//end of tokenizeLine method
	
	/**
	 * Private helper method. Sets all values read from the "config.txt" file to 
	 * the appropriate setting in the <code>AppSettings</code> object.
	 *  
	 * @param tokenList the list of name/value pairs read from the file.
	 * @return the settings for the game.
	 */
	private static AppSettings applyTokens(ArrayList<String[]> tokenList){
		AppSettings returnValue = loadDefaults();
		for(String[] line : tokenList){
			if(line[0].equals(DEFAULT_SETTINGS.FULLSCREEN.NAME)){
				returnValue.setFullscreen(Boolean.parseBoolean(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.VSYNC.NAME)){
				returnValue.setVSync(Boolean.parseBoolean(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.RESOLUTION_WIDTH.NAME)){
				returnValue.setWidth(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.RESOLUTION_HEIGHT.NAME)){
				returnValue.setHeight(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.BITS_PER_PIXEL.NAME)){
				returnValue.setBitsPerPixel(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.DEPTH_BITS.NAME)){
				returnValue.setDepthBits(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.FRAME_RATE.NAME)){
				returnValue.setFrameRate(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.REFRESH_RATE.NAME)){
				returnValue.setFrequency(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.MIN_HEIGHT.NAME)){
				returnValue.setMinHeight(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.MIN_WIDTH.NAME)){
				returnValue.setMinWidth(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.SAMPLES.NAME)){
				returnValue.setSamples(Integer.parseInt(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.STEREO_3D.NAME)){
				returnValue.setStereo3D(Boolean.parseBoolean(line[1]));
			}
			else if(line[0].equals(DEFAULT_SETTINGS.TITLE.NAME)){
				returnValue.setTitle(line[1]);
			}
			//TODO set icons
		}
		return returnValue;
	}//end of applyTokens method
	
	private static void checkConfigFile(Path path){
		File config = path.toFile();
		if(!config.exists()){
			try{
				Files.createFile(path);
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}//end of checkConfigFile method
	
	public static void checkSettingsFolder(){
		File folder = new File(SETTINGS_FOLDER);
		if(!folder.exists() || !folder.isDirectory()){
			folder.mkdirs();
		}
	}//end of checkSettingsFolder method
	
}//end of ConfigLoader class