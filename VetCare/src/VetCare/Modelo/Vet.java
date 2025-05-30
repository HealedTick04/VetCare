/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gabri
 */
@Entity
@Table(name = "vet")
@NamedQueries({
    @NamedQuery(name = "Vet.findAll", query = "SELECT v FROM Vet v"),
    @NamedQuery(name = "Vet.findByVetId", query = "SELECT v FROM Vet v WHERE v.vetId = :vetId"),
    @NamedQuery(name = "Vet.findByPasswordVet", query = "SELECT v FROM Vet v WHERE v.passwordVet = :passwordVet"),
    @NamedQuery(name = "Vet.findByFirstName", query = "SELECT v FROM Vet v WHERE v.firstName = :firstName"),
    @NamedQuery(name = "Vet.findByLastName", query = "SELECT v FROM Vet v WHERE v.lastName = :lastName"),
    @NamedQuery(name = "Vet.findByNumberPhone", query = "SELECT v FROM Vet v WHERE v.numberPhone = :numberPhone"),
    @NamedQuery(name = "Vet.findByEmail", query = "SELECT v FROM Vet v WHERE v.email = :email")})
public class Vet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vetId")
    private Integer vetId;
    @Basic(optional = false)
    @Column(name = "passwordVet")
    private String passwordVet;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vetId")
    private Collection<Appointment> appointmentCollection;

    public Vet() {
    }

    public Vet(Integer vetId) {
        this.vetId = vetId;
    }

    public Vet(Integer vetId, String passwordVet, String firstName, String lastName, String numberPhone, String email) {
        this.vetId = vetId;
        this.passwordVet = passwordVet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public Integer getVetId() {
        return vetId;
    }

    public void setVetId(Integer vetId) {
        this.vetId = vetId;
    }

    public String getPasswordVet() {
        return passwordVet;
    }

    public void setPasswordVet(String passwordVet) {
        this.passwordVet = passwordVet;
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

    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vetId != null ? vetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vet)) {
            return false;
        }
        Vet other = (Vet) object;
        if ((this.vetId == null && other.vetId != null) || (this.vetId != null && !this.vetId.equals(other.vetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VetCare.Modelo.Vet[ vetId=" + vetId + " ]";
    }
    
}
