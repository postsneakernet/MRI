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
    
    public void saveImage(ImageView editImage) {
        FileChooser fileChoose = new FileChooser();
        //setPicturesOnly(fileChoose);
        fileChoose.setTitle("View Pictures");
        fileChoose.setInitialDirectory(new File(System.getProperty("user.home")));        
        String filePath = directoryName + File.separator + 
        		fileChoose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        
        fileChoose.setTitle("Open a picture");
        File file = fileChoose.showOpenDialog(stage);
        List<Image> image = new ArrayList<>();
        
        if (file != null) {
            try {
                Picture = file.getName();
                image.add(new Image("file:" + file.getAbsolutePath(), 200, 200, true, true));
                editImage.setImage(image.get(0));

                File newFile = fileChoose.showSaveDialog(stage);
                if (newFile != null) {
                    //System.out.println(newFile.getAbsolutePath());
                    ImageIO.write(SwingFXUtils.fromFXImage(editImage.getImage(), null), "jpg", newFile);
                }
            }
            catch (IOException e) {
            	e.printStackTrace();
            }
        }
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