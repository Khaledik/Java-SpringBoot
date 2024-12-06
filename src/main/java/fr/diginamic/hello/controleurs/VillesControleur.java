package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VillesControleur {

    @Autowired
    VilleService villeService;


    @GetMapping("/villes")
    public String villes(Model model) {

        List<Ville> villes = villeService.extractVilles();

        model.addAttribute("villes", villes);


        return "ville/villes";
    }


    @PostMapping("/remove-ville/{id}")
    public String removeVille(@PathVariable int id) {
        villeService.supprimerVille(id);
        return "redirect:/villes";
    }


}
