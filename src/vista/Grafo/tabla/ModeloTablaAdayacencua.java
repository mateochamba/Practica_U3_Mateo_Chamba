/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.Grafo.tabla;

import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.Utiles;
import grafo.modelo.SubEstacion;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author FA506ICB-HN114W
 */
public class ModeloTablaAdayacencua extends AbstractTableModel {

    private GrafosEtiquetadosDirigidos<SubEstacion> grafo;

    public GrafosEtiquetadosDirigidos<SubEstacion> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafosEtiquetadosDirigidos<SubEstacion> grafo) {
        this.grafo = grafo;
    }

    @Override
    public int getRowCount() {
        return grafo.num_vertices();
    }

    @Override
    public int getColumnCount() {
        return grafo.num_vertices() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            if (columnIndex == 0) {
                return grafo.getLabelE(rowIndex + 1).toString();
            } else {
                SubEstacion o = grafo.getLabelE(rowIndex + 1);
                SubEstacion d = grafo.getLabelE(columnIndex);
                if (grafo.isEdgeE(o, d)) {
                    return Utiles.redondear(grafo.peso_arista(rowIndex + 1, columnIndex)).toString();
                } else {
                    return "--";
                }
            }
        } catch (Exception e) {
            return "";
        }

    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Sub-Estaciones";
        } else {
            try {
                return grafo.getLabelE(column).toString();
            } catch (Exception e) {
                return "";
            }
        }
    }

    public void actualizarGrafo(GrafosEtiquetadosDirigidos<SubEstacion> nuevoGrafo) {
        grafo = nuevoGrafo;
        fireTableDataChanged();
    }

}
