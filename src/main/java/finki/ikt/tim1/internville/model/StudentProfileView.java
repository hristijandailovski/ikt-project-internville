package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class StudentProfileView {
    private Integer studentId;
    private String name;
    private String surname;
    private String age;
    private String countryName;
    private String degree;
    private String universityName;
    private String facultyName;
    private String major;
    private String startYear;

    public StudentProfileView(Integer studentId,String name, String surname, String age, String countryName, String degree, String facultyName, String major, String startYear) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.countryName = countryName;
        this.degree = degree;
        this.facultyName = facultyName;
        this.major = major;
        this.startYear = startYear;
    }

    public static StudentProfileView mapRowToStudentProfileView(ResultSet rs, int rowNum) throws SQLException {

        return new StudentProfileView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("age"),
                rs.getString("country_name"),
                rs.getString("study_level"),
                rs.getString("fac_name"),
                rs.getString("major"),
                rs.getString("start_year")
        );
    }
}
