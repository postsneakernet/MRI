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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController extends AnchorPane implements Initializable {
    private MRIPrototype application;
    
    @FXML
    private Label labelAppName;
    @FXML
    private Label labelPatient;
    @FXML
    private Label labelSelectedMonth;
    @FXML
    private Label labelSlice;
    @FXML
    private Label labelSliceArea;
    @FXML
    private Label labelMonthVolume;
    @FXML
    private Label labelFeedback;
    
    @FXML
    private Button buttonHelp;
    @FXML
    private Button buttonSettings;
    @FXML
    private Button buttonSelectFolder;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonAnalyze;
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
    private ImageView imageMain;
    @FXML
    private ImageView imageGraph;
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
    
    private final int MRI_IMAGE_AMOUNT = 8;
    private String initialDir = "user.dir";
    private String dir = null;
    private String sep = File.separator;
    private String currentMainImage = null;
    private List<String> fileNames = new  ArrayList<String>();
    private List<Integer[]> tumorArea = new ArrayList<Integer[]>();
    private List<Double> tumorVolume = new ArrayList<Double>();
    private File patientMonths;
    private int selectedMonth = 0;
    private int monthTotal = 0;
    private boolean toggleAnalyzed = false;
    private boolean alreadyOpened = false;
    
    public void getHelp(ActionEvent event) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +
					System.getProperty(initialDir) + sep + "Homework5.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelFeedback.setText("");
    }
    
    public void getSettings(ActionEvent event) {
    	final Stage myDialog;
        Button okButton;
        if (!alreadyOpened) {
        	alreadyOpened = !alreadyOpened;
			myDialog = new Stage();
//			myDialog.initModality(Modality.WINDOW_MODAL);
			myDialog.initModality(Modality.APPLICATION_MODAL); // always on top
			myDialog.initStyle(StageStyle.UNDECORATED);
			okButton = new Button("Save");
			okButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					alreadyOpened = !alreadyOpened;
					myDialog.close();
				}

			});
		
			Scene myDialogScene = new Scene(VBoxBuilder.create()
	                .children(new Text("Hello! it's My Dialog."), okButton)
	                .alignment(Pos.CENTER)
	                .padding(new Insets(10))
	                .build());
	      
	        myDialog.setScene(myDialogScene);
	        myDialog.show();
        }
        
        labelFeedback.setText("Settings saved");        
    }
    
    public void getPatientDir(ActionEvent event) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setInitialDirectory(new File(System.getProperty(initialDir)));
    	directoryChooser.setTitle("Select MRI patient directory");
    	patientMonths = directoryChooser.showDialog(null);
    	
    	if (patientMonths != null && validateDir()) {
    		labelPatient.setText("Patient: " + patientMonths.getName());
        	setSelectedMonth();
        	setImageGrid(getPatientMonth());
        	getTumorArea();
        	getTumorVolume();
        	labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    	} else {
    		setNullData();
    	}
    }
    
    public void getPrevMonth(ActionEvent event) {    	
    	if (monthTotal > 0 && selectedMonth != 0) {
	    	setSelectedMonth(false);
	    	setImageGrid(getPatientMonth());
	    	imageMain.setImage(null);
	    	labelSlice.setText("Slice: ");
	    	labelSliceArea.setText("Area: ");
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		toggleAnalyzed = false;
    	} else {
    		labelFeedback.setText("");
    	}
    }
    
    public void getNextMonth(ActionEvent event) {    	
    	if (monthTotal > 0 && selectedMonth != monthTotal - 1) {
	    	setSelectedMonth(true);
	    	setImageGrid(getPatientMonth());
	    	imageMain.setImage(null);
	    	labelSlice.setText("Slice: ");
	    	labelSliceArea.setText("Area: ");
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		toggleAnalyzed = false;
    	} else {
    		labelFeedback.setText("");
    	}
    }
    
    public void setImage1(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose1.getImage());
    		currentMainImage = fileNames.get(0);
    		labelSlice.setText("Slice: " + 1);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[0]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage2(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose2.getImage());
    		currentMainImage = fileNames.get(1);
    		labelSlice.setText("Slice: " + 2);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[1]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage3(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose3.getImage());
    		currentMainImage = fileNames.get(2);
    		labelSlice.setText("Slice: " + 3);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[2]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage4(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose4.getImage());
    		currentMainImage = fileNames.get(3);
    		labelSlice.setText("Slice: " + 4);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[3]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage5(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose5.getImage());
    		currentMainImage = fileNames.get(4);
    		labelSlice.setText("Slice: " + 5);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[4]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage6(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose6.getImage());
    		currentMainImage = fileNames.get(5);
    		labelSlice.setText("Slice: " + 6);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[5]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage7(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose7.getImage());
    		currentMainImage = fileNames.get(6);
    		labelSlice.setText("Slice: " + 7);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[6]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void setImage8(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose8.getImage());
    		currentMainImage = fileNames.get(7);
    		labelSlice.setText("Slice: " + 8);
    		labelSliceArea.setText("Area: " + tumorArea.get(selectedMonth)[7]);
    		labelMonthVolume.setText("Vol: " + tumorVolume.get(selectedMonth));
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    	}
    }
    
    public void analyzeImage(ActionEvent event) {
    	if (dir != null && currentMainImage != null) {
    		toggleAnalyzed = !toggleAnalyzed;
    		if (toggleAnalyzed) {
    			imageMain.setImage(FileManager.setImage(Analyze.analyzeImage(dir, sep, dir + sep + currentMainImage)));
    		} else {
    			imageMain.setImage(FileManager.setImage(dir + sep + currentMainImage));
    		}
    		labelFeedback.setText("");
    	} else {
    		labelFeedback.setText("No image selected");
    	}
    }
    
    /*
     * Checks that selected directory has appropriate data
     */
    public boolean validateDir() {
    	int jpgCount = 0;
    	monthTotal = 0;
    	
    	for (int i = 0; i < patientMonths.listFiles().length; i++) {
    		File month = patientMonths.listFiles()[i];
    		
			if (month.isDirectory()) {
				monthTotal++;
				for (int j = 0; j < month.listFiles().length; j++) {
					String f = month.listFiles()[j].getName();
	    			String[] split = f.split("\\.");
	    			
	    			if (split.length > 1 && split[1].equals("jpg")) {
	    				jpgCount++;
	    			}
				}
			}
		}

    	return jpgCount > 0 && jpgCount % MRI_IMAGE_AMOUNT == 0;
    }
    
    public void getTumorArea() {
    	tumorArea.clear();

    	for (int i = 0; i < patientMonths.listFiles().length; i++) {
    		File month = patientMonths.listFiles()[i];
    		Integer[] monthArea = new Integer[MRI_IMAGE_AMOUNT];
    		int k = 0; // true index in case month contains non-jpg files
    		
			if (month.isDirectory()) {
				for (int j = 0; j < month.listFiles().length; j++) {					
					String f = month.listFiles()[j].getName();
	    			String[] split = f.split("\\.");
	    			
	    			// only add jpgs and ignore already analyzed
	    			if (split.length > 1 && split[1].equals("jpg") && k < monthArea.length) {
	    				monthArea[k] = Analyze.analyzeImage(month.getPath() + sep + f);
	    				k++;
	    			}
				}
				
				tumorArea.add(monthArea);
			}
		}
    	
    	imageGraph.setImage(Graphing.createGraph());
    }
    
    public void getTumorVolume() {
    	tumorVolume.clear();
    	
    	for (Integer[] iArray: tumorArea) {
    		double monthVolume = 0;
    		for (int i = 0; i < iArray.length; i++) {
    			monthVolume = monthVolume + iArray[i];
    		}

    		tumorVolume.add(monthVolume);
    	}
    }
    
    /*
     * Initial directory load
     */
    public void setSelectedMonth() {
    	selectedMonth = 0;
    	int i = selectedMonth + 1; // label setText doesn't like math operations
    	labelSelectedMonth.setText("Month " + i);
    	labelSlice.setText("Slice: ");
		labelSliceArea.setText("Area: ");
    	imageMain.setImage(null);
    	toggleAnalyzed = false;
    }
    
    /*
     * Previous or next month is selected
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
    
    public File getPatientMonth() {
    	return patientMonths.listFiles()[selectedMonth];
    }
        
    public void setImageGrid(File file) {
    	if (file != null) {
    		dir = file.getPath();
    		labelFeedback.setText("Loading images in: " + dir);
    		
    		fileNames.clear(); // clear previous images
    		currentMainImage = null;
    		
    		for (int i = 0; i < file.listFiles().length; i++) {
    			if (file.listFiles()[i].isDirectory()) {
    				continue;
    			}
    			
    			String f = file.listFiles()[i].getName();
    			String[] split = f.split("\\.");
    			
    			// only add jpgs and ignore already analyzed
    			if (split.length > 1 && split[1].equals("jpg")) {
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
    
    /*
     * Clear previous images if invalid directory is selected
     */
    public void setNullData() {
		labelFeedback.setText("Directory doesn't contain enough images");
		labelSlice.setText("Slice: ");
		labelSliceArea.setText("Area: ");
		labelMonthVolume.setText("Vol: ");
		labelPatient.setText("Patient: ");
		dir = null;
		monthTotal = 0;
		selectedMonth = 0;
		setSelectedMonth();
		
		imageChoose1.setImage(null);
		imageChoose2.setImage(null);
		imageChoose3.setImage(null);
		imageChoose4.setImage(null);
		imageChoose5.setImage(null);
		imageChoose6.setImage(null);
		imageChoose7.setImage(null);
		imageChoose8.setImage(null);
		
		imageMain.setImage(null);
		imageGraph.setImage(FileManager.setImage(System.getProperty(initialDir) + sep + "blankgraph.jpg"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }
    
   	void setApp(MRIPrototype application) {
	    this.application = application;
	    throw new UnsupportedOperationException("Not supported yet.");
    }
}