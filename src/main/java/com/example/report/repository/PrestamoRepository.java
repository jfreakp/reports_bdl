package com.example.report.repository;

import com.example.report.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByClienteIdCliente(Long idCliente);
    List<Prestamo> findByEstado(String estado);
}
