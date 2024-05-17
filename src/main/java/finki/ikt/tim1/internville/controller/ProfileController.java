package finki.ikt.tim1.internville.controller;

import jakarta.servlet.http.HttpSession;
import finki.ikt.tim1.internville.model.*;
import finki.ikt.tim1.internville.model.enumerations.JobType;
import finki.ikt.tim1.internville.model.enumerations.LangLevel;
import finki.ikt.tim1.internville.model.enumerations.StudyType;
import finki.ikt.tim1.internville.repository.GlobalRepository;
import finki.ikt.tim1.internville.repository.MemberRepository;
import finki.ikt.tim1.internville.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final StudentRepository studentRepository;
    private final GlobalRepository globalRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ProfileController(StudentRepository studentRepository, GlobalRepository globalRepository, MemberRepository memberRepository) {
        this.studentRepository = studentRepository;
        this.globalRepository = globalRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping()
    public String ProfilePage(Model model, HttpSession session) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");

        if(userCredentials.getType().equals("student")){
            //za student
            Integer studentId = userCredentials.getId();
            StudentProfileView studentProfileView = this.studentRepository.findProfileByStudentId(studentId);
            Iterable<Project> projects = this.studentRepository.findAllProjectsByStudentId(studentId);
            Iterable<Experience> experiences = this.studentRepository.findAllExperiencesByStudentId(studentId);
            Iterable<Certificate> certificates = this.studentRepository.findAllCertificatesByStudentId(studentId);
            Iterable<LanguageView> languages = this.studentRepository.findAllLanguagesByStudentId(studentId);
            model.addAttribute("profile",studentProfileView);
            model.addAttribute("projects",projects);
            model.addAttribute("experiences",experiences);
            model.addAttribute("certificates",certificates);
            model.addAttribute("languages",languages);
        }else{
            Integer memberId = userCredentials.getId();
            MemberProfileView memberProfileView = this.memberRepository.findProfileByMemberId(memberId);
            model.addAttribute("profile",memberProfileView);
        }
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "profile");
        return "master-template";
    }

    @GetMapping("/edit")
    public String editProfilePage(Model model,HttpSession session) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(userCredentials.getType().equals("student")){
            //za student
            Integer studentId = userCredentials.getId();
            StudentProfileEditView studentProfileEditView = this.studentRepository.findProfileEditByStudentId(studentId);
            Iterable<Faculty> faculties = this.globalRepository.findAllFaculties();
            Iterable<Country> countries = this.globalRepository.findAllCountries();
            Iterable<Major> majors = this.globalRepository.findAllMajors();
            model.addAttribute("profile",studentProfileEditView);
            model.addAttribute("studyTypes", StudyType.values());
            model.addAttribute("countries",countries);
            model.addAttribute("faculties",faculties);
            model.addAttribute("majors",majors);
        }else{
            //za member
            Integer memberId = userCredentials.getId();
            MemberProfileEditView memberProfileEditView = this.memberRepository.findProfileEditByMemberId(memberId);
            Iterable<Country> countries = this.globalRepository.findAllCountries();
            model.addAttribute("profile",memberProfileEditView);
            model.addAttribute("countries",countries);
        }

        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "edit-profile");
        return "master-template";
    }
    @PostMapping("/edit")
    public String editProfile(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam(name = "date-of-birth") LocalDate dateOfBirth,
                              @RequestParam String password,
                              @RequestParam String address,
                              @RequestParam(name="phone-number") String phoneNumber,
                              @RequestParam String email,
                              @RequestParam(name="country-id") Integer countryId,
                              @RequestParam(name="type-of-study",required = false) String typeOfStudy,
                              @RequestParam (name="faculty-id",required = false) Integer facultyId,
                              @RequestParam(name="major-id",required = false) Integer majorId,
                              @RequestParam(name="start-of-studies",required = false) Integer startOfStudies,
                              @RequestParam(required = false) Float gpa,
                              @RequestParam(required = false) Integer credits,HttpSession session){
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(userCredentials.getType().equals("student")){
            this.studentRepository.updateStudent(userCredentials.getId(),password,name,surname,dateOfBirth,address,phoneNumber,email
                    ,countryId,typeOfStudy,gpa,credits,majorId,facultyId,startOfStudies);
        }else{
            //TODO: IMPLEMENTACIJA ZA MEMBER
            this.memberRepository.updateMember(userCredentials.getId(),password,name,surname,dateOfBirth,address,phoneNumber,email
                    ,countryId);

        }

        return "redirect:/profile";
    }
//    @GetMapping("/{id}/delete")
//    public String deleteProfile(@PathVariable Integer id){
//        this.studentRepository.deleteStudent(id);
//        return "/home";
//    }

    @GetMapping("/add-project")
    public String addProjectPage(Model model,HttpSession session) {
        //Samo student mozhe da pristapi
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "add-project");
        return "master-template";
    }
    @PostMapping("/add-project")
    public String addProject(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String completeness,
                             HttpSession session){
        //Samo student mozhe da pristapi
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.addProject(userCredentials.getId(),name,description,completeness);
        return "redirect:/profile";
    }
    @GetMapping("/delete-project/{prId}")
    public String deleteProject(@PathVariable Integer prId,HttpSession session) {
        //Samo student mozhe da pristapi
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.deleteProject(prId);
        return "redirect:/profile";
    }

    @GetMapping("/add-certificate")
    public String addCertificatePage(Model model,HttpSession session) {
        //Samo student mozhe da pristapi
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "add-certificate");
        return "master-template";
    }
    @PostMapping("/add-certificate")
    public String addCertificate(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam(name = "date-of-issue",required = false) LocalDate dateOfIssue,
                                 @RequestParam String publisher,
                                 HttpSession session){
        //Samo student mozhe da pristapi
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.addCertificate(userCredentials.getId(),name,description,dateOfIssue,publisher);
        return "redirect:/profile";
    }
    @GetMapping("/delete-certificate/{crId}")
    public String deleteCertificate( @PathVariable Integer crId,HttpSession session) {
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.deleteCertificate(crId);
        return "redirect:/profile";
    }



    @GetMapping("/add-language")
    public String addLanguagePage(Model model,HttpSession session){
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";

        model.addAttribute("langLevels", LangLevel.values());
        model.addAttribute("languages",this.studentRepository.findAllLanguages());
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "add-language");
        return "master-template";

    }
    @PostMapping("/add-language")
    public String addLanguage(@RequestParam(name = "lang-id") Integer langId,
                              @RequestParam String level,
                              HttpSession session){
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.addLanguage(userCredentials.getId(),langId,level);
        return "redirect:/profile";
    }
    @GetMapping("/delete-language/{laId}")
    public String deleteLanguage(@PathVariable Integer laId,HttpSession session) {
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.deleteLanguage(laId,userCredentials.getId());
        return "redirect:/profile";
    }
    @GetMapping("/add-experience")
    public String addExperiencePage( Model model,HttpSession session) {
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";

        model.addAttribute("jobTypes",JobType.values());
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "add-experience");
        return "master-template";
    }
    @PostMapping("/add-experience")
    public String addExperience(@RequestParam(name = "job-type") String jobType,
                                @RequestParam(required = false) String description,
                                @RequestParam(name="start-date",required = false) LocalDate startDate,
                                @RequestParam(name="duration",required = false) Integer durationInWeeks,
                                HttpSession session){
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.addExperience(userCredentials.getId(), jobType,description,startDate,durationInWeeks);
        return "redirect:/profile";
    }
    @GetMapping("/delete-experience/{exId}")
    public String deleteExperience(@PathVariable Integer exId,HttpSession session) {
        //samo student
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if(!userCredentials.getType().equals("student"))
            return "redirect:/profile";
        this.studentRepository.deleteExperience(exId);
        return "redirect:/profile";
    }
    @GetMapping("/view-profile/{studentId}")
    public String viewStudentProfileAsMember(@PathVariable Integer studentId,HttpSession session, Model model){
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        //samo za member
        if(!userCredentials.getType().equals("member"))
            return "redirect:/profile";
        StudentProfileView studentProfileView = this.studentRepository.findProfileByStudentId(studentId);
        Iterable<Project> projects = this.studentRepository.findAllProjectsByStudentId(studentId);
        Iterable<Experience> experiences = this.studentRepository.findAllExperiencesByStudentId(studentId);
        Iterable<Certificate> certificates = this.studentRepository.findAllCertificatesByStudentId(studentId);
        Iterable<LanguageView> languages = this.studentRepository.findAllLanguagesByStudentId(studentId);
        model.addAttribute("profile",studentProfileView);
        model.addAttribute("projects",projects);
        model.addAttribute("experiences",experiences);
        model.addAttribute("certificates",certificates);
        model.addAttribute("languages",languages);
        model.addAttribute("userCredentials",userCredentials);
        model.addAttribute("bodyContent", "view-student-profile");
        return "master-template";

    }

}
