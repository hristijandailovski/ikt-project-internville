package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Organization {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String address;

    public Organization(Integer id, String name, String phoneNumber, String emailAddress, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public static Organization mapRowToOrganization(ResultSet rs, int rowNum) throws SQLException {
        return new Organization(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("phone_number"),
                rs.getString("email_address"),
                rs.getString("address")
        );
    }
}
