/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Entities;

/**
 *
 * @author dary_
 */
public class User {
    private String id_CURP;
    private String password;
    private String name;
    private String lastName;
    private String numberPhone;
    private String email;

    public User(String id_CURP, String password, String name, String lastName, String numberPhone, String email) {
        this.id_CURP = id_CURP;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    /*getters */
    public String getId_CURP() {
        return id_CURP;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getNumberPhone() {
        return numberPhone;
    }
    public String getEmail() {
        return email;
    }

    /*setters*/
    public void setId_CURP(String id_CURP) {
        this.id_CURP = id_CURP;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
