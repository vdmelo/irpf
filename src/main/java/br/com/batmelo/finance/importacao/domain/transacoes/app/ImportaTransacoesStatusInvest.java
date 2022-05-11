package br.com.batmelo.finance.importacao.domain.transacoes.app;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.ativo.model.TipoAtivo;
import br.com.batmelo.finance.importacao.domain.ativo.repository.AtivoRepository;
import br.com.batmelo.finance.importacao.domain.instituicao.model.Instituicao;
import br.com.batmelo.finance.importacao.domain.instituicao.repository.InstituicaoRepository;
import br.com.batmelo.finance.importacao.domain.transacoes.model.Transacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoOperacaoFinanceira;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor

@Service
@Transactional
public class ImportaTransacoesStatusInvest {

    private final TransacaoRepository transacaoRepository;
    private final AtivoRepository ativoRepository;
    private final InstituicaoRepository instituicaoRepository;

    public void importar(File arquivo) throws ParseException, IOException {
        List<Transacao> transacoes = carregaTransacoesDoArquivoStatusInvest(arquivo);
        transacaoRepository.saveAll(transacoes);

    }

    private List<Transacao> carregaTransacoesDoArquivoStatusInvest(File arquivo) throws ParseException, IOException {
        List<Transacao> transacoes = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(arquivo);
        if (workbook.getNumberOfSheets() > 0) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();
            // Pula o header
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                transacoes.add(converteLinhaParaTransacao(row));
            }
        }
        return transacoes;
    }

    private Transacao converteLinhaParaTransacao(Row row) throws ParseException {

        Ativo ativo = obterAtivo(row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
        Instituicao instituicao = obterInstituicao(row.getCell(6).getStringCellValue());

        return Transacao.builder()
                .data(strToLocalDate(row.getCell(0).getStringCellValue()))
                .ativo(ativo.getId())
                .tipo(converteTipoOperacaoFinanceira(row.getCell(3).getStringCellValue()))
                .quantidade(strToBigDecimal(row.getCell(4).getStringCellValue()))
                .preco(strToBigDecimal(row.getCell(5).getStringCellValue()))
                .instituicao(instituicao.getId())
                .build();
    }

    private Ativo obterAtivo(String tipoAtivoStr, String codigoAtivo) {
        Ativo ativo = ativoRepository.findByCodigoAtivo(codigoAtivo);
        if (ativo == null) {
            ativo = ativoRepository.save(Ativo.builder()
                    .codigoAtivo(codigoAtivo)
                    .tipo(converteTipoAtivo(tipoAtivoStr))
                    .build());
        }
        return ativo;
    }

    private Instituicao obterInstituicao(String nomeInstituicao) {
        Instituicao instituicao = instituicaoRepository.findByNome(nomeInstituicao);

        if (instituicao == null) {
            instituicao = instituicaoRepository.save(Instituicao.builder()
                    .nome(nomeInstituicao)
                    .build());
        }

        return instituicao;
    }

    private static TipoAtivo converteTipoAtivo(String str) {
        switch (str) {
            case "ETF Exterior":
                return TipoAtivo.ETFI;
            case "Stocks":
                return TipoAtivo.STOCK;
            case "Fundos imobiliários":
                return TipoAtivo.FII;
            case "Ações":
                return TipoAtivo.EMPRESA;
            case "Tesouro direto":
                return TipoAtivo.TESOURO_DIRETO;
            case "Fundos de Investimento":
                return TipoAtivo.FUNDO_DE_INVESTIMENTO;
            case "REITS":
                return TipoAtivo.REIT;
            case "ETF":
                return TipoAtivo.ETF;
            default:
                return TipoAtivo.OUTROS;
        }
    }

    private static TipoOperacaoFinanceira converteTipoOperacaoFinanceira(String str) {
        switch (str) {
            case "V":
                return TipoOperacaoFinanceira.COMPRA;
            case "C":
                return TipoOperacaoFinanceira.VENDA;
            default:
                throw new TipoOperacaoFinanceira.TipoOperacaoFinanceiraInvalidaException();
        }
    }

    private static LocalDate strToLocalDate(String data) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, df);
    }

    private static BigDecimal strToBigDecimal(String str) throws ParseException {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return new BigDecimal(nf.parse(str).doubleValue());
    }

}
