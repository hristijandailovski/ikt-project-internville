package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
@Data
public class Country {
    private Integer id;
    private String name;

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public static Country mapRowToCountry(ResultSet resultSet, int rowNum) throws SQLException {
        return new Country(
                Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("name")
        );
    }


}
