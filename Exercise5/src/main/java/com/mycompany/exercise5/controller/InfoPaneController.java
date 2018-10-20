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
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class InfoPaneController implements Initializable {
    
    @FXML
    private Label changeDate;

    @FXML
    private Label headline;

    @FXML
    private TextArea content;

    @FXML
    private Label createDate;

    @FXML
    private ListView<Tag> tags;
    
    private TagDao tagDao = FactoryDao.getTagDao();
    private NoteDao noteDao = FactoryDao.getNoteDao();
    private NoteTagDao noteTagDao = FactoryDao.getNoteTagDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Note note = MainController.selectedNote;//
        try {
            tags.getItems().addAll(tagDao.get());
            tags.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
        } catch (SQLException ex) {
            Logger.getLogger(InfoPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        createDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(note.getCreateDate()));
        changeDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(note.getChangeDate()));
        content.setText(note.getContent());
        headline.setText(note.getHeadline());
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<NoteTag> noteTags = ((Note) s.merge(note)).getNoteTagList();
        if (noteTags != null && !noteTags.isEmpty()) {
            for (NoteTag nt : noteTags) {
                tags.getSelectionModel().select(nt.getTagId());
            }
        }
        if (s != null && s.isOpen()) {
            s.close();
        }
    }
    
}
