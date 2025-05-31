/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gabri
 */
@Entity
@Table(name = "admi")
@NamedQueries({
    @NamedQuery(name = "Admi.findAll", query = "SELECT a FROM Admi a"),
    @NamedQuery(name = "Admi.findByAdminId", query = "SELECT a FROM Admi a WHERE a.adminId = :adminId"),
    @NamedQuery(name = "Admi.findByPasswordAdmin", query = "SELECT a FROM Admi a WHERE a.passwordAdmin = :passwordAdmin"),
    @NamedQuery(name = "Admi.findByFirstName", query = "SELECT a FROM Admi a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Admi.findByLastName", query = "SELECT a FROM Admi a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Admi.findByNumberPhone", query = "SELECT a FROM Admi a WHERE a.numberPhone = :numberPhone"),
    @NamedQuery(name = "Admi.findByEmail", query = "SELECT a FROM Admi a WHERE a.email = :email")})
public class Admi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "adminId")
    private Integer adminId;
    @Basic(optional = false)
    @Column(name = "passwordAdmin")
    private String passwordAdmin;
    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "numberPhone")
    private String numberPhone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    public Admi() {
    }

    public Admi(Integer adminId) {
        this.adminId = adminId;
    }

    public Admi(Integer adminId, String passwordAdmin, String firstName, String lastName, String numberPhone, String email) {
        this.adminId = adminId;
        this.passwordAdmin = passwordAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminId != null ? adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admi)) {
            return false;
        }
        Admi other = (Admi) object;
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VetCare.Modelo.Admi[ adminId=" + adminId + " ]";
    }
    
}
