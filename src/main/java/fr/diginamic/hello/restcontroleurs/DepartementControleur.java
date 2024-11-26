package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    //  MÉTHODE GET POUR OBTENIR TOUT LES DEPARTEMENT
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.extractDepartements();
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON ID
    @GetMapping(path = "/id/{id}")
    public Departement getDepartementById(@PathVariable int id) {
        return departementService.extractDepartement(id);
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public Departement getDepartementByName(@PathVariable String name) {
        return departementService.extractDepartement(name);
    }

    //  MÉTHODE POST POUR OBTENIR LES N VILLES LES PLUS PEUPLÉES D'UN DEPARTEMENT
    @GetMapping(path = "/{id}/most-populated-villes")
    public List<Ville> getMostPopulatedVille(@PathVariable int id, @RequestParam int nombre) {
        return departementService.getMostPopulatedVilles(id, nombre);
    }

    //  MÉTHODE POST POUR OBTENIR LES  VILLES AYANT UNE POPULATION COMPRISE ENTRE UN MIN ET UN MAX
    @GetMapping("/id/{id}/villes-pops")
    public List<Ville> getVillesPopMinMaxFromDep(@PathVariable int id, @RequestParam int min, @RequestParam int max) {
        return departementService.extractPopMinMaxFromDep(id, min, max);
    }


    //  MÉTHODE POST POUR AJOUTER UN DÉPARTEMENT
    @PostMapping(path = "/add")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody Departement departement, BindingResult bindingResult) {

        if (departement == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        departementService.insertDepartement(departement);
        return ResponseEntity.ok("Département insérée avec succès : " + departement.getNom());

    }

    //  MÉTHODE PUT POUR EDITER UN DEPARTEMENT
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<String> updateDepartement(@Valid @PathVariable int id, @RequestBody Departement departement, BindingResult bindingResult) {

        if (departement == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Departement departementAModif = departementService.extractDepartement(id);

        if (departementAModif == null) {
            return ResponseEntity.notFound().build();
        }

        departementService.modifierDepartement(id, departement);
        return ResponseEntity.ok("Département mise à jour avec succès");

    }

    //  MÉTHODE DELETE POUR SUPPRIMER UN DEPARTEMENT
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Departement departement = departementService.extractDepartement(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        departementService.supprimerDepartement(id);
        return ResponseEntity.ok("Département supprimée avec succès : " + departement.getNom());
    }


}
