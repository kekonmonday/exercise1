/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.dao.dao_impl;

import com.mycompany.exercise5.dao.AbstractDao;
import com.mycompany.exercise5.entity.Tag;

/**
 *
 * @author Влад
 */
public class TagDao extends AbstractDao<Tag> {
    
    public TagDao() {
        super(Tag.class);
    }
    
}