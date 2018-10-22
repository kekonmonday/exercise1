/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.controller;

import com.mycompany.exercise4.dao.HibernateUtil;
import com.mycompany.exercise4.dao.dao_impl.CityDao;
import com.mycompany.exercise4.dao.dao_impl.CountryDao;
import com.mycompany.exercise4.dao.dao_impl.DateDao;
import com.mycompany.exercise4.dao.dao_impl.FactoryDao;
import com.mycompany.exercise4.dao.dao_impl.IndicatorDao;
import com.mycompany.exercise4.dao.dao_impl.ValueDao;
import com.mycompany.exercise4.entity.City;
import com.mycompany.exercise4.entity.Country;
import com.mycompany.exercise4.entity.Date;
import com.mycompany.exercise4.entity.Indicator;
import com.mycompany.exercise4.entity.Value;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class CountryPaneController implements Initializable {

    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private ChoiceBox<Country> countryChoiceBox;

    @FXML
    private ChoiceBox<Indicator> parameterChoiceBox;

    @FXML
    private AreaChart<String, Number> chart;

    private CityDao cityDao = FactoryDao.getInstance().getCityDao();
    private IndicatorDao indicatorDao = FactoryDao.getInstance().getIndicatorDao();
    private ValueDao valueDao = FactoryDao.getInstance().getValueDao();
    private DateDao dateDao = FactoryDao.getInstance().getDateDao();
    private CountryDao countryDao = FactoryDao.getInstance().getCountryDao();

    private List<Country> countries;
    private List<Indicator> indicators;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CityPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        timeChoiceBox.getItems().addAll("за день", "за неделю", "за месяц");
        try {
            countries = countryDao.get();
            indicators = indicatorDao.get();
        } catch (SQLException ex) {
            Logger.getLogger(CityPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countryChoiceBox.getItems().addAll(countries);
        parameterChoiceBox.getItems().addAll(indicators);
        countryChoiceBox.getSelectionModel().select(0);
        parameterChoiceBox.getSelectionModel().select(0);
        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> renderChart());
        parameterChoiceBox.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> renderChart());
        timeChoiceBox.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> renderChart());
        timeChoiceBox.getSelectionModel().select(0);
    }

    private void renderChart() {
        java.util.Date nowDate = new java.util.Date();
        long valueNowDate = nowDate.getTime();
        java.util.Date fromDate = null;
        int selectedIndex = timeChoiceBox.getSelectionModel().getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                fromDate = new java.util.Date(valueNowDate - 86400000l);
                break;
            case 1:
                fromDate = new java.util.Date(valueNowDate - 604800000l);
                break;
            case 2:
                fromDate = new java.util.Date(valueNowDate - ((long) 31 * 24 * 60 * 60 * 1000));
                break;
            default:
                break;
        }
        List<Date> periodDates = dateDao.getDatesFromToNow(sdf.format(fromDate), sdf.format(nowDate));
        Session s = HibernateUtil.getSessionFactory().openSession();
        ArrayList<ArrayList<Value>> values = new ArrayList<ArrayList<Value>>();
        List<City> cities = ((Country) s.merge(countryChoiceBox.getSelectionModel().getSelectedItem())).getCityList();
        for (City c : cities) {
            ArrayList<Value> v = (ArrayList<Value>) valueDao.getDatesFromToNow(periodDates.get(0).getId(), periodDates.get(periodDates.size() - 1).getId(), parameterChoiceBox.getSelectionModel().getSelectedItem().getId(),
                    c.getId());
            values.add(v);
        }
        System.out.println(values.toString());
        chart.getData().clear();
        for (int i = 0; i < values.size() - 1; i++) {
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(cities.get(i).getName());
            for (Value v : values.get(i)) {
                series1.getData().add(new XYChart.Data(v.getDateId().getDate(), v.getValue()));
            }
            chart.getData().add(series1);
        }
        if (s != null && s.isOpen()) {
            s.close();
        }
    }

}
