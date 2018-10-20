/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.pane;

import com.mycompany.exercise5.controller.MainController;
import com.mycompany.exercise5.dao.HibernateUtil;
import com.mycompany.exercise5.dao.dao_impl.FactoryDao;
import com.mycompany.exercise5.entity.Note;
import com.mycompany.exercise5.entity.NoteTag;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class NotePane extends Pane {
    
    private Note note;
    private Label name, date;
    private Button change, delete;
    
    public NotePane(Note note) {
        this.note = note;
        setPrefHeight(91);
        setPrefWidth(613);
        name = new Label();
        name.setLayoutX(14);
        name.setLayoutY(25);
        name.setPrefHeight(17);
        name.setPrefWidth(377);
        name.setText(note.getHeadline());
        date = new Label();
        date.setLayoutX(14);
        date.setLayoutY(50);
        date.setPrefHeight(17);
        date.setPrefWidth(198);
        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(note.getChangeDate()));
        change = new Button("Изменить");
        change.setLayoutX(459);
        change.setLayoutY(33);
        change.setPrefHeight(25);
        change.setPrefWidth(70);
        change.setOnMouseClicked(e -> {
            MainController.selectedNote = note;
            MainController.changeStage = new Stage();
            Parent parent = null;
            try {
                parent = FXMLLoader.load(NotePane.class.getResource("/fxml/change_pane.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(NotePane.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainController.changeStage.setScene(new Scene(parent));
            MainController.changeStage.show();
        });
        delete = new Button("Удалить");
        delete.setLayoutX(540);
        delete.setLayoutY(33);
        delete.setPrefHeight(25);
        delete.setPrefWidth(60);
        delete.setOnMouseClicked(e -> {
            MainController.notes.remove(this);
            Session s = HibernateUtil.getSessionFactory().openSession();
            this.note = (Note) s.merge(this.note);
            List<NoteTag> notesTag = this.note.getNoteTagList();
            for (NoteTag nt : notesTag) {
                try {
                    FactoryDao.getNoteTagDao().delete(nt);
                } catch (SQLException ex) {
                    Logger.getLogger(NotePane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (s != null && s.isOpen()) {
                s.close();
            }
            try {
                FactoryDao.getNoteDao().delete(note);
            } catch (SQLException ex) {
                Logger.getLogger(NotePane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        setOnMouseClicked(e -> {
            MainController.selectedNote = note;
            Stage infoStage = new Stage();
            Parent parent = null;
            try {
                parent = FXMLLoader.load(NotePane.class.getResource("/fxml/info_pane.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(NotePane.class.getName()).log(Level.SEVERE, null, ex);
            }
            infoStage.setScene(new Scene(parent));
            infoStage.showAndWait();
        });
        getChildren().add(name);
        getChildren().add(date);
        getChildren().add(change);
        getChildren().add(delete);       
        getStyleClass().add("note_pane");
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotePane other = (NotePane) obj;
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        return true;
    }
    
    
        
}
