package finki.ikt.tim1.internville.controller;

import finki.ikt.tim1.internville.model.*;
import finki.ikt.tim1.internville.model.enumerations.StudyType;
import finki.ikt.tim1.internville.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping
public class RegisterController {

    private final StudentRepository studentRepository;
    private final GlobalRepository globalRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public RegisterController(StudentRepository studentRepository, GlobalRepository globalRepository, MemberRepository memberRepository) {
        this.studentRepository = studentRepository;
        this.globalRepository = globalRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/register-as-student")
    public String getRegisterAsStudentPage(Model model, HttpSession session){
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";

        Iterable<Country> countries = this.globalRepository.findAllCountries();
        Iterable<Faculty> faculties = this.globalRepository.findAllFaculties();
        Iterable<Major> majors =this.globalRepository.findAllMajors();

        model.addAttribute("countries",countries);
        model.addAttribute("faculties",faculties);
        model.addAttribute("majors",majors  );
        model.addAttribute("studyTypes", StudyType.values());
        model.addAttribute("bodyContent", "register-as-student");
        return "master-template";
    }

    @PostMapping("/register-as-student")
    public String registerAsStudent(@RequestParam String username,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    @RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam(name = "date-of-birth") LocalDate dateOfBirth,
                                    @RequestParam String address,
                                    @RequestParam String phone,
                                    @RequestParam(name="country") Integer countryId,
                                    @RequestParam(name="faculty") Integer facultyId,
                                    @RequestParam(name="major") Integer majorId,
                                    @RequestParam(name="study-level") String studyLevel,
                                    @RequestParam Float gpa,
                                    @RequestParam(name="start-year") Integer startYear,
                                    @RequestParam Integer credits, Model model, HttpSession session){
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";
        this.studentRepository.createStudent(username,password,name,surname,dateOfBirth,address,phone,email
                                            ,countryId, StudyType.valueOf(studyLevel),gpa,credits,majorId
                                            ,facultyId,startYear);
        return "redirect:/login";
    }

    @GetMapping("/register-as-member")
    public String getRegisterAsMemberPage(Model model,HttpSession session) {
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";
        Iterable<Country> countries = this.globalRepository.findAllCountries();
        Iterable<Organization> organizations = this.globalRepository.findAllOrganizations();
        model.addAttribute("countries",countries);
        model.addAttribute("organizations",organizations);
        model.addAttribute("bodyContent", "register-as-member");
        return "master-template";
    }
    @PostMapping("/register-as-member")
    public  String registerAsMember(@RequestParam String username,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    @RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam(name = "date-of-birth") LocalDate dateOfBirth,
                                    @RequestParam String address,
                                    @RequestParam String phone,
                                    @RequestParam(name="country") Integer memCountryId,
                                    @RequestParam(name="organization") Integer organizationId,
                                    Model model,HttpSession session){
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";

        session.setAttribute("memberSessionObject"
                ,new MemberSessionObject(username,email,password,name,surname,dateOfBirth,address
                        ,phone,memCountryId,organizationId));

//        this.memberRepository.createMember(username,password,name,surname,dateOfBirth,address,phone,
//                                            email,countryId,organizationId);
        return "redirect:/register-as-member/second-step";
    }
    @GetMapping("/register-as-member/second-step")
    public String getRegisterAsMemberSecoundStepPage(Model model,HttpSession session) {
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";
        MemberSessionObject memberSessionObject = (MemberSessionObject) session.getAttribute("memberSessionObject");
        if(memberSessionObject == null)
            return "redirect:/register-as-member";
        Integer orgId = memberSessionObject.getOrganizationId();
        Iterable<Country> countries = this.globalRepository.findAllCountriesThatHaveCommittesByOrganization(orgId);
        model.addAttribute("countries",countries);
        model.addAttribute("bodyContent", "register-as-member-second-step");
        return "master-template";
    }
    @PostMapping("/register-as-member/second-step")
    public String registerAsMemberSecoundStepPage(@RequestParam(name = "com-country-id") Integer comCountryId, Model model,HttpSession session) {
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";
        MemberSessionObject mem = (MemberSessionObject) session.getAttribute("memberSessionObject");
        if(mem == null)
            return "redirect:/register-as-member";
        this.memberRepository.createMember(mem.getUsername(),mem.getPassword(),mem.getName(),mem.getSurname(),
                                            mem.getDateOfBirth(),mem.getAddress(),mem.getPhone(),mem.getEmail()
                                            ,mem.getMemCountryId(),mem.getOrganizationId(),comCountryId);

        return "redirect:/login";
    }


}
