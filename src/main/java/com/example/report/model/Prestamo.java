package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PRESTAMOS")
public class Prestamo {

    @Id
    @Column(name = "ID_PRESTAMO")
    private Long idPrestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", foreignKey = @ForeignKey(name = "FK_PRESTAMO_CLIENTE"))
    private Cliente cliente;

    @Column(name = "MONTO", precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "TASA_INTERES", precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "PLAZO_MESES")
    private Integer plazoMeses;

    @Column(name = "FECHA_INICIO")
    private LocalDate fechaInicio;

    /** ACTIVO / PAGADO / MORA */
    @Column(name = "ESTADO", length = 20)
    private String estado;

    public Long getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Long idPrestamo) { this.idPrestamo = idPrestamo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public BigDecimal getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(BigDecimal tasaInteres) { this.tasaInteres = tasaInteres; }

    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
