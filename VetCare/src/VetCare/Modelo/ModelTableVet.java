/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Modelo;

/**
 *
 * @author gabri
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabri
 */
public class ModelTableVet extends AbstractTableModel {
    private List<Vet> vets;
    private final String[] encabezados = {"First Name", "Last Name", "Email", "Phone"};

    public ModelTableVet(List<Vet> v) {
        vets = v;
    }

    @Override
    public int getRowCount() {
        return (vets != null) ? vets.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vet vet = vets.get(rowIndex);
        switch (columnIndex) {
            case 0: return vet.getFirstName();
            case 1: return vet.getLastName();
            case 2: return vet.getEmail();
            case 3: return vet.getNumberPhone();
            default: return null;
        }
    }
}
