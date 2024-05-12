package finki.ikt.tim1.internville.model;

import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CompanyView {
    private String companyName;
    private int companyNumber;
    private String companyEmail;
    private int countryId;
    private int numberOfEmployees;

    public CompanyView(String companyName, int companyNumber, String companyEmail, int countryId, int numberOfEmployees) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyEmail = companyEmail;
        this.countryId = countryId;
        this.numberOfEmployees = numberOfEmployees;
    }

    public static CompanyView mapRowToCompanyView(ResultSet rs, int rowNumber) throws SQLException{
        return new CompanyView(
                rs.getString("name"),
                Integer.parseInt(rs.getString("phone_number")),
                rs.getString("email_adress"),
                Integer.parseInt(rs.getString("country_id")),
                Integer.parseInt(rs.getString("number_of_employees"))
        );
    }
}
