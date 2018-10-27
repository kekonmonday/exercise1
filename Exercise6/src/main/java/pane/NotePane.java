package pane;

import java.io.File;

import com.mycompany.exercise6.data.Note;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class NotePane extends Pane {

	private boolean isSelected = false;
	private Note note;

	private Image image = new Image(new File("src/main/resources/images/key.png").toURI().toString()),
			selectedImage = new Image(new File("src/main/resources/images/selected_key.png").toURI().toString());

	private ImageView imageView = new ImageView();

	public NotePane() {
		double width = image.getWidth(), height = image.getHeight();
		setPrefHeight(height);
		setPrefWidth(width);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		imageView.setImage(image);
		getChildren().add(imageView);
		setOnMouseClicked((e) -> {
			if (isSelected) {
				imageView.setImage(image);
			} else {
				imageView.setImage(selectedImage);
			}
			isSelected = !isSelected;
		});
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
