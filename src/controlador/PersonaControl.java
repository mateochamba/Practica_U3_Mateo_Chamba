/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import modelo.Persona;

/**
 *
 * @author FA506ICB-HN114W
 */
public class PersonaControl {

    private Persona persona = new Persona();
    private DynamicList<Persona> personas;

    public PersonaControl(Persona persona) {
        this.persona = persona;
    }

    public PersonaControl() {
        this.personas = new DynamicList<>();

    }

    public Boolean guardar() {

        try {
            getPersona().setId(getPersonas().getLenght());
            getPersonas().add(getPersona());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Integer posVerificar() throws EmptyException {

        Integer bandera = 0;

        for (Integer i = 0; i <= this.personas.getLenght(); i++) {

            if (this.getPersonas().getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    public void imprimir() throws EmptyException {
        for (int i = 0; i < this.getPersonas().getLenght(); i++) {
            System.out.println(getPersonas().getInfo(i));
        }
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public DynamicList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(DynamicList<Persona> personas) {
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "DNI: " + getPersona().getDNI() + " Apellidos: " + getPersona().getApellidos() + " Nombres: " + getPersona().getNombres();
    }
}
