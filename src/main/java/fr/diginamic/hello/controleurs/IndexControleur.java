package fr.diginamic.hello.controleurs;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControleur {

    @GetMapping
    public String index() {
        return "index";
    }

}