
package VetCare.Entities;

public class Admin extends User {
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
