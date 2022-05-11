package br.com.batmelo.finance.importacao.domain.transacoes.repository;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Transacao;
import br.com.batmelo.finance.sk.identifiers.TransacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, TransacaoId> {

    List<Transacao> findAllByOrderByDataAsc();
}
