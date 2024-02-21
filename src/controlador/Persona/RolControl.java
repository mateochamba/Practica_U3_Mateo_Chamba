/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Persona;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import modelo.Rol;


/**
 *
 * @author FA506ICB-HN114W
 */

public class RolControl extends DaoImplement<Rol>{
    private DynamicList<Rol> roles;
    private Rol rol;

    public RolControl() {
        super(Rol.class);
    }

    public DynamicList<Rol> getRoles() {
        roles = all();
        return roles;
    }

    public void setRoles(DynamicList<Rol> roles) {
        this.roles = roles;
    }

    public Rol getRol() {
        if (rol == null) {
            rol = new Rol();
        }
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public Boolean persist(){
        rol.setId(all().getLenght() + 1);
        return persist(rol);
    }
    
    public static void main(String[] args) {
        RolControl rc = new RolControl();
        System.out.println(rc.all().toString());
//        rc.getRol().setDescripcion("Administrador");
//        rc.getRol().setNombre("admin");
//        rc.persist();
//        rc.setRol(null);
//        rc.getRol().setDescripcion("Cajero");
//        rc.getRol().setNombre("cash");
//        rc.persist();
//        rc.setRol(null);
//        rc.getRol().setDescripcion("Cliente");
//        rc.getRol().setNombre("client");
//        rc.persist();
//        rc.setRol(null);
    }
}
