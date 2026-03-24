package com.example.report.controller;

import com.example.report.service.TransaccionReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private TransaccionReportService transaccionReportService;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Hola desde Spring Boot con Java 8!");
        response.put("estado", "OK");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        Map<String, String> response = new HashMap<>();
        response.put("aplicacion", "report");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("estado", "ACTIVO");
        return ResponseEntity.ok(response);
    }

    /**
     * Genera el reporte de transacciones en PDF.
     *
     * Filtros opcionales:
     *   numeroCuenta  - número de cuenta exacto
     *   fechaInicio   - fecha desde (yyyy-MM-dd)
     *   fechaFin      - fecha hasta (yyyy-MM-dd)
     *   tipo          - DEPOSITO | RETIRO | TRANSFERENCIA
     *
     * Ejemplos:
     *   GET /api/reportes/transacciones/pdf
     *   GET /api/reportes/transacciones/pdf?numeroCuenta=0000000001
     *   GET /api/reportes/transacciones/pdf?tipo=DEPOSITO&fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/reportes/transacciones/pdf")
    public ResponseEntity<byte[]> reporteTransaccionesPDF(
            @RequestParam(required = false) String numeroCuenta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(required = false) String tipo) {
        try {
            byte[] pdfBytes = transaccionReportService
                    .generarReportePDF(numeroCuenta, fechaInicio, fechaFin, tipo);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "transacciones.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generando PDF: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Genera el reporte de transacciones en Excel.
     * Acepta los mismos filtros opcionales que el endpoint PDF.
     */
    @GetMapping("/reportes/transacciones/excel")
    public ResponseEntity<byte[]> reporteTransaccionesExcel(
            @RequestParam(required = false) String numeroCuenta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(required = false) String tipo) {
        try {
            byte[] xlsxBytes = transaccionReportService
                    .generarReporteExcel(numeroCuenta, fechaInicio, fechaFin, tipo);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "transacciones.xlsx");
            headers.setContentLength(xlsxBytes.length);

            return new ResponseEntity<>(xlsxBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generando Excel: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
