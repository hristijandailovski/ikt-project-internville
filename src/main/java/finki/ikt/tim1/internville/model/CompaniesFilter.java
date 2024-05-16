package finki.ikt.tim1.internville.model;

import lombok.Data;

@Data
public class CompaniesFilter {
    private Integer countryId;
    private Integer orderType;
    private Integer orderCriteria;

    public CompaniesFilter(Integer countryId, Integer orderType, Integer orderCriteria) {
        this.countryId = countryId;
        this.orderType = orderType;
        this.orderCriteria = orderCriteria;
    }
}
