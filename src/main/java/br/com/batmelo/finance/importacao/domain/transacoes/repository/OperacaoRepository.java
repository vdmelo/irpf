package br.com.batmelo.finance.importacao.domain.transacoes.repository;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoOperacaoFinanceira;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoTrade;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.OperacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, OperacaoId> {

    @Query("from Operacao o"
            + " where o.ativo = :ativo"
            + " and o.instituicao = :instituicao"
            + " and o.data = :data"
            + " and o.tipoOperacao = :tipo"
            + " and o.saldo>0")
    List<Operacao> findOperacoesDeVendaDoUsuarioNaInstituicaoNaDataComSaldoPositivo(AtivoId ativo, InstituicaoId instituicao, LocalDate data, TipoOperacaoFinanceira tipo);

    /*@Query("from OperacaoVenda o"
            + " where o.ativo = :ativo"
            + " and o.instituicao = :instituicao"
            + " and o.data = :data"
            + " and o.saldo>0")
    List<OperacaoVenda> findOperacoesDeVendaDoUsuarioNaInstituicaoNaDataComSaldoPositivo(AtivoId ativo, InstituicaoId instituicao, LocalDate data);

    @Query("from OperacaoCompra o"
            + " where o.ativo = :ativo"
            + " and o.instituicao = :instituicao"
            + " and o.data = :data"
            + " and o.saldo>0")
    List<OperacaoCompra> findOperacoesDeCompraDoUsuarioNaInstituicaoNaDataComSaldoPositivo(AtivoId ativo, InstituicaoId instituicao, LocalDate data);*/

    List<Operacao> findByAtivoAndTipoTradeOrderByDataAsc(AtivoId ativo, TipoTrade tipoTrade);
}
