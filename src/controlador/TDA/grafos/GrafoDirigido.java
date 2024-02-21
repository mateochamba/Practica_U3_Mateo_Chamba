/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeExepction;
import controlador.TDA.listas.DynamicList;



/**
 *
 * @author FA506ICB-HN114W
 */
public class GrafoDirigido extends Grafo{
    private Integer num_vertice;
    private Integer num_aristas;
    private DynamicList<Adyacencia> listaAdyacencias[];

    public GrafoDirigido(Integer num_vertices) {
        this.num_vertice = num_vertices;
        this.num_aristas = 0;
        listaAdyacencias = new DynamicList[num_vertices + 1];
        for (int i = 1; i <= this.num_vertice; i++) {
            listaAdyacencias[i] = new DynamicList<>();
        }
    }
    
    
    @Override
    public Integer num_vertices() {
        return num_vertice;
    }

    @Override
    public Integer num_aristas() {
        return num_aristas;
    }

    @Override
    public Boolean existe_arista(Integer v1, Integer v2) throws Exception{
        Boolean band = false;
        if (v1.intValue() <= num_vertice && v2.intValue() <= num_vertice) {
            DynamicList<Adyacencia> listaA = listaAdyacencias[v1];
            for (int i = 0; i < listaA.getLenght(); i++) {
                Adyacencia a = listaA.getInfo(i);
                if (a.getDestino().intValue() == v2.intValue()) {
                    band = true;
                    break;
                }
            }
        } else {
            throw new VerticeExepction();
        }
        return band;
    }

    @Override
    public Double peso_arista(Integer v1, Integer v2) throws Exception {
        Double peso = Double.NaN;
        if (existe_arista(v1, v2)) {
            DynamicList<Adyacencia> listaA = listaAdyacencias[v1];
            for (int i = 0; i < listaA.getLenght(); i++) {
                Adyacencia a = listaA.getInfo(i);
                if (a.getDestino().intValue() == v2.intValue()) {
                    peso = a.getPeso();
                    break;
                }
            }
        }
        return peso;
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        if (v1.intValue() <= num_vertice && v2.intValue() <= num_vertice) {
            if (!existe_arista(v1, v2)) {
                num_aristas++;
                listaAdyacencias[v1].add(new Adyacencia(v2, peso));
            }
        } else {
            throw new VerticeExepction();
        }
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2) throws Exception {
        insertar_arista(v1, v2, Double.NaN);
    }

    @Override
    public DynamicList<Adyacencia> adyacentes(Integer v1) {
        return listaAdyacencias[v1];
    }

    public Integer getNum_aristas() {
        return num_aristas;
    }

    public void setNum_aristas(Integer num_aristas) {
        this.num_aristas = num_aristas;
    }

    protected DynamicList<Adyacencia>[] getListaAdyacencias() {
        return listaAdyacencias;
    }

    public void setListaAdyacencias(DynamicList<Adyacencia>[] listaAdyacencias) {
        this.listaAdyacencias = listaAdyacencias;
    }
    
    public static void main(String[] args) {
        Grafo f = new GrafoDirigido(6);
        System.out.println(f);
        try {
            f.insertar_arista(1, 3, 50.0);
            f.insertar_arista(4, 5, 10.0);
            System.out.println(f);
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }
    
}
