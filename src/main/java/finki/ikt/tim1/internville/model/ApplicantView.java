package finki.ikt.tim1.internville.model;

import lombok.Data;
import java.sql.SQLException;
import java.sql.ResultSet;

@Data
public class ApplicantView {
    private int applicantId;
    private String applicantCountry;
    private String applicantName;
    private String applicantSurname;
    private int applicantAge;
    private String applicantFaculty;
    private String applicantDegree;
    private String applicantMajor;
    private String applicantStatus;

    public ApplicantView(int applicantId, String applicantCountry, String applicantName, String applicantSurname, int applicantAge, String applicantFaculty, String applicantDegree, String applicantMajor, String applicantStatus) {
        this.applicantId = applicantId;
        this.applicantCountry = applicantCountry;
        this.applicantName = applicantName;
        this.applicantSurname = applicantSurname;
        this.applicantAge = applicantAge;
        this.applicantFaculty = applicantFaculty;
        this.applicantDegree = applicantDegree;
        this.applicantMajor = applicantMajor;
        this.applicantStatus = applicantStatus;
    }

    public static ApplicantView mapRowToApplicant(ResultSet rs, int rowNumber) throws SQLException{
        return new ApplicantView(
                Integer.parseInt(rs.getString("id")),
                rs.getString("country_name"),
                rs.getString("name"),
                rs.getString("surname"),
                Integer.parseInt(rs.getString("age")),
                rs.getString("faculty"),
                rs.getString("degree"),
                rs.getString("major"),
                rs.getString("status")
        );
    }
}
