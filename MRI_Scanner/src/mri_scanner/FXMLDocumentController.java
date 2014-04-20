package mri_scanner;

import analysis.Analyze;
import analysis.Graphing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
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
    
    @FXML
    private ComboBox comboBoxPValue;
    @FXML
    private ComboBox comboBoxCValue;
    
    private final int MRI_IMAGE_AMOUNT = 8;
    private final String areaData = "area.dat";
    private final String mriHelp = "MRI_Help.pdf";
    private String env;
    private String initialDir = "user.dir";
    private String dir = null;
    private String sep = File.separator;
    private String currentMainImage = null;
    private DecimalFormat df = new DecimalFormat("0.000");
    private List<String> fileNames = new  ArrayList<String>();
    private List<Integer[]> tumorArea = new ArrayList<Integer[]>();
    private List<Double[]> cmTumorArea = new ArrayList<Double[]>();
    private List<Double> tumorVolume = new ArrayList<Double>();
    private List<Double> cmTumorVolume = new ArrayList<Double>();
    private File patientMonths;
    private int selectedMonth = 0;
    private int monthTotal = 0;
    private double pSelected;
    private double cSelected;
    private boolean toggleAnalyzed = false;
    private boolean isSettingsOpened = false;
    private boolean rememberDir = true;
    private boolean localRememberDir = true;
    private boolean ignoreAnalyzed = true;
    private boolean localIgnoreAnalyzed = true;
    
    ColorAdjust colorAdjust = new ColorAdjust();
   
    private ObservableList<String> pOptions = FXCollections.observableArrayList("5/7","3/4","4/5");
    private ObservableList<String> cOptions = FXCollections.observableArrayList("0.1", "0.2", "0.3");
    
    public void getHelp(ActionEvent event) {
		try {
			if (env.contains("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +
						System.getProperty(initialDir) + sep + mriHelp);
			} else if (env.contains("Mac")) {
				Runtime.getRuntime().exec("open " + System.getProperty(initialDir) + sep + mriHelp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelFeedback.setText("");
    }
    
    public void getSettings(ActionEvent event) {
    	final Stage settingsDialog;
    	final Text textTitle;
    	final HBox hBoxTitle;
    	final HBox hBoxButtons;
        final Button buttonSave;
        final Button buttonCancel;
        final CheckBox checkBoxRememberDir;
        final CheckBox checkBoxIgnoreAnalyzed;
        localRememberDir = rememberDir;
        localIgnoreAnalyzed = ignoreAnalyzed;

        if (!isSettingsOpened) {
        	isSettingsOpened = !isSettingsOpened;
			settingsDialog = new Stage();
			settingsDialog.initModality(Modality.APPLICATION_MODAL); // always on top
			settingsDialog.initStyle(StageStyle.UNDECORATED);
			
			textTitle = new Text("MRI Analysis Settings:");
			textTitle.setFill(Color.WHITE);
			textTitle.setStyle("-fx-font-weight: bold");
			
			hBoxTitle = new HBox();
			hBoxTitle.setPadding(new Insets(10));
			
			hBoxButtons = new HBox();
			hBoxButtons.setPadding(new Insets(10));
			hBoxButtons.setSpacing(5);
			hBoxButtons.setAlignment(Pos.CENTER);
			
			hBoxTitle.getChildren().addAll(textTitle);
			
			buttonSave = new Button("Save");
			buttonCancel = new Button("Cancel");
			
			checkBoxRememberDir = new CheckBox("Load last patient on startup");
			checkBoxRememberDir.setTextFill(Color.WHITE);
			checkBoxRememberDir.setSelected(localRememberDir);
			
			checkBoxIgnoreAnalyzed = new CheckBox("Ignore already analyzed");
			checkBoxIgnoreAnalyzed.setTextFill(Color.WHITE);
			checkBoxIgnoreAnalyzed.setSelected(localIgnoreAnalyzed);
			
			buttonSave.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					isSettingsOpened = !isSettingsOpened;
					rememberDir = localRememberDir;
					ignoreAnalyzed = localIgnoreAnalyzed;
					analysis.Settings.setProperty("rememberDir", Boolean.toString(rememberDir));
					analysis.Settings.setProperty("ignoreAnalyzed", Boolean.toString(ignoreAnalyzed));
					labelFeedback.setText("Settings saved");  
					settingsDialog.close();
				}
			});
			
			buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					isSettingsOpened = !isSettingsOpened;
					labelFeedback.setText("Settings not saved");  
					settingsDialog.close();
				}
			});
			
			checkBoxRememberDir.selectedProperty().addListener(new ChangeListener<Boolean>() {
			    @Override
			    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			    	localRememberDir = !localRememberDir;
			    }
			});
			
			checkBoxIgnoreAnalyzed.selectedProperty().addListener(new ChangeListener<Boolean>() {
			    @Override
			    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			    	localIgnoreAnalyzed = !localIgnoreAnalyzed;
			    }
			});
			
			hBoxButtons.getChildren().addAll(buttonSave, buttonCancel);
		
			Scene settingsScene = new Scene(VBoxBuilder.create()
	                .children(hBoxTitle, checkBoxRememberDir, checkBoxIgnoreAnalyzed, hBoxButtons)
	                .alignment(Pos.TOP_LEFT)
	                .padding(new Insets(40))
	                .spacing(5)
	                .style("-fx-background-color:#009999")
	                .build());
			
	        settingsDialog.setScene(settingsScene);
	        settingsDialog.show();
        }      
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
        	imageGraph.setImage(Graphing.createGraph(cmTumorVolume, pSelected, cSelected));
        	labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
        	analysis.Settings.setProperty("lastPatient", patientMonths.getPath());
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
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
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
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	} else {
    		labelFeedback.setText("");
    	}
    }
    
    public void setImage1(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose1.getImage());
    		currentMainImage = fileNames.get(0);
    		labelSlice.setText("Slice: " + 1);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[0]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage2(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose2.getImage());
    		currentMainImage = fileNames.get(1);
    		labelSlice.setText("Slice: " + 2);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[1]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage3(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose3.getImage());
    		currentMainImage = fileNames.get(2);
    		labelSlice.setText("Slice: " + 3);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[2]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage4(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose4.getImage());
    		currentMainImage = fileNames.get(3);
    		labelSlice.setText("Slice: " + 4);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[3]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage5(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose5.getImage());
    		currentMainImage = fileNames.get(4);
    		labelSlice.setText("Slice: " + 5);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[4]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage6(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose6.getImage());
    		currentMainImage = fileNames.get(5);
    		labelSlice.setText("Slice: " + 6);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[5]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage7(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose7.getImage());
    		currentMainImage = fileNames.get(6);
    		labelSlice.setText("Slice: " + 7);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[6]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void setImage8(ActionEvent event) {
    	if (fileNames.size() >= MRI_IMAGE_AMOUNT) {
    		imageMain.setImage(imageChoose8.getImage());
    		currentMainImage = fileNames.get(7);
    		labelSlice.setText("Slice: " + 8);
    		labelSliceArea.setText("Area: " + df.format(cmTumorArea.get(selectedMonth)[7]) + " cm");
    		labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
    		labelFeedback.setText("");
    		toggleAnalyzed = false;
    		buttonAnalyze.setEffect(null);
    	}
    }
    
    public void analyzeImage(ActionEvent event) {
    	if (dir != null && currentMainImage != null) {
    		toggleAnalyzed = !toggleAnalyzed;
    		if (toggleAnalyzed) {
    			buttonAnalyze.setEffect(colorAdjust);
    			imageMain.setImage(FileManager.setImage(Analyze.analyzeImage(dir, sep, dir + sep + currentMainImage)));
    		} else {
    			buttonAnalyze.setEffect(null);
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
			} else {
				return false;
			}
		}

    	return jpgCount > 0 && jpgCount % MRI_IMAGE_AMOUNT == 0;
    }
    
    /*
     * Adds area of tumor for each slice for each month to tumor arraylist
     * Uses previously calculated data from file if it exists
     * Otherwise calculates it and saves to file in month folder
     * Units are in pixels
     */
    public void getTumorArea() {
    	tumorArea.clear();

    	for (int i = 0; i < patientMonths.listFiles().length; i++) {
    		File month = patientMonths.listFiles()[i];
    		Integer[] monthArea = new Integer[MRI_IMAGE_AMOUNT];
    		int k = 0; // true index in case month contains non-jpg files
    		
			if (month.isDirectory()) {
				File data = new File(month.getPath() + sep + areaData);
				
				if (ignoreAnalyzed && data.exists()) { // don't recalculate data if already available
					System.out.println("Area already calculated, using data from file");
					monthArea = readData(month.getPath());
				} else {
					System.out.println("Calculating area...");
					for (int j = 0; j < month.listFiles().length; j++) {
						String f = month.listFiles()[j].getName();
						String[] split = f.split("\\.");

						// only add jpgs and ignore already analyzed
						if (split.length > 1 && split[1].equals("jpg")
								&& k < monthArea.length) {
							monthArea[k] = Analyze.analyzeImage(month.getPath()
									+ sep + f);
							k++;
						}
					}
					saveData(month.getPath(), monthArea);
				}

				tumorArea.add(monthArea);
			}
		}
    	
    	cmTumorArea.clear();
    	
    	for (Integer[] iArray: tumorArea) {
    		Double[] cmMonthArea = new Double[MRI_IMAGE_AMOUNT];
    		for (int i = 0; i < iArray.length; i++) {
    			cmMonthArea[i] = analysis.Analyze.convertToCm(iArray[i], true);
    		}
    		
    		cmTumorArea.add(cmMonthArea);
    	}
    }
    
    /*
     * Saves area of tumor for entire month to file in the month's directory
     */
    public void saveData(String path, Integer[] array) {
    	String fileName = path + sep + areaData;

    	try (BufferedWriter writer = new BufferedWriter(
    			new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
    	    
    	    for (int i = 0; i < array.length; i++) {
				writer.write(String.valueOf(array[i]));
				writer.newLine();
			}
    	} catch (IOException e) {
    	  e.printStackTrace();
    	}
    }
    
    /*
     * Reads area for each slice for a month from a file and returns area in an array
     */
    public Integer[] readData(String path) {
    	String fileName = path + sep + areaData;
    	Integer[] array = new Integer[MRI_IMAGE_AMOUNT];

    	try (Scanner scan = new Scanner(new File(fileName))) {
			for (int i = 0; i < array.length; i++) {
				array[i] = Integer.parseInt(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	return array;
    }
    
    /*
     * Calculates the volume for each month from the area of each slice
     * Units are in pixels
     */
    public void getTumorVolume() {
    	tumorVolume.clear();
    	double monthVolume;
    	
    	for (Integer[] iArray: tumorArea) {
    		monthVolume = 0;
    		for (int i = 0; i < iArray.length; i++) {
    			monthVolume += iArray[i];
    		}

    		tumorVolume.add(monthVolume);
    	}
    	
    	cmTumorVolume.clear();
    	// testing cm
    	for (Double d: tumorVolume) {
    		monthVolume = analysis.Analyze.convertToCm(d, false);
    		cmTumorVolume.add(monthVolume);
    	}
    	
    	// old version
//    	for (Double[] dArray: cmTumorArea) {
//    		monthVolume = 0;
//    		for (int i = 0; i < dArray.length; i++) {
//    			monthVolume += dArray[i];
//    		}
//    		
//    		cmTumorVolume.add(monthVolume);
//    	}
    }
    
    /*
     * Initial patient directory load
     */
    public void setSelectedMonth() {
    	selectedMonth = 0;
    	int i = selectedMonth + 1; // label setText doesn't like math operations
    	labelSelectedMonth.setText("Month " + i);
    	labelSlice.setText("Slice: ");
		labelSliceArea.setText("Area: ");
    	imageMain.setImage(null);
    	toggleAnalyzed = false;
    	buttonAnalyze.setEffect(null);
    }
    
    /*
     * Patient directory already loaded and previous or next month is selected
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
    
    /*
     * Returns the currently selected month
     */
    public File getPatientMonth() {
    	return patientMonths.listFiles()[selectedMonth];
    }
        
    /*
     * Sets the images for the image selection buttons
     */
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
     * Clear previous images for image selection buttons if invalid directory is selected
     */
    public void setNullData() {
		labelFeedback.setText("Invalid directory selected.");
		labelSlice.setText("Slice: ");
		labelSliceArea.setText("Area: ");
		labelMonthVolume.setText("Vol: ");
		labelPatient.setText("Patient: ");
		dir = null;
		monthTotal = 0;
		selectedMonth = 0;
		setSelectedMonth();
		analysis.Settings.setProperty("lastPatient", "null");
		
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
    
    /*
     * Converts selected combobox value from string to double and return value
     */
    public double convertDouble(String s) {
    	String[] split = s.split("/");
    	double d;
    	
    	if (split.length == 2) {
    		// converts string values like 5/4
    		d = Double.parseDouble(split[0]) / Double.parseDouble(split[1]);
    	} else {
    		d = Double.parseDouble(s);
    	}
    	
    	return d;
    }
    
    /*
     * Called at program launch, initializes program from saved properties
     * Loads previous patient directory if enabled in settings
     */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	env = System.getProperty("os.name");
    	
    	comboBoxPValue.getItems().addAll(pOptions);
    	comboBoxPValue.setValue("5/7");
    	pSelected = convertDouble((String)comboBoxPValue.getValue());
    	
    	comboBoxPValue.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
            	pSelected = convertDouble(newValue);
            	if (dir != null) {
            		imageGraph.setImage(Graphing.createGraph(cmTumorVolume, pSelected, cSelected));
            	}
            }    
        });
    	
    	comboBoxCValue.getItems().addAll(cOptions);
    	comboBoxCValue.setValue("0.1");
    	cSelected = convertDouble((String)comboBoxCValue.getValue());
    	
    	comboBoxCValue.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
            	cSelected = convertDouble(newValue);
            	if (dir != null) {
            		imageGraph.setImage(Graphing.createGraph(cmTumorVolume, pSelected, cSelected));
            	}
            }    
        });
    	
    	analysis.Settings.getDefaultProperties();
    	rememberDir = Boolean.parseBoolean(analysis.Settings.getProperty("rememberDir"));
    	ignoreAnalyzed = Boolean.parseBoolean(analysis.Settings.getProperty("ignoreAnalyzed"));
    	
    	if (rememberDir) {
			String s = analysis.Settings.getProperty("lastPatient");
			
			if (!s.equals("null")) {
				patientMonths = new File(s);
					
				if (patientMonths.exists() && validateDir()) {
					labelPatient.setText("Patient: " + patientMonths.getName());
					setSelectedMonth();
					setImageGrid(getPatientMonth());
					getTumorArea();
					getTumorVolume();
					imageGraph.setImage(Graphing.createGraph(cmTumorVolume, pSelected, cSelected));
					labelMonthVolume.setText("Vol: " + df.format(cmTumorVolume.get(selectedMonth)) + " cm");
				}
			}
		}
    	
    	colorAdjust.setHue(-.6);
    	colorAdjust.setSaturation(.35);
    }
    
   	void setApp(MRIPrototype application) {
	    this.application = application;
	    throw new UnsupportedOperationException("Not supported yet.");
    }
}