package com.usta.sistemagaleria.controllers;

import com.usta.sistemagaleria.entities.RolEntity;
import com.usta.sistemagaleria.entities.UsuarioEntity;
import com.usta.sistemagaleria.models.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping(value = "/register")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("title", "Registrar Usuario");
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("usuario") @Valid UsuarioEntity usuario,
                           BindingResult result,
                           @RequestParam("confirmarClave") String confirmarClave,
                           Model model,
                           SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Registrar Usuario");
            return "register";
        }

        if (!usuario.getContrasenaUsr().equals(confirmarClave)) {
            result.rejectValue("contrasenaUsr", "error.usuario", "Las contraseñas no coinciden.");
            model.addAttribute("title", "Registrar Usuario");
            return "register";
        }

        String pass = new BCryptPasswordEncoder().encode(usuario.getContrasenaUsr());
        usuario.setContrasenaUsr(pass);

        RolEntity rolLector = new RolEntity();
        rolLector.setIdRol(2L); //lector
        usuario.setRol(rolLector);

        usuarioService.save(usuario);
        status.setComplete();

        return "redirect:/login";
    }


}