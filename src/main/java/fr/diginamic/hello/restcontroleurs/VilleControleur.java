package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleService villeService;


    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = villeService.getVilles();
        return villes;
    }

}
