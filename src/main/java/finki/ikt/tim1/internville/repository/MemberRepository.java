package finki.ikt.tim1.internville.repository;

import finki.ikt.tim1.internville.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class MemberRepository {

    public JdbcTemplate jdbc;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
    }

    public void createMember(Integer memberId,
                             Integer committeeId,
                             String username,
                             String password,
                             String name,
                             String surname,
                             LocalDate dateOfBirth,
                             String address,
                             String phoneNumber,
                             String email,
                             Integer countryId,
                             Integer organizationId,
                             Integer committeeCountryId) {
        jdbc.update("call ikt_project.insert_end_user_member(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                memberId, committeeId, username, password, name, surname, dateOfBirth, address, phoneNumber, email, countryId, organizationId, committeeCountryId);
    }

    public void editMember(Integer memberId,
                           Integer committeeId,
                           String username,
                           String password,
                           String name,
                           String surname,
                           LocalDate dateOfBirth,
                           String address,
                           String phoneNumber,
                           String email,
                           Integer countryId,
                           Integer organizationId,
                           Integer committeeCountryId) {
        jdbc.update("call ikt_project.insert_end_user_member(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                memberId, committeeId, username, password, name, surname, dateOfBirth, address, phoneNumber, email, countryId, organizationId, committeeCountryId);
    }

    public void deleteMember(String memberId){
        jdbc.update("delete from ikt_project.end_user where id = ?", memberId);
    }

    public MemberProfileView findProfileByMemberId(String memberId){
        return jdbc.queryForObject("select * from ikt_project.member_profile_view(?)", MemberProfileView::mapRowToMemberProfileView, memberId);
    }

    public MemberProfileEditView findProfileEditByMemberId(String memberId){
        return jdbc.queryForObject("select * from ikt_project.member_profile_edit_view(?)", MemberProfileEditView::mapRowToMemberProfileEditView, memberId);
    }


}
