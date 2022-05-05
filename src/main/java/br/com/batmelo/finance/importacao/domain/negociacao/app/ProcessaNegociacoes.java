package br.com.batmelo.finance.importacao.domain.negociacao.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor

@Service
@Transactional
public class ProcessaNegociacoes {

    // operacoes
    // Quando faz uma transação de compra ou uma venda inicia uma operação
    // A operação tem várias movimentações, podendo algumas delas ser de DAY_TRADE ou SWING_TRADE
    // A operação pode ter lucro ou prejuizo
}
