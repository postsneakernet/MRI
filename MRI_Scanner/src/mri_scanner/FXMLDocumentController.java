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
    private ImageView imageChoose7;
    @FXML
    private ImageView imageChoose8;
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
    @FXML
    private Button buttonImage7;
    @FXML
    private Button buttonImage8;
    
    private String dir = null;
    private String sep = File.separator;
    private String[] fileNames = new String[8];
    private String currMain = null;
    
    // TODO Find more efficient use for these method calls
    public void setImage1(ActionEvent event) {
    	mainImage.setImage(imageChoose1.getImage());
    	currMain = fileNames[0];
    }
    
    public void setImage2(ActionEvent event) {
    	mainImage.setImage(imageChoose2.getImage());
    	currMain = fileNames[1];
    }
    
    public void setImage3(ActionEvent event) {
    	mainImage.setImage(imageChoose3.getImage());
    	currMain = fileNames[2];
    }
    
    public void setImage4(ActionEvent event) {
    	mainImage.setImage(imageChoose4.getImage());
    	currMain = fileNames[3];
    }
    
    public void setImage5(ActionEvent event) {
    	mainImage.setImage(imageChoose5.getImage());
    	currMain = fileNames[4];
    }
    
    public void setImage6(ActionEvent event) {
    	mainImage.setImage(imageChoose6.getImage());
    	currMain = fileNames[5];
    }
    
    public void setImage7(ActionEvent event) {
    	mainImage.setImage(imageChoose7.getImage());
    	currMain = fileNames[6];
    }
    
    public void setImage8(ActionEvent event) {
    	mainImage.setImage(imageChoose8.getImage());
    	currMain = fileNames[7];
    }
    
    // TODO move to file manager
    public void getImageDirectory(ActionEvent event) {
    	String initialDir = "user.dir";
    	
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setInitialDirectory(new File(System.getProperty(initialDir)));
    	directoryChooser.setTitle("Select MRI image directory");
    	
    	File file = directoryChooser.showDialog(null);
    	
    	// TODO modify code to work on any number of images
    	// TODO check to operate only on images to avoid crash and ignore analyzed jpgs
    	if (file != null) {
    		dir = file.getPath();
    		System.out.println("Loading images in: " + dir);
    		
    		
    		imageChoose1.setImage(FileManager.setImage(dir + sep + file.listFiles()[0].getName()));
    		fileNames[0] = file.listFiles()[0].getName();
    		
    		imageChoose2.setImage(FileManager.setImage(dir + sep + file.listFiles()[1].getName()));
    		fileNames[1] = file.listFiles()[1].getName();
    		
    		imageChoose3.setImage(FileManager.setImage(dir + sep + file.listFiles()[2].getName()));
    		fileNames[2] = file.listFiles()[2].getName();
    		
    		imageChoose4.setImage(FileManager.setImage(dir + sep + file.listFiles()[3].getName()));
    		fileNames[3] = file.listFiles()[3].getName();
    		
    		imageChoose5.setImage(FileManager.setImage(dir + sep + file.listFiles()[4].getName()));
    		fileNames[4] = file.listFiles()[4].getName();
    		
    		imageChoose6.setImage(FileManager.setImage(dir + sep + file.listFiles()[5].getName()));
    		fileNames[5] = file.listFiles()[5].getName();
    		
    		imageChoose7.setImage(FileManager.setImage(dir + sep + file.listFiles()[6].getName()));
    		fileNames[6] = file.listFiles()[6].getName();
    		
    		imageChoose8.setImage(FileManager.setImage(dir + sep + file.listFiles()[7].getName()));
    		fileNames[7] = file.listFiles()[7].getName();
    	}
    }
        
    // TODO save in Analyze subfolder
    public void analyzeImage(ActionEvent event) {
    	if (dir != null) {
    		mainImage.setImage(FileManager.setImage(Analyze.analyzeImage(dir + sep + currMain)));
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