package finki.ikt.tim1.internville.model;

import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Company {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private Integer country_id;
    private String numberOfEmployees;

    public Company(Integer id, String name, String phoneNumber, String emailAddress, String address, Integer country_id, String numberOfEmployees) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.country_id = country_id;
        this.numberOfEmployees = numberOfEmployees;
    }

    public static Company mapRowToCompany(ResultSet rs, int rowNum) throws SQLException {
        return new Company(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("phone_number"),
                rs.getString("email_address"),
                rs.getString("address"),
                Integer.parseInt(rs.getString("country_id")),
                rs.getString("number_of_employees")
        );
    }
}

