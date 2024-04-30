package mk.ukim.finki.nbp.aplipraksa.repository;
import mk.ukim.finki.nbp.aplipraksa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class OfferRepository {

    public JdbcTemplate jdbc;

    @Autowired
        public OfferRepository(JdbcTemplate jdbcTemplate) {
            this.jdbc = jdbcTemplate;
    }
    public void createOffer(String requirements,
                String responsibilities,
                String benefits,
                Integer salary,
                String field,
                Date start_date,
                Integer duration_in_weeks,
                Integer member_id,
                Integer company_id){
        jdbc.update("call ikt_project.insert_offer(?,?,?,?,?,?,?,?,?)",
                responsibilities, requirements, benefits, salary, field, start_date,
                duration_in_weeks, member_id, company_id);
    }

    public void updateOffer(Integer offer_id,
                            String requirements,
                            String responsibilities,
                            String benefits,
                            Integer salary,
                            String field,
                            Date start_date,
                            Integer duration_in_weeks){
        jdbc.update("call ikt_project.update_offer(?,?,?,?,?,?,?,?)",
                offer_id, requirements, responsibilities, benefits, salary, field, start_date, duration_in_weeks);
    }

    public void updateOfferAccomodation(Integer offer_id,
                                        Integer accomodation_id,
                                        String requirements,
                                        String responsibilities,
                                        String benefits,
                                        Integer salary,
                                        String field,
                                        Date start_date,
                                        Integer duration_in_weeks,
                                        String phoneNumber,
                                        String emailAddress,
                                        String address,
                                        String description){
        jdbc.update("call ikt_project.update_offer_accomodation(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                offer_id, accomodation_id, requirements, responsibilities, benefits, salary, field,
                start_date, duration_in_weeks, phoneNumber, emailAddress, address, description);
    }

    public void deleteOffer(Integer offerId) {
        jdbc.update("delete from ikt_project.offer where id =?", offerId);
    }

    public OfferEditView findOfferDetailByOfferId(Integer offerId){
        return jdbc.queryForObject("select * from ikt_project.offer_detail_view(?)", OfferDetailView::mapRowToOfferDetailView,offerId);
    }

    public OfferEditView findOfferEditByOfferId(Integer offerId) {
        return jdbc.queryForObject("select * from ikt_project.offer_edit_view(?)",OfferEditView::mapRowToOfferEditView,offerId);
    }
}
