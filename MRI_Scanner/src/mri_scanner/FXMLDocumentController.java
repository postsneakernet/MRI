package mri_scanner;


import analysis.Analyze;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Brian
 */
public class FXMLDocumentController extends AnchorPane implements Initializable {
    private MRIPrototype application;
    
    @FXML
    private Label progName;
    
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonSettings;
    @FXML
    private Button buttonSelectFolder;
    @FXML
    private Button buttonAnalyze;
    
    @FXML
    private ImageView imageChoose1;
    @FXML
    private ImageView imageChoose2;
    @FXML
    private ImageView imageChoose3;
    @FXML
    private ImageView imageChoose4;
    @FXML
    private ImageView mainImage;
    
    private String dir = null;
    private String sep = File.separator;
    
    // TODO Method is called from back button: change button label to "Select" (for temp prototype)
    // TODO or (preferably) implement ImageView buttons to handle selecting any image for main image
    public void setImages(ActionEvent event) {
    	if (dir != null) {
    		mainImage.setImage(fileManager.setImage(dir + sep + "mri2.jpg"));
    	}
    	else {
    		System.out.println("No directory selected!");
    	}
    }
    
    public void getImageDirectory(ActionEvent event) {
    	String initialDir = "user.dir";
    	
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setInitialDirectory(new File(System.getProperty(initialDir)));
    	directoryChooser.setTitle("Select MRI image directory");
    	
    	File file = directoryChooser.showDialog(null);
    	
    	// TODO to modify code to work on any number of images
    	// TODO also check to operate only on images to avoid crash
    	if (file != null) {
    		dir = file.getPath();
    		System.out.println("Loading images in: " + dir);
    		imageChoose1.setImage(fileManager.setImage(dir + sep + file.listFiles()[0].getName()));
    		imageChoose2.setImage(fileManager.setImage(dir + sep + file.listFiles()[1].getName()));
    		imageChoose3.setImage(fileManager.setImage(dir + sep + file.listFiles()[2].getName()));
    		imageChoose4.setImage(fileManager.setImage(dir + sep + file.listFiles()[3].getName()));
    	}
    }
        
    public void analyzeImage(ActionEvent event) {
    	if (dir != null) {
    		mainImage.setImage(fileManager.setImage(Analyze.analyzeImage(dir + sep + "mri2.jpg")));
    	}
    	else {
    		System.out.println("No directory selected!");
    	}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }    
    
   	void setApp(MRIPrototype application) {
	    this.application = application;
	    throw new UnsupportedOperationException("Not supported yet.");
    }
}