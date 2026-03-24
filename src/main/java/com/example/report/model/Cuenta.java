package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CUENTAS")
public class Cuenta {

    @Id
    @Column(name = "ID_CUENTA")
    private Long idCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", foreignKey = @ForeignKey(name = "FK_CUENTA_CLIENTE"))
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", foreignKey = @ForeignKey(name = "FK_CUENTA_SUCURSAL"))
    private Sucursal sucursal;

    @Column(name = "NUMERO_CUENTA", length = 20, unique = true)
    private String numeroCuenta;

    /** AHORROS / CORRIENTE */
    @Column(name = "TIPO_CUENTA", length = 20)
    private String tipoCuenta;

    @Column(name = "SALDO", precision = 15, scale = 2)
    private BigDecimal saldo;

    /** ACTIVA / INACTIVA */
    @Column(name = "ESTADO", length = 20)
    private String estado;

    @Column(name = "FECHA_APERTURA")
    private LocalDate fechaApertura;

    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDate fechaApertura) { this.fechaApertura = fechaApertura; }
}
