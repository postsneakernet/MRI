package mri_scanner;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Brian
 */
public class MRIPrototype extends Application {
    private Stage stage;    
    final private double MINIMUM_WINDOW_WIDTH = 400.0;
    final private double MINIMUM_WINDOW_HEIGHT = 600.0;
    
    @Override
    public void start(Stage stage1) throws Exception {
        stage = stage1;
        stage.setTitle("MRI Analysis");
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
      
        goToPrototype();
        stage.show();
    }
    
    public void goToPrototype() {
        try {
            FXMLDocumentController gui = (FXMLDocumentController) replaceSceneContent("FXMLDocument.fxml");
            gui.setApp(this);
        }
        catch (Exception e) {
        }
    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MRIPrototype.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MRIPrototype.class.getResource(fxml));
        AnchorPane page;

        page = (AnchorPane) loader.load(in);
        in.close();
        
        Scene scene = new Scene(page);
        stage.setScene(scene);
        
        return (Initializable) loader.getController();
    }
    
    public static void main(String[] args) {
        launch(args);
    }   
}