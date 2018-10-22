/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.controller;

import com.mycompany.exercise4.dao.dao_impl.CityDao;
import com.mycompany.exercise4.dao.dao_impl.DateDao;
import com.mycompany.exercise4.dao.dao_impl.FactoryDao;
import com.mycompany.exercise4.dao.dao_impl.IndicatorDao;
import com.mycompany.exercise4.dao.dao_impl.ValueDao;
import com.mycompany.exercise4.entity.City;
import com.mycompany.exercise4.entity.Date;
import com.mycompany.exercise4.entity.Indicator;
import com.mycompany.exercise4.entity.Value;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Влад
 */
public class CityPaneController implements Initializable {

    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private ChoiceBox<City> cityChoiceBox;

    @FXML
    private ChoiceBox<Indicator> parameterChoiceBox;

    @FXML
    private AreaChart<String, Number> chart;

    private CityDao cityDao = FactoryDao.getInstance().getCityDao();
    private IndicatorDao indicatorDao = FactoryDao.getInstance().getIndicatorDao();
    private ValueDao valueDao = FactoryDao.getInstance().getValueDao();
    private DateDao dateDao = FactoryDao.getInstance().getDateDao();

    private List<City> cities;
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
            cities = cityDao.get();
            indicators = indicatorDao.get();
        } catch (SQLException ex) {
            Logger.getLogger(CityPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cityChoiceBox.getItems().addAll(cities);
        parameterChoiceBox.getItems().addAll(indicators);
        cityChoiceBox.getSelectionModel().select(0);
        parameterChoiceBox.getSelectionModel().select(0);
        cityChoiceBox.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> renderChart());
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
        System.out.println(periodDates.toString());
        List<Value> values = valueDao.getDatesFromToNow(periodDates.get(0).getId(), periodDates.get(periodDates.size() - 1).getId(), parameterChoiceBox.getSelectionModel().getSelectedItem().getId(),
                cityChoiceBox.getSelectionModel().getSelectedItem().getId());
        values.sort((v1, v2) -> Integer.compare(v1.getDateId().getId(), v2.getDateId().getId()));
        System.out.println(values.toString());
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(parameterChoiceBox.getSelectionModel().getSelectedItem().getName());
        for (Value v : values) {
            series1.getData().add(new XYChart.Data(v.getDateId().getDate(), v.getValue()));
        }
        chart.getData().clear();
        chart.getData().add(series1);
    }

}
