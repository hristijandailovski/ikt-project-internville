package finki.ikt.tim1.internville.controller;

import jakarta.servlet.http.HttpSession;
import finki.ikt.tim1.internville.model.UserCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping(value = {"/","/home"})
    public String getHomePage(Model model, HttpSession session){
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        model.addAttribute("bodyContent", "home");
        model.addAttribute("userCredentials", userCredentials);
        return "master-template";
    }

    @GetMapping("/about-us")
    public String getAboutUsPage(Model model, HttpSession session){
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        model.addAttribute("userCredentials", userCredentials);
        model.addAttribute("bodyContent", "about-us");
        return "master-template";
    }

}
