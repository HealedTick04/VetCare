/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabri
 */
public class ModelTableCustomer extends AbstractTableModel {
    private List<Customer> customers;
    private String encabezados[] = {"First Name", "Last Name", "Email", "Phone"};
    
    public ModelTableCustomer(List<Customer> c){
    customers = c;
    }
    
    @Override
    public int getRowCount() {
       if(customers != null)
            return customers.size();
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
        switch(columnIndex+1){
            case 1: return customers.get(rowIndex ).getFirstName();
            case 2: return customers.get(rowIndex ).getLastName();
            case 3: return customers.get(rowIndex).getEmail();
            default:  return customers.get(rowIndex).getPhone();
        }
    }
    
}
