package com.mycompany.exercise6.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.mycompany.exercise6.Main;
import com.mycompany.exercise6.data.MusicalGroup;
import com.mycompany.exercise6.data.Note;
import com.mycompany.exercise6.data.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pane.NoteStan;

public class CreatePaneController implements Initializable {

	@FXML
	private ChoiceBox<Player> players;

	public static Player SELECTED_PLAYER;

	public static Stage NOTE_STAN_STAGE;

	private Gson gson = new Gson();

	private MusicalGroup musicalGroup;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (MainController.sb != null) {
			musicalGroup = gson.fromJson(MainController.sb.toString(), MusicalGroup.class);
			players.getItems().addAll(musicalGroup.getPlayers());
			players.getSelectionModel().select(0);
			Player.iD = musicalGroup.getPlayers().size();
			return;
		}
		musicalGroup = new MusicalGroup();
		Player player = new Player();
		musicalGroup.addPlayer(player);
		players.getItems().add(player);
		players.getSelectionModel().select(0);
	}

	@FXML
	private void seeNoteStan(ActionEvent event) {
		SELECTED_PLAYER = players.getSelectionModel().getSelectedItem();
		NOTE_STAN_STAGE = new Stage();
		Parent parent = null;
		try {
			parent = FXMLLoader.load(CreatePaneController.class.getResource("/fxml/create_note_stan.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		NOTE_STAN_STAGE.setScene(new Scene(parent));
		NOTE_STAN_STAGE.show();
	}

	@FXML
	private void addPlayer(ActionEvent event) {
		Player player = new Player();
		musicalGroup.addPlayer(player);
		players.getItems().add(player);
	}

	@FXML
	private void playSound(ActionEvent event) {
		synchronizeNoteStan();
		for (Note n : musicalGroup.getPlayers().get(0).getNotes()) {
			System.out.println("{");
			System.out.println("starting Time = " + n.getStartTime());
			System.out.println("duration = " + (n.getDurationNote()
					* (NoteStan.NOTE_STAN_WIDTH / musicalGroup.getPlayers().get(0).getDuring())));
			System.out.println("}");
		}
		musicalGroup.playMusic();
	}

	@FXML
	private void saveSound(ActionEvent event) {
		synchronizeNoteStan();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(Main.primaryStage);
		String jsonMG = gson.toJson(musicalGroup, MusicalGroup.class);
		try (FileWriter writer = new FileWriter(file, false)) {
			writer.write(jsonMG);
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void synchronizeNoteStan() {
		for (Player p : musicalGroup.getPlayers()) {
			p.getNotes().sort((c1, c2) -> Double.compare(c1.getStartTime(), c2.getStartTime()));
			int durationPlayer = p.getDuring();
			double oneSecond = NoteStan.NOTE_STAN_WIDTH / durationPlayer;
			int allI = p.getNotes().size();
			for (int i = 0; i < allI; i++) {
				Note note = p.getNotes().get(i);
				if (i != allI - 1) {
					Note nextNote = p.getNotes().get(i + 1);
					if (note.getDurationNote() == 0) {
						note.setDurationNote((nextNote.getStartTime() - note.getStartTime()) / oneSecond);
					} else {
						if (note.getDurationNote() > (nextNote.getStartTime() - note.getStartTime()) / oneSecond) {
							note.setDurationNote((nextNote.getStartTime() - note.getStartTime()) / oneSecond);
						} else {
							allI++;
							Note silenceNote = new Note(-1, note.getStartTime() + note.getDurationNote() * oneSecond);
							silenceNote.setDurationNote(
									(nextNote.getStartTime() - silenceNote.getStartTime()) / oneSecond);
							p.getNotes().add(i + 1, silenceNote);
						}
					}
				} else {
					if (note.getDurationNote() == 0) {
						note.setDurationNote((NoteStan.NOTE_STAN_WIDTH - note.getStartTime()) / oneSecond);
					} else {
						if (note.getDurationNote() >= (NoteStan.NOTE_STAN_WIDTH - note.getStartTime()) / oneSecond) {
							note.setDurationNote((NoteStan.NOTE_STAN_WIDTH - note.getStartTime()) / oneSecond);
						} else {
							Note silenceNote = new Note(-1, note.getStartTime() + note.getDurationNote() * oneSecond);
							silenceNote.setDurationNote(
									(NoteStan.NOTE_STAN_WIDTH - silenceNote.getStartTime()) / oneSecond);
							p.getNotes().add(silenceNote);
							break;
						}
					}
				}
			}
			if (p.getNotes().get(0).getStartTime() != 0) {
				Note silenceNote = new Note(-1, 0);
				silenceNote.setDurationNote(p.getNotes().get(0).getStartTime() / oneSecond);
				p.getNotes().add(0, silenceNote);
			}
		}
	}

}
