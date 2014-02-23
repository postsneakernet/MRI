package mri_scanner;


import analysis.Analyze;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Brian
 */
public class FXMLDocumentController extends AnchorPane implements Initializable {
    
    private Label label;
    private MRIPrototype application;
    @FXML
    private Button settings;
    @FXML
    private Button selectFolder;
    @FXML
    private Button backButton;
    @FXML
    private Label progName;
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
    
    private void handleButtonAction(ActionEvent event) {
	    System.out.println("You clicked me!");
	    label.setText("Hello World!");
    }
        
    public void setImages(ActionEvent event) {
		imageChoose1.setImage(fileManager.setImage("MriPics\\Hirnmetastase_MRT-T1_KM.jpg"));
		imageChoose2.setImage(fileManager.setImage("MriPics\\MRI_T2_Brain_axial_image.jpg"));
		imageChoose3.setImage(fileManager.setImage("MriPics\\Tumor_BrainstemGlioma2.jpg"));
		imageChoose4.setImage(fileManager.setImage("MriPics\\mri1.jpg"));
		mainImage.setImage(fileManager.setImage("MriPics\\mri2.jpg"));
    }
         
    public void analyzeImage(ActionEvent event) {
    	mainImage.setImage(fileManager.setImage(Analyze.analyzeImage("MriPics\\mri2.jpg")));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }    
    
   	void setApp(MRIPrototype aThis) {
	    this.application = aThis;
	    throw new UnsupportedOperationException("Not supported yet.");
    }
}