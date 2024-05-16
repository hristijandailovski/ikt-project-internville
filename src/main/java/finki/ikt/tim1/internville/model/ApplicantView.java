package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ApplicantView {

    private Integer offerId;
    private Integer studentId;
    private String status;
    private String country;
    private String name;
    private String surname;
    private String age;
    private String faculty;
    private String degree;
    private String major;

    public ApplicantView(Integer offerId, Integer studentId, String status, String country, String name, String surname, String age, String faculty, String degree, String major) {
        this.offerId = offerId;
        this.studentId = studentId;
        this.status = status;
        this.country = country;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.faculty = faculty;
        this.degree = degree;
        this.major = major;
    }

    public static ApplicantView mapRowToApplicantView(ResultSet rs, int rowNum) throws SQLException {
        return new ApplicantView(
                Integer.parseInt(rs.getString("offer_id")),
                Integer.parseInt(rs.getString("student_id")),
                rs.getString("status"),
                rs.getString("country"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("age"),
                rs.getString("faculty"),
                rs.getString("degree"),
                rs.getString("major")

        );

    }

}
