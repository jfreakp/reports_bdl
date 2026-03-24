package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TRANSACCIONES")
public class Transaccion {

    @Id
    @Column(name = "ID_TRANSACCION")
    private Long idTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CUENTA", foreignKey = @ForeignKey(name = "FK_TRANSACCION_CUENTA"))
    private Cuenta cuenta;

    /** DEPOSITO / RETIRO / TRANSFERENCIA */
    @Column(name = "TIPO", length = 20)
    private String tipo;

    @Column(name = "MONTO", precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    public Long getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
