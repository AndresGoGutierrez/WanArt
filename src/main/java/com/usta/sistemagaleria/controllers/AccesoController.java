package com.usta.sistemagaleria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccesoController {
    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        if (error != null) {
            model.addAttribute("error", "Login incorrecto, compruebe nuevamente su usuario y contraseña");
        }
        if (logout != null) {
            model.addAttribute("success", "Ha cerrado correctamente la sesión");
        }
        return "login";
    }
}