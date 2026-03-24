package com.example.report.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransaccionReportService {

    @Autowired
    private DataSource dataSource;

    public byte[] generarReportePDF(String numeroCuenta, Date fechaInicio, Date fechaFin, String tipo)
            throws Exception {
        JasperPrint jasperPrint = llenarReporte(numeroCuenta, fechaInicio, fechaFin, tipo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        return baos.toByteArray();
    }

    public byte[] generarReporteExcel(String numeroCuenta, Date fechaInicio, Date fechaFin, String tipo)
            throws Exception {
        JasperPrint jasperPrint = llenarReporte(numeroCuenta, fechaInicio, fechaFin, tipo);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setOnePagePerSheet(false);
        config.setDetectCellType(true);
        config.setWhitePageBackground(false);
        exporter.setConfiguration(config);
        exporter.exportReport();

        return baos.toByteArray();
    }

    private JasperPrint llenarReporte(String numeroCuenta, Date fechaInicio, Date fechaFin, String tipo)
            throws Exception {
        InputStream jrxmlStream = getClass()
                .getResourceAsStream("/reports/transacciones_por_cuenta.jrxml");

        if (jrxmlStream == null) {
            throw new IllegalStateException(
                    "No se encontró el archivo JRXML en /reports/transacciones_por_cuenta.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // Centinelas: string vacío = sin filtrar, fecha mín/máx = sin rango
        boolean filtrarFechaInicio = fechaInicio != null;
        boolean filtrarFechaFin    = fechaFin != null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Map<String, Object> params = new HashMap<>();
        params.put("numeroCuenta",    numeroCuenta != null && !numeroCuenta.trim().isEmpty() ? numeroCuenta : "");
        params.put("tipo",            tipo != null && !tipo.trim().isEmpty() ? tipo.toUpperCase() : "");
        params.put("fechaInicio",     filtrarFechaInicio
                ? new java.sql.Date(fechaInicio.getTime())
                : java.sql.Date.valueOf("1900-01-01"));
        params.put("fechaFin",        filtrarFechaFin
                ? new java.sql.Date(fechaFin.getTime())
                : java.sql.Date.valueOf("2099-12-31"));
        params.put("fechaInicioLabel", filtrarFechaInicio ? sdf.format(fechaInicio) : "Sin límite");
        params.put("fechaFinLabel",    filtrarFechaFin    ? sdf.format(fechaFin)    : "Sin límite");

        try (Connection connection = dataSource.getConnection()) {
            return JasperFillManager.fillReport(jasperReport, params, connection);
        }
    }
}

