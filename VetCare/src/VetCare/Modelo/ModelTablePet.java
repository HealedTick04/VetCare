/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabri
 */
public class ModelTablePet extends AbstractTableModel {
    private List<Pet> pets;
    private String encabezados[] = {"Name", "Specie", "Sex", "Owner", "Birthday"};
    
    public ModelTablePet(List<Pet> p){
    pets = p;
    }
    
    @Override
    public int getRowCount() {
       if(pets != null)
            return pets.size();
        return 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }
    
    @Override
    public String getColumnName(int c){
        return encabezados[c];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    switch (columnIndex + 1) {
        case 1:
            return pets.get(rowIndex).getPetName();
        case 2:
            return pets.get(rowIndex).getSpecies();
        case 3:
            return pets.get(rowIndex).getSex() ? "Male" : "Female";
        case 4:
            return pets.get(rowIndex).getCustomerId().getFirstName() + " " +
                   pets.get(rowIndex).getCustomerId().getLastName();
        default:
            return dateFormat.format(pets.get(rowIndex).getBirthday());
    }
    }
    
}
