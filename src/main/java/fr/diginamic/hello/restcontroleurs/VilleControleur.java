package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/api/villes")
public class VilleControleur {


    @Autowired
    private VilleService villeService;


    //  MÉTHODE GET POUR OBTENIR TOUTES LES VILLES
    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.extractVilles();
    }


    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON ID
    @GetMapping(path = "/id/{id}")
    public Ville getVilleById(@PathVariable int id) {
        return villeService.extractVille(id);
    }

    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public Ville getVilleByName(@PathVariable String name) {
        return villeService.extractVille(name);
    }



    //  MÉTHODE POST POUR AJOUTER UNE VILLE
    @PostMapping(path = "/add")
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville, BindingResult bindingResult) {

        if (ville == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        villeService.insertVille(ville);
        return ResponseEntity.ok("Ville insérée avec succès : " + ville.getNom());
    }


    //  MÉTHODE PUT POUR EDITER UNE VILLE
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<String> updateVille(@Valid @PathVariable int id, @RequestBody Ville ville, BindingResult bindingResult) {

        if (ville == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Ville villeAModif = villeService.extractVille(id);

        if (villeAModif == null) {
            return ResponseEntity.notFound().build();
        }


        villeService.modifierVille(id, ville);
        return ResponseEntity.ok("Ville mise à jour avec succès");
    }


    //  MÉTHODE DELETE POUR SUPPRIMER UNE VILLE
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Ville ville = villeService.extractVille(id);

        if (ville == null) {
            return ResponseEntity.notFound().build();
        }

        villeService.supprimerVille(id);
        return ResponseEntity.ok("Ville supprimée avec succès : " + ville.getNom());
    }


}
