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
}