package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DepartementsControleur {

    @Autowired
    DepartementService departementService;


    @GetMapping("/departements")
    public String villes(Model model) {

        List<Departement> departements = departementService.extractDepartements();

        model.addAttribute("departements", departements);

        return "departement/departements";
    }

    @PostMapping("/remove-dep/{id}")
    public String removeVille(@PathVariable int id) {
        departementService.supprimerDepartement(id);
        return "redirect:/departements";
    }


}
