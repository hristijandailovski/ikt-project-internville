package finki.ikt.tim1.internville.controller;

import finki.ikt.tim1.internville.model.CompaniesFilter;
import finki.ikt.tim1.internville.model.Country;
import jakarta.servlet.http.HttpSession;
import finki.ikt.tim1.internville.model.CompanyView;
import finki.ikt.tim1.internville.model.UserCredentials;
import finki.ikt.tim1.internville.repository.GlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        CompaniesFilter filter = (CompaniesFilter) session.getAttribute("companiesFilter");
        Iterable<CompanyView> companies = null;
        Iterable<Country> countries = this.globalRepository.findAllCountries();
        if(filter != null){
            companies = this.globalRepository.findAllCompaniesViewOnPageFiltered(pageNumber,filter.getOrderType(),filter.getOrderCriteria(),filter.getCountryId());
        }else {
            companies = this.globalRepository.findAllCompaniesViewOnPage(pageNum);
        }
        model.addAttribute("filter",filter);
        model.addAttribute("pageNumber",pageNum);
        model.addAttribute("companiesView",companies);
        model.addAttribute("countries",countries);
        model.addAttribute("bodyContent", "companies");
        model.addAttribute("userCredentials", userCredentials);

        return "master-template";
    }
    @PostMapping
    public String setFilterObjectInSessionObject(@RequestParam(name="country-id") Integer countryId,
                                                 @RequestParam(name="order-criteria") Integer orderCriteria,
                                                 @RequestParam(name="order-type") Integer orderType,
                                                 HttpSession session, Model model){
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        if (userCredentials.getType().equals("student")) {
            return "redirect:/offers";
        }
        CompaniesFilter filter = new CompaniesFilter(countryId, orderType, orderCriteria);
        session.setAttribute("companiesFilter",filter);
        return "redirect:/companies/1";

    }

    @GetMapping("/{id}/add-offer")
//    @PreAuthorize("hasRole('ROLE_MEMBER')") - So security delot ke bide ovozmozeno
    public String addOfferPage(@PathVariable Long id, Model model) {
        //id na koja kompanija, dodavame offer
        model.addAttribute("bodyContent", "add-offer");
        return "master-template";
    }

}
