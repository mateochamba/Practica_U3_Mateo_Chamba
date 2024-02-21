/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.DAO.grafosExemplo.SubEstacionDao;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;
import grafo.modelo.SubEstacion;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author FA506ICB-HN114W
 */
public class PaintGraph {

    String URL = "d3/grafo.js";
    private SubEstacionDao subEstacionDao;  // Asegúrate de tener una instancia de SubEstacionDao disponible

    public PaintGraph(SubEstacionDao subEstacionDao) {
        this.subEstacionDao = subEstacionDao;
    }

    public void updateFile(Grafo graph) throws IOException, Exception {
        String paint = "var nodes = new vis.DataSet([\n";
        for (int i = 1; i < graph.num_vertices() + 1; i++) {
            String nodeName = subEstacionDao.getNombreNodo(i); // Utiliza el método real para obtener el nombre del nodo
            paint += "{id: " + i + ", label: \"" + nodeName + "\"},\n";
        }
        paint += "]);\n";

        paint += "var edges = new vis.DataSet([\n";
        for (int i = 1; i <= graph.num_vertices(); i++) {
            try {
                DynamicList<Adyacencia> adyacencias = graph.adyacentes(i);
                for (int j = 0; j < adyacencias.getLenght(); j++) {
                    Adyacencia adyacencia = adyacencias.getInfo(j);
                    int destino = adyacencia.getDestino();
                    String destinoName = subEstacionDao.getNombreNodo(destino); // Utiliza el método real para obtener el nombre del nodo
                    paint += "{from: " + i + ", to: " + destino + ", label: \"" + adyacencia.getPeso().toString() + "\"" + "},\n";
                }
            } catch (Exception e) {
            }
        }
        paint += "]);\n";
        paint += "var container = document.getElementById(\"mynetwork\");\n"
                + "      var data = {\n"
                + "        nodes: nodes,\n"
                + "        edges: edges,\n"
                + "      };\n"
                + "      var options = {};\n"
                + "      var network = new vis.Network(container, data, options);";
        FileWriter load = new FileWriter(URL);
        load.write(paint);
        load.close();
    }

    public void highlightShortestPath(Grafo graph, DynamicList<Integer> path) {
    try {
        updateFile(graph);  // Asegúrate de mantener la representación visual actualizada

        // Genera un archivo HTML específico para el camino más corto y ábrelo en el navegador
        FileWriter load = new FileWriter("d3/shortest_path.html");
        load.write("<html>\n<head>\n<title>Shortest Path</title>\n");
        load.write("<script type=\"text/javascript\" src=\"your_graph_library.js\"></script>\n");
        load.write("</head>\n<body>\n");
        load.write("<h2>Shortest Path</h2>\n");

        // Lógica para resaltar el camino más corto en la representación visual del grafo
        for (int i = 0; i < path.getLenght()- 1; i++) {
            int from = path.getInfo(i);
            int to = path.getInfo(i + 1);
            load.write("<p>From " + from + " to " + to + "</p>\n");
            load.write("<script>\n");
            load.write("highlightNode(" + from + ");\n");
            load.write("highlightNode(" + to + ");\n");
            load.write("highlightEdge(" + from + ", " + to + ");\n");
            load.write("</script>\n");
        }

        load.write("</body>\n</html>");
        load.close();

        // Abre el nuevo archivo en el navegador
        Utiles.abrirArchivoHTML("d3/shortest_path.html");
    } catch (IOException e) {
        e.printStackTrace();  // Imprimir detalles del error en la consola
        JOptionPane.showMessageDialog(null, "Error al escribir el archivo HTML: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();  // Imprimir detalles del error en la consola
        JOptionPane.showMessageDialog(null, "Error al resaltar camino: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


}
