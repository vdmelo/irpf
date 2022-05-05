package br.com.batmelo.finance.importacao.domain.ativo.app;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.ativo.model.TipoAtivo;
import br.com.batmelo.finance.importacao.domain.ativo.repository.AtivoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor

@Service
@Transactional
public class ImportaEmpresaAppService {

    private final AtivoRepository repository;

    public void importaListaEmpresas() throws IOException {
        coletaEmpresas()
                .stream()
                .forEach(repository::saveIfNotExists);
    }

    private static List<Ativo> coletaEmpresas() throws IOException {
        Document doc = Jsoup
                .connect("https://www.idinheiro.com.br/cnpj-empresas-listadas-b3/")
                .sslSocketFactory(socketFactory())
                .get();

        Elements table = doc.getElementsByClass("content-post__block__table--striped");

        if (!table.isEmpty()) {
            Elements linhas = table.get(0).getElementsByTag("tr");
            return linhas.stream()
                    .map(linha -> linha.getElementsByTag("td"))
                    .filter(Predicate.not(Elements::isEmpty))
                    .flatMap(ImportaEmpresaAppService::converteColunasTabelaParaAtivo)
                    .collect(Collectors.toList());

        }
        return emptyList();
    }

    private static Stream<Ativo> converteColunasTabelaParaAtivo(Elements colunas) {
        String nome = colunas.get(0).text();
        String codigo = colunas.get(1).text();
        String cnpj = colunas.get(2).text();
        String bancoEscriturario = colunas.get(3).text();

        String[] codigos = codigo.split(" ");
        return Arrays.asList(codigos)
                .stream()
                .map((cod) -> Ativo.builder()
                        .codigoAtivo(cod)
                        .nome(nome)
                        .cnpj(cnpj)
                        .nomeFontePagadora(bancoEscriturario)
                        .tipo(TipoAtivo.EMPRESA)
                        .build());
    }

    private static SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }


    public static void main(String[] args) {
        String s = "XXX YYY ZZZ WWW";
        Arrays.asList(s.split(" "))
                .stream()
                .forEach(System.out::println);
    }
}
