/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gabri
 */

    public class AdmDatos {
    protected static EntityManagerFactory enf;
    public static EntityManagerFactory getEnf(){
        if (enf == null)
            enf = Persistence.createEntityManagerFactory("VetCarePU");
        return enf;
    
    }    
}
    

