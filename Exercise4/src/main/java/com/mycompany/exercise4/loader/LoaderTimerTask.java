/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.loader;

import com.mycompany.exercise4.dao.HibernateUtil;
import com.mycompany.exercise4.dao.dao_impl.CityDao;
import com.mycompany.exercise4.dao.dao_impl.DateDao;
import com.mycompany.exercise4.dao.dao_impl.FactoryDao;
import com.mycompany.exercise4.dao.dao_impl.IndicatorDao;
import com.mycompany.exercise4.dao.dao_impl.ValueDao;
import com.mycompany.exercise4.entity.City;
import com.mycompany.exercise4.entity.Date;
import com.mycompany.exercise4.entity.Indicator;
import com.mycompany.exercise4.entity.Value;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Влад
 */
public class LoaderTimerTask extends TimerTask {

    private CityDao cityDao = FactoryDao.getInstance().getCityDao();
    private IndicatorDao indicatorDao = FactoryDao.getInstance().getIndicatorDao();
    private ValueDao valueDao = FactoryDao.getInstance().getValueDao();
    private DateDao dateDao = FactoryDao.getInstance().getDateDao();

    @Override
    public void run() {
        List<Date> forNewIdDate = null;
        List<Indicator> indicators = null;
        List<City> cities = null;
        try {
            cities = cityDao.get();
            indicators = indicatorDao.get();
            forNewIdDate = dateDao.get();
        } catch (SQLException ex) {
            Logger.getLogger(LoaderTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dateString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date());
        Date date = new Date();
        if (forNewIdDate.size() == 0) {
            date.setId(0);
        } else {
            date.setId(forNewIdDate.get(forNewIdDate.size() - 1).getId() + 1);
        }
        date.setDate(dateString);
        try {
            dateDao.add(date);
        } catch (SQLException ex) {
            Logger.getLogger(LoaderTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (City c : cities) {
            try {
                Map<String, AvgValue> values = new HashMap<String, AvgValue>();
                for (Indicator i : indicators) {
                    values.put(i.getName(), new AvgValue());
                }
                HttpURLConnection con = (HttpURLConnection) new URL(getUri(c.getCountryId().getName(), c.getName())).openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject json = new JSONObject(response.toString());
                JSONArray retultArray = json.getJSONArray("results");
                for (int i = 0; i < retultArray.length(); i++) {
                    JSONObject result = retultArray.getJSONObject(i);
                    JSONArray measurements = result.getJSONArray("measurements");
                    for (int j = 0; j < measurements.length(); j++) {
                        JSONObject measurement = measurements.getJSONObject(j);
                        String parameter = measurement.getString("parameter");
                        double valueMeasurement = measurement.getDouble("value");
                        //System.out.println(valueMeasurement + "");
                        values.get(parameter).addToValue(valueMeasurement);
                        values.get(parameter).incCounter();
                    }
                }
                for (Indicator i : indicators) {
                    if (values.get(i.getName()).getCounter() > 0) {
                        Value value = new Value();
                        value.setCityId(c);
                        value.setIndicatorId(i);
                        value.setValue(values.get(i.getName()).getAvg());
                        value.setDateId(date);
                        valueDao.add(value);
                    }
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(LoaderTimerTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoaderTimerTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoaderTimerTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String getUri(String country, String city) {
        return "https://api.openaq.org/v1/latest?city=" + city + "&country=" + country;
    }

    private class AvgValue {

        private int counter = 0;
        private double value = 0;

        public void incCounter() {
            this.counter++;
        }

        public void addToValue(double add) {
            this.value += add;
        }

        public double getAvg() {
            return value / counter;
        }

        public double getCounter() {
            return this.counter;
        }

    }

}
