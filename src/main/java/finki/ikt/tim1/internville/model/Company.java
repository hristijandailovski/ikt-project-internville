package finki.ikt.tim1.internville.model;

import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Company {
    private String companyName;
    private int companyNumber;
    private String companyEmail;
    private int countryId;
    private int numberOfEmployees;

    public Company(String companyName, int companyNumber, String companyEmail, int countryId, int numberOfEmployees) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyEmail = companyEmail;
        this.countryId = countryId;
        this.numberOfEmployees = numberOfEmployees;
    }

    public static Company mapRowToCompany(ResultSet rs, int rowNum) throws SQLException{
        return new Company(
                rs.getString("name"),
                Integer.parseInt(rs.getString("phone_number")),
                rs.getString("email_adress"),
                Integer.parseInt(rs.getString("country_id")),
                Integer.parseInt(rs.getString("number_of_employees"))
        );
    }
}
