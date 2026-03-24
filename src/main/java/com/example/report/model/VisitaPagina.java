package com.example.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VISITAS_PAGINA")
public class VisitaPagina {

    @Id
    @Column(name = "ID_CONTADOR")
    private Long idContador;

    @Column(name = "TOTAL_VISITAS", nullable = false)
    private Long totalVisitas;

    public Long getIdContador() {
        return idContador;
    }

    public void setIdContador(Long idContador) {
        this.idContador = idContador;
    }

    public Long getTotalVisitas() {
        return totalVisitas;
    }

    public void setTotalVisitas(Long totalVisitas) {
        this.totalVisitas = totalVisitas;
    }
}
