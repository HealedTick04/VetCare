/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Entities;

/**
 *
 * @author dary_
 */
public class Admin extends User{
    private String id_RFC;
	
	public Admin(String id_CURP, String password, String name, String lastName,String numberPhone, String email, String id_RFC) {
	super(id_CURP, password, name, lastName, numberPhone, email);
	this.id_RFC = id_RFC;
	}
	
	public void setRFC(String id_RFC){
		this.id_RFC = id_RFC;
	}
	
	public String getRFC() {
		return id_RFC;
	}
}
