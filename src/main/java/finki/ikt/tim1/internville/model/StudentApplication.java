package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class StudentApplication {
    private Integer offerId;
    private String countryName;
    private String fieldOfStudy;
    private String startingDate;
    private String lengthsInWeeks;
    private String companyName;
    private String status;

    public StudentApplication(Integer offerId, String countryName, String fieldOfStudy, String startingDate, String lengthsInWeeks, String companyName, String status) {
        this.offerId = offerId;
        this.countryName = countryName;
        this.fieldOfStudy = fieldOfStudy;
        this.startingDate = startingDate;
        this.lengthsInWeeks = lengthsInWeeks;
        this.companyName = companyName;
        this.status = status;
    }

    public static StudentApplication mapRowStudentApplicaiton(ResultSet resultSet, int rowNum) throws SQLException {
        return new StudentApplication(
                Integer.parseInt(resultSet.getString("offer_id")),
                resultSet.getString("country_name"),
                resultSet.getString("field"),
                resultSet.getString("start_date"),
                resultSet.getString("duration_in_weeks"),
                resultSet.getString("company_name"),
                resultSet.getString("acceptance_status")
//           ,resultSet.getString("number_of_applications")
        );
    }
}
