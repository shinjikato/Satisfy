package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebHistory;
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
		lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void backButtonAction(ActionEvent event){
		//バックボタンのメソッド
		WebHistory history = lastClickWebview.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entryList=history.getEntries();
	    int currentIndex=history.getCurrentIndex();
//	    Out("currentIndex = "+currentIndex);
//	    Out(entryList.toString().replace("],","]\n"));

	    Platform.runLater(new Runnable() { public void run() { history.go(-1); } });
	    String url = entryList.get(currentIndex>0?currentIndex-1:currentIndex).getUrl();
	    lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void nextButtonAction(ActionEvent event){
		//進むボタンのメソッド
		final WebHistory history=lastClickWebview.getEngine().getHistory();
	    ObservableList<WebHistory.Entry> entryList=history.getEntries();
	    int currentIndex=history.getCurrentIndex();
//	    Out("currentIndex = "+currentIndex);
//	    Out(entryList.toString().replace("],","]\n"));

	    Platform.runLater(new Runnable() { public void run() { history.go(1); } });
	    String url = entryList.get(currentIndex<entryList.size()-1?currentIndex+1:currentIndex).getUrl();
	    lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void reloadButtonAction(ActionEvent event){
		//更新ボタン
	}
	
	@FXML
	private void leftDivideAction(ActionEvent event){
		//左に分けるのボタン
	}
	
	@FXML
	private void rightDivideAction(ActionEvent event){
		//右に分けるボタン
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String startUrl = "http://www.google.com";
		rightWebview.getEngine().load(startUrl);
		leftWebview.getEngine().load(startUrl);
		lastClickWebview = rightWebview;
		
		System.setProperty("http.proxyHost","proxy.salesio-sp.ac.jp");
	    System.setProperty("http.proxyPort", "7080");
		
	}
}
