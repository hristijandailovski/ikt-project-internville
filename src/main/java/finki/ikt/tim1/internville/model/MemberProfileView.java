package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class MemberProfileView {
    private Integer memberId;
    private String name;
    private String surname;
    private String age;
    private String countryName;

    private String organizationName;
    private String commPhoneNumber;
    private String commEmail;
    private String commAddress;
    private String commCountryName;

    public MemberProfileView(Integer memberId, String name, String surname, String age, String countryName, String organizationName, String commPhoneNumber, String commEmail, String commAddress, String commCountryName) {
        this.memberId = memberId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.countryName = countryName;
        this.organizationName = organizationName;
        this.commPhoneNumber = commPhoneNumber;
        this.commEmail = commEmail;
        this.commAddress = commAddress;
        this.commCountryName = commCountryName;
    }

    public static MemberProfileView mapRowToMemberProfileView(ResultSet rs, int rowNum) throws SQLException {
        return new MemberProfileView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("age"),
                rs.getString("country_name"),
                rs.getString("org_name"),
                rs.getString("comm_phone_number"),
                rs.getString("comm_email"),
                rs.getString("comm_address"),
                rs.getString("comm_country_name")
        );
    }
}
