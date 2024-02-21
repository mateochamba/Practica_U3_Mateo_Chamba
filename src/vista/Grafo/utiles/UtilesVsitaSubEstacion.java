/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.Grafo.utiles;

import controlador.DAO.grafosExemplo.SubEstacionDao;
import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;
import grafo.modelo.SubEstacion;
import java.io.FileWriter;
import javax.swing.JComboBox;

/**
 *
 * @author FA506ICB-HN114W
 */
public class UtilesVsitaSubEstacion {

    public static void crearMapaSubestacion(GrafosEtiquetadosDirigidos<SubEstacion> ge) throws Exception {
        String maps = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n"
                + "        osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n"
                + "        osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n"
                + "\n"
                + "var map = L.map('map').setView([-4.036, -79.201], 15);\n"
                + "\n"
                + "L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n"
                + "    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n"
                + "}).addTo(map);" + "\n";

        for (int i = 1; i <= ge.num_vertices(); i++) {
            SubEstacion ec = ge.getLabelE(i);
            maps += "L.marker([" + ec.getCordenada().getLatitud() + ", " + ec.getCordenada().getLogitud() + "]).addTo(map)" + "\n";
            maps += ".bindPopup('" + ec.toString() + "')" + "\n";
            maps += ".openPopup();" + "\n;";
        }

        FileWriter file = new FileWriter("mapas/mapa.js");
        file.write(maps);
        file.close();

    }

    public static void cargarComboSubEstacion(JComboBox cbx) throws Exception {
        cbx.removeAllItems();
        DynamicList<SubEstacion> list = new SubEstacionDao().getLista();
        for (int i = 0; i < list.getLenght(); i++) {
            cbx.addItem(list.getInfo(i));
        }
    }

    public static Double calcularDistanciaSubEstacion(SubEstacion o, SubEstacion d) {
        Double dist = Utiles.coordGpsToKm(o.getCordenada().getLatitud(), o.getCordenada().getLogitud(),
                d.getCordenada().getLatitud(), d.getCordenada().getLogitud());
        return redondear(dist);
    }

    public static Double redondear(Double x) {
        Double d = Math.round(x * 100.0) / 100.0;
        return d;
    }
     public static SubEstacion obtenerCombo(DynamicList<SubEstacion> lista, JComboBox cbx) 
            throws EmptyException, Exception {
        return lista.getInfo(cbx.getSelectedIndex());
    }
     public static Double calcularDistanciaEscuelas(SubEstacion o, SubEstacion d) {
        Double dist = UtilesControlador.coordGpsToKm(
                o.getCordenada().getLatitud(),
                o.getCordenada().getLogitud(),
                d.getCordenada().getLatitud(), 
                d.getCordenada().getLogitud());
        return redondear(dist);
    }

}
