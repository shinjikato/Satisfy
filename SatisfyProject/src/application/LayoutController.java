package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Callback;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class LayoutController implements Initializable {
	@FXML
	private TextField urlField;
	@FXML
	private TabPane leftTabPane;
	@FXML
	private Tab leftAddTab;
	@FXML
	private TabPane rightTabPane;
	@FXML
	private Tab rightAddTab;
	@FXML
	private ListView<String> leftFavoritebar;
	@FXML
	private ListView<String> rightFavoritebar;
	@FXML
	private SplitPane splitPane;
	
	boolean isLeftPane,isRightPane;
	private WebView lastClickWebview;
	Button btn;
	
	@FXML
	private void setUrlAction(ActionEvent event) {
		String url = urlField.getText();
		
		if(url.substring(0,7).equals("http://") == false && url.substring(0,8).equals("https://") == false){
			if(url.substring(0,3).equals("www")){
				url = "http://" + url;
			}else{
				url = "http://www." + url;
			}	
		}
		if(url.matches("^(https?|ftp)(:ﾂ･ﾂ･/ﾂ･ﾂ･ﾂ･/[-_.!~*ﾂ･ﾂ･'()a-zA-Z0-9;ﾂ･ﾂ･/?:ﾂ･ﾂ･@&=+ﾂ･ﾂ･$,%#]+)$")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		urlField.setText(url);
		lastClickWebview.getEngine().load(url);
		
	}
	
	@FXML
	private void backButtonAction(ActionEvent event){
		WebHistory history = lastClickWebview.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entryList=history.getEntries();
	    int currentIndex = history.getCurrentIndex();

	    Platform.runLater(new Runnable() { public void run() { history.go(-1); } });
	    String url = entryList.get(currentIndex>0?currentIndex-1:currentIndex).getUrl();
	    lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void nextButtonAction(ActionEvent event){
		final WebHistory history=lastClickWebview.getEngine().getHistory();
	    ObservableList<WebHistory.Entry> entryList=history.getEntries();
	    int currentIndex=history.getCurrentIndex();

	    Platform.runLater(new Runnable() { public void run() { history.go(1); } });
	    String url = entryList.get(currentIndex<entryList.size()-1?currentIndex+1:currentIndex).getUrl();
	    lastClickWebview.getEngine().load(url);
	}
	
	@FXML
	private void reloadButtonAction(ActionEvent event){
		lastClickWebview.getEngine().reload();
	}
	
	@FXML
	private void leftDivideAction(ActionEvent event){
		DoubleProperty dprop = splitPane.getDividers().get(0).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(500), dprop);	
		if(isLeftPane == false && isRightPane == false){
			dt.setToValue(1);
			dt.play();
			rightTabPane.setPadding(new Insets(0, 0, 0, 0));
			isLeftPane = true;
		}
		else if((isLeftPane == true && isRightPane == false) || (isLeftPane == false && isRightPane == true)){
			dt.setToValue(.5);
			dt.play();
			rightTabPane.setPadding(new Insets(0, 30, 0, 0));
			isLeftPane = false;
			isRightPane = false;
		}
	}
	
	@FXML
	private void rightDivideAction(ActionEvent event){
		DoubleProperty dprop = splitPane.getDividers().get(0).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(500), dprop);	
		if(isLeftPane == false && isRightPane == false){
			dt.setToValue(0);
			dt.play();
			leftTabPane.setPadding(new Insets(0,0,0,0));
			isRightPane = true;
		}
		else if((isLeftPane == true && isRightPane == false) || (isLeftPane == false && isRightPane == true)){
			dt.setToValue(.5);
			dt.play();
			leftTabPane.setPadding(new Insets(0,0,0,30));
			isLeftPane = false;
			isRightPane = false;
		}
		
	}
	
	@FXML
	private void leftTabAddAction(Event event){
		Tab tab = new Tab("");
		CostomWebView web = new CostomWebView(tab,urlField);
		web.web.getEngine().load("http://www.google.co.jp");
		tab.setContent(web.web);
		leftTabPane.getTabs().add(leftTabPane.getTabs().size()-1,tab);
		leftTabPane.getSelectionModel().select(tab);
		lastClickWebview = (WebView)tab.getContent();
	}
	@FXML
	private void rightTabAddAction(Event event){
		Tab tab = new Tab();
		CostomWebView web = new CostomWebView(tab,urlField);
		web.web.getEngine().load("http://www.google.co.jp");
		tab.setContent(web.web);
		rightTabPane.getTabs().add(rightTabPane.getTabs().size()-1,tab);
		rightTabPane.getSelectionModel().select(tab);
		lastClickWebview = (WebView)tab.getContent();
	}


	@FXML
	private void selectTabPane(Event event){
		TabPane tabPane = (TabPane)event.getSource();
		Tab selecttab = tabPane.getSelectionModel().getSelectedItem();
		WebView node = (WebView)selecttab.getContent();
		lastClickWebview = node;
		String url = lastClickWebview.getEngine().getLocation();
		urlField.setText(url);
	}
	
	@FXML
	private void textFiledEnterAction(ActionEvent event){
		setUrlAction(event);
	}
	
	@FXML
	private void addPageLeft(ActionEvent event){
		Tab selecttab = leftTabPane.getSelectionModel().getSelectedItem();
		WebView view = (WebView)selecttab.getContent();
		String url = view.getEngine().getLocation();
		String title = selecttab.getText();
		leftFavoritebar.getItems().add(url+","+title);
		rightFavoritebar.getItems().add(url+","+title);
	}
	@FXML
	private void addPageRight(ActionEvent event){
		Tab selecttab = rightTabPane.getSelectionModel().getSelectedItem();
		WebView view = (WebView)selecttab.getContent();
		String url = view.getEngine().getLocation();
		String title = selecttab.getText();
		rightFavoritebar.getItems().add(url+","+title);
		leftFavoritebar.getItems().add(url+","+title);
	}
	@FXML
	private void rightbarClicked(MouseEvent event){
		if(event.getButton().equals(MouseButton.SECONDARY) == false){
			Tab selecttab = rightTabPane.getSelectionModel().getSelectedItem();
			WebView view = (WebView)selecttab.getContent();
			if(rightFavoritebar.getItems().size() != 0) {
				String url_title = rightFavoritebar.getSelectionModel().getSelectedItem();
				String url = url_title.split(",")[0];
				if(url != null){
					view.getEngine().load(url);
					rightFavoritebar.getSelectionModel().setSelectionMode(null);
				}
			}
		}
	}
	@FXML
	private void leftbarClicked(MouseEvent event){
		if(event.getButton().equals(MouseButton.SECONDARY) == false){
			Tab selecttab = leftTabPane.getSelectionModel().getSelectedItem();
			WebView view = (WebView)selecttab.getContent();
			if(leftFavoritebar.getItems().size() != 0) {
				String url_title = leftFavoritebar.getSelectionModel().getSelectedItem();
				String url = url_title.split(",")[0];
				if(url != null){
					view.getEngine().load(url);
					leftFavoritebar.getSelectionModel().setSelectionMode(null);
				}
			}
		}
	}
	@FXML
	private void rightbarOverMouse(MouseEvent event){
		Timeline timeline = new Timeline();
		KeyFrame start = new KeyFrame(Duration.ZERO,new KeyValue(rightFavoritebar.prefWidthProperty(),rightFavoritebar.getPrefWidth()));
		KeyFrame end = new KeyFrame(new Duration(400),new KeyValue(rightFavoritebar.prefWidthProperty(),200));
		timeline.getKeyFrames().addAll(start,end);
		timeline.play();
	}
	@FXML
	private void leftbarOverMouse(MouseEvent event){
		Timeline timeline = new Timeline();
		KeyFrame start = new KeyFrame(Duration.ZERO,new KeyValue(leftFavoritebar.prefWidthProperty(),leftFavoritebar.getPrefWidth()));
		KeyFrame end = new KeyFrame(new Duration(400),new KeyValue(leftFavoritebar.prefWidthProperty(),200));
		timeline.getKeyFrames().addAll(start,end);
		timeline.play();
	}
	@FXML
	private void rightbarOutMouse(MouseEvent event){		Timeline timeline = new Timeline();
	KeyFrame start = new KeyFrame(Duration.ZERO,new KeyValue(rightFavoritebar.prefWidthProperty(),rightFavoritebar.getPrefWidth()));
	KeyFrame end = new KeyFrame(new Duration(400),new KeyValue(rightFavoritebar.prefWidthProperty(),30));
	timeline.getKeyFrames().addAll(start,end);
	timeline.play();
	}
	@FXML
	private void leftbarOutMouse(MouseEvent event){	
		Timeline timeline = new Timeline();
		KeyFrame start = new KeyFrame(Duration.ZERO,new KeyValue(leftFavoritebar.prefWidthProperty(),leftFavoritebar.getPrefWidth()));
		KeyFrame end = new KeyFrame(new Duration(400),new KeyValue(leftFavoritebar.prefWidthProperty(),30));
		timeline.getKeyFrames().addAll(start,end);
		timeline.play();
	}
	@FXML
	public void historyView(Event event){
		Tab tab = new Tab();
		Pane historyPane = new Pane();
		ListView<String> historyList = new ListView<String>();
		for(int i=0;i<CostomWebView.webhistory.size();i++){
			String str = CostomWebView.webhistory.get(i);
			historyList.getItems().add(str);
		}
		tab.setContent(historyPane);
		rightTabPane.getTabs().add(rightTabPane.getTabs().size()-1,tab);
		rightTabPane.getSelectionModel().select(tab);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isLeftPane = isRightPane = false;
		splitPane.setDividerPositions(0.5,0.5);
		System.setProperty("http.proxyHost", "proxy.salesio-sp.ac.jp");
		System.setProperty("http.proxyPort", "7080");
		leftFavoritebar.setCellFactory(new Callback<ListView<String>, 
			ListCell<String>>() {
	            @Override 
	            public ListCell<String> call(ListView<String> list) {
	                return new ListViewItem();
	            }
        	}
		);;
		rightFavoritebar.setCellFactory(new Callback<ListView<String>, 
				ListCell<String>>() {
		            @Override 
		            public ListCell<String> call(ListView<String> list) {
		                return new ListViewItem();
		            }
	        	}
			);;

	}
	

	
}
