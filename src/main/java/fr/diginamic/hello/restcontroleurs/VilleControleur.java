package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villes")
public class VilleControleur {


    @Autowired
    private VilleService villeService;


    //  MÉTHODE GET POUR OBTENIR TOUTES LES VILLES
    @GetMapping
    public List<Ville> getAllVilles() {
        ResponseEntity.ok();
        return villeService.getVilles();
    }


    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getVille(@PathVariable int id) {
        Ville ville = null;

        for (Ville v : villeService.getVilles()) {
            if (v.getId() == id) {
                ville = v;

            }
        }

        if (ville == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(ville);
    }


    //  MÉTHODE POST POUR AJOUTER UNE VILLE
    @PostMapping(path = "/add")
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {

        for (Ville v : villeService.getVilles()) {
            if (v.getId() == ville.getId()) {
                return ResponseEntity.badRequest().body("La ville existe déjà");
            }
        }

        villeService.insertVille(ville);
        return ResponseEntity.ok("Ville insérée avec succès : " + ville.getNom());
    }


    //  MÉTHODE PUT POUR EDITER UNE VILLE
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<String> updateVille(@RequestBody Ville ville) {

        if (ville == null) {
            return ResponseEntity.badRequest().build();
        }

        for (Ville v : villeService.getVilles()) {
            if (v.getId() == ville.getId()) {

                v.setId(ville.getId());
                v.setNom(ville.getNom());
                v.setNbHabitants(ville.getNbHabitants());

            } else {
                return ResponseEntity.notFound().build();
            }
        }


        return ResponseEntity.ok("Ville mise à jour avec succès");
    }


    //  MÉTHODE DELETE POUR SUPPRIMER UNE VILLE
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Ville ville = null;

        for (Ville v : villeService.getVilles()) {
            if (v.getId() == id) {
                ville = v;
            }
        }

        if (!villeService.getVilles().contains(ville)) {
            return ResponseEntity.notFound().build();
        }

        villeService.removeVille(ville);
        return ResponseEntity.ok("Ville supprimée avec succès : " + ville.getNom());
    }


}
