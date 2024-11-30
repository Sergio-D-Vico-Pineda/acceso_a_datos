package com.example;

public class Pokemon {

    private String nombre;
    private String tipo_principal;
    private String tipo_secundaria;
    private int nivel;

    public Pokemon(String nombre, String tipo_principal, String tipo_secundaria, int nivel) {
        this.nombre = nombre;
        this.tipo_principal = tipo_principal;
        this.tipo_secundaria = tipo_secundaria;
        this.nivel = nivel;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTipoPrincipal() {
        return this.tipo_principal;
    }

    public String getTipoSecundaria() {
        return this.tipo_secundaria;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoPrincipal(String tipo_principal) {
        this.tipo_principal = tipo_principal;
    }

    public void setTipoSecundaria(String tipo_secundaria) {
        this.tipo_secundaria = tipo_secundaria;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return this.nombre + "," + this.tipo_principal + "," + this.tipo_secundaria + "," + this.nivel;
    }
}
