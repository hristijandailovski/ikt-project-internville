package mk.ukim.finki.nbp.aplipraksa.model;

import lombok.Data;
import mk.ukim.finki.nbp.aplipraksa.model.enumerations.JobType;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Data
public class Experience {
    private Integer id;
    private String jobType;
    private String description;
    private String startDate;
    private String durationInWeeks;


    public Experience(Integer id, String jobType, String description, String startDate, String durationInWeeks) {
        this.id = id;
        this.jobType = jobType;
        this.description = description;
        this.startDate = startDate;
        this.durationInWeeks = durationInWeeks;

    }

    public static Experience mapRowToExperience(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Experience(
                Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("type_of_job"),
                resultSet.getString("description"),
                resultSet.getString("start_year"),
                resultSet.getString("duration_in_weeks")
        );
    }
}
