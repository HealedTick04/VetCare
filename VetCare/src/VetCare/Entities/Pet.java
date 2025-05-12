/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Entities;
import java.time.LocalDate;
/**
 *
 * @author dary_
 */
public class Pet {
    private String ID;
    private String name;
    private String species;
    private boolean sex;
    private LocalDate birthday;
    private Customer customer;

    public Pet(String ID, String name, String species, boolean sex, LocalDate birthday, Customer customer) {
        this.ID = ID;
        this.name = name;
        this.species = species;
        this.sex = sex;
        this.birthday = birthday;
        this.customer = customer;
    }

    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getSpecies() {
        return species;
    }
    public boolean getSex(){
        return sex;
    }
    public LocalDate getBirthDay() {
        return birthday;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setID(String iD) {
        ID = iD;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSpecies(String species){
        this.species = species;
    }
    public void setSex(boolean sex){
        this.sex = sex;
    }
    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
