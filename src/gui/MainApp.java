package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class MainApp extends Application {
	private MediaPlayer player;
	private ListView<String> folderView;
	private ListView<String> folderContentView;
	private MediaView view;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Media player");
		GridPane pane = new GridPane();
		initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	private void initContent(GridPane pane) {
		folderView = new ListView<String>();
		File dir = new File("C:/");
		File[] fileArray = dir.listFiles();

		for (File f : fileArray) {
			if (f.isDirectory()) {
				folderView.getItems().add(f.toString());
			}
		}

		pane.add(folderView, 0, 0);

		Button open = new Button("Open");

		open.setOnAction(event -> openAction());

		pane.add(open, 0, 1);

		ChangeListener<String> folderViewListener = (ov, oldString, newString) -> folderViewSelectionChanged();

		folderView.getSelectionModel().selectedItemProperty().addListener(folderViewListener);

		folderContentView = new ListView<>();

		ChangeListener<String> folderContentViewListener = (ov, oldString,
				newString) -> selectionFolderContentViewChanged();

		pane.add(folderContentView, 1, 0, 2, 1);

		folderContentView.getSelectionModel().selectedItemProperty().addListener(folderContentViewListener);

		Button play = new Button("Play");

		play.setOnAction(event -> playAction());

		pane.add(play, 1, 1);

		Button stop = new Button("Stop");

		stop.setOnAction(event -> stopAction());

		pane.add(stop, 2, 1);

		view = new MediaView(player);
	}

	private void stopAction() {
		player.stop();
	}

	private void playAction() {
		File file = new File(folderView.getSelectionModel().getSelectedItem());
		Media media = new Media(file.toURI().toString());
		player = new MediaPlayer(media);
		player.play();
		view.setMediaPlayer(player);
	}

	private void selectionFolderContentViewChanged() {

	}

	private void folderViewSelectionChanged() {
		String filePath = folderView.getSelectionModel().getSelectedItem();

		if (filePath != null) {
			File file = new File(filePath);

			if (file != null) {

				ArrayList<String> folderContentArrayList = new ArrayList<>();

				File[] listOfFiles = file.listFiles();

				if (listOfFiles != null) {
					for (File f : listOfFiles) {
						folderContentArrayList.add(f.toString());
					}
				}

				folderContentView.getItems().setAll(folderContentArrayList);
			}
		} else {
			folderContentView.getItems().clear();
		}
	}

	private void openAction() {
		File openFile = new File(this.folderView.getSelectionModel().getSelectedItem());

		File[] files = openFile.listFiles();
		ArrayList<String> folderContentArrayList = new ArrayList<>();

		if (files != null) {
			for (File f : files) {
				folderContentArrayList.add(f.toString());
			}
		}

		folderView.getItems().setAll(folderContentArrayList);
	}

	// private void initContent(GridPane pane) {
	// Media pick = new
	// Media("file:///F:/Eclipse/EclipseWS/2016-12-22-Java_Sound_Media/Ich_Will.m4a");
	//
	// player = new MediaPlayer(pick);
	// player.play();
	//
	//
	// view.getMediaPlayer();
	//
	// pane.add(view, 0, 0, 2, 2);
	//
	// }
}