/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Persona {
    private Integer id;
    private String apellidos;
    private String nombres;
    private String DNI;
    private String direccion;
    private String telefono;
    private Integer id_rol;

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
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * @param DNI the DNI to set
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the id_rol
     */
    public Integer getId_rol() {
        return id_rol;
    }

    /**
     * @param id_rol the id_rol to set
     */
    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return getApellidos() + " " + getNombres();
    }
    
    public Boolean compare(Persona p, String field, Integer type){
        // 0 menor 1 mayor
        switch (type) {
            case 0:
                if (field.equalsIgnoreCase("apellidos")) {
                    return apellidos.compareTo(p.getApellidos()) < 0;
                } else if (field.equalsIgnoreCase("nombres")) {
                    return nombres.compareTo(p.getNombres()) < 0;
                } else if (field.equalsIgnoreCase("DNI")) {
                    return DNI.compareTo(p.getDNI()) < 0;
                } else if (field.equalsIgnoreCase("direccion")) {
                    return direccion.compareTo(p.getDireccion()) < 0;
                } else if (field.equalsIgnoreCase("id")) {
                    return id.intValue() < p.getId().intValue();
                }
                //break;
            case 1:
                if (field.equalsIgnoreCase("apellidos")) {
                    return apellidos.compareTo(p.getApellidos()) > 0;
                } else if (field.equalsIgnoreCase("nombres")) {
                    return nombres.compareTo(p.getNombres()) > 0;
                } else if (field.equalsIgnoreCase("DNI")) {
                    return DNI.compareTo(p.getDNI()) > 0;
                } else if (field.equalsIgnoreCase("direccion")) {
                    return direccion.compareTo(p.getDireccion()) > 0;
                } else if (field.equalsIgnoreCase("id")) {
                    return id.intValue() > p.getId().intValue();
                }
                //break;

            default:
                return null;
              
        }
    }
}
