/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.dao.dao_impl;

import com.mycompany.exercise4.dao.AbstractDAO;
import com.mycompany.exercise4.entity.Indicator;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Влад
 */
public class IndicatorDao extends AbstractDAO<Indicator> {
    
    public IndicatorDao() {
        super(Indicator.class);
    }
    
    public Indicator getByName(String name) {
        Indicator indicator = null;
        List<Indicator> indicators = null;
        try {
            indicators = get(); 
        } catch (SQLException ex) {
            Logger.getLogger(IndicatorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Indicator i : indicators) {
            if (i.equals(name)) {
                indicator = i;
                break;
            }
        }
        return indicator;
    }
    
}
