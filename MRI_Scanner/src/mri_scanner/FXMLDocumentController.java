package mri_scanner;


import analysis.Analyze;
import analysis.Graphing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Brian
 */
public class FXMLDocumentController extends AnchorPane implements Initializable {
    private MRIPrototype application;
    
    @FXML
    private Label labelAppName;
    @FXML
    private Label labelSelectedMonth;
    @FXML
    private Label labelFeedback;
    
    @FXML
    private Button buttonHelp;
    @FXML
    private Button buttonSettings;
    @FXML
    private Button buttonSelectFolder;
    @FXML
    private Button buttonAnalyze;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    
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
    private ImageView imageMain;
    
    @FXML
    ImageView setGraphImage;
    
    
    
    
    private final int MRI_IMAGE_AMOUNT = 8;
    private String initialDir = "user.dir";
    private String dir = null;
    private String sep = File.separator;
    private String currentMainImage = null;
    private List<String> fileNames = new  ArrayList<String>();
    private int selectedMonth = 0;
    private int monthTotal = 0;
    private File patientMonths;
    private boolean isValidDir = false;
    
    public void getHelp(ActionEvent event) {
    
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + System.getProperty(initialDir) + sep + "user.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void setSettings(ActionEvent event) {
    	System.out.println("Settings not supported yet");
    	labelFeedback.setText("Settings not supported yet");
    }
    
    public void getPrev(ActionEvent event) {
    	System.out.println("Select valid directory");
    	labelFeedback.setText("Select valid directory");
    	
    	if (monthTotal > 0) {
	    	setSelectedMonth(false);
	    	setImageGrid(getPatientMonth());
	    	imageMain.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
    	}
    }
    
    public void getNext(ActionEvent event) {
    	System.out.println("Select valid directory");
    	labelFeedback.setText("Select valid directory");
    	
    	if (monthTotal > 0) {
	    	setSelectedMonth(true);
	    	setImageGrid(getPatientMonth());
	    	imageMain.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
    	}
    }
    
    /*
     * Initial directory load
     */
    public void setSelectedMonth() {
    	int i = selectedMonth + 1; // label setText doesn't like math operations
    	labelSelectedMonth.setText("Month " + i);
    }
    
    /*
     * Selecting previous and next months in directory
     */
    public void setSelectedMonth(boolean increment) {
    	if (increment) {
    		selectedMonth++;
	    	if (selectedMonth > monthTotal - 1) {
	    		selectedMonth = monthTotal - 1;
	    	}
    	} else {
    		selectedMonth--;
	    	if (selectedMonth < 0) {
	    		selectedMonth = 0;
	    	}
    	}
    	
    	int i = selectedMonth + 1; // label setText doesn't like math operations
    	labelSelectedMonth.setText("Month " + i);
    }
    
    public void setImage1(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose1.getImage());
    		currentMainImage = fileNames.get(0);
    	}
    }
    
    public void setImage2(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose2.getImage());
    		currentMainImage = fileNames.get(1);
    	}
    }
    
    public void setImage3(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose3.getImage());
    		currentMainImage = fileNames.get(2);
    	}
    }
    
    public void setImage4(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose4.getImage());
    		currentMainImage = fileNames.get(3);
    	}
    }
    
    public void setImage5(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose5.getImage());
    		currentMainImage = fileNames.get(4);
    	}
    }
    
    public void setImage6(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose6.getImage());
    		currentMainImage = fileNames.get(5);
    	}
    }
    
    public void setImage7(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose7.getImage());
    		currentMainImage = fileNames.get(6);
    	}
    }
    
    public void setImage8(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose8.getImage());
    		currentMainImage = fileNames.get(7);
    	}
    }
    
    public void getImageDirectory(ActionEvent event) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setInitialDirectory(new File(System.getProperty(initialDir)));
    	directoryChooser.setTitle("Select MRI patient directory");
    	
    	patientMonths = directoryChooser.showDialog(null);
    	
    	// validate
    	if (patientMonths == null) {
    		setNullData();
    		return;
    	}
    	for (int i = 0; i < patientMonths.listFiles().length; i++) {
			if (!patientMonths.listFiles()[i].isDirectory()) {
				System.out.println("File is not a directory");
				isValidDir = false;
				break;
			}
			isValidDir = true;
		}
    	
    	if (isValidDir) {
    		monthTotal = patientMonths.listFiles().length;
        	setSelectedMonth();
        	setImageGrid(getPatientMonth());
    	} else {
    		setNullData();
    		
    	}
    }
    
    /*
     * If invalid directory is selected, clear previous values
     */
    public void setNullData() {
    	System.out.println("Directory doesn't contain enough images");
		labelFeedback.setText("Directory doesn't contain enough images");
		dir = null;
		monthTotal = 0;
		imageChoose1.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose2.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose3.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose4.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose5.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose6.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose7.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		imageChoose8.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
		
		imageMain.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "empty.jpg"));
    }
    
    public File getPatientMonth() {
    	return patientMonths.listFiles()[selectedMonth];
    }
    
    public void setImageGrid(File file) {
    	if (file != null) {
    		dir = file.getPath();
    		System.out.println("Loading images in: " + dir);
    		System.out.println(file.listFiles().length + " files in directory");
    		labelFeedback.setText("Loading images in: " + dir);
    		
    		fileNames.clear(); // clear previous images
    		currentMainImage = null;
    		
    		for (int i = 0; i < file.listFiles().length; i++) {
    			if (file.listFiles()[i].isDirectory()) {
    				System.out.println("File is a directory");
    				continue;
    			}
    			String f = file.listFiles()[i].getName();
    			String[] split = f.split("\\.");
    			
    			if (split.length > 1 && split[1].equals("jpg")) { // only add jpgs and ignore already analyzed
    				fileNames.add(f);
    			}
    		}
    		
    		if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
	    		imageChoose1.setImage(FileManager.setImage(dir + sep + fileNames.get(0)));
	    		imageChoose2.setImage(FileManager.setImage(dir + sep + fileNames.get(1)));
	    		imageChoose3.setImage(FileManager.setImage(dir + sep + fileNames.get(2)));
	    		imageChoose4.setImage(FileManager.setImage(dir + sep + fileNames.get(3)));
	    		imageChoose5.setImage(FileManager.setImage(dir + sep + fileNames.get(4)));
	    		imageChoose6.setImage(FileManager.setImage(dir + sep + fileNames.get(5)));
	    		imageChoose7.setImage(FileManager.setImage(dir + sep + fileNames.get(6)));
	    		imageChoose8.setImage(FileManager.setImage(dir + sep + fileNames.get(7)));
    		}
    		else {
    			setNullData();
    		}
    	}
    }
    
    
   
   
    
    
    
    public void analyzeImage(ActionEvent event) {
    	if (dir != null && currentMainImage != null) {
    		Image dfsdfs = Graphing.createGraph();
    		setGraphImage.setImage(dfsdfs);
    		imageMain.setImage(FileManager.setImage(Analyze.analyzeImage(dir, sep, dir + sep + currentMainImage)));
    	}
    	else {
    		System.out.println("No valid directory or image selected");
    		labelFeedback.setText("No valid directory or image selected");
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
