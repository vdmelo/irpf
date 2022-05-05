package br.com.batmelo.finance.importacao.domain.ativo.app;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.ativo.model.TipoAtivo;
import br.com.batmelo.finance.importacao.domain.ativo.repository.AtivoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class ImportaFIIAppService {

    private final AtivoRepository repository;

    public void importaFiis() {
        try {
            List<Ativo> ativos = ImportaFIIAppService.listarFiis();
            ativos.stream().forEach(repository::saveIfNotExists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Ativo> listarFiis() throws IOException {
        List<Ativo> fiis = new ArrayList<Ativo>();
        File input = new File("C:\\Users\\vdmel\\dev-vdmelo\\temp\\fiis.html");
        String url = "file://\"";
        Document doc = Jsoup.parse(input, "UTF-8", "https://jsoup.org/cookbook/input/load-document-from-file");

        Elements linhas = doc.select("table tr.tabela_principal");
        for (Element linha : linhas) {
            Ativo ativo = extraiAtivoDaLinha(linha);
            if( ativo!=null ) {
                fiis.add(ativo);
            }
        }
        return fiis;
    }

    private static Ativo extraiAtivoDaLinha(Element linha) {
        Elements tds = linha.getElementsByTag("td");
        if(tds.size()>0 ) {
            String codigoAtivo = tds.get(0).text();
            Elements links = tds.get(1).getElementsByTag("a");
            Element link = links.get(0);
            String descAtivo = link.text();
            Ativo ativo = Ativo.builder()
                    .codigoAtivo(codigoAtivo)
                    .descricao(descAtivo)
                    .nome(descAtivo)
                    .tipo(TipoAtivo.FII)
                    .build();
            return ativo;
        }
        return null;

    }

}
