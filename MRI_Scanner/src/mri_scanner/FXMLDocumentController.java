package mri_scanner;


import analysis.Analyze;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;

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
    private List<String> fileNames = new  ArrayList<String>();
    private String currMain = null;
    
    // TODO Find more efficient use for these method calls
    public void setImage1(ActionEvent event) {
    	mainImage.setImage(imageChoose1.getImage());
    	currMain = fileNames.get(0);
    }
    
    public void setImage2(ActionEvent event) {
    	mainImage.setImage(imageChoose2.getImage());
    	currMain = fileNames.get(1);
    }
    
    public void setImage3(ActionEvent event) {
    	mainImage.setImage(imageChoose3.getImage());
    	currMain = fileNames.get(2);
    }
    
    public void setImage4(ActionEvent event) {
    	mainImage.setImage(imageChoose4.getImage());
    	currMain = fileNames.get(3);
    }
    
    public void setImage5(ActionEvent event) {
    	mainImage.setImage(imageChoose5.getImage());
    	currMain = fileNames.get(4);
    }
    
    public void setImage6(ActionEvent event) {
    	mainImage.setImage(imageChoose6.getImage());
    	currMain = fileNames.get(5);
    }
    
    public void setImage7(ActionEvent event) {
    	mainImage.setImage(imageChoose7.getImage());
    	currMain = fileNames.get(6);
    }
    
    public void setImage8(ActionEvent event) {
    	mainImage.setImage(imageChoose8.getImage());
    	currMain = fileNames.get(7);
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
    		System.out.println("File length " + file.listFiles().length);
    		
    		for (int i = 0; i < file.listFiles().length; i++) {
    			if (file.listFiles()[i].isDirectory()) {
    				System.out.println("file is dir");
    				continue;
    			}

    			String f = file.listFiles()[i].getName();
    			String[] split = file.listFiles()[i].getName().split("\\.");

    			if (split.length > 1 && split[1].equals("jpg")) {
    				fileNames.add(f);
    			}
    		}
    		
    		if (fileNames.size() >= 8) {
    		imageChoose1.setImage(FileManager.setImage(dir + sep + fileNames.get(0)));
    		imageChoose2.setImage(FileManager.setImage(dir + sep + fileNames.get(1)));
    		imageChoose3.setImage(FileManager.setImage(dir + sep + fileNames.get(2)));
    		imageChoose4.setImage(FileManager.setImage(dir + sep + fileNames.get(3)));
    		imageChoose5.setImage(FileManager.setImage(dir + sep + fileNames.get(4)));
    		imageChoose6.setImage(FileManager.setImage(dir + sep + fileNames.get(5)));
    		imageChoose7.setImage(FileManager.setImage(dir + sep + fileNames.get(6)));
    		imageChoose8.setImage(FileManager.setImage(dir + sep + fileNames.get(7)));
    		}
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