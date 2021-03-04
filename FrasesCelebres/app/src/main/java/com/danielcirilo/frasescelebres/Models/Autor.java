package com.danielcirilo.frasescelebres.Models;

import java.io.Serializable;

public class Autor implements Serializable {
    private int id;
    private String nombre;
    /** Año de nacimiento del autor. Números negativos representan AC (Antes de Cristo) */
    private String nacimiento;
    /** Año de muerte del autor. Números negativos representan AC (Antes de Cristo) */
    private String muerte;
    private String profesion;

    public Autor() {
    }

    public Autor(int id,String nacimiento, String muerte, String nombre,  String profesion) {
        this.id = id;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
        this.profesion = profesion;
    }

    public Autor( String nacimiento, String muerte, String nombre, String profesion) {

        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
        this.profesion = profesion;
    }

    public int getId() {
        return id;
    }

    /** No permitimos modificar el id desde fuera ya que es de tipo autoincrement */
    private void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getMuerte() {
        return muerte;
    }

    public void setMuerte(String muerte) {
        if(muerte != null && muerte.length() == 0) {
            this.muerte = null;
        } else {
            this.muerte = muerte;
        }
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Autor autor = (Autor) o;
        return id == autor.id;
    }

    @Override
    public int hashCode() {
        //return Objects.hash(id);
        return id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
