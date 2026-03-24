package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SUCURSALES")
public class Sucursal {

    @Id
    @Column(name = "ID_SUCURSAL")
    private Long idSucursal;

    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "CIUDAD", length = 100)
    private String ciudad;

    public Long getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Long idSucursal) { this.idSucursal = idSucursal; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}
