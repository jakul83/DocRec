package FeturesCaseID;
	

import java.lang.reflect.Field;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/FeturesCaseID/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("DocRecognition 1.62 - Beta ");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// get the model
//				String model = System.getProperty("sun.arch.data.model");
//				// the path the .dll lib location
//				String libraryPath = "C:\\DocRecognition\\dll\\x86";
//				// check for if system is 64 or 32
//				if(model.equals("64")) {
//				    libraryPath = "C:\\DocRecognition\\dll\\x64";
//				}
//				// set the path
//				System.setProperty("java.library.path", libraryPath);
//				Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
//				sysPath.setAccessible(true);
//				sysPath.set(null, null);
//				// load the lib
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//				
				launch(args);
	}
}
