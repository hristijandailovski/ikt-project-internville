package finki.ikt.tim1.internville.controller;


import jakarta.servlet.http.HttpSession;
import finki.ikt.tim1.internville.model.*;

import finki.ikt.tim1.internville.model.Company;
import finki.ikt.tim1.internville.model.OfferEditView;
import finki.ikt.tim1.internville.model.OfferShortView;

import finki.ikt.tim1.internville.repository.GlobalRepository;
import finki.ikt.tim1.internville.repository.MemberRepository;
import finki.ikt.tim1.internville.repository.OfferRepository;
import finki.ikt.tim1.internville.repository.StudentRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/offers")
public class OffersController {
    private final GlobalRepository globalRepository;
    private final MemberRepository memberRepository;
    private final OfferRepository offerRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public OffersController(GlobalRepository globalRepository, MemberRepository memberRepository,
                            OfferRepository offerRepository, StudentRepository studentRepository) {
        this.globalRepository = globalRepository;
        this.memberRepository = memberRepository;
        this.offerRepository = offerRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = {"", "/{pageNumber}"})
    public String offersPage(@PathVariable(required = false) Integer pageNumber, Model model, HttpSession session) {
        //Zemi gi kredencijalite na korisnikot od sesijata
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        Integer pageNum = (pageNumber == null || pageNumber <= 0) ? Integer.valueOf(1) : pageNumber;
        OffersFilter filter = (OffersFilter) session.getAttribute("offersFilter");
        Iterable<Country> countries = this.globalRepository.findAllCountries();
        Iterable<String> fields = this.offerRepository.findAllOfferFields();
        Iterable<OfferShortView> offerViews = null;
        if (filter != null) {
            if (userCredentials.getType().equals("student")) {
                offerViews = this.globalRepository.findAllActiveOffersFiltered(pageNum,filter);
            } else {
                offerViews = this.memberRepository.findAllOffersByMemberFiltered(userCredentials.getId(), pageNum,filter);
            }
        } else {
            if (userCredentials.getType().equals("student")) {
                offerViews = this.globalRepository.findAllActiveOffers(pageNum);
            } else {
                offerViews = this.memberRepository.findAllOffersByMember(userCredentials.getId(), pageNum);
            }
        }

        model.addAttribute("offerViews", offerViews);
        model.addAttribute("pageNumber", pageNum);
        model.addAttribute("currentDate",LocalDate.now());
        model.addAttribute("fields",fields);
        model.addAttribute("countries",countries);
        model.addAttribute("filter",filter);
        model.addAttribute("userCredentials", userCredentials);
        model.addAttribute("bodyContent", "offers");
        return "master-template";
    }
    @PostMapping
    public String setFilterObjectInSessionObject(@RequestParam(name ="country-id") Integer countryId,
                                                 @RequestParam(name="field") String field,
                                                 @RequestParam(name="start-date") String startDate,
                                                 @RequestParam(name="order-criteria") Integer orderCriteria,
                                                 @RequestParam(name="order-type") Integer orderType,
                                                 HttpSession session, Model model){
        //Regardless of type of user that is logged in, it is same for all type of users (student and member)
        OffersFilter filter = new OffersFilter(countryId,field.equals("all") ? null : field,startDate.isEmpty() ? null : LocalDate.parse(startDate),orderType,orderCriteria);
        session.setAttribute("offersFilter",filter);
        return "redirect:/offers/1";

    }

    @GetMapping("/{id}/detail-offer")
    public String detailOfferSummaryPage(@PathVariable Integer id, Model model, HttpSession session) {
        //metodov mozhe da se pristapi od bilo koj user
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        OfferEditView offerDetailView = this.memberRepository.findOfferEditView(id);
        model.addAttribute("bodyContent", "detail-offer");
        model.addAttribute("offerDetailView", offerDetailView);
        model.addAttribute("userCredentials", userCredentials);
        return "master-template";
    }

//    @GetMapping("/add-offer")
////    @PreAuthorize("hasRole('ROLE_MEMBER')") - So security delot ke bide ovozmozeno
//    public String addOfferPage(Model model) {
////        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
////        List<Category> categories = this.categoryService.listCategories();
////        model.addAttribute("manufacturers", manufacturers);
////        model.addAttribute("categories", categories);
//        //model.addAttribute - za firma, za contry
//        model.addAttribute("bodyContent", "add-offer");
//        return "master-template";
//    }

    @GetMapping("/{id}/edit-offer")
    public String editOfferPage(@PathVariable Integer id, Model model, HttpSession session) {
        //Samo member mozhe da pristapi do ovoj metod
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("member"))
            return "redirect:/offers";
//        if(this.productService.findById(id).isPresent()){
//            Product product = this.productService.findById(id).get();
//            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
//            List<Category> categories = this.categoryService.listCategories();
//            model.addAttribute("manufacturers", manufacturers);
//            model.addAttribute("categories", categories);
//            model.addAttribute("product", product);

        OfferEditView offerEditView = this.memberRepository.findOfferEditView(id);
        model.addAttribute("userCredentials", userCredentials);
        model.addAttribute("bodyContent", "edit-offer");
        model.addAttribute("offerEditView", offerEditView);
        return "master-template";
    }

    @PostMapping("/{id}/edit-offer")
    public String editOffer(@PathVariable Integer id,
                            @RequestParam String field,
                            @RequestParam String requirements,
                            @RequestParam String responsibilities,
                            @RequestParam String benefits,
                            @RequestParam Integer salary,
                            @RequestParam(name = "start-date") LocalDate startDate,
                            @RequestParam Integer duration,
                            @RequestParam(name = "acc-phone") String accPhone,
                            @RequestParam(name = "acc-email") String accEmail,
                            @RequestParam(name = "acc-address") String accAddress,
                            @RequestParam(name = "acc-description") String accDescription,
                            Model model,
                            HttpSession session) {
        //Samo member mozhe da pristapi na ovoj method
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("member"))
            return "redirect:/offers";
        this.memberRepository.updateOfferAndAccommodation(userCredentials.getId(), id, requirements, responsibilities, benefits, salary,
                field, startDate, duration, accPhone, accEmail, accAddress, accDescription);
        model.addAttribute("userCredentials", userCredentials);
        return "redirect:/offers";
    }

    @GetMapping("/add-offer")
    public String addOfferPage(Model model, HttpSession session) {
        //Samo member mozhe da pristapi na ovoj method
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("member"))
            return "redirect:/offers";

        Iterable<Company> companies = this.globalRepository.findAllCompanies();
        model.addAttribute("companies", companies);
        model.addAttribute("userCredentials", userCredentials);
        model.addAttribute("bodyContent", "add-offer");
        return "master-template";
    }

    @PostMapping("/add-offer")
    public String addOffer(@RequestParam(name = "company-id") Integer companyId,
                           @RequestParam String field,
                           @RequestParam String requirements,
                           @RequestParam String responsibilities,
                           @RequestParam Integer salary,
                           @RequestParam String benefits,
                           @RequestParam(name = "start-date") LocalDate startDate,
                           @RequestParam Integer duration,
                           Model model,
                           HttpSession session) {
        //Samo member mozhe da pristapi na ovoj method
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("member"))
            return "redirect:/offers";
        this.memberRepository.createOffer(requirements, responsibilities, benefits, salary, field, startDate, duration, userCredentials.getId(), companyId);
        return "redirect:/offers";
    }

    @GetMapping("/{id}/delete-offer")
    public String deleteOffer(@PathVariable Integer id, HttpSession session) {
        //Samo member mozhe da pristapi na ovoj method
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("member"))
            return "redirect:/offers";
        this.memberRepository.deleteOffer(userCredentials.getId(), id);
        return "redirect:/offers";
    }

//        return "redirect:/offers?error=ProductNotFound"; }

    @GetMapping("/{id}/applications")
    public String numberOfApplicationsPage(@PathVariable Long id, Model model) {
        model.addAttribute("bodyContent", "numOfApp");
        return "master-template";
    }


    @GetMapping("/{id}/apply")
    public String applyForOffer(@PathVariable Integer id, HttpSession session) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (!userCredentials.getType().equals("student"))
            return "redirect:/offers";
        this.studentRepository.applyForOffer(userCredentials.getId(), id);
        return "redirect:/applications";
    }

    @GetMapping("/{offerId}/applicants")
    public String viewApplicants(@PathVariable Integer offerId, HttpSession session, Model model) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (userCredentials.getType().equals("student"))
            return "redirect:/offers";
        Iterable<ApplicantView> applicantView = this.memberRepository.findAllApplicantByOffer(offerId);
        model.addAttribute("userCredentials", userCredentials);
        model.addAttribute("applicants", applicantView);
        model.addAttribute("bodyContent", "applied-students");
        return "master-template";
    }

    @GetMapping("/{offerId}/applicants/{studentId}")
    public String acceptAplicant(@PathVariable Integer offerId,
                                 @PathVariable Integer studentId,
                                 Model model, HttpSession session) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (userCredentials.getType().equals("student")) {
            return "redirect:/offers";
        }
        this.memberRepository.acceptApplicant(studentId, offerId);
        return "redirect:/offers/" + offerId.toString() + "/applicants";
    }

    @GetMapping("/{offerId}/applicants/{studentId}/update_status")
    public String updateApplicant(@PathVariable Integer offerId,
                                  @PathVariable Integer studentId,
                                  Model model, HttpSession session) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (userCredentials.getType().equals("student")) {
            return "redirect:/offers";
        }
        this.memberRepository.updateApplicant(offerId, studentId);
        return "redirect:/offers/" + offerId.toString() + "/applicants";

    }


}
