package finki.ikt.tim1.internville.repository;

import finki.ikt.tim1.internville.model.*;
import finki.ikt.tim1.internville.model.enumerations.StudyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class StudentRepository {
    public JdbcTemplate jdbc;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate){
        this.jdbc=jdbcTemplate;
    }

    public void createStudent(String username,
                              String password,
                              String name,
                              String surname,
                              LocalDate dateOfBirth,
                              String address,
                              String phoneNumber,
                              String emailAddress,
                              Integer countryId,
                              StudyType studyType,
                              Float gpa,
                              Integer ectsCredits,
                              Integer majorId,
                              Integer educationalInstituteId,
                              Integer startYear){
        jdbc.update("call ikt_project.insert_end_user_student(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                username,password,name,surname,dateOfBirth,address,phoneNumber,emailAddress,countryId
                ,studyType.toString(),gpa,ectsCredits,majorId,educationalInstituteId,startYear);
    }

    public void updateStudent(Integer studentId,
                              String password,
                              String name,
                              String surname,
                              LocalDate dateOfBirth,
                              String address,
                              String phoneNumber,
                              String emailAddress,
                              Integer countryId,
                              String studyType,
                              Float gpa,
                              Integer ectsCredits,
                              Integer majorId,
                              Integer educationalInstituteId,
                              Integer startYear){
        jdbc.update("call ikt_project.update_end_user_student(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                studentId,password,name,surname,dateOfBirth,address,phoneNumber,emailAddress,countryId
                ,studyType,gpa,ectsCredits,majorId,educationalInstituteId,startYear);
    }

    public void deleteStudent(Integer studentId){
        jdbc.update("delete from ikt_project.end_user where id =?",studentId);
    }
    public StudentProfileView findProfileByStudentId(Integer studentId){
        return jdbc.queryForObject("select * from ikt_project.student_profile_view(?)", StudentProfileView::mapRowToStudentProfileView,studentId);
    }
    public StudentProfileEditView findProfileEditByStudentId(Integer studentId) {
        return jdbc.queryForObject("select * from ikt_project.student_profile_edit_view(?)",StudentProfileEditView::mapRowToStudentProfileEditView,studentId);
    }
    public void addExperience(Integer studentId,
                                 String jopType,
                                 String descritpion,
                                 LocalDate startYear,
                                 Integer durationInWeeks
                                 ){
        jdbc.update("call ikt_project.insert_experience(?,?,?,?,?)",jopType,descritpion,startYear,durationInWeeks,studentId);
    }
    public Iterable<Experience>  findAllExperiencesByStudentId(Integer studentId){
        return jdbc.query("select * from ikt_project.experience where student_id=?", Experience::mapRowToExperience,studentId);
    }
    public void deleteExperience(Integer experienceId){
        jdbc.update("delete from ikt_project.experience where id=?",experienceId);
    }

    public void addCertificate(Integer studentId,
                                  String name,
                                  String description,
                                  LocalDate dateOfIssue,
                                  String publisher){
        jdbc.update("call ikt_project.insert_or_update_certificate(?,?,?,?,?)",name,description,dateOfIssue,publisher,studentId);
    }
    public Iterable<Certificate>  findAllCertificatesByStudentId(Integer studentId){
        return jdbc.query("select * from ikt_project.certificate where student_id=?", Certificate::mapRowToCertificate,studentId);
    }
    public void deleteCertificate(Integer certificationId){
        jdbc.update("delete from ikt_project.certificate where id=?",certificationId);
    }


    public void addProject(Integer studentId,
                              String name,
                              String description,
                              String completenessType){
        jdbc.update("call ikt_project.insert_or_update_project(?,?,?,?)",name,description,completenessType,studentId);

    }
    public Iterable<Project> findAllProjectsByStudentId(Integer studentId){
        return jdbc.query("select * from ikt_project.project where student_id=?", Project::mapRowToProject,studentId);
    }

    public void deleteProject(Integer projectId){
        jdbc.update("delete from ikt_project.project where id=?",projectId);
    }

    public void addLanguage(Integer studentId,
                            Integer languageId,
                            String langLevel){
        jdbc.update("call ikt_project.insert_or_update_knows_language(?,?,?)",studentId,languageId,langLevel);

    }
    public Iterable<LanguageView> findAllLanguagesByStudentId(Integer studentId){
        return jdbc.query("select * from ikt_project.lang_known_by_student(?)", LanguageView::mapRowToLanguageView,studentId);
    }
    public void deleteLanguage(Integer languageId, Integer studentId){
        jdbc.update("delete from ikt_project.knows_language where lang_id=? and student_id=?",languageId,studentId);
    }


    public Iterable<Language> findAllLanguages() {
        return jdbc.query("select * from ikt_project.language",Language::mapRowToLanguage);
    }


    public Iterable<StudentApplication> findMyApplications(Integer id,Integer pageNumber) {
        return jdbc.query("select * from ikt_project.student_application(?,?)",StudentApplication::mapRowStudentApplicaiton, id,pageNumber);
//        return null;
    }

    public void applyForOffer(Integer studentId, Integer offerId) {
        jdbc.update("call ikt_project.student_apply_for_offer(?, ?)", studentId, offerId);
    }

    public void deleteMyApplication(Integer id, Integer appId) {
        jdbc.update("delete from ikt_project.applies_for as ap where ap.student_id = ? and ap.offer_id = ?",id,appId);
    }

    public void saveFeedback(Integer student_id, Integer offer_id, Integer gradeWork, Integer gradeAccommodation, String commentByStudent) {
        jdbc.update("call ikt_project.give_feedback(?, ?, ?, ?, ?)", student_id, offer_id, gradeWork, gradeAccommodation, commentByStudent);
    }
}
