package br.com.batmelo.finance.importacao.domain.instituicao.repository;

import br.com.batmelo.finance.importacao.domain.instituicao.model.Instituicao;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, InstituicaoId> {

    Instituicao findByNome(String nomeInstituicao);

}
