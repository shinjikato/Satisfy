package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import  java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICODecoder;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class LayoutController implements Initializable {
	@FXML
	private TextField urlField;//URL繝舌�ｼ
	@FXML
	private TabPane leftTabPane;//蟾ｦ縺ｮ繧ｿ繝悶ヱ繝�
	@FXML
	private Tab leftAddTab;//蟾ｦ繧ｿ繝悶�ｮ縲�+縲阪�懊ち繝ｳ
	@FXML
	private TabPane rightTabPane;//蜿ｳ縺ｮ繧ｿ繝悶ヱ繝�
	@FXML
	private Tab rightAddTab;//蜿ｳ繧ｿ繝悶�ｮ縲�+縲阪�懊ち繝ｳ
	@FXML
	private ListView<String> leftFavoritebar;//蟾ｦ縺ｮ縺頑ｰ励↓蜈･繧翫ヰ繝ｼ
	@FXML
	private ListView<String> rightFavoritebar;//蜿ｳ縺ｮ縺頑ｰ励↓蜈･繧翫ヰ繝ｼ
	
	@FXML
	private SplitPane splitPane;//蛻�蜑ｲ繝代ロ(蜿ｳ縺ｨ蟾ｦ縺ｮ繝代ロ縺悟ｭ占ｦ∫ｴ�)
	
	boolean isLeftPane,isRightPane;//蜿ｳ縺ｮ繝代ロ繝ｫ�ｼ悟ｷｦ縺ｮ繝代ロ
	
	private WebView lastClickWebview;//譛�蠕後↓繧ｯ繝ｪ繝�繧ｯ縺輔ｌ縺欷ebView
	
	@FXML
	private void setUrlAction(ActionEvent event) {//URL繝舌�ｼ縺ｮ繝�繧ｭ繧ｹ繝医°繧姥ebView縺ｮ繝壹�ｼ繧ｸ繧貞､画峩縺吶ｋ
		String url = urlField.getText();//URL繝舌�ｼ縺九ｉ繝�繧ｭ繧ｹ繝医ｒ蜿門ｾ�
		//繝�繧ｭ繧ｹ繝医�ｮ譛�蛻昴′縺敬ttps://縺吃ttps://縺倥ｃ縺ｪ縺�縺ｨ縺阪�ｯ
		if(url.substring(0,7).equals("http://") == false && url.substring(0,8).equals("https://") == false){
			if(url.substring(0,3).equals("www")){//繝�繧ｭ繧ｹ繝医′www縺ｧ蟋九∪繧峨↑縺九▲縺溘ｉ
				url = "http://" + url;//http://繧偵▽縺代ｋ
			}else{
				url = "http://www." + url;//http://www.繧偵▽縺代ｋ
			}	
		}
		if(url.matches("^(https?|ftp)(:ﾂ･ﾂ･/ﾂ･ﾂ･ﾂ･/[-_.!~*ﾂ･ﾂ･'()a-zA-Z0-9;ﾂ･ﾂ･/?:ﾂ･ﾂ･@&=+ﾂ･ﾂ･$,%#]+)$")){//URL蛻､螳壼�ｦ逅�
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		urlField.setText(url);//URL繝舌�ｼ縺ｮ譁�蟄励ｒ譖ｴ譁ｰ
		lastClickWebview.getEngine().load(url);//url縺ｮ蝣ｴ謇�縺ｸWebView繧貞虚縺九☆
		
	}
	
	@FXML
	private void backButtonAction(ActionEvent event){//謌ｻ繧九�懊ち繝ｳ繧偵♀縺励◆譎ゅ�ｮ蜃ｦ逅�
		WebHistory history = lastClickWebview.getEngine().getHistory();//螻･豁ｴ繧貞叙蠕�
		ObservableList<WebHistory.Entry> entryList=history.getEntries();//
	    int currentIndex = history.getCurrentIndex();

	    Platform.runLater(new Runnable() { public void run() { history.go(-1); } });
	    String url = entryList.get(currentIndex>0?currentIndex-1:currentIndex).getUrl();//荳�蛟句燕縺ｮURL縺ｮ蜿門ｾ�
	    lastClickWebview.getEngine().load(url);//縺昴ｌ縺ｮ繝ｭ繝ｼ繝�
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
			//splitPane.setDividerPositions(1.0,0.0);
			isLeftPane = true;
		}
		else if((isLeftPane == true && isRightPane == false) || (isLeftPane == false && isRightPane == true)){
			dt.setToValue(.5);
			dt.play();
			//splitPane.setDividerPositions(0.5,0.5);
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
			//splitPane.setDividerPositions(0.0,1.0);
			isRightPane = true;
		}
		else if((isLeftPane == true && isRightPane == false) || (isLeftPane == false && isRightPane == true)){
			dt.setToValue(.5);
			dt.play();
			//splitPane.setDividerPositions(0.5,0.5);
			isLeftPane = false;
			isRightPane = false;
		}
		
	}
	
	@FXML
	private void leftTabAddAction(Event event){
		Tab tab = new Tab("");
		CostomWebView web = new CostomWebView(tab,urlField);
		//WebView web = new WebView();
		//tab.setText("new Tab");
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
		//WebView web = new WebView();
		//tab.setText("new Tab");
		//System.out.println(web.getEngine().getLocation());
		//ImageView imageview = new ImageView(getIconImage("http://www.google.co.jp/favicon.ico"));
		//ImageView imageview = new ImageView(new Image(getClass().getResourceAsStream("icon.png")));
		//ImageView imageview = new ImageView(getIconImage("http://www.google.co.jp/favicon.ico"));
		web.web.getEngine().load("http://www.google.co.jp");
		tab.setContent(web.web);
		//web.iconView.fitHeightProperty().bind(rightTabPane.getTabMaxHeight());
		//tab.setGraphic(web.iconView);
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
		//System.out.print(url);
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
		
		leftFavoritebar.getItems().add(url);
		//System.out.println("add page");
	}
	
	@FXML
	private void leftbarClicked(MouseEvent event){
		if(event.getButton().equals(MouseButton.SECONDARY) == false){
			Tab selecttab = leftTabPane.getSelectionModel().getSelectedItem();
			WebView view = (WebView)selecttab.getContent();
			
			if(leftFavoritebar.getItems().size() != 0) {
				String url = leftFavoritebar.getSelectionModel().getSelectedItem();
				view.getEngine().load(url);
				leftFavoritebar.getSelectionModel().setSelectionMode(null);
			}
		}
	}
	@FXML
	public void historyView(Event event){
		Tab tab = new Tab();
		//	rightTabPane
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
		// TODO Auto-generated method stub
		
		isLeftPane = isRightPane = false;
		splitPane.setDividerPositions(0.5,0.5);
		System.setProperty("http.proxyHost", "proxy.salesio-sp.ac.jp");
		System.setProperty("http.proxyPort", "7080");
		//rightTabPane.getTabs().remove(0);
		//leftTabPane.getTabs().remove(0);
		//leftTabAddAction(null);
		//rightTabAddAction(null);
		//lastClickWebview =	rightWebview;
	}
	

	
}
