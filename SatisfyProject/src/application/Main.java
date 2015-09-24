package application;

import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//URL imgUrl = primaryStage.getClass().getClassLoader().getResource("/src/application/icon.png");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
			//primaryStage.getIcons().add(new Image("./src/application/icon.png"));
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Layout.fxml"));
			Scene scene = new Scene(root,1920,1080);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//leftWebview.getEngine().load("http://www.google.com");
			//leftEngine.load("http://www.google.com");
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void setProxy(){
		//System.setProperty("http.proxyHost", "my-proxy.com");
		//System.setProperty("http.proxyPort", "80");
	}
}
