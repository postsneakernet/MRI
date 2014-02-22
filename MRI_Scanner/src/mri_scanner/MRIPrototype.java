/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private Stage stage1;
    private Stage primaryStage;
    final private double MINIMUM_WINDOW_WIDTH = 400.0;
    final private double MINIMUM_WINDOW_HEIGHT = 600.0;
    
    @Override
    public void start(Stage stage2) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         //stage = stage1;
            stage1 = stage2;
            stage1.setTitle("FXML Login Sample");
            stage1.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage1.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        //Scene scene = new Scene(root);
            goToPrototype();
            stage1.show();
        //stage.setScene(scene);
        //stage.show();
            //goToPrototype();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    
     public void goToPrototype() {
        try {
            FXMLDocumentController login = (FXMLDocumentController) replaceSceneContent("FXMLDocument.fxml");
            login.setApp(this);
        } catch (Exception e) {
        }
    }
    
        private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MRIPrototype.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MRIPrototype.class.getResource(fxml));
        AnchorPane page;

        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

       // if ("Login.fxml".equals(fxml)) {
            //stage.setFullScreen(true);
            Scene scene = new Scene(page/*, 600, 400*/);
            stage1.setScene(scene);
            //stage1.show();
            //stage.sizeToScene();
//        } else {
//            Scene scene = new Scene(page, d.width, d.height/*, 1550, 700*/);
//            stage.setScene(scene);
//            //stage.sizeToScene();
//
//            stage.setFullScreen(true);
//            stage.sizeToScene();
//        }
        return (Initializable) loader.getController();
    }
    
    public static void main(String[] args) {
        launch(args);
    }   
}
