package com.gestion.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tipos_Vehiculo")
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTipo;
    private String tipo;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

    // Constructor
    public TipoVehiculo(int idTipo, String tipo) {
        this.idTipo = idTipo;
        this.tipo = tipo;
    }

    public TipoVehiculo(String tipo) {
        this.tipo = tipo;
    }

    public TipoVehiculo() {
    }

    // Getters and Setters
    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public String toString() {
        return "TipoVehiculo{" +
                "idTipo=" + idTipo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}