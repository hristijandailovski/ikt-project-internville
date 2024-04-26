package mk.ukim.finki.nbp.aplipraksa.model;


import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
@Data
public class Faculty {
    private Integer id;
    private Integer superiorId;
    private String name;

    public Faculty(Integer id, Integer superiorId, String name) {
        this.id = id;
        this.superiorId = superiorId;
        this.name = name;
    }
    public static Faculty mapRowToFaculty(ResultSet rs, int rowNum) throws SQLException {
        return new Faculty(
                Integer.parseInt(rs.getString("id")),
                Integer.parseInt(rs.getString("superior_id")),
                rs.getString("name")
        );
    }

}
