package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class MemberProfileEditView {
    private Integer id;
    private String username;
    private String password;

    private String name;
    private String surname;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer countryId;

    private String organizationName;
    private String commPhoneNumber;
    private String commEmail;
    private String commAddress;
    private String commCountryName;

    public MemberProfileEditView(Integer studentId, String username, String password, String name, String surname, String dateOfBirth, String address, String phoneNumber, String email, Integer countryId, String organizationName, String commPhoneNumber, String commEmail, String commAddress, String commCountryName) {
        this.id = studentId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.countryId = countryId;
        this.organizationName = organizationName;
        this.commPhoneNumber = commPhoneNumber;
        this.commEmail = commEmail;
        this.commAddress = commAddress;
        this.commCountryName = commCountryName;
    }
    public static MemberProfileEditView mapRowToMemberProfileEditView(ResultSet rs,int rowNum) throws SQLException {
        return new MemberProfileEditView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("date_of_birth"),
                rs.getString("address"),
                rs.getString("phone_number"),
                rs.getString("email_address"),
                Integer.parseInt(rs.getString("country_id")),
                rs.getString("org_name"),
                rs.getString("comm_phone_number"),
                rs.getString("comm_email"),
                rs.getString("comm_address"),
                rs.getString("comm_country_name")
        );
    }
}
