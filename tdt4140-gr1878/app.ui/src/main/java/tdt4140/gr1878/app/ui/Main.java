package tdt4140.gr1878.app.ui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tdt4140.gr1878.app.core.User;
import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

/**
 * The main program entry point for this Javafx application.
 */
//brukerhistorie 1 sprint 1, issue nr 1.1 og 1.2 : opprette kontakt mellom database og program


public class Main extends Application {

	public User bruker;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Leser innloggingsinformasjon til databasen fra db.properties og kobler til via databaseImpl
	// Setter innloggingssiden "logon2" til å være første vindu i programmet,  og setter et userLoginController til å være controller-objekt
	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.currentThread().setName("JavaFX");
		System.out.println("Loading database properties file...");
		Properties dbProps = new Properties();
		try (InputStream is = getClass().getResourceAsStream("db.properties")) {
			if (is == null)
				throw new FileNotFoundException();
			dbProps.load(is);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find db.properties file, did you remember to rename db.default.properties?");
			e.printStackTrace();
		}
		
		System.out.println("Loading data from database...");
		
		Database db = new DatabaseImpl(dbProps);
		
		primaryStage.setTitle("Start");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Logon2.fxml"));
		UserLoginController controller = new UserLoginController(primaryStage, db, this);
		loader.setController(controller);
		AnchorPane root = (AnchorPane) loader.load();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setHeight(572);
		primaryStage.setWidth(572);
		primaryStage.setScene(scene);

		System.out.println("Done. Showing primary stage.");
		primaryStage.show();
	}

}
