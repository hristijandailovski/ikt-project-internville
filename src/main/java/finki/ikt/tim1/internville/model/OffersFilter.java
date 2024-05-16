package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OffersFilter {
    private Integer countryId;
    private String field;
    private LocalDate startDate;
    private Integer orderType;
    private Integer orderCriteria;

    public OffersFilter(Integer countryId, String field, LocalDate startDate, Integer orderType, Integer orderCriteria) {
        this.countryId = countryId;
        this.field = field;
        this.startDate = startDate;
        this.orderType = orderType;
        this.orderCriteria = orderCriteria;
    }
}
