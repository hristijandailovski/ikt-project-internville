package finki.ikt.tim1.internville.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Offer {
    private Integer id;
    private String requirements;
    private String responsibilities;
    private String benefits;
    private String salary;
    private String field;
    private String startDate;
    private String durationInWeeks;
    private Integer memberId;
    private Integer companyId;
    private String is_active;

    public Offer(Integer id, String requirements, String responsibilities, String benefits, String salary, String field, String startDate, String durationInWeeks, Integer memberId, Integer companyId,String is_active) {
        this.id = id;
        this.requirements = requirements;
        this.responsibilities = responsibilities;
        this.benefits = benefits;
        this.salary = salary;
        this.field = field;
        this.startDate = startDate;
        this.durationInWeeks = durationInWeeks;
        this.memberId = memberId;
        this.companyId = companyId;
        this.is_active=is_active;
    }


    public static Offer mapRowToOffer(ResultSet rs, int rowNum) throws SQLException {
        return new Offer(
                Integer.parseInt(rs.getString("id")),
                rs.getString("requirements"),
                rs.getString("responsibilities"),
                rs.getString("benefits"),
                rs.getString("salary"),
                rs.getString("field"),
                rs.getString("start_date"),
                rs.getString("duration_in_weeks"),
                Integer.parseInt(rs.getString("member_id")),
                Integer.parseInt(rs.getString("company_id")),
                rs.getString("is_active")
        );
    }
}
