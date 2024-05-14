package finki.ikt.tim1.internville.controller;

import jakarta.servlet.http.HttpSession;
import finki.ikt.tim1.internville.model.CompanyView;
import finki.ikt.tim1.internville.model.UserCredentials;
import finki.ikt.tim1.internville.repository.GlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompaniesController {
    private final GlobalRepository globalRepository;

    @Autowired
    public CompaniesController(GlobalRepository globalRepository) {
        this.globalRepository = globalRepository;
    }

    @GetMapping(value={"","/{pageNumber}"})
    public String companiesPage(@PathVariable(required = false) Integer pageNumber, HttpSession session, Model model) {
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (userCredentials.getType().equals("student")) {
            return "redirect:/offers";
        }
        Integer pageNum = (pageNumber == null || pageNumber <= 0) ? Integer.valueOf(1) : pageNumber;
        Iterable<CompanyView> companies = this.globalRepository.findAllCompaniesViewOnPage(pageNum);
        model.addAttribute("pageNumber",pageNum);
        model.addAttribute("companiesView",companies);
        model.addAttribute("bodyContent", "companies");
        model.addAttribute("userCredentials", userCredentials);

        return "master-template";
    }

    @GetMapping("/{id}/add-offer")
//    @PreAuthorize("hasRole('ROLE_MEMBER')") - So security delot ke bide ovozmozeno
    public String addOfferPage(@PathVariable Long id, Model model) {
        //id na koja kompanija, dodavame offer
        model.addAttribute("bodyContent", "add-offer");
        return "master-template";
    }

}
