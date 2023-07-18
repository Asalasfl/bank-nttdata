package  com.nttdata.autenticacion.controller;

import com.nttdata.autenticacion.dto.NuevoUsuarioDTO;
import com.nttdata.autenticacion.dto.RequestDTO;
import com.nttdata.autenticacion.dto.TokenDTO;
import com.nttdata.autenticacion.dto.UsuarioDTO;
import com.nttdata.autenticacion.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/autenticacion")
public class AutenticacionController {
    @Autowired
    AutenticacionService autenticacionService;

    @PostMapping(value = "/login", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<TokenDTO>> login(@RequestBody UsuarioDTO dto) {
        return Mono.just(dto)
                .flatMap(autenticacionService::login)
                .map(tokenDto -> ResponseEntity.ok(tokenDto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/validar", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<TokenDTO>> validate(@RequestParam String token, @RequestBody RequestDTO dto) {
        return Mono.just(dto)
                .flatMap(request -> autenticacionService.validate(token, request))
                .map(tokenDto -> ResponseEntity.ok(tokenDto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/crear", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Object>> create(@RequestBody NuevoUsuarioDTO dto) {
        return autenticacionService.save(dto)
                .map(usuario -> ResponseEntity.ok(usuario))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
