package finki.ikt.tim1.internville.model;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class StudentProfileEditView {
    private Integer id;
    private String username;
    private String password;

    private String name;
    private String surname;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer countryId;
    private String degree;
    private Integer majorId;
    private Integer facultyId;
    private String startYear;
    private String gpa;
    private String ectsCredits;

    public StudentProfileEditView(Integer studentId, String name, String surname, String dateOfBirth
            ,String address, String username, String password, String phoneNumber
            , String email, Integer countryId, String degree, Integer majorId
            , Integer facultyId, String startYear, String gpa,String ectsCredits) {
        this.address=address;
        this.id = studentId;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.countryId = countryId;
        this.degree = degree;
        this.majorId = majorId;
        this.facultyId = facultyId;
        this.startYear = startYear;
        this.gpa = gpa;
        this.ectsCredits =ectsCredits;

    }
    public static StudentProfileEditView mapRowToStudentProfileEditView(ResultSet rs, int rowNum) throws SQLException {

        return new StudentProfileEditView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("date_of_birth"),
                rs.getString("address"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("phone_number"),
                rs.getString("email_address"),
                Integer.parseInt(rs.getString("country_id")),
                rs.getString("study_level"),
                Integer.parseInt(rs.getString("major_id")),
                Integer.parseInt(rs.getString("fac_id")),
                rs.getString("start_year"),
                rs.getString("gpa"),
                rs.getString("ects_credits")
        );
    }


}
