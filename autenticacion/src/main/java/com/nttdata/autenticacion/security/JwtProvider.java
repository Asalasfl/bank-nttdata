package com.nttdata.autenticacion.security;

import com.nttdata.autenticacion.dto.RequestDTO;
import com.nttdata.autenticacion.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.stereotype.Component;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    RouteValidator routeValidator;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/api/autenticacion/login", "/api/autenticacion/crear").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin()
                .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                    // Aquí puedes definir acciones personalizadas después de una autenticación exitosa
                    return Mono.fromRunnable(() -> {
                        // Por ejemplo, redirigir a una página específica o devolver una respuesta personalizada
                    });
                })
                .and()
                .csrf().disable()
                .build();
    }
    public String crearToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(usuario.getUsuario());
        claims.put("id", usuario.getId());
        claims.put("rol", usuario.getRol());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token, RequestDTO dto) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        if(!isAdmin(token) && routeValidator.isAdminPath(dto))
            return false;
        return true;
    }

    public String getUserNameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e) {
            return "bad token";
        }
    }

    private boolean isAdmin(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("rol").equals("admin");
    }
}
