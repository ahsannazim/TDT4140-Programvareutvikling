package tdt4140.gr1878.app.ui;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.DeleteUserController;
import tdt4140.gr1878.app.ui.MyProfileController;
import tdt4140.gr1878.app.ui.RegisterUserController;
import tdt4140.gr1878.app.ui.TrackingController;

public class TrackingFXTest extends ApplicationTest {


	private TrackingController controller;

	@Override
	public void start(Stage stage) throws Exception {
		stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/../../../../../../main/java/tdt4140/gr1878/app/ui/Tracking2.fxml"));
		Parent root = loader.load();
		this.controller = loader.getController();
		Scene scene = new Scene(root);

		stage.setTitle("JavaFX and Maven");
		stage.setScene(scene);
		stage.show();

	}


	@Test
	public void test() throws Exception {
		Platform.runLater(() -> {
			try {
				Platform.setImplicitExit(false);
				start(null);
			} catch (Exception e) {
				fail("Failed testing " + controller.toString() + " FX test");
			}
		});

	}
}