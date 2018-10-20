/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.controller;

import com.mycompany.exercise5.dao.HibernateUtil;
import com.mycompany.exercise5.dao.dao_impl.FactoryDao;
import com.mycompany.exercise5.dao.dao_impl.NoteDao;
import com.mycompany.exercise5.dao.dao_impl.NoteTagDao;
import com.mycompany.exercise5.dao.dao_impl.TagDao;
import com.mycompany.exercise5.entity.Note;
import com.mycompany.exercise5.entity.NoteTag;
import com.mycompany.exercise5.entity.Tag;
import com.mycompany.exercise5.pane.NotePane;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class AddPaneController implements Initializable {

    @FXML
    private ListView<Tag> tagList;

    @FXML
    private TextArea contentText;

    @FXML
    private TextField newTagText;

    @FXML
    private TextField headlineText;

    private TagDao tagDao = FactoryDao.getTagDao();
    private NoteDao noteDao = FactoryDao.getNoteDao();
    private NoteTagDao noteTagDao = FactoryDao.getNoteTagDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tagList.getItems().addAll(tagDao.get());
            tagList.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
        } catch (SQLException ex) {
            Logger.getLogger(AddPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteTag() throws SQLException {
        ObservableList<Tag> selectedTags = tagList.getSelectionModel().getSelectedItems();
        if (!selectedTags.isEmpty()) {
            for (Tag t : selectedTags) {
                tagList.getItems().remove(t);
                Session s = HibernateUtil.getSessionFactory().openSession();
                t = (Tag) s.merge(t);
                List<NoteTag> noteTags = t.getNoteTagList();
                if (noteTags != null && !noteTags.isEmpty()) {
                    for (NoteTag nt : t.getNoteTagList()) {
                        noteTagDao.delete(nt);
                    }
                }
                if (s != null && s.isOpen()) {
                    s.close();
                }
                tagDao.delete(t);
                MainController.tagsList.remove(t);
            }
        }
    }

    @FXML
    void addTag() throws SQLException {
        if (!newTagText.getText().isEmpty()) {
            Tag tag = new Tag();
            if (tagDao.get().size() == 0) {
                tag.setId(0);
            } else {
                tag.setId(tagDao.get().get(tagDao.get().size() - 1).getId() + 1);
            }
            tag.setName(newTagText.getText());
            tagDao.add(tag);
            tagList.getItems().add(tag);
            MainController.tagsList.add(tag);
        }
    }

    @FXML
    void addNote() throws SQLException {
        ObservableList<Tag> tags = tagList.getSelectionModel().getSelectedItems();
        Note note = new Note();
        if (noteDao.get().size() == 0) {
            note.setId(0);
        } else {
            note.setId(noteDao.get().get(noteDao.get().size() - 1).getId() + 1);
        }
        note.setCreateDate(new Date());
        note.setChangeDate(new Date());
        note.setHeadline(headlineText.getText());
        note.setContent(contentText.getText());
        noteDao.add(note);
        for (Tag t : tags) {
            NoteTag nt = new NoteTag();
            if (noteTagDao.get().size() == 0) {
                nt.setId(0);
            } else {
                nt.setId(noteTagDao.get().get(noteTagDao.get().size() - 1).getId() + 1);
            }
            nt.setNoteId(note);
            nt.setTagId(t);
            noteTagDao.add(nt);
        }
        MainController.notes.add(new NotePane(note));
        MainController.addStage.close();
        MainController.addStage = null;
    }

}
