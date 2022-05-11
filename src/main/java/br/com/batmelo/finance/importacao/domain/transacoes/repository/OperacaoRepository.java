package br.com.batmelo.finance.importacao.domain.transacoes.repository;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoOperacaoFinanceira;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.OperacaoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, OperacaoId> {

    @Query("from Operacao o"
         + " where o.ativo = :ativo"
         + " and o.instituicao = :instituicao"
         + " and o.data = :data"
         + " and o.tipoOperacao = :tipoOperacao"
         + " and o.saldo>0")
    List<Operacao> findOperacoesDoUsuarioNaInstituicaoNaDataComSaldoPositivo(AtivoId ativo, InstituicaoId instituicao, LocalDate data, TipoOperacaoFinanceira tipoOperacao);

}
