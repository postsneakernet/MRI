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
import javafx.scene.image.Image;
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
    private ImageView imageChoose5;
    @FXML
    private ImageView imageChoose6;
    @FXML
    private ImageView mainImage;
    
    @FXML
    private Button buttonImage1;
    @FXML
    private Button buttonImage2;
    @FXML
    private Button buttonImage3;
    @FXML
    private Button buttonImage4;
    @FXML
    private Button buttonImage5;
    @FXML
    private Button buttonImage6;
    
    private String dir = null;
    private String sep = File.separator;
    
    // TODO Method is called from back button: change button label to "Select" (for temp prototype)
    // TODO or (preferably) implement ImageView buttons to handle selecting any image for main image
    public void setImages(ActionEvent event) {
    	if (dir != null) {
    		mainImage.setImage(FileManager.setImage(dir + sep + "mri2.jpg"));
    	}
    	else {
    		System.out.println("No directory selected!");
    	}
    }
    
    public void setImage1(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose1.getImage());
    	imageChoose1.setImage(temp);
    }
    
    public void setImage2(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose2.getImage());
    	imageChoose2.setImage(temp);
    }
    
    public void setImage3(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose3.getImage());
    	imageChoose3.setImage(temp);
    }
    
    public void setImage4(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose4.getImage());
    	imageChoose4.setImage(temp);
    }
    
    public void setImage5(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose5.getImage());
    	imageChoose5.setImage(temp);
    }
    
    public void setImage6(ActionEvent event){
    	Image temp = mainImage.getImage();
    	mainImage.setImage(imageChoose6.getImage());
    	imageChoose6.setImage(temp);
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
    		imageChoose1.setImage(FileManager.setImage(dir + sep + file.listFiles()[0].getName()));
    		imageChoose2.setImage(FileManager.setImage(dir + sep + file.listFiles()[1].getName()));
    		imageChoose3.setImage(FileManager.setImage(dir + sep + file.listFiles()[2].getName()));
    		imageChoose4.setImage(FileManager.setImage(dir + sep + file.listFiles()[3].getName()));
    	}
    }
        
    public void analyzeImage(ActionEvent event) {
    	if (dir != null) {
    		mainImage.setImage(FileManager.setImage(Analyze.analyzeImage(dir + sep + "mri2.jpg")));
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