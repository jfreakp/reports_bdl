package com.example.report.controller;

import com.example.report.repository.ClienteRepository;
import com.example.report.repository.CuentaRepository;
import com.example.report.repository.PrestamoRepository;
import com.example.report.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("totalCuentas", cuentaRepository.count());
        model.addAttribute("totalTransacciones", transaccionRepository.count());
        model.addAttribute("totalPrestamos", prestamoRepository.count());
        model.addAttribute("activeMenu", "dashboard");
        return "index";
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        model.addAttribute("activeMenu", "reports");
        return "reportes";
    }
}
