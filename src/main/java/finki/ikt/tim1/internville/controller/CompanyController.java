package finki.ikt.tim1.internville.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CompanyController {
    @GetMapping("/company")
    public String getAboutUsPage(Model model){
        model.addAttribute("bodyContent", "add-company");
        return "master-template";
    }
}