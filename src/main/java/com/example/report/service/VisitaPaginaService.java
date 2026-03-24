package com.example.report.service;

import com.example.report.model.VisitaPagina;
import com.example.report.repository.VisitaPaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitaPaginaService {

    private static final Long COUNTER_ID = 1L;

    @Autowired
    private VisitaPaginaRepository visitaPaginaRepository;

    @Transactional
    public long incrementarYObtener() {
        VisitaPagina contador = visitaPaginaRepository.findById(COUNTER_ID)
                .orElseGet(this::crearContadorInicial);

        visitaPaginaRepository.incrementarVisita(COUNTER_ID);

        return visitaPaginaRepository.findById(COUNTER_ID)
                .map(VisitaPagina::getTotalVisitas)
                .orElse(contador.getTotalVisitas());
    }

    private VisitaPagina crearContadorInicial() {
        VisitaPagina nuevo = new VisitaPagina();
        nuevo.setIdContador(COUNTER_ID);
        nuevo.setTotalVisitas(0L);
        return visitaPaginaRepository.save(nuevo);
    }
}
