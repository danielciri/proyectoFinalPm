package com.danielcirilo.frasescelebres.Models;


import java.io.Serializable;

public class Categoria  implements Serializable {
    private int id;
    private String nombre;

    public Categoria() {
    }

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Categoria( String nombre) {

        this.nombre = nombre;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Categoria categoria = (Categoria) o;

        return id == categoria.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

