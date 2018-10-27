package pane;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class NoteStan extends Pane {

	private ObservableList<NotePane> notePanes = FXCollections.observableArrayList();

	public static int NOTE_STAN_WIDTH = 1374, NOTE_STAN_HEIGHT = 176;

	public NoteStan() {
		setPrefHeight(NOTE_STAN_HEIGHT);
		setPrefWidth(NOTE_STAN_WIDTH);
		setLayoutX(5);
		setLayoutY(132);
		getStyleClass().add("stan_pane");
		notePanes.addListener(new NotePaneListener());
	}

	public ObservableList<NotePane> getNotePanes() {
		return notePanes;
	}

	public void setNotePanes(ObservableList<NotePane> notePanes) {
		this.notePanes = notePanes;
	}

	public void addNotePane(NotePane np) {
		notePanes.add(np);
	}

	public void removeNotePane(NotePane np) {
		notePanes.remove(np);
	}

	public ArrayList<NotePane> getSelectedNotePanes() {
		ArrayList<NotePane> selectedNotePanes = new ArrayList<NotePane>();
		for (NotePane np : notePanes) {
			if (np.isSelected()) {
				selectedNotePanes.add(np);
			}
		}
		return selectedNotePanes;
	}

	private class NotePaneListener implements ListChangeListener<NotePane> {

		@Override
		public void onChanged(Change<? extends NotePane> c) {
			c.next();
			if (c.wasAdded()) {
				Platform.runLater(() -> {
					getChildren().add(c.getAddedSubList().get(c.getAddedSize() - 1));
				});
			}
			if (c.wasRemoved()) {
				Platform.runLater(() -> {
					getChildren().remove(c.getRemoved().get(c.getRemovedSize() - 1));
				});
			}

		}

	}

}
