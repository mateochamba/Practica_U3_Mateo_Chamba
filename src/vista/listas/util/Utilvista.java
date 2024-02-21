/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.listas.util;

import controlador.RolControl;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Rol;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Utilvista {

    public static void cargarcomboRolesL(JComboBox cbx) throws EmptyException {
        controlador.Persona.RolControl rc = new controlador.Persona.RolControl();
        cbx.removeAllItems();
        if (rc.getRoles().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista vacia");
        } else {
            for (int i = 0; i < rc.getRoles().getLenght(); i++) {
                cbx.addItem(rc.getRoles().getInfo(i));
            }
        }
    }

    public static void cargarcomboRoles(JComboBox cbx) throws EmptyException {
        RolControl rc = new RolControl();
        cbx.removeAllItems();
        for (Integer i = 0; i < rc.getRoles().getLenght(); i++) {
            System.out.println(rc.getRoles().getLenght());
            System.out.println(rc.getRoles().getInfo(i).getNombre());
            cbx.addItem(rc.getRoles().getInfo(i));
        }
    }

    public static Rol obtenerRolControl(JComboBox cbx) {
        return (Rol) cbx.getSelectedItem();
    }
}
