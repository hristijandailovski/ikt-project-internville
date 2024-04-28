package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Major {
    private Integer id;
    private String major;

    public Major(Integer id, String major) {
        this.id = id;
        this.major = major;
    }

    public static Major mapRowToMajor(ResultSet resultSet,int rowNum) throws SQLException {
        return new Major(
                Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("major")
        );
    }
}
