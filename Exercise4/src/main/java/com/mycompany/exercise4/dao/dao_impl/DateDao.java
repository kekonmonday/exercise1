/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.dao.dao_impl;

import com.mycompany.exercise4.dao.AbstractDAO;
import com.mycompany.exercise4.dao.HibernateUtil;
import com.mycompany.exercise4.entity.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Влад
 */
public class DateDao extends AbstractDAO<Date> {

    public DateDao() {
        super(Date.class);
    }

    public List<Date> getDatesFromToNow(String from, String to) {
        List<Date> dates = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        String sqlQuery = "SELECT * FROM date where date > '" + from + "' and date < '" + to + "'";
        System.err.println(sqlQuery);
        SQLQuery query = s.createSQLQuery(sqlQuery).addEntity(Date.class);
        dates = query.list();
        if (s != null && s.isOpen()) {
            s.close();
        }
        return dates;
    }

}
