/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.dao.dao_impl;

import com.mycompany.exercise4.dao.AbstractDAO;
import com.mycompany.exercise4.entity.City;

/**
 *
 * @author Влад
 */
public class CityDao extends AbstractDAO<City> {
    
    public CityDao() {
        super(City.class);
    }
    
}
