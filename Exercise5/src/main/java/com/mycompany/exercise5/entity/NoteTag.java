/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Влад
 */
@Entity
@Table(name = "note_tag", catalog = "exercise5", schema = "")
public class NoteTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Note noteId;
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tag tagId;

    public NoteTag() {
    }

    public NoteTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Note getNoteId() {
        return noteId;
    }

    public void setNoteId(Note noteId) {
        this.noteId = noteId;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoteTag)) {
            return false;
        }
        NoteTag other = (NoteTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject3.entity.NoteTag[ id=" + id + " ]";
    }
    
}
