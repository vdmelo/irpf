package br.com.batmelo.finance.importacao.domain.negociacao.repository;

import br.com.batmelo.finance.importacao.domain.negociacao.model.Negociacao;
import br.com.batmelo.finance.sk.identifiers.NegociacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegociacaoRepository extends JpaRepository<Negociacao, NegociacaoId> {
}
