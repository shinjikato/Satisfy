package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class satisfy extends Application {
	final static int HEIGHT = 640;
	final static int WIDTH = 960;
	
	@Override
	public void start(Stage stage) {
		setProxy();
	    StackPane root = new StackPane();

	    WebView view = new WebView();
	    WebEngine engine = view.getEngine();
	    
        // 垂直方向にレイアウトするコンテナ
        VBox vbox = new VBox(10);
        vbox.setLayoutY(10);

        // 水平方向にレイアウトするコンテナ
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        
        // テキスト入力
        TextField box = new TextField();
        box.setPrefColumnCount(80);
        hbox.getChildren().add(box);
        

        // ボタン
        Button button = new Button("Load");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // テキストボックスから取得した文字列を
                // WebEngine でロードする
                String url = box.getText();
                engine.load(url);
            }
        });
        
        
        hbox.getChildren().add(button);
        
        // HBoxをVBoxに貼る
        vbox.getChildren().add(hbox);
        
	    
	    engine.load("https://www.google.com");
	    
	    vbox.getChildren().add(view);
	    
	    root.getChildren().add(vbox);

	    Scene scene = new Scene(root, WIDTH, HEIGHT);
	    stage.setScene(scene);
	    stage.show();
        
	}
	
	public static void main(String[] args) {
		launch(satisfy.class,args);
	}
	
	private void setProxy(){
	    System.setProperty("http.proxyHost","proxy.salesio-sp.ac.jp");
	    System.setProperty("http.proxyPort","7080");
	    System.setProperty("https.proxyHost","proxy.salesio-sp.ac.jp");
	    System.setProperty("https.proxyPort","7080");
	}
}

