package finki.ikt.tim1.internville.repository;

import finki.ikt.tim1.internville.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalRepository {
    public JdbcTemplate jdbc;
    
    @Autowired
    public GlobalRepository(JdbcTemplate jdbcTemplate){
        this.jdbc=jdbcTemplate;
    }

    public Iterable<Faculty> findAllFaculties() {
        return jdbc.query("select * from ikt_project.educational_institute as ed where ed.superior_id is not null",Faculty::mapRowToFaculty);
    }

    public Iterable<Country> findAllCountries() {
        return jdbc.query("select * from ikt_project.country",Country::mapRowToCountry);
    }

    public Iterable<Major> findAllMajors() {
        return jdbc.query("select * from ikt_project.major",Major::mapRowToMajor);
    }
    public Iterable<OfferShortView> findAllActiveOffers(Integer pageNumber){
        return jdbc.query("select * from ikt_project.active_offers(?)", OfferShortView::mapRowToOfferView,pageNumber);
    }
    public Iterable<CompanyView> findAllCompaniesViewOnPage(Integer pageNumber){
        return jdbc.query("select * from ikt_project.companies_view_on_page(?)",CompanyView::mapCompanyInformation,pageNumber);
    }

    public Offer findOfferById(Integer id) {
        return jdbc.queryForObject("select * from ikt_project.offer",Offer::mapRowToOffer);
    }

    public Iterable<Company> findAllCompanies() {
        return jdbc.query("select * from ikt_project.company",Company::mapCompanyInformation);
    }

    public UserCredentials findUserCredentialsByUserNameAndPassword(String username, String password) {
        return jdbc.queryForObject("select * from ikt_project.find_user_credentials_with_username_and_password(?,?)"
                ,UserCredentials::mapRowToUserCredentials,username,password);
    }

    public Iterable<Organization> findAllOrganizations() {
        return jdbc.query("select * from ikt_project.organization",Organization::mapRowToOrganization);
    }

    public Iterable<Country> findAllCountriesThatHaveCommittesByOrganization(Integer orgId) {
        return jdbc.query("select * from ikt_project.countries_of_committees_by_organization(?)",Country::mapRowToCountry,orgId);
    }
}
