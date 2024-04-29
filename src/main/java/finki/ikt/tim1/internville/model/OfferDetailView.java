package finki.ikt.tim1.internville.model;


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

   /* public static OfferEditView mapRowToOfferDetailView(ResultSet rs, int rowNum) throws SQLException {
        return new OfferEditView(
                Integer.parseInt(rs.getString("offer_id")),
                rs.getString("country_name"),
                rs.getString("company_name"),
                rs.getString("company_address"),
                rs.getString("requirements"),
                rs.getString("responsibilities"),
                rs.getString("benefits"),
                rs.getString("salary"),
                rs.getString("field"),
                rs.getString("start_date"),
                rs.getString("duration_in_weeks"),
                rs.getString("acc_phone"),
                rs.getString("acc_email"),
                rs.getString("acc_address"),
                rs.getString("acc_description")
        );}*/


}
