package tdt4140.gr1878.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

public class MainControllerDataCollector {

	protected Stage myStage;
	protected Scene logonScene;
	protected Database db;
	protected Main main;
	
	@FXML
	Button profileButton;
	@FXML
	Button activityButton;
	@FXML
	Button trondheimButton;
	@FXML
	Button totaltripsButton;
	
	
	public MainControllerDataCollector(Stage primaryStage, Database db, Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}
	
	public void initialize() { 
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
		
	}
	
	@FXML
	public void activityButton(ActionEvent event) {
		AreaDataViewController ab = new AreaDataViewController(myStage, db, main);
		newScene("AreaDataView.fxml", "AreaDataView", ab);
	}
	
	@FXML
	public void totaltripsButton(ActionEvent event){
		MySettingsController sb = new MySettingsController(myStage, db, main);
		newScene("MySettings.fxml", "Innstillinger", sb);
	}
	
	@FXML
	public void trondheimButton(){
		TrondheimController tc = new TrondheimController(myStage, db, main);
		newScene("Trondheim.fxml", "TrondheimData", tc);
	}
	
	@FXML
	public void MyProfileDataCollectorController(ActionEvent event) {
		MyProfileDataCollectorController tc1 = new MyProfileDataCollectorController(myStage, db, main);
		newScene("MyProfileDataCollect.fxml", "Myprofile", tc1);
	}
	
	protected void newScene(String resource, String title, Object controller) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {

			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		myStage.setHeight(572);
		myStage.setWidth(572);
		myStage.setTitle(title);
		myStage.setScene(scene);
		myStage.centerOnScreen();
	}
	
}
