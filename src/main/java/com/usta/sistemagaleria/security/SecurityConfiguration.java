package com.usta.sistemagaleria.security;

import com.usta.sistemagaleria.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessHandler LoginSuccesHandler;

    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http //LO QUE VEN LOS ADMINISTRADORES
                .authorizeRequests(authorize -> authorize.requestMatchers("/crearArtista", "/eliminarArtista", "/editarArtista",
                                "/crearObra", "/editarObra","/eliminarObra/",
                                "/crearExposicion","/eliminarExposicion", "/editarExposicion")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/artista", "/detalleArtista",
                                "/clasificacionObra", "/filtroObra", "/detalleObra",
                                "/exposicion", "/detalleExposicion")
                        .hasAnyRole("ADMINISTRADOR", "VISITANTE")
                        .anyRequest()
                        .permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .successHandler(LoginSuccesHandler)
                        .permitAll())
                .logout(logouut -> logouut
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .exceptionHandling(exeptions -> exeptions
                        .accessDeniedPage("/error404"));
        return http.build();
    }
}