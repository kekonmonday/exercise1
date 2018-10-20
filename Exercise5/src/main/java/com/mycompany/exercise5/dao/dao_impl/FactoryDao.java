/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.dao.dao_impl;

import com.mycompany.exercise5.dao.AbstractDao;
import java.io.Serializable;

/**
 *
 * @author Влад
 */
public class FactoryDao {
    
    private static NoteDao noteDao;
    private static NoteTagDao noteTagDao;
    private static TagDao tagDao;
    
    public static NoteDao getNoteDao() {
        if (noteDao == null) {
            noteDao = new NoteDao();
        }
        return noteDao;
    }
    
    public static NoteTagDao getNoteTagDao() {
        if (noteTagDao == null) {
            noteTagDao = new NoteTagDao();
        }
        return noteTagDao;
    }
    
    public static TagDao getTagDao() {
        if (tagDao == null) {
            tagDao = new TagDao();
        }
        return tagDao;
    }
}
