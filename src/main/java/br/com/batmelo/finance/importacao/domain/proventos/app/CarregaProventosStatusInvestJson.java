package br.com.batmelo.finance.importacao.domain.proventos.app;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.ativo.model.TipoAtivo;
import br.com.batmelo.finance.importacao.domain.ativo.repository.AtivoRepository;
import br.com.batmelo.finance.importacao.domain.proventos.dto.StatusInvestProventoDto;
import br.com.batmelo.finance.importacao.domain.proventos.model.Provento;
import br.com.batmelo.finance.importacao.domain.proventos.model.TipoProvento;
import br.com.batmelo.finance.importacao.domain.proventos.repository.ProventoRepository;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor

@Service
@Transactional
public class CarregaProventosStatusInvestJson {

    private final ProventoRepository proventoRepository;
    private final AtivoRepository ativoRepository;

    public void carregaProventosStatusInvest(File arquivo) {
        List<Provento> proventos = listarProventosStatusInvest(arquivo);
        proventoRepository.saveAll(proventos);
    }

    private List<Provento> listarProventosStatusInvest(File proventos) {
        List<Provento> retorno = new ArrayList<Provento>();
        try {
            List<StatusInvestProventoDto> lista = carregaProventosDoJsonStatusInvestFile(proventos);
            UsuarioId usuario = UsuarioId.from("e556e769-c828-4bbe-b849-4f514cfb0eee");

            for (StatusInvestProventoDto dto : lista) {
                Ativo ativo = criaAtivoSeNaoExiste(dto);

                Provento provento = Provento.builder()
                        .ativo(ativo.getId())
                        .usuario(usuario)
                        .total(strToBigDecimal(dto.getTotalValue()))
                        .liquido(strToBigDecimal(dto.getNetTotalValue()))
                        .dataCom(strToLocalDate(dto.getDateCom_F()))
                        .dataPagamento(strToLocalDate(dto.getPaymentDate_F()))
                        .tipo(TipoProvento.convert(dto.getDividendTypeName().toUpperCase()))
                        .build();

                retorno.add(provento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private Ativo criaAtivoSeNaoExiste(StatusInvestProventoDto dto) {
        Ativo ativo = ativoRepository.findByCodigoAtivo(dto.getCode());
        if (ativo == null) {
            ativo = ativoRepository.save(Ativo.builder()
                    .codigoAtivo(dto.getCode())
                    .tipo(converteTipoAtivo(dto.getCategoryName()))
                    .nome(dto.getName())
                    .build());
        }

        return ativo;
    }

    private static TipoAtivo converteTipoAtivo(String str) {
        switch (str) {
            case "Ações":
                return TipoAtivo.EMPRESA;
            case "FIIs":
                return TipoAtivo.FII;
            case "Stocks":
                return TipoAtivo.STOCK;
            case "ETF Exterior":
                return TipoAtivo.ETFI;
            case "REITS":
                return TipoAtivo.REIT;
            case "ETF":
                return TipoAtivo.ETF;
            default:
                return TipoAtivo.OUTROS;
        }
    }

    private static List<StatusInvestProventoDto> carregaProventosDoJsonStatusInvestFile(File arquivo) throws IOException {
        InputStream in = new FileInputStream(arquivo);
        try (in) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(in);
            JsonNode data = node.at("/data");
            JsonNode list = data.findValue("list");
            List<Map<String, Object>> mapa = converteJsonNodeParaMapaDeValores(list);

            return mapa.stream()
                    .map(m -> StatusInvestProventoDto.builder()
                            .code(removeAspas(m.get("code")))
                            .dividendTypeName(removeAspas(m.get("dividendTypeName")))
                            .totalValue(removeAspas(m.get("totalValue")))
                            .paymentDate_F(removeAspas(m.get("paymentDate_F")))
                            .dateCom_F(removeAspas(m.get("dateCom_F")))
                            .categoryName(removeAspas(m.get("categoryName")))
                            .name(removeAspas(m.get("name")))
                            .build())
                    .collect(Collectors.toList());
        }
    }

    private static List<Map<String, Object>> converteJsonNodeParaMapaDeValores(JsonNode lista) {
        List<Map<String, Object>> mapa = new ArrayList<Map<String, Object>>();

        lista.forEach((p) -> {
            final HashMap<String, Object> m = new HashMap<String, Object>();
            p.fields().forEachRemaining((e) -> {
                m.put(e.getKey(), e.getValue());
            });
            mapa.add(m);
        });

        return mapa;
    }

    private static BigDecimal strToBigDecimal(String str) {
        return BigDecimal.valueOf(Double.valueOf(requireNonNullElse(str, "0")));
    }

    private static LocalDate strToLocalDate(String data) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(data, df);
        } catch (Exception e) {
            return null;
        }
    }

    private static String removeAspas(Object obj) {
        if (obj == null) return "";
        String str = obj.toString();
        if (str.isEmpty()) {
            return "";
        }

        return str.replace("\"", "");
    }
}
