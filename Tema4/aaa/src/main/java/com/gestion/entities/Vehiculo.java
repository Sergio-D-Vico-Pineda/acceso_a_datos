package com.gestion.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVehiculo;
    
    @Column(unique = true)
    private String matricula;
    private String marca;
    private String modelo;
    private int añoFabricacion;
    private BigDecimal precio;
    
    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Propietario propietario;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoVehiculo tipo;
    
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<HistorialMantenimiento> historialesMantenimiento;
    
    // Getters and Setters
    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAñoFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public List<HistorialMantenimiento> getHistorialesMantenimiento() {
        return historialesMantenimiento;
    }

    public void setHistorialesMantenimiento(List<HistorialMantenimiento> historialesMantenimiento) {
        this.historialesMantenimiento = historialesMantenimiento;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", añoFabricacion=" + añoFabricacion +
                ", precio=" + precio +
                ", propietario=" + propietario.getNombre() + " " + propietario.getApellido() +
                ", tipo=" + tipo.getTipo() +
                '}';
    }
}