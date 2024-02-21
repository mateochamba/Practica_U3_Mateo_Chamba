/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Persona;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;
import java.lang.reflect.Field;
import modelo.Persona;

/**
 *
 * @author FA506ICB-HN114W
 */


public class PersonaControl extends DaoImplement<Persona>{
    private DynamicList<Persona> personas;
    private Persona persona;

    public PersonaControl() {
        super(Persona.class);
    }

    public DynamicList<Persona> getPersonas() {
        personas = all();
        return personas;
    }

    public void setPersonas(DynamicList<Persona> personas) {
        this.personas = personas;
    }

    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public Boolean persist(){
        persona.setId(all().getLenght() + 1);
        return persist(persona);
    }
    
    //metodo seleccion
    public DynamicList<Persona> ordenar(DynamicList<Persona> lista, Integer tipo, String field) throws EmptyException, Exception{
        Field attribute = Utiles.getField(Persona.class, field);
        Integer n = lista.getLenght();
        Persona[] personas = lista.toArray();
        
        if (attribute != null) {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                Persona t = personas[i];
                for (int j = i + 1; j < n; j++) {

                    //if (personas[j].getApellidos().compareTo(t.getApellidos()) < 0)
                    if (personas[j].compare(t, field, tipo)) {
                        t = personas[j];
                        k = j;
                    }

                }
                personas[k] = personas[i];
                personas[i] = t;
            }
        } else {
            throw new Exception("no existe el crieterio de busqueda");
        }
        return lista.toList(personas);
    }
    
    //metodo quicksort
    public DynamicList<Persona> quickSort(DynamicList<Persona> lista, int bajo, int alto, String field, Integer tipo) throws Exception {
        Field attribute = Utiles.getField(Persona.class, field);
        Integer n = lista.getLenght();
        Persona[] personas = lista.toArray();
        if (attribute != null) {
            quickSortRecursivo(personas, 0, n-1, field, tipo);
            
        } else {
            throw new Exception("no existe el crieterio de busqueda");
        }
        return lista.toList(personas);
    }
    
    private void quickSortRecursivo(Persona[] arr, int bajo, int alto, String field, Integer tipo){
        if (bajo < alto) {
            // Particionar el arreglo, arr[p] estará ahora en el lugar correcto
            int p = particion(arr, bajo, alto, field, tipo);

            // Ordenar recursivamente los elementos antes y después de la partición
            quickSortRecursivo(arr, bajo, p - 1, field, tipo);
            quickSortRecursivo(arr, p + 1, alto, field, tipo);
        }
    }
    
    private int particion(Persona[] arr, int bajo, int alto, String field, Integer tipo) {
        Persona pivote = arr[alto];
        int i = bajo - 1;

        for (int j = bajo; j < alto; j++) {
            if (arr[j].compare(pivote, field, tipo)) {
                i++;
                // Intercambiar arr[i] y arr[j]
                Persona temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Intercambiar arr[i+1] y arr[alto] (pivote)
        Persona temp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = temp;

        return i + 1;
    }
    
    //shell sort
    public DynamicList<Persona> shellSort(DynamicList<Persona> lista, String field, Integer type) throws EmptyException {
        if (lista.isEmpty()) {
            return lista;
        }

        int n = lista.getLenght();
        Persona[] personas = lista.toArray();

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Persona key = personas[i];
                int j = i;
                while (j >= gap && !personas[j - gap].compare(key, field, type)) {
                    personas[j] = personas[j - gap];
                    j -= gap;
                }
                personas[j] = key;
            }
        }

        return lista.toList(personas);
    }
    
    public DynamicList<Persona> buscar(String texto, DynamicList<Persona> personaList, String field){
        
        DynamicList<Persona> lista = new DynamicList<>();
        try {
            Persona[] aux = ordenar(personaList, 0, field).toArray();
            for(Persona p : aux){
                Field attribute = Utiles.getField(Persona.class, field);
                if (attribute != null) {
                    attribute.setAccessible(true);
                    Object fieldValue = attribute.get(p);
                    if (fieldValue != null && fieldValue.toString().toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(p);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error en buscar " + e.getMessage());
        }
        return lista;
    }
    
    //busqueda binaria
    public DynamicList<Persona> busquedaBinaria(String texto, DynamicList<Persona> personaList, String field) {
        DynamicList<Persona> lista = new DynamicList<>();
        try {
            DynamicList<Persona> listaOrdenada = ordenar(personaList, 0, field);
            Persona[] aux = listaOrdenada.toArray();

            int n = aux.length;
            int inf = 0, sup = n - 1;

            while (inf <= sup) {
                int centro = (sup + inf) / 2;
                String fieldValue = getFieldValue(aux[centro], field);

                if (fieldValue != null && fieldValue.toLowerCase().contains(texto.toLowerCase())) {
                    // Agregar el elemento actual
                    lista.add(aux[centro]);

                    // Buscar hacia la izquierda
                    int izquierda = centro - 1;
                    while (izquierda >= 0 && getFieldValue(aux[izquierda], field).toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(aux[izquierda]);
                        izquierda--;
                    }

                    // Buscar hacia la derecha
                    int derecha = centro + 1;
                    while (derecha < n && getFieldValue(aux[derecha], field).toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(aux[derecha]);
                        derecha++;
                    }

                    return lista;
                }

                if (fieldValue.toLowerCase().compareTo(texto.toLowerCase()) < 0) {
                    inf = centro - 1;
                } else {
                    sup = centro + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error en buscar " + e.getMessage());
        }
        return lista;
    }

    private String getFieldValue(Object object, String fieldName){
        try {
            Field field = Utiles.getField(object.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    if (fieldValue instanceof String) {
                        return (String) fieldValue;
                    } else {
                        return fieldValue.toString();
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error en getFieldValue " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        //buscar
        PersonaControl pc = new PersonaControl();
        DynamicList<Persona> lista = pc.getPersonas();
        System.out.println(pc.buscar("a", lista, "nombres"));
        System.out.println(pc.busquedaBinaria("a", lista, "nombres"));

    }
}
