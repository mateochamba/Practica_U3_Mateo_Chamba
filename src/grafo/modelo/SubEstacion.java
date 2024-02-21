/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafo.modelo;

/**
 *
 * @author FA506ICB-HN114W
 */
public class SubEstacion {

    private Integer id;
    private String nombre;
    private String portada;
    private String escudo;
    private Cordenada cordenada;

    public SubEstacion() {
    }
    public SubEstacion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public Cordenada getCordenada() {
        if (cordenada == null) {
            cordenada = new Cordenada();
        }
        return cordenada;
    }

    public void setCordenada(Cordenada cordenada) {
        this.cordenada = cordenada;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
