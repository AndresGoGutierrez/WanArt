package com.usta.sistemagaleria.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class erroresController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        int statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "error404";
        }
        if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "error500";
        }
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }

}