/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.Grafo.tabla;

import controlador.TDA.listas.DynamicList;
import grafo.modelo.SubEstacion;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author FA506ICB-HN114W
 */
public class ModeloTablaSubEstacion extends AbstractTableModel {

    private DynamicList<SubEstacion> subestaciones;

    @Override
    public int getRowCount() {
        return subestaciones.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            SubEstacion e = subestaciones.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (e != null) ? e.getNombre() : " ";
                case 1:
                    return (e != null) ? e.getEscudo() : "";
                case 2:
                    return (e != null) ? e.getPortada() : "";
                case 3:
                    return (e != null) ? e.getCordenada().getLatitud() : "";
                case 4:
                    return (e != null) ? e.getCordenada().getLogitud() : "";
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "NOMBRE";
            case 1:
                return "PORTADA";
            case 2:
                return "ESCUDO";
            case 3:
                return "LATITUD";
            case 4:
                return "LONGITUD";
            default:
                return null;
        }
    }

    public DynamicList<SubEstacion> getSubEstaciones() {
        return subestaciones;
    }

    public void setSubEstaciones(DynamicList<SubEstacion> subestaciones) {
        this.subestaciones = subestaciones;
    }

}
