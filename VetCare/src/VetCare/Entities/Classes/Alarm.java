/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Entities.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gabri
 */
@Entity
@Table(name = "alarm")
@NamedQueries({
    @NamedQuery(name = "Alarm.findAll", query = "SELECT a FROM Alarm a"),
    @NamedQuery(name = "Alarm.findByAlarmId", query = "SELECT a FROM Alarm a WHERE a.alarmId = :alarmId"),
    @NamedQuery(name = "Alarm.findByThreshold", query = "SELECT a FROM Alarm a WHERE a.threshold = :threshold")})
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "alarmId")
    private String alarmId;
    @Basic(optional = false)
    @Column(name = "threshold")
    private int threshold;
    @Lob
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @ManyToOne(optional = false)
    private Product productId;

    public Alarm() {
    }

    public Alarm(String alarmId) {
        this.alarmId = alarmId;
    }

    public Alarm(String alarmId, int threshold) {
        this.alarmId = alarmId;
        this.threshold = threshold;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alarmId != null ? alarmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alarm)) {
            return false;
        }
        Alarm other = (Alarm) object;
        if ((this.alarmId == null && other.alarmId != null) || (this.alarmId != null && !this.alarmId.equals(other.alarmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VetCare.EntitiesClasses.Alarm[ alarmId=" + alarmId + " ]";
    }
    
}
