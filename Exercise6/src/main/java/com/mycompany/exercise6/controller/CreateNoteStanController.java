package com.mycompany.exercise6.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mycompany.exercise6.data.MusicalInstrument;
import com.mycompany.exercise6.data.Note;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pane.NotePane;
import pane.NoteStan;

public class CreateNoteStanController implements Initializable {

	@FXML
	private ChoiceBox<MusicalInstrument> instruments;

	@FXML
	private ChoiceBox<Integer> during;

	@FXML
	private Pane mainPane;

	@FXML
	private TextField duringNote;

	@FXML
	private Button createNoteButton;

	private boolean isCreateNote = false;

	private NoteStan noteStan = new NoteStan();

	@FXML
	void deleteNote(ActionEvent event) {
		ArrayList<NotePane> selectedNotePanes = noteStan.getSelectedNotePanes();
		if (selectedNotePanes.size() == 0)
			return;
		for (NotePane np : selectedNotePanes) {
			CreatePaneController.SELECTED_PLAYER.removeNote(np.getNote());
			noteStan.removeNotePane(np);
		}
	}

	@FXML
	void setDuringNote(ActionEvent event) {
		ArrayList<NotePane> selectedNotePanes = noteStan.getSelectedNotePanes();
		System.out.println("     ");
		if (selectedNotePanes.size() == 0 || duringNote.getText().isEmpty()) {
			System.out.println(selectedNotePanes.size() + "     ");
			return;
		}
		System.out.println(selectedNotePanes.size() + "     ");
		for (NotePane np : selectedNotePanes) {
			np.getNote().setDurationNote(Double.valueOf(duringNote.getText()));
		}
	}

	void createNote() {
		if (isCreateNote) {
			noteStan.setOnMouseClicked(null);
			createNoteButton.getStyleClass().remove(createNoteButton.getStyleClass().size() - 1);
		} else {
			noteStan.setOnMouseClicked((e) -> {
				NotePane np = new NotePane();
				np.setLayoutX(e.getX());
				np.setLayoutY(e.getY());
				noteStan.addNotePane(np);
				Note note = new Note(e.getY(), e.getX());
				np.setNote(note);
				CreatePaneController.SELECTED_PLAYER.addNote(note);
			});
			createNoteButton.getStyleClass().add("selected_key_buttom");
		}
		isCreateNote = !isCreateNote;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Note> notes = CreatePaneController.SELECTED_PLAYER.getNotes();
		if (notes.size() != 0) {
			for (int i = notes.size() - 1; i >= 0; i--) {
				Note n = notes.get(i);
				if (n.getNote() != -1) {
					NotePane np = new NotePane();
					np.setLayoutX(n.getStartTime());
					np.setLayoutY(n.getNote());
					noteStan.addNotePane(np);
				} else {
					notes.remove(i);
				}
			}
		}
		createNoteButton.setOnMouseClicked((e) -> createNote());
		for (MusicalInstrument mi : MusicalInstrument.ALL_INSTRUMENTS) {
			instruments.getItems().add(mi);
		}
		for (int i = 5; i <= 30; i++) {
			during.getItems().add(i);
		}
		if (CreatePaneController.SELECTED_PLAYER.getInstrument() == null) {
			CreatePaneController.SELECTED_PLAYER.setInstrument(instruments.getItems().get(0));
			instruments.getSelectionModel().select(0);
		} else {
			instruments.getSelectionModel().select(CreatePaneController.SELECTED_PLAYER.getInstrument());
		}
		instruments.valueProperty()
				.addListener((a, old, newV) -> CreatePaneController.SELECTED_PLAYER.setInstrument(newV));
		during.getSelectionModel().select(new Integer(CreatePaneController.SELECTED_PLAYER.getDuring()));
		during.valueProperty().addListener((a, old, newV) -> CreatePaneController.SELECTED_PLAYER.setDuring(newV));
		mainPane.getChildren().add(noteStan);
	}
}