/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.dao.dao_impl;

/**
 *
 * @author Влад
 */
public class FactoryDao {
    
    private static FactoryDao factoryDao; 
    private CityDao cityDao = new CityDao();
    private CountryDao countryDao = new CountryDao();
    private IndicatorDao indicatorDao = new IndicatorDao();
    private ValueDao valueDao = new ValueDao();
    private DateDao dateDao = new DateDao();
    
    private FactoryDao() {}      
    
    public static FactoryDao getInstance() {
        if (factoryDao == null) {
            factoryDao = new FactoryDao();
        }
        return factoryDao;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public CountryDao getCountryDao() {
        return countryDao;
    }

    public IndicatorDao getIndicatorDao() {
        return indicatorDao;
    }

    public ValueDao getValueDao() {
        return valueDao;
    }

    public DateDao getDateDao() {
        return dateDao;
    }
    
    
}
