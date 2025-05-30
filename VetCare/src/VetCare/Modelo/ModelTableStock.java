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
public class ModelTableStock extends AbstractTableModel {
    private List<Product> pro;
    private String encabezados[] = {"Name", "Description", "type", "Brand", "Packaging","Cost","Batch", "Stock"};
    
    public ModelTableStock(List<Product> p){
    pro = p;
    }
    
    @Override
    public int getRowCount() {
       if(pro != null)
            return pro.size();
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
            return pro.get(rowIndex).getName();
        case 2:
            return pro.get(rowIndex).getDescription();
        case 3:
            return pro.get(rowIndex).getType();
        case 4:
            return pro.get(rowIndex).getBrand();
        case 5:
            return pro.get(rowIndex).getPackaging();
        case 6:
            return pro.get(rowIndex).getCost();
        case 7:
            return pro.get(rowIndex).getBatch();
        default:
            return pro.get(rowIndex).getStock();
      
    }
    }
    
}
