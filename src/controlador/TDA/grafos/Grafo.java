/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;

/**
 *
 * @author FA506ICB-HN114W
 */
public abstract class Grafo {

    // ---- G = {v, e}
    public abstract Integer num_vertices();

    public abstract Integer num_aristas();

    //v1 ------ v2
    public abstract Boolean existe_arista(Integer v1, Integer v2) throws Exception;

    public abstract Double peso_arista(Integer v1, Integer v2) throws Exception;

    public abstract void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception;

    public abstract void insertar_arista(Integer v1, Integer v2) throws Exception;

    public abstract DynamicList<Adyacencia> adyacentes(Integer v1) throws Exception;

    @Override
    public String toString() {
        StringBuffer grafo = new StringBuffer("Grafo").append("\n");
        try {
            for (int i = 1; i <= num_vertices(); i++) {
                grafo.append("V").append(i).append("\n");
                DynamicList<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLenght(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("ady ").append(a.getDestino()).append(" peso ").append(a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString();
    }

//   public DynamicList<Integer> caminoMasCorto(Integer origen, Integer destino) throws EmptyException, Exception {
//    DynamicList<Integer> camino = new DynamicList<>();
//
//    if (estaConectado()) {
//        DynamicList<Double> pesos = new DynamicList<>();
//        Boolean finalizar = false;
//        Integer inicial = origen;
//        camino.add(origen);
//
//        while (!finalizar) {
//            DynamicList<Adyacencia> adyacencias = adyacentes(inicial);
//            Double peso = Double.MAX_VALUE;
//            Integer siguiente = -1;  // Vertice destino
//
//            for (int j = 0; j < adyacencias.getLenght(); j++) {
//                Adyacencia ad = adyacencias.getInfo(j);
//
//                if (!estaCamino(camino, ad.getDestino())) {
//                    Double pesoArista = ad.getPeso();
//                    if (destino.equals(ad.getDestino())) {
//                        siguiente = ad.getDestino();
//                        peso = pesoArista;
//                        break;
//                    } else if (pesoArista < peso) {
//                        siguiente = ad.getDestino();
//                        peso = pesoArista;
//                    }
//                }
//            }
//
//            if (siguiente == -1) {
//                camino.deleteAll();
//                break;
//            }
//
//            pesos.add(peso);
//            camino.add(siguiente);
//            inicial = siguiente;
//
//            if (destino.equals(inicial)) {
//                finalizar = true;
//            }
//        }
//    }
//
//    return camino;
//}
//
//
//
//    private boolean estaCamino(DynamicList<Integer> lista, Integer vertice) {
//        return lista.contains(vertice);
//    }
    private boolean estaConectado() {
        for (int i = 1; i <= num_vertices(); i++) {
            try {
                DynamicList<Adyacencia> lista = adyacentes(i);
                if (lista.isEmpty()) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public DynamicList<Adyacencia> bellmanFord(Integer origen, Integer destino) throws EmptyException, Exception {
        DynamicList<Adyacencia> camino = new DynamicList<>();

        if (estaConectado()) {
            // Inicializar distancias y padres
            Double[] distancias = new Double[num_vertices() + 1];
            Integer[] padres = new Integer[num_vertices() + 1];

            for (int i = 1; i <= num_vertices(); i++) {
                distancias[i] = Double.MAX_VALUE;
                padres[i] = null;
            }

            distancias[origen] = 0.0;

            // Relajación de aristas
            for (int i = 1; i < num_vertices(); i++) {
                for (int v = 1; v <= num_vertices(); v++) {
                    DynamicList<Adyacencia> adyacencias = adyacentes(v);

                    for (int j = 0; j < adyacencias.getLenght(); j++) {
                        Adyacencia ady = adyacencias.getInfo(j);
                        Double pesoArista = ady.getPeso();

                        if (distancias[v] != Double.MAX_VALUE && distancias[v] + pesoArista < distancias[ady.getDestino()]) {
                            distancias[ady.getDestino()] = distancias[v] + pesoArista;
                            padres[ady.getDestino()] = v;
                        }
                    }
                }
            }

            // Verificar ciclos de peso negativo
            for (int v = 1; v <= num_vertices(); v++) {
                DynamicList<Adyacencia> adyacencias = adyacentes(v);

                for (int j = 0; j < adyacencias.getLenght(); j++) {
                    Adyacencia ady = adyacencias.getInfo(j);
                    Double pesoArista = ady.getPeso();

                    if (distancias[v] != Double.MAX_VALUE && distancias[v] + pesoArista < distancias[ady.getDestino()]) {
                        // Ciclo de peso negativo detectado, puedes manejarlo según tus necesidades
                        throw new Exception("Ciclo de peso negativo detectado");
                    }
                }
            }

            // Construir el camino desde origen hasta destino
            Integer actual = destino;

            while (actual != null) {
                Adyacencia ady = new Adyacencia(actual, distancias[actual]);
                camino.insertarPrimero(ady);
                actual = padres[actual];
            }
        }

        return camino;
    }

public DynamicList<Adyacencia> floydWarshall(Integer origen, Integer destino) throws EmptyException, Exception {
    DynamicList<Adyacencia> camino = new DynamicList<>();

    if (estaConectado()) {
        try {
            // Matriz de distancias mínimas entre todos los pares de vértices
            Double[][] distancias = new Double[num_vertices() + 1][num_vertices() + 1];

            // Inicializar la matriz de distancias
            for (int i = 1; i <= num_vertices(); i++) {
                for (int j = 1; j <= num_vertices(); j++) {
                    if (i == j) {
                        distancias[i][j] = 0.0; // Distancia de un vértice a sí mismo es 0
                    } else if (existe_arista(i, j)) {
                        distancias[i][j] = peso_arista(i, j); // Peso de la arista directa
                    } else {
                        distancias[i][j] = Double.MAX_VALUE; // Distancia infinita si no hay arista directa
                    }
                }
            }

            // Calcular distancias mínimas usando el algoritmo de Floyd-Warshall
            for (int k = 1; k <= num_vertices(); k++) {
                for (int i = 1; i <= num_vertices(); i++) {
                    for (int j = 1; j <= num_vertices(); j++) {
                        if (distancias[i][k] != null && distancias[k][j] != null
                                && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                            distancias[i][j] = distancias[i][k] + distancias[k][j];
                        }
                    }
                }
            }

            // Construir el camino desde origen hasta destino utilizando la información de distancias
            Integer actual = origen;

            while (!actual.equals(destino)) {
                Double distancia = distancias[actual][destino];

                if (distancia == null || distancia == Double.MAX_VALUE) {
                    // No hay conexión directa o la distancia es infinita, se interrumpe
                    System.out.println("No hay conexión directa desde " + actual + " a " + destino);
                    camino.deleteAll();
                    break;
                }

                Adyacencia ady = new Adyacencia(actual, distancia);
                camino.insertarPrimero(ady);

                Integer siguiente = null;
                Double distanciaMinima = Double.MAX_VALUE;

                for (int vecino = 1; vecino <= num_vertices(); vecino++) {
                    if (distancias[actual][vecino] != null && distancias[vecino][destino] != null
                            && distancias[actual][vecino] + distancias[vecino][destino] == distancias[actual][destino]) {
                        siguiente = vecino;
                        distanciaMinima = distancias[actual][vecino];
                    }
                }

                if (siguiente == null) {
                    // No hay conexión al destino, se interrumpe
                    camino.deleteAll();
                    break;
                }

                actual = siguiente;
            }

            // Agregar el destino al final del camino
            Adyacencia adyDestino = new Adyacencia(destino, distancias[origen][destino]);
            camino.insertarPrimero(adyDestino);

            // Mostrar el camino en la interfaz gráfica
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error en el método floydWarshall: " + e.getMessage());
        }
    }

    return camino;
}

}
