/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;


/**
 *
 * @author FA506ICB-HN114W
 */
public class Adyacencia {
    private Integer destino;
    private Double peso;

    public Adyacencia() {
    }

    public Adyacencia(Integer destino, Double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Integer getDestino() {
        return destino;
    }

    public void setDestino(Integer destino) {
        this.destino = destino;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    @Override
    public String toString(){
        return "  Destino :  " + destino + "  Peso :  " + peso;
    }
    
}
