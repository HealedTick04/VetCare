/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Entities;

/**
 *
 * @author dary_
 */
public class Vet extends User {
    public String IDVet;
    
    public Vet(String IDVet, String id_CURP, String password, String name, String lastName, String numberPhone, String email) {
        super(id_CURP, password, name, lastName, numberPhone, email);

        this.IDVet = IDVet;
    }

    public String getIDVet() {
        return IDVet;
    }
    public void setIDVet(String iDVet) {
        IDVet = iDVet;
    }
}
