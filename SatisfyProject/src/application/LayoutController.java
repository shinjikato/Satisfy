package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class LayoutController implements Initializable {
	  @FXML
	  private TextField urlField;
	  @FXML
	  private WebView leftWebview;
	  @FXML
	  private WebView rightWebview;
	  
	  private WebView lastClickWebview;
	  
	  @FXML
	  private void setUrlAction(ActionEvent event) {
	    String url = urlField.getText();
	    leftWebview.getEngine().load(url);
	  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String startUrl = "http://www.google.com";
		rightWebview.getEngine().load(startUrl);
		leftWebview.getEngine().load(startUrl);
		lastClickWebview = rightWebview;
		
		System.setProperty("http.proxy","proxy.salesio-sp.ac.jp");
	    System.setProperty("http.proxyPort", "7080");
		
	}
}