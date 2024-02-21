package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeExepction;
import controlador.TDA.listas.DynamicList;

/**
 *
 * @author FA506ICB-HN114W
 */
public class GrafoEtiquetadosNoDirigido<E> extends GrafosEtiquetadosDirigidos<E> {

    public GrafoEtiquetadosNoDirigido(Integer num_vertices, Class clazz) {
        super(num_vertices, clazz);
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        if (v1.intValue() <= num_vertices() && v2.intValue() <= num_vertices()) {
            if (!existe_arista(v1, v2)) {
                setNum_aristas(num_aristas() + 1);
                getListaAdyacencias()[v1].add(new Adyacencia(v2, peso));
                getListaAdyacencias()[v2].add(new Adyacencia(v1, peso));

            }
        } else {
            throw new VerticeExepction();
        }
    }

    public Integer getVerticeNum(E etiqueta) throws Exception {
        for (int i = 1; i <= num_vertices(); i++) {
            if (getLabelE(i).equals(etiqueta)) {
                return i;
            }
        }
        
        return null; // Si no se encuentra, devolvemos null
    }


    public void limpiarGrafo() {
        for (int i = 1; i <= num_vertices(); i++) {
            getListaAdyacencias()[i] = null;
        }
        setNum_aristas(0);
    }



    
    
}
