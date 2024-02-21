/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Llanta {
    private Integer id;
    private Integer id_marca;
    private Double precio;
    private Integer stock;
    private Double presion;
    private Integer aro;
    private String descripcion;
    private boolean estado;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the id_marca
     */
    public Integer getId_marca() {
        return id_marca;
    }

    /**
     * @param id_marca the id_marca to set
     */
    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return the presion
     */
    public Double getPresion() {
        return presion;
    }

    /**
     * @param presion the presion to set
     */
    public void setPresion(Double presion) {
        this.presion = presion;
    }

    /**
     * @return the aro
     */
    public Integer getAro() {
        return aro;
    }

    /**
     * @param aro the aro to set
     */
    public void setAro(Integer aro) {
        this.aro = aro;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
