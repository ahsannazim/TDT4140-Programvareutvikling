package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import junit.framework.Assert;

public class LogonPageTest extends ApplicationTest {
	@FXML 
	Button logon;
	@FXML
	Button makeuser;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	

    @BeforeClass
    //Denne gjør at gitlab ikke kjører disse testene fordi gitlab ikke har mulighet til å kjøre siden som er vår applikasjon.
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }


	
	public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Logon.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
    
    
    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return lookup(query).query();
    }
    @Before
    public void setUp() {
        
        logon = find("#logonButton");
        makeuser = find("#makeUser");
        username = find("#usernameField");
        password = find("#passwordField");
        
    }
    
    @Test
    public void testUsername() {
    		username.setText("testusername");
    		Assert.assertEquals(username.getText(), "testusername");
    		Assert.assertEquals(username.getPromptText(), "Username");
    }
    @Test
    public void testPassword() {
    		password.setText("123");
    		Assert.assertEquals(password.getText(), "123");
    		Assert.assertEquals(password.getPromptText(), "Password");
    		
    }
    @Test
    public void testLogonButtonText() {
    	Assert.assertEquals(logon.getText(), "Log in");
    }
    
    @Test
    public void testMakeUserButtonText() {
    	Assert.assertEquals(makeuser.getText(), "Make user");
    }
   
    @After
    public void tearDown() throws TimeoutException {
        /* Lukker vinduet etter hver test. Det åpnes for hver test.  */
        FxToolkit.hideStage();
    }

	
}
	
	
