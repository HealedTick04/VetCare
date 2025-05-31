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
public class ModelTableAppointment extends AbstractTableModel {

    private List<Appointment> appointments;
    private final String[] columnNames = {
        "Fecha", "Hora", "Cliente", "Mascota", "Veterinario", "Motivo", "Estado"
    };

    public ModelTableAppointment(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public int getRowCount() {
        return appointments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Appointment appointment = appointments.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return appointment.getAppointmentDate();
            case 1:
                return appointment.getAppointmentTime();
            case 2:
                return appointment.getCustomerId().getFirstName() + " " + appointment.getCustomerId().getLastName();
            case 3:
                return appointment.getPetId().getPetName();
            case 4:
                return appointment.getVetId().getFirstName()+ " " + appointment.getVetId().getLastName();
            case 5:
                return appointment.getReason();
            case 6:
                return appointment.getStatus();
            default:
                return null;
        }
    }

    public Appointment getAppointmentAt(int rowIndex) {
        return appointments.get(rowIndex);
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        fireTableDataChanged();
    }
}

