/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Влад
 */
@Entity
@Table(name = "value", catalog = "exercise4", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Value.findAll", query = "SELECT v FROM Value v")
    , @NamedQuery(name = "Value.findById", query = "SELECT v FROM Value v WHERE v.id = :id")
    , @NamedQuery(name = "Value.findByValue", query = "SELECT v FROM Value v WHERE v.value = :value")})
public class Value implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private City cityId;
    @JoinColumn(name = "date_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Date dateId;
    @JoinColumn(name = "indicator_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Indicator indicatorId;

    public Value() {
    }

    public Value(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Date getDateId() {
        return dateId;
    }

    public void setDateId(Date dateId) {
        this.dateId = dateId;
    }

    public Indicator getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Indicator indicatorId) {
        this.indicatorId = indicatorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Value)) {
            return false;
        }
        Value other = (Value) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value + "";
    }
        
}
