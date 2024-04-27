package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class UserCredentials {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String type;

    public UserCredentials(Integer id, String username, String password, String name, String surname, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public static UserCredentials mapRowToUserCredentials(ResultSet rs, int rowNum) throws SQLException {
        return new UserCredentials(
                Integer.parseInt(rs.getString("id")),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("type")
        );
    }
}
