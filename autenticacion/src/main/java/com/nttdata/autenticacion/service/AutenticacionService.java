package  com.nttdata.autenticacion.service;

import com.nttdata.autenticacion.dao.IUsuarioDao;
import com.nttdata.autenticacion.dto.NuevoUsuarioDTO;
import com.nttdata.autenticacion.dto.RequestDTO;
import com.nttdata.autenticacion.dto.TokenDTO;
import com.nttdata.autenticacion.dto.UsuarioDTO;
import com.nttdata.autenticacion.model.Usuario;
import com.nttdata.autenticacion.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class AutenticacionService {
    @Autowired
    IUsuarioDao usuarioDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    public Mono<Object> save(NuevoUsuarioDTO dto) {
        return usuarioDao.findByUsuario(dto.getUsuario())
                .flatMap(existingUser -> Mono.error(new RuntimeException("El usuario ya existe")))
                .switchIfEmpty(Mono.defer(() -> {
                    String password = passwordEncoder.encode(dto.getPassword());
                    Usuario usuarioSave = Usuario.builder()
                            .usuario(dto.getUsuario())
                            .password(password)
                            .rol(dto.getRol())
                            .build();
                    return (Mono<Usuario>) usuarioDao.save(usuarioSave); // Casting explícito a Mono<Usuario>
                }));
    }

    public Mono<TokenDTO> login(UsuarioDTO dto) {
        return usuarioDao.findByUsuario(dto.getUsuario())
                .filter(usuario -> passwordEncoder.matches(dto.getPassword(), usuario.getPassword()))
                .map(usuario -> new TokenDTO(jwtProvider.crearToken(usuario)));
    }

    public Mono<TokenDTO> validate(String token, RequestDTO dto) {
        if (!jwtProvider.validate(token, dto))
            return Mono.empty();

        String usuario = jwtProvider.getUserNameFromToken(token);
        return usuarioDao.findByUsuario(usuario)
                .map(usr -> new TokenDTO(token));
    }
}
