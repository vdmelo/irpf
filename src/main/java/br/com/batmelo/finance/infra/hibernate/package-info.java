@TypeDef(defaultForType = AtivoId.class, typeClass = AtivoId.AtivoIdType.class)
@TypeDef(defaultForType = UsuarioId.class, typeClass = UsuarioId.UsuarioIdType.class)
@TypeDef(defaultForType = ProventoId.class, typeClass = ProventoId.ProventoIdType.class)
@TypeDef(defaultForType = InstituicaoId.class, typeClass = InstituicaoId.InstituicaoIdType.class)
@TypeDef(defaultForType = NegociacaoId.class, typeClass = NegociacaoId.NegociacaoIdType.class)

package br.com.batmelo.finance.infra.hibernate;

import br.com.batmelo.finance.sk.identifiers.*;
import org.hibernate.annotations.TypeDef;
