package application;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
public class CostomWebView {
	WebView web = new WebView();
	TextField urlField;
	Tab tab;
	String[] downloadEnds = {".doc",".xls",".zip",".tgz",".jar",".exe",".lzh"};
	
	CostomWebView(Tab t, TextField tf){
		tab = t;
		urlField = tf;
		AddListeners();
	}
	void AddListeners(){
		web.getEngine().getLoadWorker().stateProperty().addListener(
		        new ChangeListener<State>() {
		            public void changed(ObservableValue ov, State oldState, State newState) {
		                if (newState == State.SUCCEEDED) {
		                	urlField.setText(web.getEngine().getLocation());
		                	String title = web.getEngine().getTitle();
		                	try{
			                	if (title.length() > 10){
			                		title = title.substring(0,9);
			                	}
			                	tab.setText(title);
		                	}catch(NullPointerException ne){
		                		System.out.println("When tab's title set webTitle, webTitle is null " + ne);
		                	}
		                }
		            }
		        });
		web.getEngine().locationProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldLoc, String newLoc){
				// TODO Auto-generated method stub
				String downloadEnd = null;
				String[] downloadEnds = {".doc",".xls",".zip",".tgz",".jar",".exe",".lzh"};
				
				
				for (String end : downloadEnds){
					if(newLoc.endsWith(end)){
						downloadEnd = end;
						break;
					}
				}
				
				if (downloadEnd != null){
					FileChooser chooser = new FileChooser();
					System.out.println("newLoc " + newLoc);
					chooser.setTitle("save" + newLoc);
					chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("newFile", downloadEnd));
					
					int fileNameIdx = newLoc.lastIndexOf("/")+1;
					if(fileNameIdx != 0){
						File saveFile = chooser.showSaveDialog(web.getScene().getWindow());
						if(saveFile != null){
							BufferedInputStream inStream = null;
							BufferedOutputStream outStream = null;
							try{
								inStream = new BufferedInputStream(new URL(newLoc).openStream());
								outStream = new BufferedOutputStream(new FileOutputStream(saveFile));
								int tmp = inStream.read();
								while(tmp != -1){
									outStream.write(tmp);
									tmp = inStream.read();
								}
							}catch(FileNotFoundException e){
								System.out.println("Can't save file" + e);
							}catch(MalformedURLException e){
								System.out.println("Can't save file" + e);
							}catch(IOException e){
								System.out.println("Can't save file" + e);
							}finally{
								try{if(inStream != null) inStream.close();}catch(IOException e){System.out.println("Can't close inStream"+e);}
								try{if(outStream != null) outStream.close();}catch(IOException e){System.out.println("Can't close outStream"+e);}
							}
						}
					}
				}
				
			}
			
		});
	}
	

}
