package finki.ikt.tim1.internville.model;

import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CompanyView {
    private Integer id;
    private String name;
    private String address;
    private String countryName;
    private String numberOfEmployees;
    private String offersCount;

    public CompanyView(Integer id, String name, String address, String countryName, String numberOfEmployees, String offersCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.countryName = countryName;
        this.numberOfEmployees = numberOfEmployees;
        this.offersCount = offersCount;
    }

    public static CompanyView  mapRowToCompanyView(ResultSet rs, int rowNum) throws SQLException {
        return new CompanyView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("country_name"),
                rs.getString("number_of_employees"),
                rs.getString("offers_count")
        );
    }


}
