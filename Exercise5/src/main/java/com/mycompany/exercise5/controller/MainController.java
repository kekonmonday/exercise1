/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.controller;

import com.mycompany.exercise5.dao.AbstractDao;
import com.mycompany.exercise5.dao.HibernateUtil;
import com.mycompany.exercise5.dao.dao_impl.FactoryDao;
import com.mycompany.exercise5.dao.dao_impl.NoteDao;
import com.mycompany.exercise5.dao.dao_impl.NoteTagDao;
import com.mycompany.exercise5.dao.dao_impl.TagDao;
import com.mycompany.exercise5.entity.Note;
import com.mycompany.exercise5.entity.NoteTag;
import com.mycompany.exercise5.entity.Tag;
import com.mycompany.exercise5.pane.NotePane;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class MainController implements Initializable {

    @FXML
    private VBox noteVbox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Tag> tags;

    public static Note selectedNote;
    public static Stage addStage, changeStage;

    private static NoteDao noteDao = FactoryDao.getNoteDao();
    private static TagDao tagDao = FactoryDao.getTagDao();
    private static NoteTagDao noteTagDao = FactoryDao.getNoteTagDao();
    public static ObservableList<NotePane> notes = FXCollections.observableArrayList();
    public static ObservableList<Tag> tagsList = FXCollections.observableArrayList();

    @FXML
    void sotrByDate() {
        for (NotePane np : notes) {
            noteVbox.getChildren().remove(np);
        }
        notes.sort((a, b) -> a.getNote().getChangeDate().compareTo(b.getNote().getChangeDate()));
        for (NotePane np : notes) {
            noteVbox.getChildren().add(np);
        }
    }

    @FXML
    void sortByName() {
        for (NotePane np : notes) {
            noteVbox.getChildren().remove(np);
        }
        notes.sort((a, b) -> a.getNote().getHeadline().compareTo(b.getNote().getHeadline()));
        for (NotePane np : notes) {
            noteVbox.getChildren().add(np);
        }
    }

    @FXML
    void showAllNotes() {
        for (int i = notes.size() - 1; i >= 0; i--) {
            notes.remove(i);
        }
        try {
            for (Note n : noteDao.get()) {
                notes.add(new NotePane(n));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void addNote() {
        addStage = new Stage();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(MainController.class.getResource("/fxml/add_pane.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addStage.setScene(new Scene(parent));
        addStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notes.addListener(new NoteListener());
        tagsList.addListener(new TagListener());
        try {
            tags.getItems().addAll(tagDao.get());
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tags.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> {
            try {
                for (int i = notes.size() - 1; i >= 0; i--) {
                    notes.remove(i);
                }
                List<Note> notes = noteDao.get();
                Session s = HibernateUtil.getSessionFactory().openSession();
                if (!notes.isEmpty()) {
                    for (Note note : notes) {
                        List<NoteTag> noteTags = ((Note) s.merge(note)).getNoteTagList();
                        if (noteTags != null && !noteTags.isEmpty()) {
                            for (NoteTag nt : noteTags) {
                                if (nt.getTagId().equals(newV)) {
                                    this.notes.add(new NotePane(note));
                                    break;
                                }
                            }
                        }
                    }
                }
                if (s != null && s.isOpen()) {
                    s.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        datePicker.valueProperty().addListener((a, old, newV) -> {
            try {
                for (int i = notes.size() - 1; i >= 0; i--) {
                    notes.remove(i);
                }
                List<Note> notes = noteDao.get();
                int newDays = newV.getDayOfYear() - 2;
                System.out.println(newDays + "");
                for (Note note : notes) {
                    long noteDays = ((((note.getChangeDate().getTime() - 1514764800000l) / 1000) / 60) / 60) / 24;
                    System.out.println(noteDays + "");
                    if (newDays == noteDays) {
                        this.notes.add(new NotePane(note));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        showAllNotes();
    }

    private class TagListener implements ListChangeListener<Tag> {

        public TagListener() {
        }

        @Override
        public void onChanged(Change<? extends Tag> c) {
            c.next();
            if (c.wasAdded()) {
                Platform.runLater(() -> {
                    tags.getItems().add(c.getAddedSubList().get(c.getAddedSize() - 1));
                });
            }
            if (c.wasRemoved()) {
                Platform.runLater(() -> {
                    tags.getItems().remove(c.getRemoved().get(c.getRemovedSize() - 1));
                });
            }
        }
    }

    private class NoteListener implements ListChangeListener<NotePane> {

        @Override
        public void onChanged(Change<? extends NotePane> c) {
            c.next();
            if (c.wasAdded()) {
                Platform.runLater(() -> {
                    noteVbox.getChildren().add(c.getAddedSubList().get(c.getAddedSize() - 1));
                    noteVbox.setPrefHeight(noteVbox.getPrefHeight() + 91);
                });
            }
            if (c.wasRemoved()) {
                Platform.runLater(() -> {
                    noteVbox.getChildren().remove(c.getRemoved().get(c.getRemovedSize() - 1));
                    noteVbox.setPrefHeight(noteVbox.getPrefHeight() - 91);
                });
            }
        }
    }

}
