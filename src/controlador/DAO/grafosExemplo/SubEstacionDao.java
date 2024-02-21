/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO.grafosExemplo;

import controlador.DAO.DaoImplement;
import controlador.TDA.grafos.Adyacencia;
import controlador.TDA.grafos.Grafo;
import controlador.TDA.grafos.GrafoEtiquetadosNoDirigido;
import controlador.TDA.listas.DynamicList;
import grafo.modelo.SubEstacion;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author FA506ICB-HN114W
 */
public class SubEstacionDao extends DaoImplement<SubEstacion> {

    private DynamicList<SubEstacion> lista = new DynamicList<>();
    private SubEstacion estacion;
    private GrafoEtiquetadosNoDirigido<SubEstacion> grafo;

    public SubEstacionDao() {
        super(SubEstacion.class);
    }
    

    public GrafoEtiquetadosNoDirigido<SubEstacion> getGrafo() throws Exception {
        if (grafo == null) {
            DynamicList<SubEstacion> list = getLista();
            if (!list.isEmpty()) {
                grafo = new GrafoEtiquetadosNoDirigido(list.getLenght(), SubEstacion.class);
                for (int i = 0; i < list.getLenght(); i++) {
                    grafo.labelVertice((i + 1), list.getInfo(i));
                }
            }
        }
        return grafo;
    }

    public void setGrafo(GrafoEtiquetadosNoDirigido<SubEstacion> grafo) {
        this.grafo = grafo;
    }

    public DynamicList<SubEstacion> getLista() {
        if (lista.isEmpty()) {
            lista = all();
        }
        return lista;
    }

    public void setLista(DynamicList<SubEstacion> lista) {
        this.lista = lista;
    }

    public SubEstacion getSubEstacion() {
        if (estacion == null) {
            estacion = new SubEstacion();
        }
        return estacion;
    }

    public void cargarGrafo() throws FileNotFoundException, Exception {
        grafo = (GrafoEtiquetadosNoDirigido<SubEstacion>) getConection().fromXML(new FileReader("files/grafo.json"));
        setGrafo(grafo);  // Agrega esta línea para asignar el grafo cargado a la instancia
        lista.reset();
        for (int i = 1; i <= grafo.num_vertices(); i++) {
            lista.add(grafo.getLabelE(i));
        }
    }

    public void setSubEstacion(SubEstacion estacion) {
        this.estacion = estacion;
    }

    public Boolean persist() {
        estacion.setId(all().getLenght() + 1);
        estacion.getCordenada().setId(all().getLenght() + 1);
        return persist(estacion);
    }

    public void guardarGrafo() throws Exception {
        getConection().toXML(grafo, new FileWriter("files/grafo.json"));
    }

    public String getNombreNodo(int id) throws Exception {
        DynamicList<SubEstacion> lista = getLista();
        for (int i = 0; i < lista.getLenght(); i++) {
            if (lista.getInfo(i).getId().equals(id)) {
                return lista.getInfo(i).getNombre();
            }
        }
        return "Node" + id;
    }
    
    public void loadGrapg() throws Exception{
        
        grafo = (GrafoEtiquetadosNoDirigido<SubEstacion>)getConection().fromXML(new FileReader("files/grafo.json"));
        lista.Limpiar();
        for(int i = 1; i <= grafo.num_vertices(); i++){
            lista.add(grafo.getLabelE(i));
        }
    }
    
       public static String floydWarshall(Grafo grafo) throws Exception {
        int numVertices = grafo.num_vertices();
        double[][] distancias = new double[numVertices + 1][numVertices + 1];
        int[][] predecesores = new int[numVertices + 1][numVertices + 1];

        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else if (grafo.existe_arista(i, j)) {
                    distancias[i][j] = grafo.peso_arista(i, j);
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
                predecesores[i][j] = -1;
            }
        }

        for (int k = 1; k <= numVertices; k++) {
            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    if (distancias[i][k] != Double.POSITIVE_INFINITY && distancias[k][j] != Double.POSITIVE_INFINITY
                            && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = k;
                    }
                }
            }
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Distancias mínimas entre todos los pares de vértices:\n");
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                resultado.append(distancias[i][j] == Double.POSITIVE_INFINITY ? "INF\t" : distancias[i][j] + "\t");
            }
            resultado.append("\n");
        }
        return resultado.toString();
    }

    public static String bellmanFord(Grafo grafo, int inicio) throws Exception {
        int numVertices = grafo.num_vertices();
        double[] distancias = new double[numVertices + 1];
        int[] predecesores = new int[numVertices + 1];

        for (int i = 1; i <= numVertices; i++) {
            distancias[i] = Double.POSITIVE_INFINITY;
            predecesores[i] = -1;
        }
        distancias[inicio] = 0;

        for (int i = 1; i <= numVertices - 1; i++) {
            for (int j = 1; j <= numVertices; j++) {
                DynamicList<Adyacencia> listaA = grafo.adyacentes(j);
                if (listaA != null) {
                    for (int k = 0; k < listaA.getLenght(); k++) {
                        Adyacencia a = listaA.getInfo(k);
                        if (distancias[j] != Double.POSITIVE_INFINITY && distancias[j] + a.getPeso() < distancias[a.getDestino()]) {
                            distancias[a.getDestino()] = distancias[j] + a.getPeso();
                            predecesores[a.getDestino()] = j;
                        }
                    }
                }
            }
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Distancias mínimas desde el vértice ").append(inicio).append(":\n");
        for (int i = 1; i <= numVertices; i++) {
            resultado.append("Vértice ").append(i).append(": ").append(distancias[i]).append("\n");
        }
        return resultado.toString();
    }

}
