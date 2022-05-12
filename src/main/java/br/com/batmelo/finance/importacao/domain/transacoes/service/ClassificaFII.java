package br.com.batmelo.finance.importacao.domain.transacoes.service;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.ativo.model.TipoAtivo;
import br.com.batmelo.finance.importacao.domain.ativo.repository.AtivoRepository;
import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor

@Service
public class ClassificaFII {

    private final OperacaoRepository operacaoRepository;

    private final AtivoRepository ativoRepository;

    public void classificaFIITodasOperacoes() {
        operacaoRepository.findAll()
                .stream()
                .forEach(this::classificaFII);

    }

    private void classificaFII(Operacao operacao) {

        Ativo ativo = ativoRepository.getById(operacao.getAtivo());
        if (ativo.getTipo() == TipoAtivo.FII) {
            operacao.transformaEmFII();
            operacaoRepository.save(operacao);
        }

    }

}
