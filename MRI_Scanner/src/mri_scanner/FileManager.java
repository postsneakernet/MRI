package mri_scanner;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class FileManager {
	public final static String sep = File.separator;
	public final static String initialDir = "user.dir";
	public final static String areaData = "area.dat";
	public final static String mriHelp = "MRI_Help.pdf";
	public final static int MRI_IMAGE_AMOUNT = 8;
	
    public static Image setImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return SwingFXUtils.toFXImage(img, null);
    }
    
    /*
     * Opens user manual
     */
    public static void getHelp() {
    	String env = System.getProperty("os.name");
    	try {
			if (env.contains("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +
						System.getProperty(initialDir) + sep + mriHelp);
			} else if (env.contains("Mac")) {
				Runtime.getRuntime().exec("open " + System.getProperty(initialDir) + sep + mriHelp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * Reads area for each slice for a month from a file and returns area in an array
     */
    public static Integer[] readData(String path) {
    	String fileName = path + sep + areaData;
    	Integer[] array = new Integer[MRI_IMAGE_AMOUNT];

    	try (Scanner scan = new Scanner(new File(fileName))) {
			for (int i = 0; i < array.length; i++) {
				array[i] = Integer.parseInt(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	return array;
    }
    
    /*
     * Saves area of tumor for entire month to file in the month's directory
     */
    public static void saveData(String path, Integer[] array) {
    	String fileName = path + sep + areaData;

    	try (BufferedWriter writer = new BufferedWriter(
    			new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
    	    
    	    for (int i = 0; i < array.length; i++) {
				writer.write(String.valueOf(array[i]));
				writer.newLine();
			}
    	} catch (IOException e) {
    	  e.printStackTrace();
    	}
    }
    
    /*
     * Checks that selected directory has appropriate data
     * Changes static variable monthTotal in FXMLDocumentController
     */
    public static boolean validateDir(File patientMonths) {
    	int jpgCount = 0;
    	FXMLDocumentController.monthTotal = 0;
    	
    	for (int i = 0; i < patientMonths.listFiles().length; i++) {
    		File month = patientMonths.listFiles()[i];
    		
			if (month.isDirectory()) {
				FXMLDocumentController.monthTotal++;
				for (int j = 0; j < month.listFiles().length; j++) {
					String f = month.listFiles()[j].getName();
	    			String[] split = f.split("\\.");
	    			
	    			if (split.length > 1 && split[1].equals("jpg")) {
	    				jpgCount++;
	    			}
				}
			} else {
				System.out.println("Patient root contains non-directory files.");
				return false;
			}
		}

    	return jpgCount > 0 && jpgCount % FileManager.MRI_IMAGE_AMOUNT == 0;
    }
}