package com.nttdata.autenticacion.dao;

import com.nttdata.autenticacion.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface IUsuarioDao extends ReactiveMongoRepository<Usuario,String> {

    Mono<Usuario> findByUsuario(String usuario);

}