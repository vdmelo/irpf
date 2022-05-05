package br.com.batmelo.finance.importacao.domain.ativo.repository;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtivoRepository extends JpaRepository<Ativo, AtivoId> {

    boolean existsByCodigoAtivo(String codigoAtivo);

    default void saveIfNotExists(Ativo ativo) {
        String codigoAtivo = ativo.getCodigoAtivo();
        String codigoAtivoLetras = codigoAtivo.replaceAll("[^a-zA-Z]", "");

        if( !existsByCodigoAtivo(codigoAtivo) && !existsByCodigoAtivo(codigoAtivoLetras) ) {
            save(ativo);
        }

    };

    default Ativo findByCodigoAtivo(String codigoAtivo) {
        String codigoAtivoLetras = codigoAtivo.replaceAll("[^a-zA-Z]", "");

        return getByCodigoAtivo(codigoAtivo)
                .orElseGet(() -> getByCodigoAtivo(codigoAtivoLetras).orElse(null));
    }

    Optional<Ativo> getByCodigoAtivo(String codigoAtivo);

}
