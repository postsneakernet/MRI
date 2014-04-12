package mri_scanner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 *
 * @author Brian
 */
public class FileManager {
    private Window stage;
    private final String directoryName = "MRI_Scans";
    private String Picture;
    
    public void makeDir() {
		File directory = new File(directoryName);
		directory.mkdir();
    }
    
    public static Image setImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        }    
        catch (IOException e) {
        	e.printStackTrace();
        }
        return SwingFXUtils.toFXImage(img, null);
    }
}