/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.controller;

import com.mycompany.exercise4.dao.HibernateUtil;
import com.mycompany.exercise4.dao.dao_impl.CityDao;
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
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import org.hibernate.Session;

/**
 *
 * @author Влад
 */
public class TimePaneController implements Initializable {

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ChoiceBox<City> cityChoiceBox;

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
        try {
            cities = cityDao.get();
            indicators = indicatorDao.get();
        } catch (SQLException ex) {
            Logger.getLogger(CityPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cityChoiceBox.getItems().addAll(cities);
        cityChoiceBox.getSelectionModel().select(0);
        cityChoiceBox.getSelectionModel().selectedItemProperty().addListener((a, old, newV) -> renderChart());
        toDatePicker.valueProperty().addListener((a, old, newV) -> renderChart());
        fromDatePicker.valueProperty().addListener((a, old, newV) -> renderChart());
    }

    private void renderChart() {
        if (toDatePicker.getValue() == null || fromDatePicker.getValue() == null) {
            return;
        }
        LocalDate fld = fromDatePicker.getValue();
        LocalDate tld = toDatePicker.getValue();
        String preF = (((int) fld.getDayOfMonth() / 10) == 0) ? "0" : "";
        String fromDate = preF + fld.getDayOfMonth() + "." + fld.getMonthValue() + "." + fld.getYear() + " 00:00";
        String preT = (((int) tld.getDayOfMonth() / 10) == 0) ? "0" : "";
        String toDate = preT + tld.getDayOfMonth() + "." + tld.getMonthValue() + "." + tld.getYear() + " 00:00";
        List<Date> periodDates = dateDao.getDatesFromToNow(fromDate, toDate);
        if (periodDates.size() == 0) {
            return;
        }
        ArrayList<ArrayList<Value>> values = new ArrayList<ArrayList<Value>>();
        for (Indicator c : indicators) {
            ArrayList<Value> v = (ArrayList<Value>) valueDao.getDatesFromToNow(periodDates.get(0).getId(), periodDates.get(periodDates.size() - 1).getId(), c.getId(),
                    cityChoiceBox.getSelectionModel().getSelectedItem().getId());
            values.add(v);
        }
        System.out.println(values.toString());
        chart.getData().clear();
        for (int i = 0; i < values.size() - 1; i++) {
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(indicators.get(i).getName());
            for (Value v : values.get(i)) {
                series1.getData().add(new XYChart.Data(v.getDateId().getDate(), v.getValue()));
            }
            chart.getData().add(series1);
        }
    }
}
