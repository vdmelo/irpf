package br.com.batmelo.finance.importacao.domain.proventos.repository;

import br.com.batmelo.finance.importacao.domain.proventos.model.Provento;
import br.com.batmelo.finance.sk.identifiers.ProventoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProventoRepository extends JpaRepository<Provento, ProventoId> {
}
