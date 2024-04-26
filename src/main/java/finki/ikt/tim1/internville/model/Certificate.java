package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;
import mk.ukim.finki.nbp.aplipraksa.model.enumerations.JobType;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Data
public class Certificate {
    public Certificate(Integer id, String name, String description, String dateOfIssue, String publisher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateOfIssue = dateOfIssue;
        this.publisher = publisher;
    }

    private Integer id;
    private String name;
    private String description;
    private String dateOfIssue;
    private  String publisher;



    public static Certificate mapRowToCertificate(ResultSet resultSet,int rowNum) throws SQLException {
        return new Certificate(
            Integer.parseInt(resultSet.getString("id")),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("date_of_issue"),
            resultSet.getString("publisher")
        );
    }
}
