/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gabri
 */
@Entity
@Table(name = "pet")
@NamedQueries({
    @NamedQuery(name = "Pet.findAll", query = "SELECT p FROM Pet p"),
    @NamedQuery(name = "Pet.findByPetId", query = "SELECT p FROM Pet p WHERE p.petId = :petId"),
    @NamedQuery(name = "Pet.findByPetName", query = "SELECT p FROM Pet p WHERE p.petName = :petName"),
    @NamedQuery(name = "Pet.findBySpecies", query = "SELECT p FROM Pet p WHERE p.species = :species"),
    @NamedQuery(name = "Pet.findBySex", query = "SELECT p FROM Pet p WHERE p.sex = :sex"),
    @NamedQuery(name = "Pet.findByBirthday", query = "SELECT p FROM Pet p WHERE p.birthday = :birthday")})
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "petId")
    private Integer petId;
    @Basic(optional = false)
    @Column(name = "petName")
    private String petName;
    @Basic(optional = false)
    @Column(name = "species")
    private String species;
    @Basic(optional = false)
    @Column(name = "sex")
    private boolean sex;
    @Basic(optional = false)
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "petId")
    private Collection<Appointment> appointmentCollection;
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    @ManyToOne(optional = false)
    private Customer customerId;

    public Pet() {
    }

    public Pet(Integer petId) {
        this.petId = petId;
    }

    public Pet(Integer petId, String petName, String species, boolean sex, Date birthday) {
        this.petId = petId;
        this.petName = petName;
        this.species = species;
        this.sex = sex;
        this.birthday = birthday;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (petId != null ? petId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pet)) {
            return false;
        }
        Pet other = (Pet) object;
        if ((this.petId == null && other.petId != null) || (this.petId != null && !this.petId.equals(other.petId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VetCare.Modelo.Pet[ petId=" + petId + " ]";
    }

    Object getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
