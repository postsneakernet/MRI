package mri_scanner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class FileManager {    
    public static Image setImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return SwingFXUtils.toFXImage(img, null);
    }
}