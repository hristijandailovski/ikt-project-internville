package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Language {
    private Integer id;
    private String name;
    private String code;

    public Language(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public static Language mapRowToLanguage(ResultSet resultSet, int rowNum) throws SQLException {
        return new Language(
                Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("code")
        );
    }
}
