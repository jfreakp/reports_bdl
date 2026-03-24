package com.example.report.repository;

import com.example.report.model.PagoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoPrestamoRepository extends JpaRepository<PagoPrestamo, Long> {
    List<PagoPrestamo> findByPrestamoIdPrestamo(Long idPrestamo);
}
