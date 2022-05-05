package br.com.batmelo.finance.importacao.domain.usuario.model;

import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter

@Entity
public class Usuario {

    @Id
    private UsuarioId id;



}