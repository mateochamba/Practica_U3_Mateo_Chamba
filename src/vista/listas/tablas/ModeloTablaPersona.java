/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.listas.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import modelo.Persona;

/**
 *
 * @author FA506ICB-HN114W
 */
public class ModeloTablaPersona extends AbstractTableModel {

    private DynamicList<Persona> personas;

    @Override
    public int getRowCount() {
        return personas.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Persona p = personas.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (p != null) ? p.getDNI() : " ";
                case 1:
                    return (p != null) ? p.getApellidos() + " " + p.getNombres() : "";
                case 2:
                    return (p != null) ? p.getTelefono() : "";
                case 3:
                    return (p != null) ? p.getDireccion() : "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "DNI";
            case 1:
                return "USUARIO";
            case 2:
                return "TELEFONO";
            case 3:
                return "DIRECCION";
            default:
                return null;
        }
    }

    public DynamicList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(DynamicList<Persona> personas) {
        this.personas = personas;
    }

}
