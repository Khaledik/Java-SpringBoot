package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@RestController
@RequestMapping("/api/villes")
public class VilleControleur {


    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    //  MÉTHODE GET POUR OBTENIR TOUTES LES VILLES
    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.extractVilles();
    }


    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {

        Optional<Ville> ville = villeService.extractVilleById(id);

        if (ville.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ville.get());
    }

    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Ville> getVilleByName(@PathVariable String name) {

        Ville ville = villeService.extractVilleByName(name);

        if (ville == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(ville);
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

        Ville villeAModif = villeService.modifierVille(id, ville);

        if (villeAModif == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Ville mise à jour avec succès : " + villeAModif.getNom());
    }


    //  MÉTHODE DELETE POUR SUPPRIMER UNE VILLE
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Optional<Ville> ville = villeService.supprimerVille(id);

        if (ville.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Ville supprimée avec succès : " + ville.get().getNom());
    }

    // REQUÊTES PERSONNALISÉ REPOSITORY JPA

    // Recherche de toutes les villes dont le nom commence par une chaine de caractères données
    @GetMapping(path = "/start-with/{nom}")
    public ResponseEntity<Object> getVillesStartWith(@PathVariable String nom) {

        List<Ville> villes = villeService.extractVillesStartWith(nom);

        if (villes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes dont la population est supérieure à min (paramètre de type int)
    @GetMapping(path = "/pop-min")
    public ResponseEntity<Object> getVillesGreaterThan(@RequestParam int min) {

        List<Ville> villes = villeService.extractVillesGreaterThan(min);

        if (villes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes dont la population est supérieure à min et inférieure à max.
    @GetMapping(path = "/pop-min-max")
    public ResponseEntity<Object> getVillesBetween(@RequestParam int min, @RequestParam int max) {


        List<Ville> villes = villeService.extractVillesBetween(min, max);

        if (villes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes d’un département dont la population est supérieure à min (paramètre de type int)
    @GetMapping(path = "/departement/{code}/pop-dep-min")
    public ResponseEntity<Object> getVillesByDepartementGreaterThan(@PathVariable String code, @RequestParam int min) {

        Departement departement = departementService.extractDepartementByCode(code);
        List<Ville> villes = villeService.extractVillesByDepartementGreaterThan(departement.getId(), min);

        if (departement == null || villes.isEmpty()) {
            ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes d’un département dont la population est supérieure à min et inférieure à max.
    @GetMapping(path = "/departement/{code}/pop-dep-min-max")
    public ResponseEntity<Object> getVillesByDepartementBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) {

        Departement departement = departementService.extractDepartementByCode(code);
        List<Ville> villes = villeService.extractVillesByDepartementBetween(departement.getId(), min, max);

        if (departement == null || villes.isEmpty()) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(villes);

    }

    //  Recherche des n villes les plus peuplées d’un département donné (n est aussi un paramètre)
    @GetMapping(path = "/departement/{code}/top-villes")
    public ResponseEntity<Object> getTopNVillesByDepartement(@PathVariable String code, @RequestParam int n) {

        Departement departement = departementService.extractDepartementByCode(code);

        List<Ville> villes = villeService.extractTopNVillesByDepartement(departement.getId(), n);


        if (departement == null || villes.isEmpty()) {
            ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(villes);
    }
}
