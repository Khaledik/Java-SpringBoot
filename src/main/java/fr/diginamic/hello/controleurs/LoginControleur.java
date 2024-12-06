package fr.diginamic.hello.controleurs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControleur {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
