package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Project {
    private Integer id;
    private String name;
    private String description;
    private String completeness;

    public Project(Integer id, String name, String description, String completeness) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completeness = completeness;
    }
    public static Project mapRowToProject(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Project(
                Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("completeness")
        );
    }
}
