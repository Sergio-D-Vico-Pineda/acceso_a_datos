package com.gestion.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Historial_Mantenimiento")
public class HistorialMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMantenimiento;
    
    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;
    private Date fecha;
    private String descripcion;
    private BigDecimal coste;
    
    // Getters and Setters
    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCoste() {
        return coste;
    }

    public void setCoste(BigDecimal coste) {
        this.coste = coste;
    }

    @Override
    public String toString() {
        return "HistorialMantenimiento{" +
                "idMantenimiento=" + idMantenimiento +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", coste=" + coste +
                '}';
    }
}