package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	@FXML
	private TabPane leftTabPane;
	@FXML
	private Tab leftAddTab;
	@FXML
	private TabPane rightTabPane;
	@FXML
	private Tab rightAddTab;
	
	private WebView lastClickWebview;
	
	 
	@FXML
	private void setUrlAction(ActionEvent event) {
		String url = urlField.getText();
		lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void backButtonAction(ActionEvent event){
		//�o�b�N�{�^���̃��\�b�h
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
		//�i�ރ{�^���̃��\�b�h
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
		//�X�V�{�^��
	}
	
	@FXML
	private void leftDivideAction(ActionEvent event){
		//���ɕ�����̃{�^��
	}
	
	@FXML
	private void rightDivideAction(ActionEvent event){
		//�E�ɕ�����{�^��
	}
	
	@FXML
	private void leftTabAddAction(Event event){
		Tab tab = new Tab();
		WebView web = new WebView();
		tab.setText("new Tab");
		web.getEngine().load("http://www.google.com");
		tab.setContent(web);
		leftTabPane.getTabs().add(leftTabPane.getTabs().size()-1,tab);
		leftTabPane.getSelectionModel().select(tab);
	}
	@FXML
	private void rightTabAddAction(Event event){
		Tab tab = new Tab();
		WebView web = new WebView();
		tab.setText("new Tab");
		web.getEngine().load("http://www.google.com");
		tab.setContent(web);
		rightTabPane.getTabs().add(rightTabPane.getTabs().size()-1,tab);
		rightTabPane.getSelectionModel().select(tab);
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
