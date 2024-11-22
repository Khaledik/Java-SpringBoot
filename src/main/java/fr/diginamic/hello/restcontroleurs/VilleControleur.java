package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {
        List<Ville> villes = villeService.getVilles();

        for (Ville v : villes) {
            if (v.getNom().equals(ville.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe déjà");
            }
        }



        villeService.insertVille(ville);
        return ResponseEntity.ok("Ville insérée avec succès");
    }
}
