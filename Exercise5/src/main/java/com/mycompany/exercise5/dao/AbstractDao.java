/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise5.dao;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Влад
 * 
 */
abstract public class AbstractDao<T extends Serializable> {
	Class<? extends Serializable> c ;
    
    public AbstractDao(Class<? extends Serializable> c) {
        this.c = c;
    }
    
    public void add(T t) throws SQLException{
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.save(t);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!s.equals(null) || s.isOpen()) s.close();
        }
    }

    
    public void delete(T t) throws SQLException{
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(t);
            s.getTransaction().commit();;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!s.equals(null) || s.isOpen()) s.close();
        }
    }

    
    public List<T> get() throws SQLException{
        List<T> list = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            list = s.createCriteria(c).list();
        } catch (Exception e) {
            e.printStackTrace();
       } finally {
            if ((s != null) || s.isOpen()) s.close();
        }
        return list;
    }

    
    public T get(int i) throws SQLException{
        T t = null;
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            t = (T) s.get(c, i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!s.equals(null) || s.isOpen()) s.close();
        }
        return t;
    }
    
   
    public void edit(T t) throws SQLException{
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(t);
            s.getTransaction().commit();;
        } catch (Exception e) {
            e.printStackTrace();
       } finally {
            if (!s.equals(null) || s.isOpen()) s.close();
        }
    }
}
