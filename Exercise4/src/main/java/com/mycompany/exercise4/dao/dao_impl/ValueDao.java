/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.dao.dao_impl;

import com.mycompany.exercise4.dao.AbstractDAO;
import com.mycompany.exercise4.dao.HibernateUtil;
import com.mycompany.exercise4.entity.Value;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Влад
 */
public class ValueDao extends AbstractDAO<Value> {
    
    public ValueDao() {
        super(Value.class);
    }
    
    public List<Value> getDatesFromToNow(int fromDate, int toDate, int indicator, int city) {
        List<Value> values = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        String sqlQuery = "SELECT * FROM value where date_id >= " + fromDate + " and date_id <= " + toDate + " and indicator_id = " + indicator
                + " and city_id = " + city;
        System.err.println(sqlQuery);
        SQLQuery query = s.createSQLQuery(sqlQuery).addEntity(Value.class);
        values = query.list();
        if (s != null && s.isOpen()) {
            s.close();
        }
        return values;
    }
    
}
