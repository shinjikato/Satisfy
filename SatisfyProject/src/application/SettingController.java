package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SettingController implements Initializable {
	@FXML
	TextField text_proxyHost;
	@FXML
	TextField text_proxyPort;
	@FXML
	CheckBox check_useProxy;
	@FXML
	Button button_proxySet;
	@FXML
	ListView<String> listView_webHistory;
	@FXML
	ListView<String> listView_webDownloadHistory;
	
	static ObservableList<String> webHistory = FXCollections.observableArrayList();
	static ObservableList<String> webDownloadHistory = FXCollections.observableArrayList();

	static String urlEngine = "https://www.google.co.jp/#safe=off&q=";//google
	
	public void onProxyUse(Event e){
		if(check_useProxy.selectedProperty().get() == true){
			text_proxyHost.editableProperty().set(true);
			text_proxyPort.editableProperty().set(true);
			text_proxyHost.setDisable(false);
			text_proxyPort.setDisable(false);
			button_proxySet.setDisable(false);
			setProxy(null);
		}else{
			text_proxyHost.editableProperty().set(false);
			text_proxyPort.editableProperty().set(false);		
			text_proxyHost.setDisable(true);
			text_proxyPort.setDisable(true);
			button_proxySet.setDisable(true);
			System.setProperty("http.proxyHost", "");
			System.setProperty("http.proxyPort", "");
		}
	}
	public void setProxy(Event e){
		String Host = text_proxyHost.getText();
		String Port = text_proxyPort.getText();
		System.setProperty("http.proxyHost", Host);
		System.setProperty("http.proxyPort", Port);
	}
	
	public void onRemoveWebHisotry(Event e){
		webHistory.clear();
	}
	
	public void onRemoveWebDownloadHistory(Event e){
		webDownloadHistory.clear();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		listView_webHistory.setItems(webHistory);
		listView_webDownloadHistory.setItems(webDownloadHistory);
	}
}
