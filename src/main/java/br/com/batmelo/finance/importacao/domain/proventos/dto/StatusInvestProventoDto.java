package br.com.batmelo.finance.importacao.domain.proventos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class StatusInvestProventoDto {
    private String code;
    private String totalValue;
    private String netTotalValue;
    private String paymentDate_F;
    private String dividendTypeName;
    private String dateCom_F;
    private String categoryName;
    private String name;

}
