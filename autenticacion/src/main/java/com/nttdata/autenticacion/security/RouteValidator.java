package com.nttdata.autenticacion.security;

import com.nttdata.autenticacion.dto.RequestDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

//@AllArgsConstructor
//@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {
    private List<RequestDTO> paths;

    public List<RequestDTO> getPaths() {
        return paths;
    }

    public void setPaths(List<RequestDTO> paths) {
        this.paths = paths;
    }

    public boolean isAdminPath(RequestDTO dto) {
        return paths.stream().anyMatch(p ->
                Pattern.matches(p.getUri(), dto.getUri()) && p.getMethod().equals(dto.getMethod()));
    }
}
