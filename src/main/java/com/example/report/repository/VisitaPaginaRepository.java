package com.example.report.repository;

import com.example.report.model.VisitaPagina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitaPaginaRepository extends JpaRepository<VisitaPagina, Long> {

    @Modifying
    @Query("UPDATE VisitaPagina v SET v.totalVisitas = v.totalVisitas + 1 WHERE v.idContador = :id")
    int incrementarVisita(@Param("id") Long id);
}
