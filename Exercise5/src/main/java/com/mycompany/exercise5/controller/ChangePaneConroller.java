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
public class ChangePaneConroller implements Initializable {

    @FXML
    private ListView<Tag> tagList;

    @FXML
    private TextArea contentText;

    @FXML
    private TextField newTagText;

    @FXML
    private TextField headlineText;

    private Note note;
    private TagDao tagDao = FactoryDao.getTagDao();
    private NoteDao noteDao = FactoryDao.getNoteDao();
    private NoteTagDao noteTagDao = FactoryDao.getNoteTagDao();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        note = MainController.selectedNote;
        try {
            tagList.getItems().addAll(tagDao.get());
            tagList.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePaneConroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentText.setText(note.getContent());
        headlineText.setText(note.getHeadline());
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<NoteTag> noteTags = ((Note) s.merge(note)).getNoteTagList();
        if (noteTags != null && !noteTags.isEmpty()) {
            for (NoteTag nt : noteTags) {
                tagList.getSelectionModel().select(nt.getTagId());
            }
        }
        if (s != null && s.isOpen()) {
            s.close();
        }
    }

    @FXML
    void changeNote() throws SQLException {
        note.setChangeDate(new Date());
        note.setContent(contentText.getText());
        note.setHeadline(headlineText.getText());
        noteDao.edit(note);
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<NoteTag> noteTags = ((Note) s.merge(note)).getNoteTagList();
        if (noteTags != null && !noteTags.isEmpty()) {
            for (NoteTag nt : noteTags) {
                noteTagDao.delete(nt);
            }
        }
        if (s != null && s.isOpen()) {
            s.close();
        }
        for (Tag t : tagList.getSelectionModel().getSelectedItems()) {
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
        for (int i = MainController.notes.size() - 1; i >= 0; i--) {
            MainController.notes.remove(i);
        }
        for (Note n : noteDao.get()) {
            MainController.notes.add(new NotePane(note));
        }
        MainController.changeStage.close();
        MainController.changeStage = null;
    }

}
