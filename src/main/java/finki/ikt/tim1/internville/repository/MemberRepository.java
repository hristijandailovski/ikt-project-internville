package finki.ikt.tim1.internville.repository;

import finki.ikt.tim1.internville.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class MemberRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public MemberRepository(JdbcTemplate jdbc){
        this.jdbc=jdbc;
    }
//    @PostConstruct
//    public void testCreateOffer(){
//        this.createOffer("0","0","0",1,"0", Date.valueOf("2019-01-10").toLocalDate(),2, 3,1);
//    }
    public void createOffer(String requirements,
                        String responsibilities,
                        String benefits,
                        Integer salary,
                        String field,
                        LocalDate startDate,
                        Integer durationInWeeks,
                        Integer memberId,
                        Integer companyId){
        jdbc.update("call ikt_project.insert_offer(?, ?,?,?,?,?,?,?,?)", requirements,responsibilities,benefits,salary,field,startDate,durationInWeeks,memberId,companyId);
    }
    public void deleteOffer1(Integer offerId){
        jdbc.update("delete from ikt_project.offer where id=?",offerId);
    }

    public void deleteOffer(Integer memberId,Integer offerId){
        jdbc.update("call ikt_project.delete_offer(?,?)",memberId,offerId);
    }
    public void updateOffer(Integer offerId,
                            String requirements,
                            String responsibilities,
                            String benefits,
                            Integer salary,
                            String field,
                            LocalDate startDate,
                            Integer durationInWeeks,
                            Integer memberId,
                            Integer companyId){
        jdbc.update("call ikt_project.update_offer(?, ?,?,?,?,?,?,?,?,?)",offerId,requirements,responsibilities,benefits,salary,field,startDate,durationInWeeks,memberId,companyId);
    }
    public void updateOfferAndAccommodation(
            Integer memberId,
            Integer offerId,
            String requirements,
            String responsibilities,
            String benefits,
            Integer salary,
            String field,
            LocalDate startDate,
            Integer durationInWeeks,
            String accPhone,
            String accEmail,
            String accAddress,
            String accDescription){
        jdbc.update("call ikt_project.update_offer_accommodation(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                memberId,offerId,requirements,responsibilities,benefits,salary,field,startDate,
                durationInWeeks,accPhone,accEmail,accAddress,accDescription);
    }
//    @PostConstruct
//    public void testReadOfferView(){
//        Iterable<OfferView> offerViews = readOfferView();
//        System.out.println(offerViews.iterator().next().toString());
//    }
    public Iterable<OfferShortView> readOfferShortView(){
        return jdbc.query("select * from ikt_project.offer_view", OfferShortView::mapRowToOfferView);
    }

    public OfferEditView findOfferEditView(Integer offerId){
        return jdbc.queryForObject("select * from ikt_project.offer_edit_view(?)",OfferEditView::mapRowToOfferEditView,offerId);
    }
    public Iterable<OfferShortView> findAllOffersByMember(Integer memberId,Integer pageNumber){
        return jdbc.query("select * from ikt_project.offers_created_by_member(?,?)",OfferShortView::mapRowToOfferView,memberId,pageNumber);
    }

    public MemberProfileView findProfileByMemberId(Integer memberId) {
        return jdbc.queryForObject("select * from ikt_project.member_profile_view(?)",MemberProfileView::mapRowToMemberProfileView,memberId);
    }

    public MemberProfileEditView findProfileEditByMemberId(Integer memberId) {
        return jdbc.queryForObject("select * from ikt_project.member_profile_edit_view(?)",MemberProfileEditView::mapRowToMemberProfileEditView,memberId);
    }

    public void updateMember(Integer id,
                             String password,
                             String name,
                             String surname,
                             LocalDate dateOfBirth,
                             String address,
                             String phoneNumber,
                             String email,
                             Integer countryId) {
        jdbc.update("call ikt_project.update_end_user(?,?,?,?,?,?,?,?,?)",
                id,password,name,surname,dateOfBirth,address,phoneNumber,email,countryId);
    }

    public void createMember(String username, String password, String name, String surname,
                             LocalDate dateOfBirth, String address, String phone, String email,
                             Integer countryId, Integer organizationId,Integer comCountryId) {
        jdbc.update("call ikt_project.insert_end_user_member(?,?,?,?,?,?,?,?,?,?,?)",
                username,password,name,surname,dateOfBirth,address,phone,email,countryId
                ,organizationId,comCountryId );


    }

    public Iterable<ApplicantView> findAllApplicantByOffer(Integer offerId) {
        return jdbc.query("select * from ikt_project.all_applicant_by_offer(?)",ApplicantView::mapRowToApplicant,offerId);

    }

    public void updateApplicant(Integer offerId, Integer studentId) {
        jdbc.update("call ikt_project.update_applicant_status(?,?)",offerId,studentId);
    }

    public void acceptApplicant(Integer studentId,Integer offerId) {
        jdbc.update("call ikt_project.accept_applicant(?,?)",studentId,offerId);
    }
}
