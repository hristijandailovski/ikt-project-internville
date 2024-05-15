package finki.ikt.tim1.internville.controller;

import finki.ikt.tim1.internville.model.UserCredentials;
import finki.ikt.tim1.internville.repository.GlobalRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {
    private final GlobalRepository globalRepository;

    @Autowired
    public LoginController(GlobalRepository globalRepository) {
        this.globalRepository = globalRepository;
    }

    @GetMapping("/login")
    public String getHomePage(HttpSession session, Model model){
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/offers";

        model.addAttribute("bodyContent", "login");
        return "master-template";
    }
    @PostMapping("/login")
    public String getHomePage(HttpSession session,
                              @RequestParam String username,
                              @RequestParam String password, Model model){
        //check wheather the user is already logged in.
        if(session.getAttribute("userCredentials")!=null)
            return "redirect:/home";
        UserCredentials userCredentials = this.globalRepository.findUserCredentialsByUserNameAndPassword(username,password);
        if(userCredentials!=null){
            //Postoi takov korisnik
            //Dodaj go vo HttpSession
            session.setAttribute("userCredentials",userCredentials);
            return "redirect:/offers";
        }else{
            //Ne postoi takov korisnik, neka se obide povtorno
            return "redirect:/login";
        }

//        model.addAttribute("bodyContent", "login");
//        return "master-template";
    }
}
