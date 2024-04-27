package finki.ikt.tim1.internville.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferDetailView {
    private Integer offerId;
    private String countryName;
    private String companyName;
    private String companyAddress;

    private String requirements;
    private String responsibilities;
    private String benefits;
    private String salary;
    private String field;
    private String startDate;
    private String durationInWeeks;

    private String accPhone;
    private String accEmail;
    private String accAddress;
    private String accDescription;

    public OfferDetailView(Integer offerId, String countryName, String companyName, String companyAddress, String requirements, String responsibilities, String benefits, String salary, String field, String startingDate, String durationInWeeks, String accPhone, String accEmail, String accAddress, String accDescription) {
        this.offerId = offerId;
        this.countryName = countryName;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.requirements = requirements;
        this.responsibilities = responsibilities;
        this.benefits = benefits;
        this.salary = salary;
        this.field = field;
        this.startDate = startingDate;
        this.durationInWeeks = durationInWeeks;
        this.accPhone = accPhone;
        this.accEmail = accEmail;
        this.accAddress = accAddress;
        this.accDescription = accDescription;
    }

}
