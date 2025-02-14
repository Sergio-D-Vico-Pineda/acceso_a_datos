package com.gestion.entities;

import jakarta.persistence.*;
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

    public HistorialMantenimiento(int idMantenimiento, Vehiculo vehiculo, Date fecha, String descripcion,
            BigDecimal coste) {
        this.idMantenimiento = idMantenimiento;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
    }

    public HistorialMantenimiento() {
    }

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
        return String.format("[%d] - %tF descripción='%s', coste=%,.2f",
                idMantenimiento, fecha, descripcion, coste);
    }
}