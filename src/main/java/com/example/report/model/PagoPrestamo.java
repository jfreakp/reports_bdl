package com.example.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PAGOS_PRESTAMOS")
public class PagoPrestamo {

    @Id
    @Column(name = "ID_PAGO")
    private Long idPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRESTAMO", foreignKey = @ForeignKey(name = "FK_PAGO_PRESTAMO"))
    private Prestamo prestamo;

    @Column(name = "MONTO", precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "FECHA_PAGO")
    private LocalDate fechaPago;

    public Long getIdPago() { return idPago; }
    public void setIdPago(Long idPago) { this.idPago = idPago; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
}
