package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @Column(name = "ID_CLIENTE")
    private Long idCliente;

    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Column(name = "APELLIDO", length = 100)
    private String apellido;

    @Column(name = "CEDULA", length = 20, unique = true)
    private String cedula;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
    }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
